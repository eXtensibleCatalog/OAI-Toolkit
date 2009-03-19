/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package info.extensiblecatalog.OAIToolkit.struts.action;

import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import info.extensiblecatalog.OAIToolkit.api.Facade;
import info.extensiblecatalog.OAIToolkit.oai.RecordCacher;
import info.extensiblecatalog.OAIToolkit.struts.form.OaiRequestForm;
import info.extensiblecatalog.OAIToolkit.utils.Logging;

/** 
 * MyEclipse Struts
 * Creation date: 03-03-2008
 * 
 * XDoclet definition:
 * @struts.action path="/oaiRequest" name="oaiRequestForm" input="/form/oaiRequest.jsp" scope="request" validate="true"
 */
public class OaiRequestAction extends Action {

	/** The programmer's log object */
        private static String programmer_log = "programmer";
        private static final Logger prglog = Logging.getLogger(programmer_log);
	//private static final Logger logger = Logging.getLogger();

	/** 
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		OaiRequestForm oaiForm = (OaiRequestForm) form;
		String verb = oaiForm.getVerb();
		prglog.info("[PRG] verb = " + verb);
		
		Facade facade = new Facade(oaiForm);
		facade.setResponseHeader(request.getRequestURL());
		
		if("Identify".equals(verb)) {
			facade.doIdentify();
			return mapping.findForward("Identify");
		} else if("ListSets".equals(verb)) {
			facade.doListSets();
			return mapping.findForward("ListSets");
		} else if("ListMetadataFormats".equals(verb)) {
			facade.doListMetadataFormats();
			return mapping.findForward("ListMetadataFormats");
		} else if("ListIdentifiers".equals(verb)) {
			facade.doListIdentifiers();
			return mapping.findForward("ListIdentifiers");
		} else if("ListRecords".equals(verb)) {
			facade.setMarcXMLSchema(
					request.getScheme() 
					+ "://" + request.getServerName() 
					+ ":" +  request.getServerPort() 
					+ request.getContextPath()
			);
			boolean hasMoreResult = facade.doListRecords();
			if(hasMoreResult && oaiForm.isCacheable()) {
				Thread recordCacheRunner = new Thread(new RecordCacher(facade));
				recordCacheRunner.start();
				prglog.info("[PRG] Thread started");
			}
			prglog.info("[PRG] findForward");
			return mapping.findForward("ListRecords");
		} else if("GetRecord".equals(verb)) {
			facade.setMarcXMLSchema(
					request.getScheme() 
					+ "://" + request.getServerName() 
					+ ":" +  request.getServerPort() 
					+ request.getContextPath()
			);
			facade.doGetRecord();
			if(oaiForm.isCacheable()) {
				Thread recordCacheRunner = new Thread(new RecordCacher(facade));
				recordCacheRunner.start();
				prglog.info("[PRG] Thread started");
			}
			prglog.info("[PRG] findForward");
			return mapping.findForward("GetRecord");
		/*
		} else if("gp".equals(verb)) {
			Properties props = System.getProperties();
			Enumeration<Object> en = props.keys();
			StringBuffer sb = new StringBuffer();
			sb.append("<props><!--");
			while(en.hasMoreElements()) {
				String key = (String)en.nextElement();
				sb.append(key).append(": ").append(props.getProperty(key)).append("\n");
			}
			sb.append("--></props>");
			oaiForm.setXml(sb.toString());
			//props.list(System.out);
			return mapping.findForward("GetRecord");
		*/
		} else {
			facade.doIllegalVerb();
			return mapping.findForward("GetRecord");
		}

		//return mapping.getInputForward();
	}
}