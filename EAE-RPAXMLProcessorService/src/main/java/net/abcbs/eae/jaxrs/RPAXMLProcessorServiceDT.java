package net.abcbs.eae.jaxrs;
import java.util.ArrayList;
import java.util.*;
/***********************************************************************************************************************************************************************
 * @author mfribeiro
 * 
 * Description: RPAXMLProcessorServiceDT class is used to manually test the web service call to retrieve values out of a data table
 * 
 * Project: Bluecard Host Adjustment 
 ***********************************************************************************************************************************************************************/
public class RPAXMLProcessorServiceDT {
	
	public List<RPAXMLProcessorServiceMessage> getDBServiceLines(){
		RPAXMLProcessorServiceMessage servLn1 = new RPAXMLProcessorServiceMessage("COVID19",1);
		RPAXMLProcessorServiceMessage servLn2 = new RPAXMLProcessorServiceMessage("ITS",2);
		RPAXMLProcessorServiceMessage servLn3 = new RPAXMLProcessorServiceMessage("N/A",3);
		
		List<RPAXMLProcessorServiceMessage> serviceLinesLs = new ArrayList<>();
		serviceLinesLs.add(servLn1);
		serviceLinesLs.add(servLn2);
		serviceLinesLs.add(servLn3);
		return serviceLinesLs;
	}

}
