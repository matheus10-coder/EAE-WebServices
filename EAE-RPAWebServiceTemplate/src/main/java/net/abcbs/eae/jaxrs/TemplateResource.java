package net.abcbs.eae.jaxrs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;

import net.abcbs.issh.util.pub.common.IsSharedApplicationDataObject;

@Path("/Temp")
@OpenAPIDefinition(
		servers = {
				@Server(
					description = "localhost",
					url = "localhost:9080/RPAWebServiceTemplate/resources")
		})
@SecurityScheme(name = "basicAuth",
		type = SecuritySchemeType.HTTP,
		scheme = "basic",
		description = "Username and Password are used for authorization")

public class TemplateResource {

	// Data object to get database information
	private static IsSharedApplicationDataObject isSharedApplicationDataObject = null;

	static {
		try {
			isSharedApplicationDataObject = new IsSharedApplicationDataObject(Constants.SYSTEM_NAME, Constants.AUTH_KEY, Constants.AUTH_PASS_PHRASE_DEV);
		} catch (Exception e) {
			e.printStackTrace();
		}
	};
	
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
							schema = @Schema(implementation = JsonPayload.class))),
					@ApiResponse(responseCode = "401", description = "Credentials not authorized") }
	)
	public JsonPayload baseEndpoint() {
		JsonPayload payload = new JsonPayload();
		payload.setMessage("base endpoint can be hit!");
		return payload;
	}

	// test method for credential validation. If a user gets a 401 they are unauthorized
	@GET
	@Path("/test/authorization") 
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Simple test authorization method",
		security = @SecurityRequirement(name = "basicAuth"),
		description = "Test your credentials",
		responses = {
			@ApiResponse(
					description = "A method to simply test your credentials against the web service to see if you are authorized",
					content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "401", description = "Credentials not authorized") }
	)
	public JsonPayload testAuthorization() {
		JsonPayload payload = new JsonPayload();
		payload.setMessage("authorization valid");
		return payload;
	}
}
