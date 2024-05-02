package net.abcbs.eae.jaxrs;
public class Query {
	private String system;
	private String tenant;
	private String type;
	private String process;
	private String schema;
	private String query;
	private String description;
	private String parameter;
	private String fileName;
	private String dbType;
	private String outputData;
	private String outputType;
	private String outputEndpoint;
	private String createUserId;
	private String updateUserId;
	private int seq;
	private int active;
	
	
	public Query() {
		
	}

	public Query(String system,String tenant,String type,String process,String schema,String query,String description,String parameter,
	String fileName,String dbType,String outputData,String outputType,String outputEndpoint,String createUserId, String updateUserId, int seq, int active) 
	{
		super();
		this.system = system;
		this.tenant = tenant;
		this.type = type;
		this.process = process;
		this.schema = schema;
		this.query = query;
		this.description = description;
		this.parameter = parameter;
		this.fileName = fileName;
		this.dbType = dbType;
		this.outputData = outputData;
		this.outputType = outputType;
		this.outputEndpoint = outputEndpoint;
		this.createUserId = createUserId;
		this.updateUserId = updateUserId;
		this.seq = seq;
		this.active = active;
	}

	
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
	public String getDBType() {
		return dbType;
	}
	public void setDBType(String dbType) {
		this.dbType = dbType;
	}
	
	public String getOutputData() {
		return outputData;
	}
	public void setOutputData(String outputData) {
		this.outputData = outputData;
	}
	public String getOutputType() {
		return outputType;
	}
	public void setOutputType(String outputType) {
		this.outputType = outputType;
	}
	
	public String getOutputEndpoint() {
		return outputEndpoint;
	}
	public void setOutputEndpoint(String outputEndpoint) {
		this.outputEndpoint = outputEndpoint;
	}
	
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	
	public String getUpdateUserId() {
		return updateUserId;
	}
	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}

}
