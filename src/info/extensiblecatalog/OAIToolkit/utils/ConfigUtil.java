/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package info.extensiblecatalog.OAIToolkit.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
        //System.out.println("The config file is " + configFile);
        //System.out.println("The config file name is " + configFileName);

		if (!(new File(configFile.getPath()).exists())) {
			prglog.error("[PRG] Inexistent configuration file: " + configFileName);
			throw new Exception("Inexistent configuration file: "
					+ configFileName );
		}

        BufferedReader re = new BufferedReader(new FileReader(configFile.getPath()));
/*        String tmpfile = "tmpfile.txt";
        // Create new file
        File temp = new File(tmpfile);

        boolean success = temp.createNewFile();
        if (success) {
             System.out.println("Its success");
            //File did not exist and was created
        } else {
             System.out.println("File already exists");
             //File already exists
        }


         if (!temp.exists())
            throw new IllegalArgumentException("no such file or directory: " + temp);

        if (!temp.canWrite())
            throw new IllegalArgumentException("Write protected: " + temp);

        System.out.println("Temporary file created. File is " + temp);
        //System.out.println("Temporary file created. Path is " + temp.getPath());

        BufferedWriter out = new BufferedWriter(new FileWriter(temp));
        while(true)
        {
            String s = re.readLine();
            //System.out.println(s);
            if(s!=null) {
                s = s.replaceAll("\\\\", "/");
                //System.out.println(s);
                out.write(s + System.getProperty("line.separator"));
                out.flush();
            }
            else
                break;
        }
        out.close();
*/
		try {
			//PropertiesConfiguration prop = new PropertiesConfiguration(temp);
			PropertiesConfiguration prop = new PropertiesConfiguration(configFile.getPath());
			prglog.info("[PRG] successful ConfigUtil::load");
            //temp.deleteOnExit();
            /*boolean dsuccess = temp.delete();
            if (!dsuccess)
                throw new IllegalArgumentException("Delete: deletion failed");*/
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
