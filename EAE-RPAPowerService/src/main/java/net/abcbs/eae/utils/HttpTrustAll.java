package net.abcbs.eae.utils;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;

import org.apache.hc.client5.http.impl.DefaultRedirectStrategy;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactoryBuilder;
import org.apache.hc.client5.http.ssl.TrustAllStrategy;
import org.apache.hc.core5.ssl.SSLContexts;

public class HttpTrustAll {

	private HttpTrustAll() {
		throw new UnsupportedOperationException("This class cannot be instantiated");
	}
	
	public static CloseableHttpClient initHttpClient() {
		try {
			final SSLContext sslcontext = SSLContexts.custom()
					.loadTrustMaterial(null, new TrustAllStrategy())
					.build();
			final SSLConnectionSocketFactory sslSocketFactory = SSLConnectionSocketFactoryBuilder.create()
					.setSslContext(sslcontext)
					.build();
			final HttpClientConnectionManager cm = PoolingHttpClientConnectionManagerBuilder.create()
					.setSSLSocketFactory(sslSocketFactory)
					.build();
			return HttpClients.custom()
					.setRedirectStrategy(new DefaultRedirectStrategy())
					.setConnectionManager(cm)
					.evictExpiredConnections()
					.build();

		} catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
			e.printStackTrace();
		}
		return null;
	}
}
