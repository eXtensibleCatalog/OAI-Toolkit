/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/.
  *
  */

package info.extensiblecatalog.OAIToolkit.utils;

import info.extensiblecatalog.OAIToolkit.utils.ConfigUtil;
import info.extensiblecatalog.OAIToolkit.utils.Logging;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

/**
 *
 * @author shreyanshv
 */
public class XcOaiIdConfigUtil {

    private static String programmer_log = "programmer";
    private static final Logger prglog = Logging.getLogger(programmer_log);

    private static String oaiIdDomainName;
	private static String oaiIdRepositoryIdentifier;

    private XcOaiIdConfigUtil(String fileName) throws Exception {
		init(fileName);
	}

	private XcOaiIdConfigUtil() throws Exception {
		init("xcoaiid.properties");
	}

    public static void init(String fileName) throws Exception {

        PropertiesConfiguration props = ConfigUtil.load(fileName);

		oaiIdDomainName = (String) props.getProperty("oaiIdentifierDomainName");
		oaiIdRepositoryIdentifier = (String) props.getProperty("oaiIdentifierRepositoryIdentifier");

		prglog.info("[PRG] XC OAI ID parameters: fileName: " + fileName
				+ ", Oai Identifier Domain name: " + oaiIdDomainName
				+ ", Oai Id Repository Identifier: " + oaiIdRepositoryIdentifier
				);
		}

    public static String getOaiIdDomainName() {
        return oaiIdDomainName;
    }

    public static String getOaiIdRepositoryIdentifier() {
        return oaiIdRepositoryIdentifier;
    }

}
