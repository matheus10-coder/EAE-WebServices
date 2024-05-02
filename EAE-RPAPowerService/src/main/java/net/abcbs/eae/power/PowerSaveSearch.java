package net.abcbs.eae.power;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import net.abcbs.eae.utils.RetrieveIsSharedProperties;

public class PowerSaveSearch {
	private static final Logger logger = LogManager.getLogger(PowerSaveSearch.class);
	private String xml;
	private String powerUrl;
	private String powerCredentials;

	public PowerSaveSearch() {
		RetrieveIsSharedProperties isshprp = new RetrieveIsSharedProperties();
		powerUrl = isshprp.getPowerUrl();
		logger.info("POWER URL: {}", powerUrl);
		powerCredentials = isshprp.getPowerCredentials();
		if (!StringUtils.isBlank(powerCredentials)) {
			logger.info("POWER Credentials obtained");
		}
	}

	public String xmlToString(Document doc) {
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer t;
		try {
			t = tf.newTransformer();
			StringWriter writer = new StringWriter();
			t.transform(new DOMSource(doc), new StreamResult(writer));
			return writer.getBuffer().toString();
		} catch (TransformerException e) {
			logger.error(e);
			throw new PowerException("Exception occurred when transforming XML to String: " + e.getLocalizedMessage());
		}
	}

	// prepare the XML to send to Power API
	public void prepSoapEnvelope(String searchName) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		logger.trace("Parsing template SOAP XML, writing data for save search");
		try {
			// get soap envelope for save_search_bot
			InputStream soapEnv = Thread.currentThread().getContextClassLoader().getResourceAsStream("power-save-search-bot.xml");
			dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

			DocumentBuilder builder = dbf.newDocumentBuilder();
			Document doc = builder.parse(soapEnv);

			doc.getDocumentElement().normalize();
			NodeList list = doc.getElementsByTagName("soapenv:Body");
			NodeList saveSearch = ((Element)list.item(0)).getElementsByTagName("web:saved_search_bot");
			saveSearch.item(0).getChildNodes().item(1).setTextContent(searchName);
			xml = xmlToString(doc);
			logger.trace("XML Envelope: {}", xml);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			logger.error(e);
			throw new PowerException("Error trying to parse request XML for save search bot: " + e.getLocalizedMessage());
		}
	}

	public int getPowerRecordCount(String searchName) {
		logger.info("Getting Record Count from POWER");
		int claimCount = 0;
		PowerCalls power = new PowerCalls();
		prepSoapEnvelope(searchName);

		try { 
			Document searchResult = power.powerRequest(powerUrl, powerCredentials, xml);

			searchResult.getDocumentElement().normalize();
			claimCount = Integer.parseInt(searchResult.getElementsByTagName("return").item(0).getFirstChild().getTextContent());
		} catch (Exception e) {
			logger.error(e);
			throw new PowerException("Error trying to call save search count: " + e.getLocalizedMessage());
		}
		return claimCount;
	}

}
