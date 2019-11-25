package com.noplugins.keepfit.android.util.net;

import com.noplugins.keepfit.android.entity.VersionEntity;
import com.noplugins.keepfit.android.util.net.entity.Bean;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

public interface CoachService {
    /**
     * 版本升级
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("versionsCheck")
    Observable<Bean<VersionEntity>> update_version(@Body RequestBody map);
}
