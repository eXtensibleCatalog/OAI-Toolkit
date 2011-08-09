/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package test.extensiblecatalog.OAIToolkit;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import info.extensiblecatalog.OAIToolkit.utils.TextUtil;
import info.extensiblecatalog.OAIToolkit.utils.XMLValidator;
import info.extensiblecatalog.OAIToolkit.utils.XPathUtil;
import junit.framework.TestCase;

public class OAIServerTestCase extends TestCase {
	
	private XMLValidator validator;
	private String LOCAL   = "localhost:8160";
	private String REMOTE1 = "128.151.244.134:8090"; 
	private String REMOTE2 = "128.151.244.132:8080"; 
	private String baseUrl = "http://" + LOCAL + "/OAIToolkit/oai-request.do";
	
	protected void setUp() throws Exception {
		super.setUp();
		validator = new XMLValidator();
	}

	public void xtestIdentifier() throws IOException, SAXException {
		String url = baseUrl + "?verb=Identify";
		System.out.println("Testing " + url);

		String content = TextUtil.getWebContent(url);
		validate(content);

		XPathUtil xpathDoc = new XPathUtil(content);
		NodeList result;
		
		result = xpathDoc.evaluate("//responseDate");
		assertEquals("must have responseDate", 1, result.getLength());
		String responseDate = result.item(0).getTextContent();
		assertTrue("bad responseDate format", matches(responseDate, 
				"\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}Z"));
		
		result = xpathDoc.evaluate("//repositoryName");
		assertEquals("must have repositoryName", 1, result.getLength());

		result = xpathDoc.evaluate("//baseURL");
		assertEquals("must have baseURL", 1, result.getLength());

		result = xpathDoc.evaluate("//protocolVersion");
		assertEquals("must have protocolVersion", 1, result.getLength());

		result = xpathDoc.evaluate("//adminEmail");
		assertEquals("must have adminEmail", 1, result.getLength());

		result = xpathDoc.evaluate("//scheme");
		assertEquals("must have scheme", 1, result.getLength());
		String scheme = result.item(0).getTextContent();

		result = xpathDoc.evaluate("//repositoryIdentifier");
		assertEquals("must have repositoryIdentifier", 1, result.getLength());
		String repositoryIdentifier = result.item(0).getTextContent();

		result = xpathDoc.evaluate("//delimiter");
		assertEquals("must have delimiter", 1, result.getLength());
		String delimiter = result.item(0).getTextContent();

		result= xpathDoc.evaluate("//sampleIdentifier");
		assertEquals("must have sampleIdentifier", 1, result.getLength());
		String sampleIdentifier = result.item(0).getTextContent();
		
		assertTrue("sampleIdentifier pattern", sampleIdentifier.startsWith(
				scheme + delimiter + repositoryIdentifier + delimiter));
	}

	public void xtestListMetadataFormats() throws IOException, SAXException {
		String url = baseUrl + "?verb=ListMetadataFormats";
		System.out.println("Testing " + url);

		String content = TextUtil.getWebContent(url);
		validate(content);

		XPathUtil xpathDoc = new XPathUtil(content);
		NodeList result;
		
		result = xpathDoc.evaluate("/OAI-PMH/request[@verb='ListMetadataFormats']");
		assertEquals("must have <request verb='ListMetadataFormats'>", 1, result.getLength());
		
		result = xpathDoc.evaluate("//metadataPrefix[text()='oai_dc']");
		assertEquals("must support oai_dc", 1, result.getLength());
		assertEquals("oai_dc must have a schema", "schema", 
				result.item(0).getNextSibling().getNextSibling().getNodeName());
		assertEquals("oai_dc must have a metadataNamespace", "metadataNamespace", 
				result.item(0).getNextSibling().getNextSibling()
				.getNextSibling().getNextSibling().getNodeName());

		result = xpathDoc.evaluate("//metadataPrefix[text()='oai_marc']");
		assertEquals("must support oai_marc", 1, result.getLength());
		assertEquals("oai_marc must have a schema", "schema", 
				result.item(0).getNextSibling().getNextSibling().getNodeName());
		assertEquals("oai_marc must have a metadataNamespace", "metadataNamespace", 
				result.item(0).getNextSibling().getNextSibling()
				.getNextSibling().getNextSibling().getNodeName());

		result = xpathDoc.evaluate("//metadataPrefix[text()='marc21']");
		assertEquals("must support marc21", 1, result.getLength());
		assertEquals("marc21 must have a schema", "schema", 
				result.item(0).getNextSibling().getNextSibling().getNodeName());
		assertEquals("marc21 must have a metadataNamespace", "metadataNamespace", 
				result.item(0).getNextSibling().getNextSibling()
				.getNextSibling().getNextSibling().getNodeName());

	}

