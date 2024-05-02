package net.abcbs.rpa.dto;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/***********************************************************************************************************************************************************************
 * @author mfribeiro
 * 
 * Description: RefRulesDTO class used to perform core task such as create and construct the object used in StaffWorkloadJavaBean
 * 
 * Project: Staffing Service
 ***********************************************************************************************************************************************************************/

public class RefRulesDTO {
	
	//private variables
	private String orgRegionName;
	private String type;
	private int ruleId;
	private int rulePriority;
	private String ruleName;
	private String ruleDescription;
	private String userType;
	private String active;
	private int maxPercent;
	private int maxMember;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getRuleId() {
		return ruleId;
	}
	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
	}
	public int getRulePriority() {
		return rulePriority;
	}
	public void setRulePriority(int rulePriority) {
		this.rulePriority = rulePriority;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public String getRuleDescription() {
		return ruleDescription;
	}
	public void setRuleDescription(String ruleDescription) {
		this.ruleDescription = ruleDescription;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public int getMaxPercent() {
		return maxPercent;
	}
	public void setMaxPercent(int maxPercent) {
		this.maxPercent = maxPercent;
	}
	public int getMaxMember() {
		return maxMember;
	}
	public void setMaxMember(int maxMember) {
		this.maxMember = maxMember;
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

