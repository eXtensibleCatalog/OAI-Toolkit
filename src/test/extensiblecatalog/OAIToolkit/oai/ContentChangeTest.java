/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package test.extensiblecatalog.OAIToolkit.oai;

import junit.framework.TestCase;

public class ContentChangeTest extends TestCase {

	private static final String NS_DECL = 
		" xmlns:marc=\"http://www.loc.gov/MARC21/slim\""
		+ " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
		+ " xsi:schemaLocation=\"http://www.loc.gov/MARC21/slim "
		+ "http://localhost:1860/OAIToolkit/schema/MARC21slim_custom.xsd";

	private String content = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><collection xmlns=\"http://www.loc.gov/MARC21/slim\"><record><leader>01202cam a22003490  45 A</leader><controlfield tag=\"001\">2</controlfield><controlfield tag=\"005\">20010508133058.0</controlfield><controlfield tag=\"008\">760302s1976    nyu      s    001 0 eng  </controlfield><datafield tag=\"010\" ind1=\" \" ind2=\" \"><subfield code=\"a\">76007005</subfield></datafield><datafield tag=\"020\" ind1=\" \" ind2=\" \"><subfield code=\"a\">0393011305</subfield></datafield><datafield tag=\"035\" ind1=\" \" ind2=\" \"><subfield code=\"b\">ocl</subfield><subfield code=\"a\">02089674</subfield></datafield><datafield tag=\"035\" ind1=\" \" ind2=\" \"><subfield code=\"9\">00000005</subfield></datafield><datafield tag=\"040\" ind1=\" \" ind2=\" \"><subfield code=\"a\">DLC</subfield><subfield code=\"c\">DLC</subfield><subfield code=\"d\">CHS</subfield><subfield code=\"d\">RRR</subfield></datafield><datafield tag=\"049\" ind1=\" \" ind2=\" \"><subfield code=\"a\">RRRR</subfield></datafield><datafield tag=\"050\" ind1=\"0\" ind2=\" \"><subfield code=\"a\">RC514</subfield><subfield code=\"b\">.H34 1976</subfield></datafield><datafield tag=\"096\" ind1=\" \" ind2=\" \"><subfield code=\"a\">WM 203 H323 1976</subfield></datafield><datafield tag=\"099\" ind1=\" \" ind2=\" \"><subfield code=\"a\">RC514 .H34 1976</subfield><subfield code=\"a\">WM 203 H323 1976</subfield></datafield><datafield tag=\"245\" ind1=\"0\" ind2=\"2\"><subfield code=\"a\">A Harry Stack Sullivan case seminar :</subfield><subfield code=\"b\">treatment of a young male schizophrenic /</subfield><subfield code=\"c\">with comment, twenty-five years later, by John C. Dillingham ... [et al.] ; Robert G. Kvarnes, editor, Gloria H. Parloff, assistant editor.</subfield></datafield><datafield tag=\"250\" ind1=\" \" ind2=\" \"><subfield code=\"a\">1st ed.</subfield></datafield><datafield tag=\"260\" ind1=\" \" ind2=\" \"><subfield code=\"a\">New York :</subfield><subfield code=\"b\">Norton,</subfield><subfield code=\"c\">c1976.</subfield></datafield><datafield tag=\"300\" ind1=\" \" ind2=\" \"><subfield code=\"a\">xvii, 241 p. ;</subfield><subfield code=\"c\">22 cm.</subfield></datafield><datafield tag=\"500\" ind1=\" \" ind2=\" \"><subfield code=\"a\">Includes index.</subfield></datafield><datafield tag=\"600\" ind1=\"1\" ind2=\"0\"><subfield code=\"a\">Sullivan, Harry Stack,</subfield><subfield code=\"d\">1892-1949.</subfield></datafield><datafield tag=\"650\" ind1=\" \" ind2=\"0\"><subfield code=\"a\">Schizophrenia</subfield><subfield code=\"x\">Case studies.</subfield></datafield><datafield tag=\"650\" ind1=\" \" ind2=\"2\"><subfield code=\"a\">Schizophrenia</subfield><subfield code=\"x\">case studies.</subfield></datafield><datafield tag=\"700\" ind1=\"1\" ind2=\" \"><subfield code=\"a\">Sullivan, Harry Stack,</subfield><subfield code=\"d\">1892-1949.</subfield></datafield><datafield tag=\"700\" ind1=\"1\" ind2=\" \"><subfield code=\"a\">Dillingham, John C.</subfield></datafield><datafield tag=\"700\" ind1=\"1\" ind2=\" \"><subfield code=\"a\">Kvarnes, Robert G.</subfield></datafield><datafield tag=\"700\" ind1=\"1\" ind2=\" \"><subfield code=\"a\">Parloff, Gloria H.</subfield></datafield><datafield tag=\"911\" ind1=\" \" ind2=\" \"><subfield code=\"a\">p</subfield></datafield><datafield tag=\"966\" ind1=\" \" ind2=\" \"><subfield code=\"l\">Stack*</subfield><subfield code=\"m\">MMON</subfield><subfield code=\"s\">WM 203 H323 1976</subfield></datafield><datafield tag=\"966\" ind1=\" \" ind2=\" \"><subfield code=\"l\">Ref</subfield><subfield code=\"m\">RJCIRC</subfield><subfield code=\"s\">RC514 .H34 1976</subfield><subfield code=\"b\">39087007145107</subfield></datafield></record></collection>";

	public void test() {

		String content2 = content.replaceAll("<\\?xml [^<>]+\\?>", "")
				.replaceAll("<collection[^<>]+>", "")
				.replace("</collection>", "")
				.replace("</collection>", "")
				.replace("<record", "<record" + NS_DECL)
				.replace("</", "</marc:")
				.replaceAll("<([^/])", "<marc:$1")
		;
		System.out.println(content2);
	}
}