	public void xtestListSets() throws IOException, SAXException {
		String url = baseUrl + "?verb=ListSets";
		System.out.println("Testing " + url);

		String content = TextUtil.getWebContent(url);
		validate(content);

		XPathUtil xpathDoc = new XPathUtil(content);
		NodeList result;
		
		result = xpathDoc.evaluate("/OAI-PMH/request[@verb='ListSets']");
		assertEquals("must have <request verb='ListSets'>", 1, result.getLength());
		
		result = xpathDoc.evaluate("//setSpec[text()='bib']");
		assertEquals("must support bib", 1, result.getLength());
		assertEquals("bib must have a name", "setName", 
				result.item(0).getNextSibling().getNextSibling().getNodeName());

		result = xpathDoc.evaluate("//setSpec[text()='auth']");
		assertEquals("must support auth", 1, result.getLength());
		assertEquals("auth must have a schema", "setName", 
				result.item(0).getNextSibling().getNextSibling().getNodeName());

		result = xpathDoc.evaluate("//setSpec[text()='hold']");
		assertEquals("must support hold", 1, result.getLength());
		assertEquals("hold must have a schema", "setName", 
				result.item(0).getNextSibling().getNextSibling().getNodeName());

	}

	public void xtestListIdentifiers()  throws IOException, SAXException {
		String url = baseUrl + "?verb=ListIdentifiers";
		System.out.println("Testing " + url);

		String content = TextUtil.getWebContent(url);
		validate(content);

		XPathUtil xpathDoc = new XPathUtil(content);
		NodeList result;
		
		result = xpathDoc.evaluate("/OAI-PMH/request[@verb='ListIdentifiers']");
		assertEquals("must have <request verb='ListIdentifiers'>", 1, result.getLength());

	}

	public void xtestListIdentifiers_SetAuth() throws IOException, SAXException {
		String url = baseUrl + "?verb=ListIdentifiers&set=auth";
		System.out.println("Testing " + url);

		String content = TextUtil.getWebContent(url);
		validate(content);

		XPathUtil xpathDoc = new XPathUtil(content);
		NodeList result;
		
		result = xpathDoc.evaluate("/OAI-PMH/request[@verb='ListIdentifiers' and @set='auth']");
		assertEquals("must have <request verb='ListIdentifiers'>", 1, result.getLength());
	}

