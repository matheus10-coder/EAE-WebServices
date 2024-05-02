package net.abcbs.eae.jaxrs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import net.abcbs.eae.power.PowerCountRecord;
import net.abcbs.eae.power.PowerSaveSearch;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;

@Path("/power")
@OpenAPIDefinition(
		servers = {
				@Server(
						description = "localhost",
						url = "localhost:9080/RPAPowerService/resources"),
				@Server(
						description = "development",
						url = "https://isshareddev.abcbs.net/RPAPowerService/resources"),
				@Server(
						description = "test",
						url = "https://issharedtst.abcbs.net/RPAPowerService/resources"),
				@Server(
						description = "stage",
						url = "https://issharedstg.abcbs.net/RPAPowerService/resources"),
				@Server(
						description = "production",
						url = "https://isshared.abcbs.net/RPAPowerService/resources")
		})
@SecurityScheme(name = "basicAuth",
type = SecuritySchemeType.HTTP,
scheme = "basic",
description = "Username and Password are used for authorization")

public class RPAPowerServiceResource {
	private static final Logger logger = LogManager.getLogger(RPAPowerServiceResource.class);

	// Returns a base endpoint message
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "A base endpoint for the template",
	security = @SecurityRequirement(name = "basicAuth"),
	description = "A simple base endpoint that you can hit to get a response from the server",
	responses = {
			@ApiResponse(
					description = "JSON response",
					content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = PowerOutputResponse.class))),
			@ApiResponse(responseCode = "401", description = "Credentials not authorized") })

	public PowerOutputResponse baseEndpoint() {
		PowerOutputResponse response = new PowerOutputResponse();
		response.setMessage("base endpoint can be hit!");
		return response;
	}

	// Returns the number of records in a POWER save search
	@Path("/saveSearchCount/{searchName}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Returns number of records in a POWER save search",
	security = @SecurityRequirement(name = "basicAuth"),
	description = "Returns the total number of records from a provided POWER save search name",
	responses = {
			@ApiResponse(
					description = "JSON response",
					content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "401", description = "Credentials not authorized") })

	public PowerOutputResponse getSaveSearchCount(@PathParam("searchName") String searchName) {
		logger.info("getSaveSearchCount method hit");
		PowerOutputResponse response = new PowerOutputResponse();
		PowerSaveSearch search = new PowerSaveSearch();

		int count = search.getPowerRecordCount(searchName);
		response.setCount(count);

		return response;
	}

	// Returns the number of records in a POWER save search
	@Path("/queueCount/{queueName}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Returns number of records in a POWER save search",
	security = @SecurityRequirement(name = "basicAuth"),
	description = "Returns the total number of records from a provided POWER save search name",
	responses = {
			@ApiResponse(
					description = "JSON response",
					content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "401", description = "Credentials not authorized") })

	public PowerOutputResponse getQueueCount(@PathParam("queueName") String queueName) {
		logger.info("getQueueCount method hit");
		PowerOutputResponse response = new PowerOutputResponse();
		PowerCountRecord countRecord = new PowerCountRecord();

		int count = countRecord.getPowerQueueCount(queueName);
		response.setCount(count);

		return response;
	}

}
