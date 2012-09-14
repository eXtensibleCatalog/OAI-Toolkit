/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package info.extensiblecatalog.OAIToolkit.oai;

import org.apache.log4j.Logger;

import info.extensiblecatalog.OAIToolkit.api.Facade;
import info.extensiblecatalog.OAIToolkit.utils.ApplInfo;
import info.extensiblecatalog.OAIToolkit.utils.Logging;
import info.extensiblecatalog.OAIToolkit.utils.ApplInfo.RequestState;

/**
 * Run the cache creation phase in a distinct thread
 * @author kiru
 */
public class RecordCacher implements Runnable {
	
	/** The programmer's log object */
        private static String programmer_log = "programmer";
        private static final Logger prglog = Logging.getLogger(programmer_log);
	//private static final Logger logger = Logging.getLogger();

	/** The facade object */
	private Facade facade;
	
	public RecordCacher(Facade facade) {
		this.facade = facade;
	}
	
	/** run create cache in a distinct thread */
	public void run() {
		long t0 = System.currentTimeMillis();
		String caheId = facade.getCacheId();
		prglog.info("[PRG] " + caheId + " cache registered? " + 
				ApplInfo.cacheRegister.containsKey(caheId));
		if(!ApplInfo.cacheRegister.containsKey(caheId)) {
			ApplInfo.cacheRegister.put(caheId, RequestState.STARTED);
			prglog.info("[PRG] " + caheId + " registered: " 
					+ ApplInfo.cacheRegister.containsKey(caheId));
			facade.createCache();
			ApplInfo.cacheRegister.put(caheId, RequestState.FINISHED);
		}
		prglog.info("[PRG] Thread finished in " + (System.currentTimeMillis()-t0));
	}
}
