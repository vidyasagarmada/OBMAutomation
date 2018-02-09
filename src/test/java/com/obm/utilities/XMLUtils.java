package com.obm.utilities;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XMLUtils {

	public static String getNodeValue(String xmlString, String xpath) throws SAXException, IOException {
		String returnValue = "";
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder builder = null;
		try {
			builder = builderFactory.newDocumentBuilder();
			Document xmlDocument = builder.parse(new ByteArrayInputStream(
					xmlString.getBytes()));

			XPath xPath = XPathFactory.newInstance().newXPath();
			// read a string value
			returnValue = xPath.compile(xpath).evaluate(xmlDocument);
			
		} catch (ParserConfigurationException e) {
			System.out
					.println("Exception occurred while reading XML. XML Contents:"
							+ xmlString);
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			System.out
					.println("Exception occurred while reading XML. XML Contents:"
							+ xmlString);
			e.printStackTrace();
		}

		return returnValue;
	}

	
	 public static void addEmailtoDataXML(String emailMessageID, String
	 fromAddress, String emailTemplate, String emailSubject, String emailBody) throws TransformerException, SAXException, IOException, XPathExpressionException
	 {
	 DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
	 domFactory.setIgnoringComments(true);
	 DocumentBuilder builder;
	 Document doc = null;
	 try {
	 builder = domFactory.newDocumentBuilder();
	 String currentPath =
	 XMLUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	 doc = builder.parse(new File(currentPath+"configurations/Data.xml"));
	 XPath xPath = XPathFactory.newInstance().newXPath();
	 String expression =
	 "/configurations/community[@name='GAP']/communityGroup[@name='GAPCG']/communityClient[@name='GAPCL']";
	 Node node = (Node) xPath.compile(expression).evaluate(doc, XPathConstants.NODE);
	
	 Element emailElement = doc.createElement("emailMessage");
	 emailElement.setAttribute("id", emailMessageID);
	
	 Element fromAddressElement = doc.createElement("fromAddress");
	 fromAddressElement.appendChild(doc.createTextNode(fromAddress));
	 emailElement.appendChild(fromAddressElement);
	
	 Element emailTemplateElement = doc.createElement("emailTemplate");
	 emailTemplateElement.appendChild(doc.createTextNode(emailTemplate));
	 emailElement.appendChild(emailTemplateElement);
	
	 Element emailSubjectElement = doc.createElement("emailSubject");
	 emailSubjectElement.appendChild(doc.createTextNode(emailSubject));
	 emailElement.appendChild(emailSubjectElement);
	
	 Element emailBodyElement = doc.createElement("emailBody");
	 emailBodyElement.appendChild(doc.createTextNode(emailBody));
	 emailElement.appendChild(emailBodyElement);
	 node.appendChild(emailElement);
	
	 TransformerFactory transformerFactory = TransformerFactory.newInstance();
	 Transformer transformer = transformerFactory.newTransformer();
	 DOMSource domSource = new DOMSource(doc);
	 StreamResult streamResult = new StreamResult(new
	 File(currentPath+"configurations/Data.xml"));
	 StreamResult resultLocal = new StreamResult(new
	 File("C:\\projects\\Data.xml"));
	 transformer.transform(domSource, streamResult);
	 transformer.transform(domSource, resultLocal);
	
	
	 } catch (ParserConfigurationException e) {
	 e.printStackTrace();
	 }
	 }
	
	 public static void addCampaignDetailsToDataXML(String campaignName) throws SAXException, IOException, XPathExpressionException, TransformerException {
	
	 DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
	 domFactory.setIgnoringComments(true);
	 DocumentBuilder builder;
	 Document doc = null;
	 try {
	 builder = domFactory.newDocumentBuilder();
	 System.out.println("Path:"+XMLUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath());
	 String currentPath =
	 XMLUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	 doc = builder.parse(new File(currentPath+"configurations/Data.xml"));
	 XPath xPath = XPathFactory.newInstance().newXPath();
	 String expression =
	 "/configurations/community[@name='GAP']/communityGroup[@name='GAPCG']/communityClient[@name='GAPCL']";
	 Node node = (Node) xPath.compile(expression).evaluate(doc, XPathConstants.NODE);
	
	 NodeList list = doc.getElementsByTagName("campaign");
	 if(list != null && list.getLength() > 0){
	 node.removeChild(list.item(0));}
	
	 Element campaignElement = doc.createElement("campaign");
	 campaignElement.setAttribute("name", campaignName);
	 node.appendChild(campaignElement);
	
	
	 TransformerFactory transformerFactory = TransformerFactory.newInstance();
	 Transformer transformer = transformerFactory.newTransformer();
	 DOMSource domSource = new DOMSource(doc);
	 StreamResult streamResult = new StreamResult(new
	 File(currentPath+"configurations/Data.xml"));
	 StreamResult resultLocal = new StreamResult(new
	 File("C:\\projects\\Data.xml"));
	 transformer.transform(domSource, streamResult);
	 transformer.transform(domSource, resultLocal);
	
	
	 } catch (ParserConfigurationException e) {
	 e.printStackTrace();
	 }
	 }
	
	 public static void addEventDetailsToDataXML(String eventID, String
	 eventName, String eventType, String actionType) throws TransformerException, SAXException, IOException, XPathExpressionException {
	
	 DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
	 domFactory.setIgnoringComments(true);
	 DocumentBuilder builder;
	 Document doc = null;
	 try {
	 builder = domFactory.newDocumentBuilder();
	 System.out.println("Path:"+XMLUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath());
	 String currentPath =
	 XMLUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	 doc = builder.parse(new File(currentPath+"configurations/Data.xml"));
	 XPath xPath = XPathFactory.newInstance().newXPath();
	 String expression =
	 "/configurations/community[@name='GAP']/communityGroup[@name='GAPCG']/communityClient[@name='GAPCL']/campaign/offer";
	 Node node = (Node) xPath.compile(expression).evaluate(doc,	 XPathConstants.NODE);
	
	 Element eventElement = doc.createElement("event");
	 eventElement.setAttribute("id", eventID);
	
	 Element eventNameElement = doc.createElement("name");
	 eventNameElement.appendChild(doc.createTextNode(eventName));
	 eventElement.appendChild(eventNameElement);
	
	 Element eventTypeElement = doc.createElement("EventType");
	 eventTypeElement.appendChild(doc.createTextNode(eventType));
	 eventElement.appendChild(eventTypeElement);
	
	 Element eventActionElement = doc.createElement("eventAction");
	 eventActionElement.appendChild(doc.createTextNode(actionType));
	 eventElement.appendChild(eventActionElement);
	
	 node.appendChild(eventElement);
	
	 TransformerFactory transformerFactory = TransformerFactory.newInstance();
	 Transformer transformer = transformerFactory.newTransformer();
	 DOMSource domSource = new DOMSource(doc);
	 StreamResult streamResult = new StreamResult(new
	 File(currentPath+"configurations/Data.xml"));
	 StreamResult resultLocal = new StreamResult(new
	 File("C:\\projects\\Data.xml"));
	 transformer.transform(domSource, streamResult);
	 transformer.transform(domSource, resultLocal);
	
	
	 } catch (ParserConfigurationException e) {
	 e.printStackTrace();
	 }
	 }
	
	 public static void addOfferToDataXML(String offerID, String offerName) throws TransformerException, SAXException, IOException, XPathExpressionException {
	
	 DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
	 domFactory.setIgnoringComments(true);
	 DocumentBuilder builder;
	 Document doc = null;
	 try {
	 builder = domFactory.newDocumentBuilder();
	 System.out.println("Path:"+XMLUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath());
	 String currentPath =
	 XMLUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	 doc = builder.parse(new File(currentPath+"configurations/Data.xml"));
	 XPath xPath = XPathFactory.newInstance().newXPath();
	 String expression =
	 "/configurations/community[@name='GAP']/communityGroup[@name='GAPCG']/communityClient[@name='GAPCL']/campaign";
	 Node node = (Node) xPath.compile(expression).evaluate(doc, XPathConstants.NODE);
	
	 NodeList list = doc.getElementsByTagName("offer");
	 if(list != null && list.getLength() > 0){
	 node.removeChild(list.item(0));}
	
	 Element offerElement = doc.createElement("offer");
	 offerElement.setAttribute("id", offerID);
	 offerElement.setAttribute("name", offerName);
	
	 node.appendChild(offerElement);
	
	 TransformerFactory transformerFactory = TransformerFactory.newInstance();
	 Transformer transformer = transformerFactory.newTransformer();
	 DOMSource domSource = new DOMSource(doc);
	 StreamResult streamResult = new StreamResult(new
	 File(currentPath+"configurations/Data.xml"));
	 StreamResult resultLocal = new StreamResult(new
	 File("C:\\projects\\Data.xml"));
	 transformer.transform(domSource, streamResult);
	 transformer.transform(domSource, resultLocal);
	
	
	 } catch (ParserConfigurationException e) {
	 e.printStackTrace();
	 }
	 }
	
	 public static void addSMStoDataXML(String smsMessageID, String smsText) throws TransformerException, SAXException, IOException, XPathExpressionException {
	
	 DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
	 domFactory.setIgnoringComments(true);
	 DocumentBuilder builder;
	 Document doc = null;
	 try {
	 builder = domFactory.newDocumentBuilder();
	 System.out.println("Path:"+XMLUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath());
	 String currentPath =
	 XMLUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	 doc = builder.parse(new File(currentPath+"configurations/Data.xml"));
	 XPath xPath = XPathFactory.newInstance().newXPath();
	 String expression =
	 "/configurations/community[@name='GAP']/communityGroup[@name='GAPCG']/communityClient[@name='GAPCL']";
	 Node node = (Node) xPath.compile(expression).evaluate(doc, XPathConstants.NODE);
	
	 Element smsElement = doc.createElement("SMSMessage");
	 smsElement.setAttribute("id", smsMessageID);
	
	 Element smsTextElement = doc.createElement("SMSText");
	 smsTextElement.appendChild(doc.createTextNode(smsText));
	 smsElement.appendChild(smsTextElement);
	
	 node.appendChild(smsElement);
	
	 TransformerFactory transformerFactory = TransformerFactory.newInstance();
	 Transformer transformer = transformerFactory.newTransformer();
	 DOMSource domSource = new DOMSource(doc);
	 StreamResult streamResult = new StreamResult(new
	 File(currentPath+"configurations/Data.xml"));
	 StreamResult resultLocal = new StreamResult(new
	 File("C:\\projects\\Data.xml"));
	 transformer.transform(domSource, streamResult);
	 transformer.transform(domSource, resultLocal);
	
	
	 } catch (ParserConfigurationException e) {
	 e.printStackTrace();
	 }
	 }
	
	
	public static void readMethodName(String endpoingMessageID,
			String messageText, String fields, String customFields) throws TransformerException, SAXException, IOException, XPathExpressionException {

		DocumentBuilderFactory domFactory = DocumentBuilderFactory
				.newInstance();
		domFactory.setIgnoringComments(true);
		DocumentBuilder builder;
		Document doc = null;
		try {
			builder = domFactory.newDocumentBuilder();
			System.out.println("Path:"
					+ XMLUtils.class.getProtectionDomain().getCodeSource()
							.getLocation().getPath());
			String currentPath = XMLUtils.class.getProtectionDomain()
					.getCodeSource().getLocation().getPath();
			doc = builder.parse(new File(currentPath + "configurations/Data.xml"));
			XPath xPath = XPathFactory.newInstance().newXPath();
			String expression = "/configurations/community[@name='GAP']/communityGroup[@name='GAPCG']/communityClient[@name='GAPCL']";
			Node node = (Node) xPath.compile(expression).evaluate(doc, XPathConstants.NODE);

			Element endPointElement = doc.createElement("EndpointMessage");
			endPointElement.setAttribute("id", endpoingMessageID);

			Element endpointTextElement = doc.createElement("Text");
			endpointTextElement.appendChild(doc.createTextNode(messageText));
			endPointElement.appendChild(endpointTextElement);

			Element endpointFieldsElement = doc.createElement("Fields");
			endpointFieldsElement.appendChild(doc.createTextNode(fields));
			endPointElement.appendChild(endpointFieldsElement);

			Element endpointCustomFieldsElement = doc
					.createElement("CustomeFields");
			endpointCustomFieldsElement.appendChild(doc
					.createTextNode(customFields));
			endPointElement.appendChild(endpointCustomFieldsElement);

			node.appendChild(endPointElement);

			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(doc);
			StreamResult streamResult = new StreamResult(new File(currentPath
					+ "configurations/Data.xml"));
			StreamResult resultLocal = new StreamResult(new File(
					"C:\\projects\\Data.xml"));
			transformer.transform(domSource, streamResult);
			transformer.transform(domSource, resultLocal);

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	public static void saveXML(String xmlString, String targetXMLFileName) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		DocumentBuilder builder;
		try {
			String wellFormedXML = escapeXMLCharacters(xmlString);
			System.out.println("wellFormedXML:"+wellFormedXML);
			builder = factory.newDocumentBuilder();

			Document document = builder.parse(new InputSource(new StringReader(
					wellFormedXML)));

			TransformerFactory tranFactory = TransformerFactory.newInstance();
			Transformer aTransformer = tranFactory.newTransformer();
			Source src = new DOMSource(document);
			Result dest = new StreamResult(new File(
					"C:\\projects\\workspace\\SelCukesFW\\src\\test\\resources\\"
							+ targetXMLFileName));// "c:\\projects\\delete\\xmlFileName.xml"
			// ) );
			aTransformer.transform(src, dest);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static String escapeXMLCharacters(String rawXML)
	{
		String output = rawXML;
		output = output.replaceAll("&lt;", "<");
		output = output.replaceAll("&gt;", ">");
		output = output.replaceAll("&amp;", "&");
		output = output.replaceAll("&quot;", "\"");
		output = output.replaceAll("&apos;", "'");
		return output;
	}
	
	public static String getCDataXML(String xmlString) throws Exception
	{
		CustomSAXParser ref = new CustomSAXParser();
		return ref.parseString(xmlString);
		
		
	}
}
