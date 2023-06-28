package com.samil.stdadt;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class Application implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		trustAllHosts();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}

	private void trustAllHosts() {
		TrustManager[] trustManagers = new TrustManager[] { 
			new X509TrustManager() {
				@Override
				public X509Certificate[] getAcceptedIssuers() {return null;}
				
				@Override
				public void checkClientTrusted(X509Certificate[] certs, String authType) {return;}
	
				@Override
				public void checkServerTrusted(X509Certificate[] certs, String authType) throws CertificateException {
					if(certs == null) {
						throw new IllegalArgumentException("there were no certificates.");
					}
					
					for(X509Certificate c : certs) {
						c.checkValidity();
					}
				}
			}		
		};
		
		// Install the all-trusting trust manager
		try {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustManagers, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
