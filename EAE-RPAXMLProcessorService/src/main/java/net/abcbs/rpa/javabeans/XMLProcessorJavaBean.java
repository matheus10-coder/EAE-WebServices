package net.abcbs.rpa.javabeans;
/***********************************************************************************************************************************************************************
 * @author ABCBS resource
 * 
 * Description: XMLProcessorJavaBean class will be used to perform the proper connection with Oracle database and query the correct value required by the user 
 * 
 * Project: RPA XML Processor Service 
 ***********************************************************************************************************************************************************************/

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import java.io.*;
import net.abcbs.issh.util.pub.javabeans.IsSharedJavaBean;
import net.abcbs.rpa.dto.XMLProcessorDTO;


public class XMLProcessorJavaBean extends IsSharedJavaBean {
	private static final String INNERJOIN = "INNER JOIN";
	private static final String LEFTJOIN = "LEFT JOIN";
	
	
	public List<XMLProcessorDTO> xmlPayload(String dataSource, String scheme, String messageRoot) {
		this.setDbFunctionDelete(dbFunctionDelete);
		ArrayList<XMLProcessorDTO> arrayList = new ArrayList<>();
		
		 
		
		try {
			this.initializeConnection(dataSource, "");
			 
			sqlStatement.append("SELECT PAYLOAD_DATA as XMLPath");//1
			
			sqlStatement.append(" FROM " + scheme + ".IPP_MSG");
			
			sqlStatement.append(" WHERE MSG_ID = ? WITH UR");
			
			preparedStatement = connection.prepareStatement(sqlStatement.toString());
			preparedStatement.setString(1, messageRoot);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				XMLProcessorDTO xmlPayloadDt = new XMLProcessorDTO();

				xmlPayloadDt.setXmlPayload(resultSet.getString(1)); //set XML data
								
				arrayList.add(XMLParser(xmlPayloadDt));
			}
		}
		catch (SQLException se) {
			this.processException(se);
		}
		catch (Exception e) {
			this.processException(e);
		}
		finally {
			displayResults();
			this.closeConnections();
		}

		return arrayList;
	}
	
	
	/***********************************************************************************************************************************************************************
	 * Description: XMLParser method will be used to perform the xml parse to String then we will identify the value on the name tag and assign the proper fields for the
	 * Boolean variable and Name in our DTO 
	 * 
	 * Project: RPA XML Processor Service 
	 ***********************************************************************************************************************************************************************/
	
	public XMLProcessorDTO XMLParser (XMLProcessorDTO xmlPayloadDt) {
		
		XMLProcessorDTO payloadData = new XMLProcessorDTO();
		String xmlData = xmlPayloadDt.getXmlPayload();
		
		
		try {
			//Document Builder
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			XMLStreamReader reader = inputFactory.createXMLStreamReader(new StringReader(xmlData));
			//Doc reader
			while(reader.hasNext()) {
				
				int event = reader.next();
				//Check the name tag in the xml
				if(event == XMLStreamReader.START_ELEMENT && reader.getLocalName().equals("name")) {
					String nameValue = reader.getElementText();
					//Assign the value from name tag
					payloadData.setName(nameValue);
					
					if (nameValue.toLowerCase().equals("blueadvantage arkansas")) {
						payloadData.setBlueAdvantageArkansas(true); 
					}
					else {
						payloadData.setBlueAdvantageArkansas(false);
					}
				}
				
			}
			
		} catch (XMLStreamException e) {
			
			e.printStackTrace();
		} 
		return payloadData;
	}
	
	
}