	public void xtestListIdentifiers_SetBib() throws IOException, SAXException {
		String url = baseUrl + "?verb=ListIdentifiers&set=bib";
		System.out.println("Testing " + url);

		String content = TextUtil.getWebContent(url);
		validate(content);

		XPathUtil xpathDoc = new XPathUtil(content);
		NodeList result;
		
		result = xpathDoc.evaluate("/OAI-PMH/request[@verb='ListIdentifiers'" +
				" and @set='bib']");
		assertEquals("must have <request verb='ListIdentifiers'>", 1, 
				result.getLength());
		
		result = xpathDoc.evaluate("//resumptionToken");
		assertEquals("must have <resumptionToken>", 1, result.getLength());
		
		Node resumptionTokenNode = result.item(0);
		int completeListSize = Integer.parseInt(resumptionTokenNode
				.getAttributes().getNamedItem("completeListSize")
				.getTextContent());
		assertTrue(completeListSize > 50);
		String resumptionToken = resumptionTokenNode.getTextContent();
		
		url = baseUrl + "?verb=ListIdentifiers&"
			+ "resumptionToken=" + resumptionToken;
		System.out.println("Testing " + url);

		content = TextUtil.getWebContent(url);
		validate(content);
		
		xpathDoc = new XPathUtil(content);
		//System.out.println(content);
		result = xpathDoc.evaluate("/OAI-PMH/request[@verb='ListIdentifiers'" +
				" and @resumptionToken='" + resumptionToken + "']");
		assertEquals("must have <request resumptionToken='" 
				+ resumptionToken + "'>", 1, result.getLength());
		
		result = xpathDoc.evaluate("//resumptionToken");
		assertEquals("must have <resumptionToken>", 1, result.getLength());
		resumptionToken = result.item(0).getTextContent();
		
		url = baseUrl + "?verb=ListIdentifiers&"
			+ "resumptionToken=" + resumptionToken;
		System.out.println("Testing " + url);

		content = TextUtil.getWebContent(url);
		validate(content);
		
		xpathDoc = new XPathUtil(content);
		//System.out.println(content);
		result = xpathDoc.evaluate("/OAI-PMH/request[@verb='ListIdentifiers'" +
				" and @resumptionToken='" + resumptionToken + "']");
		assertEquals("must have <request resumptionToken='" 
				+ resumptionToken + "'>", 1, result.getLength());

	}

	public void xtestListIdentifiers_GetBibRecords() throws IOException, SAXException {
		String url = baseUrl + "?verb=ListIdentifiers&set=bib";
		System.out.println("Testing " + url);

		String content = TextUtil.getWebContent(url);
		validate(content);

		XPathUtil xpathDoc = new XPathUtil(content);
		NodeList result;
		
		result = xpathDoc.evaluate("/OAI-PMH/request[@verb='ListIdentifiers'" +
				" and @set='bib']");
		assertEquals("must have <request verb='ListIdentifiers'>", 1, 
				result.getLength());
		
		NodeList identifiers = xpathDoc.evaluate("//identifier");
		assertTrue("must have more <identifier>s", identifiers.getLength() > 1);

		validator = new XMLValidator();
		for(int i=0; i<identifiers.getLength(); i++) {
			String id = identifiers.item(i).getTextContent();
			//System.out.println(id);
		
			url = baseUrl 
				+ "?verb=GetRecord"
				+ "&identifier=" + id 
				+ "&metadataPrefix=marc21";
			System.out.println("Testing " + url);

			content = TextUtil.getWebContent(url);
			try {
				validate(content);
			} catch(SAXParseException e) {
				System.out.println("not valid at " 
						+ e.getLineNumber() + ":" + e.getColumnNumber() 
						+ " " + e.getMessage());
			}
		
			xpathDoc = new XPathUtil(content);
			//System.out.println(content);
			result = xpathDoc.evaluate("/OAI-PMH/request[@verb='GetRecord'" +
				" and @identifier='" + id + "']");
			assertEquals("must have <request verb='GetRecord'>", 1, 
					result.getLength());
		
			result = xpathDoc.evaluate("//record");
			assertEquals("must have <record> for id " + id, 1, result.getLength());
		}
	}

	public void xtestListIdentifiers_SetHold() throws IOException, SAXException {
		String url = baseUrl + "?verb=ListIdentifiers&set=hold";
		System.out.println("Testing " + url);

		String content = TextUtil.getWebContent(url);
		validate(content);

		XPathUtil xpathDoc = new XPathUtil(content);
		NodeList result;
		
		result = xpathDoc.evaluate("/OAI-PMH/request[@verb='ListIdentifiers'"
				+ " and @set='hold']");
		assertEquals("must have <request verb='ListIdentifiers'>", 1, result.getLength());
	}

