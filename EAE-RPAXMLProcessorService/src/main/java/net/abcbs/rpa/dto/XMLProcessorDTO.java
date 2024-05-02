package net.abcbs.rpa.dto;
/***********************************************************************************************************************************************************************
 * @author mfribeiro
 * 
 * Description: XMLProcessorDTO class used to perform core task such as create and construct the object used in XMLProcessorJavaBean
 * 
 * Project: XML Processor 
 ***********************************************************************************************************************************************************************/

public class XMLProcessorDTO {
	
	//private variables
	private String xmlPayload;
	private boolean isBlueAdvantageArkansas; 
	private String contactOnReceiptName;

	public String getXmlPayload() {
		return xmlPayload;
	}

	public void setXmlPayload(String xmlPayload) {
		this.xmlPayload = xmlPayload;
	}

	public boolean isBlueAdvantageArkansas() {
		return isBlueAdvantageArkansas;
	}

	public void setBlueAdvantageArkansas(boolean isBlueAdvantageArkansas) {
		this.isBlueAdvantageArkansas = isBlueAdvantageArkansas;
	}

	public String getName() {
		return contactOnReceiptName;
	}

	public void setName(String name) {
		this.contactOnReceiptName = name;
	}
	

}

