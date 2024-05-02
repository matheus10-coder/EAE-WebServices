package net.abcbs.eae.javabeans;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.utils.Base64;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.net.URIBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.abcbs.eae.exception.RPAMDMDataServiceException;
import net.abcbs.eae.pojo.AffiniteAssignment;
import net.abcbs.eae.pojo.NurseDataFromDb;
import net.abcbs.eae.pojo.NurseLicensureResponse;
import net.abcbs.eae.pojo.RPAMDMDataServiceResponse;
import net.abcbs.eae.pojo.RPAWorkdayServiceRequest;
import net.abcbs.eae.util.HttpTrustAll;
import net.abcbs.eae.util.HttpTrustAllWithProxy;
import net.abcbs.eae.util.RetrieveIsSharedProperties;

public class RPAMDMDataServiceJavaBeans {
	private static final Logger logger = LogManager.getLogger(RPAMDMDataServiceJavaBeans.class);
	private String connectionUrl;
	private String affiniteUrl;
	private String rpaWorkdayServiceUrl;
	private String credentials;

	public RPAMDMDataServiceJavaBeans() {
		try {
			RetrieveIsSharedProperties prop = new RetrieveIsSharedProperties();
			connectionUrl = prop.buildJdbcUrl();
			affiniteUrl = prop.getAffiniteUrl();
			rpaWorkdayServiceUrl = prop.getRpaWorkdayUrl();
			credentials = prop.getProxyUsername() + ":" + prop.getProxyPassword();
		} catch (Exception e) {
			logger.error("Unable to instantiate RPAMDMDataServiceJavaBeans: ", e);
			throw new RPAMDMDataServiceException(e.getLocalizedMessage());
		}
	}

	public List<NurseDataFromDb> nurseDataQuery(String sql) {
		List<NurseDataFromDb> results;
		Connection conn = null;
		QueryRunner run = new QueryRunner();

		logger.info("Setting up database connection");
		logger.trace("JDBC URL - {}", connectionUrl);

		try {
			// this conn variable below was not working for me until I added this line below
			// apparently you don't need to do this but it works!
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection(connectionUrl);

			logger.info("Database connection successfully established");

			// capture database results into array of pojos
			ResultSetHandler<List<NurseDataFromDb>> h = new BeanListHandler<NurseDataFromDb>(NurseDataFromDb.class);
			results = run.query(conn, sql, h);
		} catch (SQLException | ClassNotFoundException e) {
			logger.error("Error trying to execute SQL: ", e);
			throw new RPAMDMDataServiceException(e.getMessage());
		} finally {
			DbUtils.closeQuietly(conn);
		}

		return results;
	}

	public List<NurseLicensureResponse> nurseLicensureQuery(String sql) {
		List<NurseLicensureResponse> results;
		Connection conn = null;
		QueryRunner run = new QueryRunner();

		logger.info("Setting up database connection");
		logger.trace("JDBC URL - {}", connectionUrl);

		try {
			// this conn variable below was not working for me until I added this line below
			// apparently you dont't need to do his but it works!
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection(connectionUrl);

			logger.info("Database connection successfully established");

			// capture database results into array of pojos
			ResultSetHandler<List<NurseLicensureResponse>> h = new BeanListHandler<NurseLicensureResponse>(NurseLicensureResponse.class);
			results = run.query(conn, sql, h);
		} catch (SQLException | ClassNotFoundException e) {
			logger.error("Error trying to execute SQL: ", e);
			throw new RPAMDMDataServiceException(e.getMessage());
		} finally {
			DbUtils.closeQuietly(conn);
		}
		return results;
	}