	public void xtestListIdentifier_fromUntil() throws IOException, SAXException, ParseException {
		String from = "1972-06-01T19:20:30Z";
		String until = "1998-07-01T19:20:30Z";
		String url = baseUrl + "?verb=ListIdentifiers" +
				"&from=" + from + "&until=" + until;
		System.out.println("Testing " + url);

		String content = TextUtil.getWebContent(url);
		validate(content);

		XPathUtil xpathDoc = new XPathUtil(content);
		NodeList result;
		
		result = xpathDoc.evaluate("/OAI-PMH/request[@verb='ListIdentifiers'"
			+ "and @from='" + from + "'" + "and @until='" + until + "']");
		assertEquals("must have <request>", 1, result.getLength());

		result = xpathDoc.evaluate("//header");
		assertNotSame("no result", 0, result.getLength());
		String from2 = TextUtil.utcToMysqlTimestamp(from);
		String until2 = TextUtil.utcToMysqlTimestamp(until);
		String datestamp;
		for(int i=0; i<result.getLength(); i++) {
			NodeList children = result.item(i).getChildNodes();
			for(int j=0; j<children.getLength(); j++){
				if(children.item(j).getNodeName().equals("datestamp")) {
					datestamp = children.item(j).getTextContent();
					assertTrue("datestamp is after from", 
							datestamp.compareTo(from2)  >= 0);
					assertTrue("datestamp is before until",
							datestamp.compareTo(until2) <= 0);
				}
			}
			
		}
	}

	public void xtestListRecords() throws IOException, SAXException {
		String url = baseUrl + "?verb=ListRecords";
		System.out.println("Testing " + url);

		String content = TextUtil.getWebContent(url);
		System.out.println("content ok");
		
		validate(content);
		System.out.println("validation ok");

		XPathUtil xpathDoc = new XPathUtil(content);
		System.out.println("XPAth ok");
		NodeList result;
		
		result = xpathDoc.evaluate("/OAI-PMH/request[@verb='ListRecords'"
			//+ "and @from='" + from + "'" + "and @until='" + until + "'" 
			+ "]");
		System.out.println("XPAth ListRecords ok");
		assertEquals("must have <request>", 1, result.getLength());

		result = xpathDoc.evaluate("/OAI-PMH/ListRecords/record/header/identifier");
		System.out.println("XPAth header ok");
		assertNotSame("no result", 0, result.getLength());
		//String from2 = TextUtil.utcToMysqlTimestamp(from);
		//String until2 = TextUtil.utcToMysqlTimestamp(until);
		/*
		String datestamp;
		for(int i=0; i<result.getLength(); i++) {
			NodeList children = result.item(i).getChildNodes();
			for(int j=0; j<children.getLength(); j++){
				if(children.item(j).getNodeName().equals("datestamp")) {
					datestamp = children.item(j).getTextContent();
					//assertTrue("datestamp is after from", datestamp.compareTo(from2)  >= 0);
					//assertTrue("datestamp is before until", datestamp.compareTo(until2) <= 0);
				}
			}
		}
		*/
		System.out.println("everything ok");

	}

	public void xtestError_BadVerb() throws IOException, SAXException {
		String url = baseUrl + "?verb=IllegalVerb";
		System.out.println("Testing " + url);

		String content = TextUtil.getWebContent(url);
		validate(content);

		XPathUtil xpathDoc = new XPathUtil(content);
		NodeList result;
		
		result = xpathDoc.evaluate("/OAI-PMH/request[not(@verb)]");
		assertEquals("must have <request>", 1, result.getLength());

		result = xpathDoc.evaluate("/OAI-PMH/error[@code='badVerb']");
		assertEquals("must have <error code='badVerb'>", 1, result.getLength());
		assertEquals("wrong error message", 
				"Illegal OAI verb.", 
				result.item(0).getTextContent());
	}
	
