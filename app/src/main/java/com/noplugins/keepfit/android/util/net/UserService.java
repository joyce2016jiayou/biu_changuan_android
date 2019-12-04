package com.noplugins.keepfit.android.util.net;

import com.noplugins.keepfit.android.bean.CityCode;
import com.noplugins.keepfit.android.bean.GetCityCode;
import com.noplugins.keepfit.android.bean.GetQuCode;
import com.noplugins.keepfit.android.util.net.entity.Bean;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

public interface UserService {

    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("findAllCity")
    Observable<Bean<CityCode>> get_province(@Body RequestBody json);

    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("findAllCity")
    Observable<Bean<GetCityCode>> get_city(@Body RequestBody json);

    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("findAllCity")
    Observable<Bean<GetQuCode>> get_qu(@Body RequestBody json);


}
