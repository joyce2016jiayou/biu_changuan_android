package com.noplugins.keepfit.android.util.net.interceptor;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Created by shiyujia02 on 2017/8/17.
 */

public class LogInterceptor implements Interceptor {
    public String Tag;

    public LogInterceptor(String tag){
        Tag=tag;
        Logger.addLogAdapter(new AndroidLogAdapter());

    }


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        //the requdest url
        String url = request.url().toString();
        //the request method
        String method = request.method();
        long t1 = System.nanoTime();
        Logger.w("网络请求，请求的url："+url);
        Logger.w("网络请求，请求的method："+method);
        Logger.w("网络请求，请求的时间："+t1);
        //the request body
        RequestBody requestBody = request.body();
        if(requestBody!= null) {
            StringBuilder sb = new StringBuilder("Request Body [");
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);
            Charset charset = Charset.forName("UTF-8");
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(charset);
            }
            if(isPlaintext(buffer)){
                sb.append(buffer.readString(charset));
                sb.append(" (Content-Type = ").append(contentType.toString()).append(",")
                        .append(requestBody.contentLength()).append("-byte body)");
            }else {
                sb.append(" (Content-Type = ").append(contentType.toString())
                        .append(",binary ").append(requestBody.contentLength()).append("-byte body omitted)");
            }
            sb.append("]");
            Logger.w("网络请求，请求体："+sb.toString());

        }
        Response response = chain.proceed(request);
        long t2 = System.nanoTime();
        Logger.w("网络请求，返回的时间："+t2);
        ResponseBody body = response.body();

        BufferedSource source = body.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();
        Charset charset = Charset.defaultCharset();
        MediaType contentType = body.contentType();
        if (contentType != null) {
            charset = contentType.charset(charset);
        }
        String bodyString = buffer.clone().readString(charset);

        //打印返回的json
        Logger.e(Tag+"返回的json",bodyString);
        Logger.json(bodyString);
        return response;
    }

    static boolean isPlaintext(Buffer buffer){
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }


}
