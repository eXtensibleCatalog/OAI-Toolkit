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
import java.net.URL;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

/**
 * Helper class to find and load properties
 * @author Peter Kiraly
 */
public class ConfigUtil {

	/** The programmer's log object */
        private static String programmer_log = "programmer";
        private static final Logger prglog = Logging.getLogger(programmer_log);
        //private static final Logger logger = Logging.getLogger();

	public static PropertiesConfiguration load(String configFileName)
			throws Exception {
		prglog.info("[PRG] ConfigUtil::load("+configFileName+")");
		ClassLoader cloader = ConfigUtil.class.getClassLoader();
		URL configFile = cloader.getResource(configFileName);
		if (null == configFile) {
			File f = new File(configFileName);
			prglog.info("[PRG] load from file: " + f.getAbsolutePath());
			configFile = new URL("file", "", f.getAbsolutePath());
		}
		prglog.info("config file: " + configFile.getPath());

		if (!(new File(configFile.getPath()).exists())) {
			prglog.error("[PRG] Inexistent configuration file: " + configFileName);
			throw new Exception("Inexistent configuration file: "
					+ configFileName );
		}

		try {
			PropertiesConfiguration prop = new PropertiesConfiguration(configFile);
			prglog.info("[PRG] successful ConfigUtil::load");
			return prop;
		} catch (ConfigurationException e) {
			prglog.error("[PRG] Unable to load properties from configuration file: "
					+ configFileName + ". " + e.getMessage());
			throw new Exception(
					"Unable to load properties from configuration file: "
							+ configFileName + ". " + e.getMessage());
		}
	}
}
