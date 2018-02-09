package com.obm.utilities;

import java.io.ByteArrayInputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.ParserAdapter;

public class CustomSAXParser extends DefaultHandler {
//	private static String START_TAG = "start";
	private static String cDataValue = "";

	@Override
	public synchronized void startElement(String namespace, String localName, String qName, Attributes atts) {
//		if (localName.equals(START_TAG)) {
//			System.out.println(START_TAG + " encountered");

//		}
	}

	@Override
	public synchronized void characters(char[] cbuf, int start, int len) {
//		System.out.print("Characters:");
//		System.out.println(new String(cbuf, start, len));
		cDataValue = cDataValue + new String(cbuf, start, len);
		
	}

	@Override
	public synchronized void processingInstruction(String target, String data) throws SAXException {
//		System.out.println("ProcessingInstruction:" + target + " " + data);
	}

	public  synchronized String parseString(String msgXML) throws Exception {

		System.out.println("Processing Message " + msgXML);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
//		System.out.println("CLASS :" + sp.getClass());
//		System.out.println(System.getProperty("javax.xml.parsers.SAXParserFactory"));
		ParserAdapter pa = new ParserAdapter(sp.getParser());
		pa.setContentHandler(this);
		byte bytearray[] = msgXML.getBytes();
		ByteArrayInputStream bais = new ByteArrayInputStream(bytearray);
		pa.parse(new InputSource(bais));
		return cDataValue;

	}

	public static void main(String[] args) throws Exception {
		CustomSAXParser ref = new CustomSAXParser();
		// ref.parseString("<start><![CDATA['This is the cdata']]></start>");
		ref.parseString("<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"><soap:Body><GetWeatherResponse xmlns=\"http://www.webserviceX.NET\"><GetWeatherResult>&lt;?xml version=\"1.0\" encoding=\"utf-16\"?&gt;&lt;CurrentWeather&gt;  &lt;Location&gt;CHICAGO/WAUKEGAN REGIONAL, IL, United States (KUGN) 42-25N 087-52W&lt;/Location&gt;  &lt;Time&gt;Sep 26, 2014 - 03:24 AM EDT / 2014.09.26 0724 UTC&lt;/Time&gt;  &lt;Wind&gt; Calm:0&lt;/Wind&gt;  &lt;Visibility&gt; 3/4 mile(s):0&lt;/Visibility&gt;  &lt;SkyConditions&gt; clear&lt;/SkyConditions&gt;  &lt;Temperature&gt; 51.1 F (10.6 C)&lt;/Temperature&gt;  &lt;DewPoint&gt; 50.0 F (10.0 C)&lt;/DewPoint&gt;  &lt;RelativeHumidity&gt; 96%&lt;/RelativeHumidity&gt;  &lt;Pressure&gt; 30.23 in. Hg (1023 hPa)&lt;/Pressure&gt;  &lt;Status&gt;Success&lt;/Status&gt;&lt;/CurrentWeather&gt;</GetWeatherResult></GetWeatherResponse></soap:Body></soap:Envelope>");
		System.out.println(cDataValue);
	}
}
