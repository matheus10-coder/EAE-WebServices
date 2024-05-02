package net.abcbs.eae.queryservice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class RpaQueryService {
	@JsonProperty("system")
	private String system;
	
	@JsonProperty("tenant")
	private String tenant;
	
	@JsonProperty("type")
	private String type;
	
	@JsonProperty("process")
	private String process;
	
	@JsonProperty("schema")
	private String schema;
	
	@JsonProperty("query")
	private String query;
	
	@JsonProperty("description")
	private String description;
	
	@JsonProperty("parameter")
	private String parameter;
	
	@JsonProperty("fileName")
	private String fileName;
	
	@JsonProperty("dbtype")
	private String dbtype;
	
	@JsonProperty("outputData")
	private String outputData;
	
	@JsonProperty("outputEndpoint")
	private String outputEndpoint;

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getTenant() {
		return tenant;
	}

	public void setTenant(String tenant) {
		this.tenant = tenant;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getDbtype() {
		return dbtype;
	}

	public void setDbtype(String dbtype) {
		this.dbtype = dbtype;
	}

	public String getOutputData() {
		return outputData;
	}

	public void setOutputData(String outputData) {
		this.outputData = outputData;
	}

	public String getOutputEndpoint() {
		return outputEndpoint;
	}

	public void setOutputEndpoint(String outputEndpoint) {
		this.outputEndpoint = outputEndpoint;
	}

	@Override
	public String toString() {
		return "RpaQueryService [system=" + system + ", tenant=" + tenant + ", type=" + type + ", process=" + process
				+ ", schema=" + schema + ", query=" + query + ", description=" + description + ", parameter="
				+ parameter + ", fileName=" + fileName + ", dbtype=" + dbtype + ", outputData=" + outputData
				+ ", outputEndpoint=" + outputEndpoint + "]";
	}
}
