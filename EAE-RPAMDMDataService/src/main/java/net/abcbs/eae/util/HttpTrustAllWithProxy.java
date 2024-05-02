package net.abcbs.eae.util;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;

import org.apache.hc.client5.http.auth.AuthCache;
import org.apache.hc.client5.http.auth.AuthScope;
import org.apache.hc.client5.http.auth.CredentialsProvider;
import org.apache.hc.client5.http.impl.DefaultRedirectStrategy;
import org.apache.hc.client5.http.impl.auth.BasicAuthCache;
import org.apache.hc.client5.http.impl.auth.BasicScheme;
import org.apache.hc.client5.http.impl.auth.CredentialsProviderBuilder;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.impl.routing.DefaultProxyRoutePlanner;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.client5.http.protocol.HttpClientContext;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactoryBuilder;
import org.apache.hc.client5.http.ssl.TrustAllStrategy;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HttpTrustAllWithProxy {
	private static final Logger logger = LogManager.getLogger(HttpTrustAllWithProxy.class);
	
	private HttpTrustAllWithProxy() {
		throw new UnsupportedOperationException("This class cannot be instantiated");
	}
	
	public static CloseableHttpClient initHttpClient() {
		String proxyHost = "wsaproxy.abcbs.net";
		int proxyPort = 8080;
		
		HttpHost proxy = new HttpHost(proxyHost, proxyPort);

		RetrieveIsSharedProperties prop = new RetrieveIsSharedProperties();
		String proxyUsername = prop.getProxyUsername();
		String proxyPassword = prop.getProxyPassword();
		
		CredentialsProvider credentialsProvider = CredentialsProviderBuilder.create()
				.add(new AuthScope(proxy), proxyUsername, proxyPassword.toCharArray()).build();
		
		DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
		
		// Create AuthCache instance 
		AuthCache authCache = new BasicAuthCache(); 
		// Generate BASIC scheme object and add it to the local auth cache
		BasicScheme basicAuth = new BasicScheme(); 
		authCache.put(proxy, basicAuth); 
		HttpClientContext context = HttpClientContext.create(); 
		context.setCredentialsProvider(credentialsProvider); 
		context.setAuthCache(authCache); 
		
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
					.setDefaultCredentialsProvider(credentialsProvider)
					.setProxy(proxy)
					.setRoutePlanner(routePlanner)
					.setRedirectStrategy(new DefaultRedirectStrategy())
					.setConnectionManager(cm)
					.evictExpiredConnections()
					.build();

		} catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
			logger.error(e.getMessage());
		}
		return null;
	}
}
