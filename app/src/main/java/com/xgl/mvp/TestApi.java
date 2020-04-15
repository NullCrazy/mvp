package com.xgl.mvp;

import com.xgl.libs.network.respone.BaseResponse;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * @Description: TODO
 * @Author: xingguo.lei@luckincoffee.com
 * @Date: 2020/4/14 18:38
 */
public interface TestApi {

    @POST("Login")
    Observable<BaseResponse<LoginBean>> login(@QueryMap Map<String, String> map);
}
