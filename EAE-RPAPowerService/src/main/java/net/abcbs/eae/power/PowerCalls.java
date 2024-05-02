package net.abcbs.eae.power;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.codec.binary.Base64;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;

import net.abcbs.eae.utils.HttpTrustAll;


public class PowerCalls {
	private static final Logger logger = LogManager.getLogger(PowerCalls.class);    

	public Document powerRequest(String powerURL, String powerCredentials, String xml) {
		try {
			CloseableHttpClient httpClient = HttpTrustAll.initHttpClient();
			HttpPost httpPost = new HttpPost(powerURL);
			byte[] encodedAuth = Base64.encodeBase64(
					powerCredentials.getBytes(StandardCharsets.ISO_8859_1));
			httpPost.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + new String(encodedAuth));
			httpPost.addHeader(HttpHeaders.CONTENT_TYPE, "text/xml");

			HttpEntity stringEntity = new StringEntity(xml, ContentType.TEXT_XML);

			httpPost.setEntity(stringEntity);

			logger.info("Sending HTTP request to Power");
			CloseableHttpResponse response = httpClient.execute(httpPost);

			Document doc = null;
			if (response.getCode() == 200) {
				HttpEntity entity = response.getEntity();

				if(entity != null){
					String responseText = EntityUtils.toString(entity);  

					// the response has some unwanted characters so we need to clean it up
					responseText = responseText.replace("&lt;","<");
					responseText = responseText.replace("&gt;",">");
					responseText = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n"+responseText.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>","");

					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
					DocumentBuilder builder = factory.newDocumentBuilder();
					InputStream is = new ByteArrayInputStream(responseText.getBytes());
					doc = builder.parse(is);
					EntityUtils.consume(entity);
				}
			}

			// close connections
			httpClient.close();
			response.close();
			return doc;
		} catch (Exception e) {
			logger.error(e);
			throw new PowerException("Error making API call to POWER: " + e.getLocalizedMessage());
		}
	}
}
