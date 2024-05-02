package net.abcbs.eae.jaxrs;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import java.util.List;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.*;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.annotation.JacksonFeatures;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import net.abcbs.issh.util.pub.common.IsSharedApplicationDataObject;
import net.abcbs.rpa.javabeans.XMLProcessorJavaBean;
import net.abcbs.rpa.dto.*;

/***********************************************************************************************************************************************************************
 * @Author mfribeiro
 * 
 * Body for all the methods used for the REST Web service
 * 
 * Description: BluecardHostResource class is the application resource level which the main methods will be called in order to return the message to the user 
 * 
 * Project: XML Processor Service 
 ***********************************************************************************************************************************************************************/
@Path("/blue2")
@OpenAPIDefinition(
		servers = {
				@Server(
					description = "localhost",
					url = "localhost:9080/RPAXMLProcessorService/resources"),
				@Server(
					description = "development",
					url = "https://isshareddev.abcbs.net/RPAXMLProcessorService/resources"),
				@Server(
						description = "test",
						url = "https://issharedtst.abcbs.net/RPAXMLProcessorService/resources"),
				@Server(
					description = "stage",
					url = "https://issharedstg.abcbs.net/RPAXMLProcessorService/resources"),
				@Server(
					description = "production",
					url = "https://isshared.abcbs.net/RPAXMLProcessorService/resources")
		})
@SecurityScheme(name = "basicAuth",
		type = SecuritySchemeType.HTTP,
		scheme = "basic",
		description = "Username and Password are used for authorization")

public class RPAXMLProcessorServiceResource {
	
    /**
     * Private method
     * 
     * Data object to get database information
     * 
     * Utilizing isSharedApplication class
     * 
     */
	private static Logger logger = LogManager.getLogger(RPAXMLProcessorServiceResource.class);
	private static IsSharedApplicationDataObject isSharedApplicationDataObject = null;

	static {
		try {
			isSharedApplicationDataObject = new IsSharedApplicationDataObject(Constants.SYSTEM_NAME, Constants.AUTH_KEY, Constants.AUTH_PASS_PHRASE_DEV);
		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		}
	}
	/**
     * Public method
     * 
     * Method to output successful message from the server
     * 
     * Includes brief instruction on how to use this service
     * 
     * @return List value
     * 
     */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Service base endpoint that you can hit to get a response from the server",
			security = @SecurityRequirement(name = "basicAuth"),
			description = "A simple base endpoint that you can hit to get a response from the server",
			responses = {
					@ApiResponse(
							description = "JSON response",
							content = @Content(mediaType = "application/json",
							schema = @Schema(implementation = JsonPayload.class))),
					@ApiResponse(responseCode = "401", description = "Credentials not authorized") }
	)	
	public String blueCardMessage(){
		return "{\"message\": \"XML Processor service will accept in a request and respond with the appropriate information queried from the Blue2 database. Db2jndiName: " + isSharedApplicationDataObject.getDb2JndiName() + ". Schema: " + isSharedApplicationDataObject.getDb2Schema() + ". Please append your sccf number to this endpoint. \"}";
	}
	/**
     * Public method
     * 
     * Calculates the total count of lines for *COVID* given a claim number. 
     * 
     * Message ID is a string type that will be required from the user during the call 
     * 
     * It will go through the claim number validation security guidelines in order to retrieve the final service line count (*COVID*) for that particular claim 
     * 
     * @return returns the total count of lines for *COVID* given a claim number
     * 
     */
	@GET
	@Path("/xml-payload/{messageId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Purpose for this GET method is to retrieve queried information from blue2ITS for each sccf#",
	security = @SecurityRequirement(name = "basicAuth"),
	description = "Returns a complete list of all the information requested from each sccf",
	responses = {
		@ApiResponse(
				description = "JSON response in a form of array",
				content = @Content(mediaType = "application/json",
				array = @ArraySchema(schema = @Schema(implementation = XMLProcessorDTO.class)))),
		@ApiResponse(responseCode = "401", description = "Credentials not authorized") }
	)
	@JacksonFeatures(serializationEnable =  { SerializationFeature.INDENT_OUTPUT })
	
	public List<XMLProcessorDTO> blueCardTotalInfo (
			@Parameter(description = "Identify the string and perform input validation. {messageId} refers to the 'message root id' in Blue2 (DB2) database",
			required = true)
			@PathParam("messageId") String messageId) {
		RPAXMLProcessorServiceMessage info = new RPAXMLProcessorServiceMessage();
		String messageRootId = info.getMessage(messageId);
		XMLProcessorJavaBean xMLProcessorJavaBean = new XMLProcessorJavaBean();
			
		return xMLProcessorJavaBean.xmlPayload(isSharedApplicationDataObject.getDb2JndiName(), isSharedApplicationDataObject.getDb2Schema(),messageRootId);
	}
	
	

	/**
     * Public method
     * 
     * Performs credential validation 
     * 
     * If user gets a 401 they are unauthorized
     * 
     * @return JsonPayLoad type message 
     * 
     */
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
			@ApiResponse(responseCode = "401", description = "Credentials not authorized")}
	)
	public JsonPayload testAuthorization() {
		JsonPayload payload = new JsonPayload();
		payload.setMessage("authorization valid");
		return payload;
	}
}
