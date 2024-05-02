package net.abcbs.rpa.dto;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class OrgRegionDTO {
	
	private String orgRegionName;
	private String active;
	private Date createTs;
	private String createUserId;
	private Date updateTs;
	private String updateUserId;
	//exception message
	private String error;
	
	
	//getters and setters
	public String getOrgRegionName() {
		return orgRegionName;
	}
	public void setOrgRegionName(String orgRegionName) {
		this.orgRegionName = orgRegionName;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public Date getCreateTs() {
		return createTs;
	}
	public void setCreateTs(Date createTs) {
		this.createTs = createTs;
	}
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	public Date getUpdateTs() {
		return updateTs;
	}
	public void setUpdateTs(Date updateTs) {
		this.updateTs = updateTs;
	}
	public String getUpdateUserId() {
		return updateUserId;
	}
	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	

	
	


 

	
	

}
