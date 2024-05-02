package net.abcbs.eae.queryservice;

import org.apache.hc.client5.http.fluent.Content;
import org.apache.hc.client5.http.fluent.Request;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.abcbs.eae.exception.RPAMDMDataServiceException;
import net.abcbs.eae.util.HttpTrustAll;
import net.abcbs.eae.util.RetrieveIsSharedProperties;

public class RpaQueryServiceApi {
	private static final Logger logger = LogManager.getLogger(RpaQueryServiceApi.class);
	private String queryServiceUrl;
	private String queryNurseDataUrl;

	public RpaQueryServiceApi() {
		try {
			RetrieveIsSharedProperties prop = new RetrieveIsSharedProperties();
			queryServiceUrl = prop.getRpaQueryServiceUrl();
			queryNurseDataUrl = prop.getRpaQueryServiceNurseDataUrl();
		} catch (Exception e) {
			logger.error("Error instantiating RpaQueryServiceApi: ", e);
			throw new RPAMDMDataServiceException(e.getMessage());
		}
	}

	// API call to get query and license data
	public RpaQueryService nurseLicenseRequest() {
		RpaQueryService query = new RpaQueryService();
		logger.info("Making API call to RPAQueryService");

		try {
			Request request = Request.get(queryServiceUrl);
			Content response = request.execute(HttpTrustAll.initHttpClient()).returnContent();

			ObjectMapper mapper = new ObjectMapper();
			query = mapper.readValue(response.asStream(), RpaQueryService.class);
			logger.info("RPAQueryService API call successful, : {}", query);
		} catch (Exception e) {
			logger.error("Error calling RPAQueryService: ", e);
			throw new RPAMDMDataServiceException(e.getMessage());
		}
		return query;
	}
	
	// API call to get query and nurse data
	public RpaQueryService nurseDataRequest() {
		RpaQueryService query = new RpaQueryService();
		logger.info("Making API call to RPAQueryService - Nurse data");

		try {
			Request request = Request.get(queryNurseDataUrl);
			Content response = request.execute(HttpTrustAll.initHttpClient()).returnContent();

			ObjectMapper mapper = new ObjectMapper();
			query = mapper.readValue(response.asStream(), RpaQueryService.class);
			logger.info("RPAQueryService API call successful, : {}", query);
		} catch (Exception e) {
			logger.error("Error calling RPAQueryService: ", e);
			throw new RPAMDMDataServiceException(e.getMessage());
		}
		return query;
	}
}