	public void xtestError_badId() throws IOException, SAXException {
		String url = baseUrl + "?verb=GetRecord" +
				"&identifier=oai:extensiblecatalog.info:6d" +
				"&metadataPrefix=oai_dc";
		System.out.println("Testing " + url);

		String content = TextUtil.getWebContent(url);
		validate(content);

		XPathUtil xpathDoc = new XPathUtil(content);
		NodeList result;
		
		result = xpathDoc.evaluate("/OAI-PMH/request[@verb='GetRecord' " +
				"and @identifier='oai:extensiblecatalog.info:6d' " +
				"and @metadataPrefix='oai_dc']");
		assertEquals("must have <request> with parameters", 1, result.getLength());

		result = xpathDoc.evaluate("/OAI-PMH/error[@code='badArgument']");
		assertEquals("must have <error code='badArgument'>", 1, result.getLength());
		assertTrue("wrong error message", 
				result.item(0).getTextContent().indexOf(
					"The identifier parameter should ends with an integer") > -1);
	}
	
	
	public void xtestError_noRecordsMatch() throws IOException, SAXException {
		String url = baseUrl + "?verb=ListIdentifiers" +
				"&from=1972-06-01T19:20:30Z" +
				"&until=1973-07-01T19:20:30Z";
		System.out.println("Testing " + url);

		String content = TextUtil.getWebContent(url);
		validate(content);

		XPathUtil xpathDoc = new XPathUtil(content);
		NodeList result;
		
		result = xpathDoc.evaluate("/OAI-PMH/request[@verb='ListIdentifiers' " +
				"and @from='1972-06-01T19:20:30Z' " +
				"and @until='1973-07-01T19:20:30Z']");
		assertEquals("must have <request> with parameters", 1, result.getLength());

		result = xpathDoc.evaluate("/OAI-PMH/error[@code='noRecordsMatch']");
		assertEquals("must have <error code='noRecordsMatch'>", 1, 
				result.getLength());
		assertEquals("wrong error message",
				"The combination of the values of the from, until, set, "
				+ "and metadataPrefix arguments results in an empty list.",
				result.item(0).getTextContent());
	}

	public void xtestError_IdDoesNotExist() throws IOException, SAXException {
		String url = baseUrl + "?verb=GetRecord" +
				"&identifier=oai:extensiblecatalog.info:0" +
				"&metadataPrefix=oai_dc";
		System.out.println("Testing " + url);

		String content = TextUtil.getWebContent(url);
		validate(content);

		XPathUtil xpathDoc = new XPathUtil(content);
		NodeList result;
		
		result = xpathDoc.evaluate("/OAI-PMH/request[@verb='GetRecord' " +
				"and @identifier='oai:extensiblecatalog.info:0' " +
				"and @metadataPrefix='oai_dc']");
		assertEquals("must have <request> with parameters", 1, result.getLength());

		result = xpathDoc.evaluate("//error[@code='idDoesNotExist']");
		assertEquals("no error", 1, result.getLength());
		assertEquals("wrong error message",
				"The value of the identifier argument is unknown or illegal "
				+ "in this repository.",
				result.item(0).getTextContent());
	}
	
	public void xtestATokenRemote() {
		String url = "http://128.151.244.132:8080/OAIToolkit/oai-request.do?verb=ListRecords&metadataPrefix=marc21&resumptionToken=289|";
		int offset = 500000;
		long t1;
		String content;
		for(int i=0; i<50; i++){
			offset += 5000;
			System.out.println("Testing " + url + offset);
			t1 = System.currentTimeMillis();
			content = TextUtil.getWebContent(url + offset);
			System.out.println("time: " + (System.currentTimeMillis()-t1) 
					+ " length: " + content.length());
		}
	}

