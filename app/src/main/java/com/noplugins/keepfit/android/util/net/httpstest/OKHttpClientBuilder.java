package com.noplugins.keepfit.android.util.net.httpstest;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
 
/**
 * Author: comg
 * Date: 2019/2/15
 */
public class OKHttpClientBuilder {
	
    public static OkHttpClient.Builder buildOKHttpClient() {
    	    	
        try {
            TrustManager[] trustAllCerts =buildTrustManagers();
            final SSLContext sslContext = SSLContext.getInstance("SSL");//("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
 
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier((hostname, session) -> true);
            return builder;
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
            return new OkHttpClient.Builder();
        }
    }
    
   public static OkHttpClient.Builder getUnsafeOkHttpClient() {
	   try {
           // Create a trust manager that does not validate certificate chains
           final TrustManager[] trustAllCerts = new TrustManager[] {
                   new X509TrustManager() {
                       @Override
                       public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                       }

                       @Override
                       public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                       }

                       @Override
                       public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                           return new java.security.cert.X509Certificate[]{};
                       }
                   }
           };

           // Install the all-trusting trust manager
           final SSLContext sslContext = SSLContext.getInstance("SSL");
           sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
           // Create an ssl socket factory with our all-trusting manager
           final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

           OkHttpClient.Builder builder = new OkHttpClient.Builder();
           builder.sslSocketFactory(sslSocketFactory);
           builder.hostnameVerifier(new HostnameVerifier() {
               @Override
               public boolean verify(String hostname, SSLSession session) {
                   return true;
               }
           });

//           OkHttpClient okHttpClient = builder.build();
           return builder;
       } catch (Exception e) {
           throw new RuntimeException(e);
       }
   }
  /*  //信任所有服务器地址
    okhttpClient.hostnameVerifier(new HostnameVerifier() {
        @Override
        public boolean verify(String s, SSLSession sslSession) {
            //设置为true
            return true;
        }
    });*/
    
    /**
     * 绕过验证
     *
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
   /* public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sc = SSLContext.getInstance("SSLv3");

        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };

        sc.init(null, new TrustManager[] { trustManager }, null);
        return sc;
    }*/
    
  //创建管理器
/*    TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
        @Override
        public void checkClientTrusted(
                java.security.cert.X509Certificate[] x509Certificates,
                String s) throws java.security.cert.CertificateException {
        }

        @Override
        public void checkServerTrusted(
                java.security.cert.X509Certificate[] x509Certificates,
                String s) throws java.security.cert.CertificateException {
        }

        @Override
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[] {};
        }
    } };*/
 /*   try {
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

        //为OkHttpClient设置sslSocketFactory
        okhttpClient.sslSocketFactory(sslContext.getSocketFactory());

    } catch (Exception e) {
        e.printStackTrace();
    }


}*/
    private static TrustManager[] buildTrustManagers() {
    	
    	
    	/*
    	 * 
    	//创建管理器
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] x509Certificates,
                    String s) throws java.security.cert.CertificateException {
            }
 
            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] x509Certificates,
                    String s) throws java.security.cert.CertificateException {
            }
 
            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[] {};
            }
        } };
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
 
            //为OkHttpClient设置sslSocketFactory
            okhttpClient.sslSocketFactory(sslContext.getSocketFactory());
 
        } catch (Exception e) {
            e.printStackTrace();
        }
 

    	*/
        return new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException{
                      return ;
                    }
 
                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException{
                      return;
                    }
 
                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[]{};
                    }
                }
        };
    }
}