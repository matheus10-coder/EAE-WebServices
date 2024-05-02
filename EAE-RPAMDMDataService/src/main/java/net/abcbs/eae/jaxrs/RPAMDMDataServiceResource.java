package net.abcbs.eae.jaxrs;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.annotation.JacksonFeatures;

import io.swagger.v3.oas.annotations.servers.Server;
import net.abcbs.eae.javabeans.RPAMDMDataServiceJavaBeans;
import net.abcbs.eae.pojo.NurseRequest;
import net.abcbs.eae.pojo.RPAMDMDataServiceResponse;
import net.abcbs.eae.pojo.AffiniteAssignment;
import net.abcbs.eae.pojo.NurseDataFromDb;
import net.abcbs.eae.pojo.NurseLicensureResponse;
import net.abcbs.eae.queryservice.RpaQueryService;
import net.abcbs.eae.queryservice.RpaQueryServiceApi;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;


@Path("/MDMData")
@OpenAPIDefinition(
		servers = {
				@Server(
						description = "localhost",
						url = "localhost:9080/RPAMDMDataService/resources"),
				@Server(
						description = "development",
						url = "https://isshareddev.abcbs.net/RPAMDMDataService/resources"),
				@Server(
						description = "test",
						url = "https://issharedtst.abcbs.net/RPAMDMDataService/resources"),
				@Server(
						description = "stage",
						url = "https://issharedstg.abcbs.net/RPAMDMDataService/resources"),
				@Server(
						description = "production",
						url = "https://isshared.abcbs.net/RPAMDMDataService/resources")})

public class RPAMDMDataServiceResource {
	private static final Logger logger = LogManager.getLogger(RPAMDMDataServiceResource.class);

	// POST request to base endpoint, returns nurse data
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Nurse workload data",
		description = "Returns data for a nurse like the license and the org region, along with the current workload",
		responses = {@ApiResponse(description = "JSON response",content = @Content(mediaType = "application/json"))})
	public Response nurseData(NurseRequest request) {
		logger.info("Base endpoint POST request hit");
		RPAMDMDataServiceJavaBeans bean = new RPAMDMDataServiceJavaBeans();
		RPAMDMDataServiceResponse response = new RPAMDMDataServiceResponse();

		// get the first part of the data
		// Id, username, first name, last name,
		// UserType, OrgTeam, Number of cases,
		// number of assignments, number of assignments today
		AffiniteAssignment[] affinite = bean.getNurseWorkload(request.getUsername());

		response.setId(affinite[0].getId());
		response.setUsername(affinite[0].getUsername());
		response.setFirstName(affinite[0].getFirstName());
		response.setLastName(affinite[0].getLastName());
		response.setOrgTeam(affinite[0].getOrgTeam());
		response.setUserType(affinite[0].getUserType());
		response.setNumberOfAssignments(affinite[0].getNumberOfAssignments());
		response.setNumberOfAssignmentsToday(affinite[0].getNumberOfAssignmentsToday());
		response.setNumberOfCases(affinite[0].getNumberOfCases());

		// second part of the data, query stuff
		RpaQueryServiceApi queryApi = new RpaQueryServiceApi();
		RpaQueryService query = queryApi.nurseDataRequest();
		// add request's username to the query
		String sql = query.getQuery().concat("'" + request.getUsername() + "'");
		logger.info("SQL query - {}", sql);
		
		// make database call
		List<NurseDataFromDb> nurseDataDb = bean.nurseDataQuery(sql);
		
		if (nurseDataDb.isEmpty()) {
			response.setLicenseType(null);
			response.setState(null);
		} else { 
			response.setLicenseType(nurseDataDb.get(0).getLicenseType());
			response.setState(nurseDataDb.get(0).getState());
			if (response.getFirstName() == null || response.getFirstName().isEmpty()) {
				response.setFirstName(nurseDataDb.get(0).getFirstName().trim());
			}
			if (response.getLastName() == null || response.getLastName().isEmpty()) {
				response.setLastName(nurseDataDb.get(0).getLastName().trim());
			}
		}
		// for onHold indicator
		bean.outOfOfficeFromWorkday(response);
		
		return Response.ok(response).build();
	}

	// End point that will check nurse licensure  
	@POST
	@Path("/nurseLicense")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Check licensure of a nurse",
		description = "Runs a query agains the Enterprise Layer DB to check a given nurse's licensure",
		responses = {@ApiResponse(description = "JSON response",content = @Content(mediaType = "application/json"))})
	@JacksonFeatures(serializationEnable =  { SerializationFeature.INDENT_OUTPUT })
	public Response checkNurseLicense(NurseRequest request) {
		logger.info("/nurseLicense endpoint hit");
		RPAMDMDataServiceJavaBeans bean = new RPAMDMDataServiceJavaBeans();

		// use RPAQueryService to make updates to SQL
		// code below makes API call and retrieves query
		RpaQueryServiceApi queryApi = new RpaQueryServiceApi();
		RpaQueryService query = queryApi.nurseLicenseRequest();

		// add request's username to the query
		String sql = query.getQuery().concat("'" + request.getUsername() + "'");
		logger.info("SQL query - {}", sql);

		// run query and get results in pojo array
		List<NurseLicensureResponse> response = bean.nurseLicensureQuery(sql);

		return Response.ok(response).build();
	}

}
