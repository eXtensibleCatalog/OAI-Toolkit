/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package info.extensiblecatalog.OAIToolkit.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/** 
 * Class for xslt transformations
 * @author Peter Kiraly
 */
public class XsltTransformator {
	
	private Transformer transformer;
	
	/**
	 * Create a new XSLT transformator
	 */
	public XsltTransformator() {}

	/**
	 * Create a new XSLT transformator based on an XSLT file.
	 * @param xsltFile The XSLT file
	 * @throws FileNotFoundException
	 * @throws TransformerConfigurationException
	 */
	public XsltTransformator(File xsltFile) throws FileNotFoundException,
			TransformerConfigurationException {
		this(xsltFile.getAbsolutePath());
	}

	/**
	 * Create a new XSLT transformator based on an XSLT file.
	 * @param xsltFileName The XSLT file
	 * @throws FileNotFoundException
	 * @throws TransformerConfigurationException
	 */
	public XsltTransformator(String xsltFileName) throws FileNotFoundException,
			TransformerConfigurationException {
		InputStream xslStr = new FileInputStream(xsltFileName);

		TransformerFactory tFactory = TransformerFactory.newInstance();
		transformer = tFactory.newTransformer(new StreamSource(
				xslStr));
	}

	/**
	 * Transform XML file with XSLT
	 * @param xmlFile The XML source we should transform
	 * @return The string representation of the newly created content
	 * @throws TransformerException
	 * @throws IOException
	 */
	public String transform(File xmlFile) throws TransformerException, IOException {
		return transform(TextUtil.readFileAsString(xmlFile));
	}

	/** 
	 * Transform XML content.
	 * @param xmlSource The XML source content
	 * @param xsltFileName The XSLT file name
	 * @return The transformed content
	 * @throws FileNotFoundException
	 * @throws TransformerConfigurationException
	 * @throws TransformerException
	 */
	public String transform(String xmlSource) throws TransformerException {

		Writer out = new StringWriter();
		StreamResult dest = new StreamResult(out);
		Source src = new StreamSource(new java.io.StringReader(xmlSource));
		transformer.transform(src, dest);
		return out.toString();
	}

	/**
	 * Set uo parameters for the XSLT file. It will populate the XSLT's root 
	 * &lt;xsl:param&gt; elements. The parameters should be in a map of 
	 * key-value format.
	 * @param params The actual parameters used by XSLT 
	 */
	public void setParams(Map<String, String> params) {
		if (null != params && params.size() > 0) {
			for(Map.Entry<String, String> e : params.entrySet()) {
				transformer.setParameter(e.getKey(), e.getValue());
			}
		}
	}
}