	public AffiniteAssignment[] getNurseWorkload(String username) {
		AffiniteAssignment[] result = null;

		// set up get request with all the query parameters
		HttpGet httpGet = new HttpGet(affiniteUrl);
		List<NameValuePair> nvps = new ArrayList<>();
		nvps.add(new BasicNameValuePair("customerId", "16"));
		nvps.add(new BasicNameValuePair("productId", "2"));
		nvps.add(new BasicNameValuePair("OrgTeam", ""));
		nvps.add(new BasicNameValuePair("State", ""));
		nvps.add(new BasicNameValuePair("County", ""));
		nvps.add(new BasicNameValuePair("UserType", ""));
		nvps.add(new BasicNameValuePair("FirstName", ""));
		nvps.add(new BasicNameValuePair("LastName", ""));
		nvps.add(new BasicNameValuePair("UserName", username));
		nvps.add(new BasicNameValuePair("isEmployee", "true"));

		// code below isn't the prettiest
		// but I can't get the proxy settings working on my end.
		// works on server though! So this solution,
		// while not great, should work both locally and on the server
		// if someone figures out proxy settings that work both locally and 
		// on the server, please feel free to refactor this code
		int maxRetries = 1; // change to property at some point maybe?
		int retryCount = 0;
		CloseableHttpClient httpClient = HttpTrustAll.initHttpClient();

		while (retryCount <= maxRetries) {
			try {
				// build URL
				URI uri = new URIBuilder(new URI(affiniteUrl))
						.addParameters(nvps)
						.build();

				// Affinite API needs X-VDT header. Value doesn't matter
				httpGet.addHeader("X-VDT", "");
				httpGet.setUri(uri);

				// API call
				CloseableHttpResponse response = httpClient.execute(httpGet);
				logger.info("Response from Affinite API: {}", response);

				if (response.getCode() == 200) {
					// set result to String
					String json = EntityUtils.toString(response.getEntity());

					// map String to pojo
					ObjectMapper mapper = new ObjectMapper();
					result = mapper.readValue(json, AffiniteAssignment[].class);
				} else if (response.getCode() == 404) {
					logger.warn("Response from Affinite is 404, no user found from API");
					AffiniteAssignment noUsers = new AffiniteAssignment();
					result = new AffiniteAssignment[]{noUsers};
				}
				httpClient.close();
				response.close();
				break; // exit loop if request succeeds
			} catch (Exception e) {
				logger.error("Error trying to make API call to Affinite: ", e);
				
				// check connection reset and retry with proxy
				if (e.getMessage().contains("Connection reset") && retryCount <= maxRetries) {
					logger.warn("Retrying request with proxy settings");
					retryCount++;
					httpClient = HttpTrustAllWithProxy.initHttpClient();
				} else {
					throw new RPAMDMDataServiceException(e.getMessage());
				}
			}
		}
		return result;
	}
	
	public boolean outOfOfficeFromWorkday(RPAMDMDataServiceResponse mdmData) {
		// set up WorkdayService request
		RPAWorkdayServiceRequest workday = new RPAWorkdayServiceRequest();
		workday.setFirstName(mdmData.getFirstName());
		workday.setLastName(mdmData.getLastName());
		
		try {
			// initialize http client
			CloseableHttpClient client = HttpTrustAll.initHttpClient();
			
			// set up post request with auth header
			HttpPost request = new HttpPost(rpaWorkdayServiceUrl);
			byte[] encodedAuth = Base64.encodeBase64(credentials.getBytes(StandardCharsets.ISO_8859_1));
			request.setHeader("Authorization", "Basic " + new String(encodedAuth));
			
			// write pojo to json
			ObjectMapper mapper = new ObjectMapper();
			String jsonString = mapper.writeValueAsString(workday);
			HttpEntity stringEntity = new StringEntity(jsonString, ContentType.APPLICATION_JSON);
			request.setEntity(stringEntity);
			logger.info("JSON going to RPAWorkdayService: {}", jsonString);
			
			// make http request
			CloseableHttpResponse response = client.execute(request);
			String strResponse = EntityUtils.toString(response.getEntity());
			logger.info("Response from RPAWorkdayService: {}", strResponse);
			
			// add onHold indicator based on PTO
			if (strResponse.contains("true")) {
				mdmData.setOnHold("true");
				return true;
			}
		} catch (Exception e) {
			logger.error("Error trying to make API call to RPAWorkdayService: ", e);
			throw new RPAMDMDataServiceException(e.getMessage());
		}
		mdmData.setOnHold("false");
		return false;
	}
}
