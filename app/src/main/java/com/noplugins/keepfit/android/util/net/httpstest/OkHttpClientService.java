package com.noplugins.keepfit.android.util.net.httpstest;

import java.io.IOException;
import java.util.HashMap;

import com.alibaba.fastjson.JSONObject;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpClientService {
	
	private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static OkHttpClient client = new OkHttpClient();

    
  /*  private SSLContext getSLLContext() {
        SSLContext sslContext = null;
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            InputStream certificate = mContext.getAssets().open("gdroot-g2.crt");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            String certificateAlias = Integer.toString(0);
            keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));
            sslContext = SSLContext.getInstance("TLS");
            final TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return sslContext;
    }
*/
    
    public static Response post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response;
    }

    public static Response get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response;
    }
    
    public static Response httpsPost2(String url, String json)throws IOException{
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .headers(Headers.of(new HashMap<>()))
                .post(body)
                .build();
//        OkHttpClient client = OKHttpClientBuilder.getUnsafeOkHttpClient();
        return OKHttpClientBuilder.getUnsafeOkHttpClient()
        .build()
        .newCall(request).execute();
    
    }
    
    public static Response httpspost(String url, String json) throws IOException {
    	
    	/*MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        String url = "https://www.baidu.com";
        String json = "{}";
        Request request = new Request.Builder()
                .url(url)
                .headers(Headers.of(new HashMap<>()))
                .post(RequestBody.create(JSON, json))
                .build();
        OKHttpClientBuilder.buildOKHttpClient()
                .build()
                .newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        System.out.println("onFailure()");
                        e.printStackTrace();
                    }
 
                    @Override
                    public void onResponse(Call call, Response response) {
                        System.out.println("onResponse()");
                    }
                });*/
        
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .headers(Headers.of(new HashMap<>()))
                .post(body)
                .build();
        
	  return OKHttpClientBuilder.buildOKHttpClient()
	        .build()
	        .newCall(request).execute();
//        .enqueue(new Callback() {	
//            @Override
//            public void onFailure(Call call, IOException e) {
//                System.out.println("onFailure()");
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) {
//                System.out.println("onResponse()");
//            }
//        });
//         return null;
//        Response response = client.newCall(request).execute();
//        return response;
    }
    public static void test2() {
//		System.setProperty("https.protocols", "TLSv1.2,TLSv1.1,SSLv3");
		String url="https://test2.noplugins.com/api/gym-service/getCocahTeacherSetTime";//"https://localhost:8888/api/gym-service/getCocahTeacherSetTime";
//		http://47.100.40.6:8888
//		String url="https://47.100.40.6:8888/api/gym-service/getCocahTeacherSetTime";//"https://localhost:8888/api/gym-service/getCocahTeacherSetTime";
		String json="{'teacherNum':'CUS19111558697621'}";//JSONObject.toString("teacherNum", "CUS19111558697621");
		 JSONObject jsonObjectRequest = new JSONObject();
		 jsonObjectRequest.put("teacherNum","CUS19111558697621");
		try {
			Response res=OkHttpClientService.httpspost(url, jsonObjectRequest.toJSONString());
			System.out.println(res.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
    public static void main(String[] args) throws Exception{
		test2();
    }
  }/*
	
	//通过ServiceIml调用okHttp方式发送post请求，得到返回的json数据
	public Map<String,String> checkUsernameAndPassword(String username, String password, String host) {
	        Response response = null;
	        StringBuilder url = new StringBuilder();
	
	        //临时数据类型
	        JSONObject jsonObjectTemp = new JSONObject();
	        JSONObject[] jsonArrayTemp = new JSONObject[1];
	        JSONArray jsonResultArrayTemp = null;
	        String StringTemp;
	
	        JSONObject jsonObjectRequest = new JSONObject();
	        Map<String,String> resultMap = new HashMap<>();
	
	
	        try {
	            //构建指定json格式，使用post方式访问接口，格式模板
	　　　　　　　　{
	　　　　　　　　　　"loginInfos":[
	　　　　　　　　　　　　{
	
	　　　　　　　　　　　　　　"userName":"haieradmin",
	　　　　　　　　　　　　　　"passwd":"Haier,1231",
	　　　　　　　　　　　　　　"host":"10.135.106.249"
	　　　　　　　　　　　　}
	　　　　　　　　　]
	　　　　　　　}   
	
	
	            url.setLength(0);
	            url.append(ToolConfig.MACHINE_MONITOR_URL).append("init");
	            jsonObjectTemp.put("userName",username);
	            jsonObjectTemp.put("passwd", password);
	            jsonObjectTemp.put("host", host);
	            jsonArrayTemp[0] = jsonObjectTemp;
	            jsonObjectRequest.put("loginInfos",jsonArrayTemp);
	            response = HttpUtils.post(url.toString(), jsonObjectRequest.toString());
	
	　　　　　　　//解析返回的json数据
	            StringTemp = response.body().string();
	            jsonObjectTemp = (JSONObject) JSONObject.parse(StringTemp);
	            jsonResultArrayTemp = (JSONArray) jsonObjectTemp.get("results");
	            resultMap.put("status",jsonResultArrayTemp.getJSONObject(0).getString("status"));
	            resultMap.put("message",jsonResultArrayTemp.getJSONObject(0).getString("message"));
	
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        	return resultMap;
	    }
	}
	
	//拿到返回的map进行数据的判断
	Map<String,String> result = machineIpService.checkUsernameAndPassword(username,password,ip);
	if(result.get("status").equals("FAILED")){
	    return Response.fail(Constant.CODE_NEGATIVE_ONE,result.get("message"));
	}
}*/