	public void testATokenLocal() {
		/*
		String url = "http://" + LOCAL + "/OAIToolkit/oai-request.do" +
			"?verb=ListRecords&metadataPrefix=marc21&resumptionToken=43|"; //
		*/
		String url = "http://" + REMOTE2 + "/OAIToolkit/oai-request.do" +
				"?verb=ListRecords&metadataPrefix=marc21&resumptionToken=324|"; //43
		/*
		*/
		int DELTA = 5000, offset = 500000;
		long t0 = System.currentTimeMillis(), t1;
		String content;
		boolean testWithoutCache = false;
		if(testWithoutCache) {
			for(int i=0; i<10; i++){
				offset += DELTA;
				//System.out.println("Testing " + url + offset);
				t1 = System.currentTimeMillis();
				content = TextUtil.getWebContent(url + offset);
				System.out.println(i + ") time: " + (System.currentTimeMillis()-t1) 
					+ " length: " + content.length());
			}
			System.out.println("total time without cache: " 
					+ (System.currentTimeMillis()-t0));
		}

		System.out.println("CACHE");
		offset = 500000;
		t0 = System.currentTimeMillis();
		for(int i=0; i<10; i++){
			offset += DELTA;
			String testUrl = url + offset + "&cacheable=1";
			//System.out.println("Testing " + testUrl);
			t1 = System.currentTimeMillis();
			content = TextUtil.getWebContent(testUrl);
			System.out.println(i + ") time: " + (System.currentTimeMillis()-t1) 
					+ " length: " + content.length());
		}
		System.out.println("total time with cache: " + (System.currentTimeMillis()-t0));
	}
			
	public void xtestError_BadArgument() throws IOException, SAXException {
		String url = baseUrl + "?verb=GetRecord&metadataPrefix=oai_dc";
		System.out.println("Testing " + url);

		String content = TextUtil.getWebContent(url);
		validate(content);

		XPathUtil xpathDoc = new XPathUtil(content);
		NodeList result;
		
		result = xpathDoc.evaluate("/OAI-PMH/request[@verb='GetRecord' " +
				"and @metadataPrefix='oai_dc']");
		assertEquals("must have <request> with parameters", 1, result.getLength());

		result = xpathDoc.evaluate("//error[@code='badArgument']");
		assertEquals("no error", 1, result.getLength());
		assertEquals("wrong error message",
			"The request includes illegal arguments or is missing required "
			+ "arguments. Missing identifier parameter",
			result.item(0).getTextContent());
	}

	public void xtestMetadataPrefixes() {
		List<String> metadataFormats = getMetadataFormats();
		assertEquals(5, metadataFormats.size());
	}

	public void xtestSets() {
		List<String> metadataFormats = getSets();
		assertEquals(5, metadataFormats.size());
	}
	
	public List<String> getMetadataFormats() {
		
		List<String> metadataFormats = new ArrayList<String>();
		String content = TextUtil.getWebContent(baseUrl + "?verb=ListMetadataFormats");
		XPathUtil xpathDoc = new XPathUtil(content);
		
		NodeList results = xpathDoc.evaluate("//metadataPrefix");
		for(int i=0; i<results.getLength(); i++) {
			metadataFormats.add(results.item(i).getTextContent());
		}
		
		return metadataFormats;
	}

	public List<String> getSets() {
		
		List<String> sets = new ArrayList<String>();
		String content = TextUtil.getWebContent(baseUrl + "?verb=ListSets");
		XPathUtil xpathDoc = new XPathUtil(content);
		
		NodeList results = xpathDoc.evaluate("//setSpec");
		for(int i=0; i<results.getLength(); i++) {
			sets.add(results.item(i).getTextContent());
		}
		
		return sets;
	}

	public String getSchemaUrl(String content) {
		
		String xsd = "";
		XPathUtil xpathDoc = new XPathUtil(content);
		
		NodeList results = xpathDoc.evaluate("/OAI-PMH/@schemaLocation");
		if(results.getLength() == 1) {
			String raw = results.item(0).getTextContent();
			int i = raw.indexOf(' ');
			xsd = raw.substring(i+1);
		}
		
		return xsd;
	}

	private boolean find(String content, String pattern) {
		return Pattern.compile(pattern).matcher(content).find();
	}

	private boolean matches(String content, String pattern) {
		return Pattern.compile(pattern).matcher(content).matches();
	}
	
	private void validate(String content) throws SAXParseException, 
			SAXException, IOException {
		try {
			validator.validate(content);
		} catch(SAXParseException e) {
			System.out.println(e.getMessage() + "\n" 
				+ validator.showContext(e, content));
			throw e; 
		}
	}

}
