package com.wolf.lib.okhttpcool.cache;

import com.wolf.lib.okhttpcool.util.NetworkUtils;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 1、修改response缓存设置，忽略服务端缓存控制
 * 2、无网络，直接请求缓存
 * 3、url缓存可设置, 以url+queryString哈希值为key，所以可以通过传递新querykey=timestamp来强刷新数据
 * 只读网络(Cache-Control: no-CacheStrategyUtil),
 * 无存储(Cache-Control: no-store),
 * 缓存时间(Cache-Control: max-age=640000)，没有超出maxAge,不管怎么样都是返回缓存数据，超过了maxAge,发起新的请求获取数据更新，请求失败返回缓存数据。
 * 只读缓存(Cache-Control:public, only-if-cached, max-stale=2419200),没有超过maxStale，不管怎么样都返回缓存数据，超过了maxStale,发起请求获取更新数据，请求失败返回失败
 * <p>
 * 这个拦截器决定要不要做统一缓存,Response header 最终决定缓存读写策略
 */
public class NetworkInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oRequest = chain.request();
        boolean connected = NetworkUtils.isAvailable();
        CacheControl cacheControl = oRequest.cacheControl();

        Request request = oRequest;

        if (connected) {
            Response tryCacheResponse = CacheStrategyUtil.doForNetworkInterceptor(chain, oRequest);
            if (tryCacheResponse != null) {
                return tryCacheResponse;
            } else {
                if (cacheControl.noCache()) {
                    //这里很关键，如果设置Response header也为no-CacheStrategyUtil, 就真的不缓存了，所以这里不做处理，默认会存缓存，等网络不可用时直接返回缓存
                    return chain.proceed(request);
                } else {
                    Response originalResponse = chain.proceed(request);
                    return originalResponse.newBuilder()
                            .removeHeader("Pragma")//清除响应体对Cache有影响的信息
                            .removeHeader("Cache-Control")//清除响应体对Cache有影响的信息
                            .header("Cache-Control", cacheControl.toString())
                            .build();
                }
            }
        } else {
            //如果没有网络，不做处理，直接返回
            return chain.proceed(oRequest);
        }
    }
}