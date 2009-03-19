/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package test.extensiblecatalog.OAIToolkit.DTOs;

import info.extensiblecatalog.OAIToolkit.DTOs.ResumptionTokenDTO;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Date;
import junit.framework.*;

/**
 * The class <code>ResumptionTokenDTOTestCase</code> contains tests for 
 * the class <code>{@link ResumptionTokenDTO}</code>.
 *
 * @generatedBy CodePro at 2008.05.15. 18:03
 * @author kiru
 * @version $Revision: 1.0 $
 */
public class ResumptionTokenDTOTestCase extends TestCase {

	private ResumptionTokenDTO fixture1;
	private ResumptionTokenDTO fixture2;
	private ResumptionTokenDTO fixture3;
	private ResumptionTokenDTO fixture4;
	private ResumptionTokenDTO fixture5;
	private ResumptionTokenDTO fixture6;
	private ResumptionTokenDTO fixture7;
	private ResumptionTokenDTO fixture8;
	private ResumptionTokenDTO fixture9;
	private ResumptionTokenDTO fixture10;
	private ResumptionTokenDTO fixture11;
	private ResumptionTokenDTO fixture12;
	private ResumptionTokenDTO fixture13;
	private ResumptionTokenDTO fixture14;
	private ResumptionTokenDTO fixture15;
	private ResumptionTokenDTO fixture16;
	private ResumptionTokenDTO fixture17;
	private ResumptionTokenDTO fixture18;
	private ResumptionTokenDTO fixture19;
	private ResumptionTokenDTO fixture20;
	private ResumptionTokenDTO fixture21;
	private ResumptionTokenDTO fixture22;
	private ResumptionTokenDTO fixture23;
	private ResumptionTokenDTO fixture24;
	private ResumptionTokenDTO fixture25;
	private ResumptionTokenDTO fixture26;
	private ResumptionTokenDTO fixture27;
	private ResumptionTokenDTO fixture28;
	private ResumptionTokenDTO fixture29;
	private ResumptionTokenDTO fixture30;

	public ResumptionTokenDTO getFixture1()
		throws Exception {
		if (fixture1 == null) {
			fixture1 = new ResumptionTokenDTO();
		}
		return fixture1;
	}

	public ResumptionTokenDTO getFixture2()
		throws Exception {
		if (fixture2 == null) {
			fixture2 = new ResumptionTokenDTO();
			fixture2.setCreationDate(new Timestamp(1591115236000L));
			fixture2.setId(Integer.valueOf(-1));
			fixture2.setMetadataPrefix("");
			fixture2.setQuery("");
			fixture2.setQueryForCount("");
		}
		return fixture2;
	}

	public ResumptionTokenDTO getFixture3()
		throws Exception {
		if (fixture3 == null) {
			fixture3 = new ResumptionTokenDTO();
			fixture3.setCreationDate(new Timestamp(1591115236000L));
			fixture3.setId(Integer.valueOf(-1));
			fixture3.setMetadataPrefix("0123456789");
			fixture3.setQuery("0123456789");
			fixture3.setQueryForCount("");
		}
		return fixture3;
	}

	public ResumptionTokenDTO getFixture4()
		throws Exception {
		if (fixture4 == null) {
			fixture4 = new ResumptionTokenDTO();
			fixture4.setCreationDate(new Timestamp(1591115236000L));
			fixture4.setId(Integer.valueOf(-1));
			fixture4.setMetadataPrefix("0123456789");
			fixture4.setQuery("0123456789");
			fixture4.setQueryForCount("0123456789");
		}
		return fixture4;
	}

	public ResumptionTokenDTO getFixture5()
		throws Exception {
		if (fixture5 == null) {
			fixture5 = new ResumptionTokenDTO();
			fixture5.setCreationDate(new Timestamp(1591115236000L));
			fixture5.setId(Integer.valueOf(-1));
			fixture5.setMetadataPrefix("0123456789");
			fixture5.setQuery("0123456789");
			fixture5.setQueryForCount("Ant-1.0.txt");
		}
		return fixture5;
	}

	public ResumptionTokenDTO getFixture6()
		throws Exception {
		if (fixture6 == null) {
			fixture6 = new ResumptionTokenDTO();
			fixture6.setCreationDate(new Timestamp(1591115236000L));
			fixture6.setId(Integer.valueOf(0));
			fixture6.setMetadataPrefix("0123456789");
			fixture6.setQuery("0123456789");
			fixture6.setQueryForCount("");
		}
		return fixture6;
	}

	public ResumptionTokenDTO getFixture7()
		throws Exception {
		if (fixture7 == null) {
			fixture7 = new ResumptionTokenDTO();
			fixture7.setCreationDate(new Timestamp(1591115236000L));
			fixture7.setId(Integer.valueOf(0));
			fixture7.setMetadataPrefix("0123456789");
			fixture7.setQuery("0123456789");
			fixture7.setQueryForCount("0123456789");
		}
		return fixture7;
	}

	public ResumptionTokenDTO getFixture8()
		throws Exception {
		if (fixture8 == null) {
			fixture8 = new ResumptionTokenDTO();
			fixture8.setCreationDate(new Timestamp(1591115236000L));
			fixture8.setId(Integer.valueOf(0));
			fixture8.setMetadataPrefix("Ant-1.0.txt");
			fixture8.setQuery("Ant-1.0.txt");
			fixture8.setQueryForCount("Ant-1.0.txt");
		}
		return fixture8;
	}

	public ResumptionTokenDTO getFixture9()
		throws Exception {
		if (fixture9 == null) {
			fixture9 = new ResumptionTokenDTO();
			fixture9.setCreationDate(new Timestamp(1591115236000L));
			fixture9.setId(Integer.valueOf(1));
			fixture9.setMetadataPrefix("Ant-1.0.txt");
			fixture9.setQuery("Ant-1.0.txt");
			fixture9.setQueryForCount("");
		}
		return fixture9;
	}

	public ResumptionTokenDTO getFixture10()
		throws Exception {
		if (fixture10 == null) {
			fixture10 = new ResumptionTokenDTO();
			fixture10.setCreationDate(new Timestamp(1591115236000L));
			fixture10.setId(Integer.valueOf(1));
			fixture10.setMetadataPrefix("Ant-1.0.txt");
			fixture10.setQuery("Ant-1.0.txt");
			fixture10.setQueryForCount("0123456789");
		}
		return fixture10;
	}

	public ResumptionTokenDTO getFixture11()
		throws Exception {
		if (fixture11 == null) {
			fixture11 = new ResumptionTokenDTO();
			fixture11.setCreationDate(new Timestamp(1591115236000L));
			fixture11.setId(Integer.valueOf(1));
			fixture11.setMetadataPrefix("Ant-1.0.txt");
			fixture11.setQuery("Ant-1.0.txt");
			fixture11.setQueryForCount("Ant-1.0.txt");
		}
		return fixture11;
	}

	public ResumptionTokenDTO getFixture12()
		throws Exception {
		if (fixture12 == null) {
			fixture12 = new ResumptionTokenDTO();
			fixture12.setCreationDate(new Timestamp(644344036000L));
			fixture12.setId(Integer.valueOf(-1));
			fixture12.setMetadataPrefix("0123456789");
			fixture12.setQuery("0123456789");
			fixture12.setQueryForCount("");
		}
		return fixture12;
	}

	public ResumptionTokenDTO getFixture13()
		throws Exception {
		if (fixture13 == null) {
			fixture13 = new ResumptionTokenDTO();
			fixture13.setCreationDate(new Timestamp(644344036000L));
			fixture13.setId(Integer.valueOf(-1));
			fixture13.setMetadataPrefix("0123456789");
			fixture13.setQuery("0123456789");
			fixture13.setQueryForCount("0123456789");
		}
		return fixture13;
	}

	public ResumptionTokenDTO getFixture14()
		throws Exception {
		if (fixture14 == null) {
			fixture14 = new ResumptionTokenDTO();
			fixture14.setCreationDate(new Timestamp(644344036000L));
			fixture14.setId(Integer.valueOf(-1));
			fixture14.setMetadataPrefix("0123456789");
			fixture14.setQuery("0123456789");
			fixture14.setQueryForCount("Ant-1.0.txt");
		}
		return fixture14;
	}

	public ResumptionTokenDTO getFixture15()
		throws Exception {
		if (fixture15 == null) {
			fixture15 = new ResumptionTokenDTO();
			fixture15.setCreationDate(new Timestamp(644344036000L));
			fixture15.setId(Integer.valueOf(0));
			fixture15.setMetadataPrefix("0123456789");
			fixture15.setQuery("0123456789");
			fixture15.setQueryForCount("");
		}
		return fixture15;
	}

	public ResumptionTokenDTO getFixture16()
		throws Exception {
		if (fixture16 == null) {
			fixture16 = new ResumptionTokenDTO();
			fixture16.setCreationDate(new Timestamp(644344036000L));
			fixture16.setId(Integer.valueOf(0));
			fixture16.setMetadataPrefix("0123456789");
			fixture16.setQuery("0123456789");
			fixture16.setQueryForCount("0123456789");
		}
		return fixture16;
	}

	public ResumptionTokenDTO getFixture17()
		throws Exception {
		if (fixture17 == null) {
			fixture17 = new ResumptionTokenDTO();
			fixture17.setCreationDate(new Timestamp(644344036000L));
			fixture17.setId(Integer.valueOf(0));
			fixture17.setMetadataPrefix("Ant-1.0.txt");
			fixture17.setQuery("Ant-1.0.txt");
			fixture17.setQueryForCount("0123456789");
		}
		return fixture17;
	}

	public ResumptionTokenDTO getFixture18()
		throws Exception {
		if (fixture18 == null) {
			fixture18 = new ResumptionTokenDTO();
			fixture18.setCreationDate(new Timestamp(644344036000L));
			fixture18.setId(Integer.valueOf(0));
			fixture18.setMetadataPrefix("Ant-1.0.txt");
			fixture18.setQuery("Ant-1.0.txt");
			fixture18.setQueryForCount("Ant-1.0.txt");
		}
		return fixture18;
	}

	public ResumptionTokenDTO getFixture19()
		throws Exception {
		if (fixture19 == null) {
			fixture19 = new ResumptionTokenDTO();
			fixture19.setCreationDate(new Timestamp(644344036000L));
			fixture19.setId(Integer.valueOf(1));
			fixture19.setMetadataPrefix("Ant-1.0.txt");
			fixture19.setQuery("Ant-1.0.txt");
			fixture19.setQueryForCount("");
		}
		return fixture19;
	}

	public ResumptionTokenDTO getFixture20()
		throws Exception {
		if (fixture20 == null) {
			fixture20 = new ResumptionTokenDTO();
			fixture20.setCreationDate(new Timestamp(644344036000L));
			fixture20.setId(Integer.valueOf(1));
			fixture20.setMetadataPrefix("Ant-1.0.txt");
			fixture20.setQuery("Ant-1.0.txt");
			fixture20.setQueryForCount("0123456789");
		}
		return fixture20;
	}

	/**
	 * Return an instance of the class being tested.
	 *
	 * @return an instance of the class being tested
	 *
	 * @see ResumptionTokenDTO
	 *
	 * @generatedBy CodePro at 2008.05.15. 18:03
	 */
	public ResumptionTokenDTO getFixture21()
		throws Exception {
		if (fixture21 == null) {
			fixture21 = new ResumptionTokenDTO();
			fixture21.setCreationDate(new Timestamp(644344036000L));
			fixture21.setId(Integer.valueOf(1));
			fixture21.setMetadataPrefix("Ant-1.0.txt");
			fixture21.setQuery("Ant-1.0.txt");
			fixture21.setQueryForCount("Ant-1.0.txt");
		}
		return fixture21;
	}

	public ResumptionTokenDTO getFixture22()
		throws Exception {
		if (fixture22 == null) {
			fixture22 = new ResumptionTokenDTO();
			fixture22.setCreationDate(new Timestamp(946713599000L));
			fixture22.setId(Integer.valueOf(-1));
			fixture22.setMetadataPrefix("0123456789");
			fixture22.setQuery("0123456789");
			fixture22.setQueryForCount("");
		}
		return fixture22;
	}

	public ResumptionTokenDTO getFixture23()
		throws Exception {
		if (fixture23 == null) {
			fixture23 = new ResumptionTokenDTO();
			fixture23.setCreationDate(new Timestamp(946713599000L));
			fixture23.setId(Integer.valueOf(-1));
			fixture23.setMetadataPrefix("0123456789");
			fixture23.setQuery("0123456789");
			fixture23.setQueryForCount("0123456789");
		}
		return fixture23;
	}

	public ResumptionTokenDTO getFixture24()
		throws Exception {
		if (fixture24 == null) {
			fixture24 = new ResumptionTokenDTO();
			fixture24.setCreationDate(new Timestamp(946713599000L));
			fixture24.setId(Integer.valueOf(-1));
			fixture24.setMetadataPrefix("0123456789");
			fixture24.setQuery("0123456789");
			fixture24.setQueryForCount("Ant-1.0.txt");
		}
		return fixture24;
	}

	public ResumptionTokenDTO getFixture25()
		throws Exception {
		if (fixture25 == null) {
			fixture25 = new ResumptionTokenDTO();
			fixture25.setCreationDate(new Timestamp(946713599000L));
			fixture25.setId(Integer.valueOf(0));
			fixture25.setMetadataPrefix("0123456789");
			fixture25.setQuery("0123456789");
			fixture25.setQueryForCount("");
		}
		return fixture25;
	}

	public ResumptionTokenDTO getFixture26()
		throws Exception {
		if (fixture26 == null) {
			fixture26 = new ResumptionTokenDTO();
			fixture26.setCreationDate(new Timestamp(946713599000L));
			fixture26.setId(Integer.valueOf(0));
			fixture26.setMetadataPrefix("Ant-1.0.txt");
			fixture26.setQuery("Ant-1.0.txt");
			fixture26.setQueryForCount("0123456789");
		}
		return fixture26;
	}

	public ResumptionTokenDTO getFixture27()
		throws Exception {
		if (fixture27 == null) {
			fixture27 = new ResumptionTokenDTO();
			fixture27.setCreationDate(new Timestamp(946713599000L));
			fixture27.setId(Integer.valueOf(0));
			fixture27.setMetadataPrefix("Ant-1.0.txt");
			fixture27.setQuery("Ant-1.0.txt");
			fixture27.setQueryForCount("Ant-1.0.txt");
		}
		return fixture27;
	}

	public ResumptionTokenDTO getFixture28()
		throws Exception {
		if (fixture28 == null) {
			fixture28 = new ResumptionTokenDTO();
			fixture28.setCreationDate(new Timestamp(946713599000L));
			fixture28.setId(Integer.valueOf(1));
			fixture28.setMetadataPrefix("Ant-1.0.txt");
			fixture28.setQuery("Ant-1.0.txt");
			fixture28.setQueryForCount("");
		}
		return fixture28;
	}

	public ResumptionTokenDTO getFixture29()
		throws Exception {
		if (fixture29 == null) {
			fixture29 = new ResumptionTokenDTO();
			fixture29.setCreationDate(new Timestamp(946713599000L));
			fixture29.setId(Integer.valueOf(1));
			fixture29.setMetadataPrefix("Ant-1.0.txt");
			fixture29.setQuery("Ant-1.0.txt");
			fixture29.setQueryForCount("0123456789");
		}
		return fixture29;
	}

	public ResumptionTokenDTO getFixture30()
		throws Exception {
		if (fixture30 == null) {
			fixture30 = new ResumptionTokenDTO();
			fixture30.setCreationDate(new Timestamp(946713599000L));
			fixture30.setId(Integer.valueOf(1));
			fixture30.setMetadataPrefix("Ant-1.0.txt");
			fixture30.setQuery("Ant-1.0.txt");
			fixture30.setQueryForCount("Ant-1.0.txt");
		}
		return fixture30;
	}

	public void testGetCreationDate_fixture1_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture1();
		Timestamp result = fixture.getCreationDate();
		// add additional test code here
		assertEquals(null, result);
	}

	public void testGetCreationDate_fixture2_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture2();
		Timestamp result = fixture.getCreationDate();
		// add additional test code here
		assertNotNull(result);
		assertEquals(1591115236000L, result.getTime());
		assertEquals(DateFormat.getInstance().format(new Date(1591115236000L)),
				DateFormat.getInstance().format(result));
	}

	public void testGetCreationDate_fixture3_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture3();
		Timestamp result = fixture.getCreationDate();
		// add additional test code here
		assertNotNull(result);
		assertEquals(1591115236000L, result.getTime());
		assertEquals(DateFormat.getInstance().format(new Date(1591115236000L)),
				DateFormat.getInstance().format(result));
	}

	public void testGetCreationDate_fixture4_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture4();
		Timestamp result = fixture.getCreationDate();
		// add additional test code here
		assertNotNull(result);
		assertEquals(1591115236000L, result.getTime());
		assertEquals(DateFormat.getInstance().format(new Date(1591115236000L)),
				DateFormat.getInstance().format(result));
	}

	public void testGetCreationDate_fixture5_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture5();
		Timestamp result = fixture.getCreationDate();
		// add additional test code here
		assertNotNull(result);
		assertEquals(1591115236000L, result.getTime());
		assertEquals(DateFormat.getInstance().format(new Date(1591115236000L)),
				DateFormat.getInstance().format(result));
	}

	public void testGetCreationDate_fixture6_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture6();
		Timestamp result = fixture.getCreationDate();
		// add additional test code here
		assertNotNull(result);
		assertEquals(1591115236000L, result.getTime());
		assertEquals(DateFormat.getInstance().format(new Date(1591115236000L)),
				DateFormat.getInstance().format(result));
	}

	public void testGetCreationDate_fixture7_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture7();
		Timestamp result = fixture.getCreationDate();
		// add additional test code here
		assertNotNull(result);
		assertEquals(1591115236000L, result.getTime());
		assertEquals(DateFormat.getInstance().format(new Date(1591115236000L)),
				DateFormat.getInstance().format(result));
	}

	public void testGetCreationDate_fixture8_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture8();
		Timestamp result = fixture.getCreationDate();
		// add additional test code here
		assertNotNull(result);
		assertEquals(1591115236000L, result.getTime());
		assertEquals(DateFormat.getInstance().format(new Date(1591115236000L)),
				DateFormat.getInstance().format(result));
	}

	public void testGetCreationDate_fixture9_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture9();
		Timestamp result = fixture.getCreationDate();
		// add additional test code here
		assertNotNull(result);
		assertEquals(1591115236000L, result.getTime());
		assertEquals(DateFormat.getInstance().format(new Date(1591115236000L)),
				DateFormat.getInstance().format(result));
	}

	public void testGetCreationDate_fixture10_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture10();
		Timestamp result = fixture.getCreationDate();
		// add additional test code here
		assertNotNull(result);
		assertEquals(1591115236000L, result.getTime());
		assertEquals(DateFormat.getInstance().format(new Date(1591115236000L)),
				DateFormat.getInstance().format(result));
	}

	public void testGetCreationDate_fixture11_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture11();
		Timestamp result = fixture.getCreationDate();
		// add additional test code here
		assertNotNull(result);
		assertEquals(1591115236000L, result.getTime());
		assertEquals(DateFormat.getInstance().format(new Date(1591115236000L)),
				DateFormat.getInstance().format(result));
	}

	public void testGetCreationDate_fixture12_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture12();
		Timestamp result = fixture.getCreationDate();
		// add additional test code here
		assertNotNull(result);
		assertEquals(644344036000L, result.getTime());
		assertEquals(DateFormat.getInstance().format(new Date(644344036000L)), 
				DateFormat.getInstance().format(result));
	}

	public void testGetCreationDate_fixture13_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture13();
		Timestamp result = fixture.getCreationDate();
		// add additional test code here
		assertNotNull(result);
		assertEquals(644344036000L, result.getTime());
		assertEquals(DateFormat.getInstance().format(new Date(644344036000L)), 
				DateFormat.getInstance().format(result));
	}

	public void testGetCreationDate_fixture14_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture14();
		Timestamp result = fixture.getCreationDate();
		// add additional test code here
		assertNotNull(result);
		assertEquals(644344036000L, result.getTime());
		assertEquals(DateFormat.getInstance().format(new Date(644344036000L)), 
				DateFormat.getInstance().format(result));
	}

	public void testGetCreationDate_fixture15_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture15();
		Timestamp result = fixture.getCreationDate();
		// add additional test code here
		assertNotNull(result);
		assertEquals(644344036000L, result.getTime());
		assertEquals(DateFormat.getInstance().format(new Date(644344036000L)), 
				DateFormat.getInstance().format(result));
	}

	public void testGetCreationDate_fixture16_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture16();
		Timestamp result = fixture.getCreationDate();
		// add additional test code here
		assertNotNull(result);
		assertEquals(644344036000L, result.getTime());
		assertEquals(DateFormat.getInstance().format(new Date(644344036000L)), 
				DateFormat.getInstance().format(result));
	}

	public void testGetCreationDate_fixture17_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture17();
		Timestamp result = fixture.getCreationDate();
		// add additional test code here
		assertNotNull(result);
		assertEquals(644344036000L, result.getTime());
		assertEquals(DateFormat.getInstance().format(new Date(644344036000L)), 
				DateFormat.getInstance().format(result));
	}

	public void testGetCreationDate_fixture18_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture18();
		Timestamp result = fixture.getCreationDate();
		// add additional test code here
		assertNotNull(result);
		assertEquals(644344036000L, result.getTime());
		assertEquals(DateFormat.getInstance().format(new Date(644344036000L)), 
				DateFormat.getInstance().format(result));
	}

	public void testGetCreationDate_fixture19_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture19();
		Timestamp result = fixture.getCreationDate();
		// add additional test code here
		assertNotNull(result);
		assertEquals(644344036000L, result.getTime());
		assertEquals(DateFormat.getInstance().format(new Date(644344036000L)), 
				DateFormat.getInstance().format(result));
	}

	public void testGetCreationDate_fixture20_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture20();
		Timestamp result = fixture.getCreationDate();
		// add additional test code here
		assertNotNull(result);
		assertEquals(644344036000L, result.getTime());
		assertEquals(DateFormat.getInstance().format(new Date(644344036000L)), 
				DateFormat.getInstance().format(result));
	}

	public void testGetCreationDate_fixture21_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture21();
		Timestamp result = fixture.getCreationDate();
		// add additional test code here
		assertNotNull(result);
		assertEquals(644344036000L, result.getTime());
		assertEquals(DateFormat.getInstance().format(new Date(644344036000L)), 
				DateFormat.getInstance().format(result));
	}

	public void testGetCreationDate_fixture22_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture22();
		Timestamp result = fixture.getCreationDate();
		// add additional test code here
		assertNotNull(result);
		assertEquals(946713599000L, result.getTime());
		assertEquals(DateFormat.getInstance().format(new Date(946713599000L)), 
				DateFormat.getInstance().format(result));
	}

	public void testGetCreationDate_fixture23_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture23();
		Timestamp result = fixture.getCreationDate();
		// add additional test code here
		assertNotNull(result);
		assertEquals(946713599000L, result.getTime());
		assertEquals(DateFormat.getInstance().format(new Date(946713599000L)),
				DateFormat.getInstance().format(result));
	}

	public void testGetCreationDate_fixture24_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture24();
		Timestamp result = fixture.getCreationDate();
		// add additional test code here
		assertNotNull(result);
		assertEquals(946713599000L, result.getTime());
		assertEquals(DateFormat.getInstance().format(new Date(946713599000L)), 
				DateFormat.getInstance().format(result));
	}

	public void testGetCreationDate_fixture25_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture25();
		Timestamp result = fixture.getCreationDate();
		// add additional test code here
		assertNotNull(result);
		assertEquals(946713599000L, result.getTime());
		assertEquals(DateFormat.getInstance().format(new Date(946713599000L)), 
				DateFormat.getInstance().format(result));
	}

	public void testGetCreationDate_fixture26_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture26();
		Timestamp result = fixture.getCreationDate();
		// add additional test code here
		assertNotNull(result);
		assertEquals(946713599000L, result.getTime());
		assertEquals(DateFormat.getInstance().format(new Date(946713599000L)), 
				DateFormat.getInstance().format(result));
	}

	public void testGetCreationDate_fixture27_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture27();
		Timestamp result = fixture.getCreationDate();
		// add additional test code here
		assertNotNull(result);
		assertEquals(946713599000L, result.getTime());
		assertEquals(DateFormat.getInstance().format(new Date(946713599000L)), 
				DateFormat.getInstance().format(result));
	}

	public void testGetCreationDate_fixture28_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture28();
		Timestamp result = fixture.getCreationDate();
		// add additional test code here
		assertNotNull(result);
		assertEquals(946713599000L, result.getTime());
		assertEquals(DateFormat.getInstance().format(new Date(946713599000L)), 
				DateFormat.getInstance().format(result));
	}

	public void testGetCreationDate_fixture29_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture29();
		Timestamp result = fixture.getCreationDate();
		// add additional test code here
		assertNotNull(result);
		assertEquals(946713599000L, result.getTime());
		assertEquals(DateFormat.getInstance().format(new Date(946713599000L)), 
				DateFormat.getInstance().format(result));
	}

	public void testGetCreationDate_fixture30_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture30();
		Timestamp result = fixture.getCreationDate();
		// add additional test code here
		assertNotNull(result);
		assertEquals(946713599000L, result.getTime());
		assertEquals(DateFormat.getInstance().format(new Date(946713599000L)), 
				DateFormat.getInstance().format(result));
	}

	public void testGetId_fixture1_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture1();
		Integer result = fixture.getId();
		// add additional test code here
		assertEquals(null, result);
	}

	public void testGetId_fixture2_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture2();
		Integer result = fixture.getId();
		// add additional test code here
		assertNotNull(result);
		assertEquals((byte) -1, result.byteValue());
		assertEquals(-1.0, result.doubleValue(), 1.0);
		assertEquals(-1.0f, result.floatValue(), 1.0f);
		assertEquals(-1, result.intValue());
		assertEquals(-1L, result.longValue());
		assertEquals((short) -1, result.shortValue());
		assertEquals("-1", result.toString());
	}

	public void testGetId_fixture3_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture3();
		Integer result = fixture.getId();
		// add additional test code here
		assertNotNull(result);
		assertEquals((byte) -1, result.byteValue());
		assertEquals(-1.0, result.doubleValue(), 1.0);
		assertEquals(-1.0f, result.floatValue(), 1.0f);
		assertEquals(-1, result.intValue());
		assertEquals(-1L, result.longValue());
		assertEquals((short) -1, result.shortValue());
		assertEquals("-1", result.toString());
	}

	public void testGetId_fixture4_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture4();
		Integer result = fixture.getId();
		// add additional test code here
		assertNotNull(result);
		assertEquals((byte) -1, result.byteValue());
		assertEquals(-1.0, result.doubleValue(), 1.0);
		assertEquals(-1.0f, result.floatValue(), 1.0f);
		assertEquals(-1, result.intValue());
		assertEquals(-1L, result.longValue());
		assertEquals((short) -1, result.shortValue());
		assertEquals("-1", result.toString());
	}

	public void testGetId_fixture5_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture5();
		Integer result = fixture.getId();
		// add additional test code here
		assertNotNull(result);
		assertEquals((byte) -1, result.byteValue());
		assertEquals(-1.0, result.doubleValue(), 1.0);
		assertEquals(-1.0f, result.floatValue(), 1.0f);
		assertEquals(-1, result.intValue());
		assertEquals(-1L, result.longValue());
		assertEquals((short) -1, result.shortValue());
		assertEquals("-1", result.toString());
	}

	public void testGetId_fixture6_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture6();
		Integer result = fixture.getId();
		// add additional test code here
		assertNotNull(result);
		assertEquals((byte) 0, result.byteValue());
		assertEquals(0.0, result.doubleValue(), 1.0);
		assertEquals(0.0f, result.floatValue(), 1.0f);
		assertEquals(0, result.intValue());
		assertEquals(0L, result.longValue());
		assertEquals((short) 0, result.shortValue());
		assertEquals("0", result.toString());
	}

	public void testGetId_fixture7_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture7();
		Integer result = fixture.getId();
		// add additional test code here
		assertNotNull(result);
		assertEquals((byte) 0, result.byteValue());
		assertEquals(0.0, result.doubleValue(), 1.0);
		assertEquals(0.0f, result.floatValue(), 1.0f);
		assertEquals(0, result.intValue());
		assertEquals(0L, result.longValue());
		assertEquals((short) 0, result.shortValue());
		assertEquals("0", result.toString());
	}

	public void testGetId_fixture8_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture8();
		Integer result = fixture.getId();
		// add additional test code here
		assertNotNull(result);
		assertEquals((byte) 0, result.byteValue());
		assertEquals(0.0, result.doubleValue(), 1.0);
		assertEquals(0.0f, result.floatValue(), 1.0f);
		assertEquals(0, result.intValue());
		assertEquals(0L, result.longValue());
		assertEquals((short) 0, result.shortValue());
		assertEquals("0", result.toString());
	}

	public void testGetId_fixture9_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture9();
		Integer result = fixture.getId();
		// add additional test code here
		assertNotNull(result);
		assertEquals((byte) 1, result.byteValue());
		assertEquals(1.0, result.doubleValue(), 1.0);
		assertEquals(1.0f, result.floatValue(), 1.0f);
		assertEquals(1, result.intValue());
		assertEquals(1L, result.longValue());
		assertEquals((short) 1, result.shortValue());
		assertEquals("1", result.toString());
	}

	public void testGetId_fixture10_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture10();
		Integer result = fixture.getId();
		// add additional test code here
		assertNotNull(result);
		assertEquals((byte) 1, result.byteValue());
		assertEquals(1.0, result.doubleValue(), 1.0);
		assertEquals(1.0f, result.floatValue(), 1.0f);
		assertEquals(1, result.intValue());
		assertEquals(1L, result.longValue());
		assertEquals((short) 1, result.shortValue());
		assertEquals("1", result.toString());
	}

	public void testGetId_fixture11_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture11();
		Integer result = fixture.getId();
		// add additional test code here
		assertNotNull(result);
		assertEquals((byte) 1, result.byteValue());
		assertEquals(1.0, result.doubleValue(), 1.0);
		assertEquals(1.0f, result.floatValue(), 1.0f);
		assertEquals(1, result.intValue());
		assertEquals(1L, result.longValue());
		assertEquals((short) 1, result.shortValue());
		assertEquals("1", result.toString());
	}

	public void testGetId_fixture12_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture12();
		Integer result = fixture.getId();
		// add additional test code here
		assertNotNull(result);
		assertEquals((byte) -1, result.byteValue());
		assertEquals(-1.0, result.doubleValue(), 1.0);
		assertEquals(-1.0f, result.floatValue(), 1.0f);
		assertEquals(-1, result.intValue());
		assertEquals(-1L, result.longValue());
		assertEquals((short) -1, result.shortValue());
		assertEquals("-1", result.toString());
	}

	public void testGetId_fixture13_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture13();
		Integer result = fixture.getId();
		// add additional test code here
		assertNotNull(result);
		assertEquals((byte) -1, result.byteValue());
		assertEquals(-1.0, result.doubleValue(), 1.0);
		assertEquals(-1.0f, result.floatValue(), 1.0f);
		assertEquals(-1, result.intValue());
		assertEquals(-1L, result.longValue());
		assertEquals((short) -1, result.shortValue());
		assertEquals("-1", result.toString());
	}

	public void testGetId_fixture14_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture14();
		Integer result = fixture.getId();
		// add additional test code here
		assertNotNull(result);
		assertEquals((byte) -1, result.byteValue());
		assertEquals(-1.0, result.doubleValue(), 1.0);
		assertEquals(-1.0f, result.floatValue(), 1.0f);
		assertEquals(-1, result.intValue());
		assertEquals(-1L, result.longValue());
		assertEquals((short) -1, result.shortValue());
		assertEquals("-1", result.toString());
	}

	public void testGetId_fixture15_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture15();
		Integer result = fixture.getId();
		// add additional test code here
		assertNotNull(result);
		assertEquals((byte) 0, result.byteValue());
		assertEquals(0.0, result.doubleValue(), 1.0);
		assertEquals(0.0f, result.floatValue(), 1.0f);
		assertEquals(0, result.intValue());
		assertEquals(0L, result.longValue());
		assertEquals((short) 0, result.shortValue());
		assertEquals("0", result.toString());
	}

	public void testGetId_fixture16_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture16();
		Integer result = fixture.getId();
		// add additional test code here
		assertNotNull(result);
		assertEquals((byte) 0, result.byteValue());
		assertEquals(0.0, result.doubleValue(), 1.0);
		assertEquals(0.0f, result.floatValue(), 1.0f);
		assertEquals(0, result.intValue());
		assertEquals(0L, result.longValue());
		assertEquals((short) 0, result.shortValue());
		assertEquals("0", result.toString());
	}

	public void testGetId_fixture17_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture17();
		Integer result = fixture.getId();
		// add additional test code here
		assertNotNull(result);
		assertEquals((byte) 0, result.byteValue());
		assertEquals(0.0, result.doubleValue(), 1.0);
		assertEquals(0.0f, result.floatValue(), 1.0f);
		assertEquals(0, result.intValue());
		assertEquals(0L, result.longValue());
		assertEquals((short) 0, result.shortValue());
		assertEquals("0", result.toString());
	}

	public void testGetId_fixture18_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture18();
		Integer result = fixture.getId();
		// add additional test code here
		assertNotNull(result);
		assertEquals((byte) 0, result.byteValue());
		assertEquals(0.0, result.doubleValue(), 1.0);
		assertEquals(0.0f, result.floatValue(), 1.0f);
		assertEquals(0, result.intValue());
		assertEquals(0L, result.longValue());
		assertEquals((short) 0, result.shortValue());
		assertEquals("0", result.toString());
	}

	public void testGetId_fixture19_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture19();
		Integer result = fixture.getId();
		// add additional test code here
		assertNotNull(result);
		assertEquals((byte) 1, result.byteValue());
		assertEquals(1.0, result.doubleValue(), 1.0);
		assertEquals(1.0f, result.floatValue(), 1.0f);
		assertEquals(1, result.intValue());
		assertEquals(1L, result.longValue());
		assertEquals((short) 1, result.shortValue());
		assertEquals("1", result.toString());
	}

	public void testGetId_fixture20_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture20();
		Integer result = fixture.getId();
		// add additional test code here
		assertNotNull(result);
		assertEquals((byte) 1, result.byteValue());
		assertEquals(1.0, result.doubleValue(), 1.0);
		assertEquals(1.0f, result.floatValue(), 1.0f);
		assertEquals(1, result.intValue());
		assertEquals(1L, result.longValue());
		assertEquals((short) 1, result.shortValue());
		assertEquals("1", result.toString());
	}

	public void testGetId_fixture21_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture21();
		Integer result = fixture.getId();
		// add additional test code here
		assertNotNull(result);
		assertEquals((byte) 1, result.byteValue());
		assertEquals(1.0, result.doubleValue(), 1.0);
		assertEquals(1.0f, result.floatValue(), 1.0f);
		assertEquals(1, result.intValue());
		assertEquals(1L, result.longValue());
		assertEquals((short) 1, result.shortValue());
		assertEquals("1", result.toString());
	}

	public void testGetId_fixture22_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture22();
		Integer result = fixture.getId();
		// add additional test code here
		assertNotNull(result);
		assertEquals((byte) -1, result.byteValue());
		assertEquals(-1.0, result.doubleValue(), 1.0);
		assertEquals(-1.0f, result.floatValue(), 1.0f);
		assertEquals(-1, result.intValue());
		assertEquals(-1L, result.longValue());
		assertEquals((short) -1, result.shortValue());
		assertEquals("-1", result.toString());
	}

	public void testGetId_fixture23_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture23();
		Integer result = fixture.getId();
		// add additional test code here
		assertNotNull(result);
		assertEquals((byte) -1, result.byteValue());
		assertEquals(-1.0, result.doubleValue(), 1.0);
		assertEquals(-1.0f, result.floatValue(), 1.0f);
		assertEquals(-1, result.intValue());
		assertEquals(-1L, result.longValue());
		assertEquals((short) -1, result.shortValue());
		assertEquals("-1", result.toString());
	}

	public void testGetId_fixture24_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture24();
		Integer result = fixture.getId();
		// add additional test code here
		assertNotNull(result);
		assertEquals((byte) -1, result.byteValue());
		assertEquals(-1.0, result.doubleValue(), 1.0);
		assertEquals(-1.0f, result.floatValue(), 1.0f);
		assertEquals(-1, result.intValue());
		assertEquals(-1L, result.longValue());
		assertEquals((short) -1, result.shortValue());
		assertEquals("-1", result.toString());
	}

	public void testGetId_fixture25_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture25();
		Integer result = fixture.getId();
		// add additional test code here
		assertNotNull(result);
		assertEquals((byte) 0, result.byteValue());
		assertEquals(0.0, result.doubleValue(), 1.0);
		assertEquals(0.0f, result.floatValue(), 1.0f);
		assertEquals(0, result.intValue());
		assertEquals(0L, result.longValue());
		assertEquals((short) 0, result.shortValue());
		assertEquals("0", result.toString());
	}

	public void testGetId_fixture26_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture26();
		Integer result = fixture.getId();
		// add additional test code here
		assertNotNull(result);
		assertEquals((byte) 0, result.byteValue());
		assertEquals(0.0, result.doubleValue(), 1.0);
		assertEquals(0.0f, result.floatValue(), 1.0f);
		assertEquals(0, result.intValue());
		assertEquals(0L, result.longValue());
		assertEquals((short) 0, result.shortValue());
		assertEquals("0", result.toString());
	}

	public void testGetId_fixture27_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture27();
		Integer result = fixture.getId();
		// add additional test code here
		assertNotNull(result);
		assertEquals((byte) 0, result.byteValue());
		assertEquals(0.0, result.doubleValue(), 1.0);
		assertEquals(0.0f, result.floatValue(), 1.0f);
		assertEquals(0, result.intValue());
		assertEquals(0L, result.longValue());
		assertEquals((short) 0, result.shortValue());
		assertEquals("0", result.toString());
	}

	public void testGetId_fixture28_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture28();
		Integer result = fixture.getId();
		// add additional test code here
		assertNotNull(result);
		assertEquals((byte) 1, result.byteValue());
		assertEquals(1.0, result.doubleValue(), 1.0);
		assertEquals(1.0f, result.floatValue(), 1.0f);
		assertEquals(1, result.intValue());
		assertEquals(1L, result.longValue());
		assertEquals((short) 1, result.shortValue());
		assertEquals("1", result.toString());
	}

	public void testGetId_fixture29_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture29();
		Integer result = fixture.getId();
		// add additional test code here
		assertNotNull(result);
		assertEquals((byte) 1, result.byteValue());
		assertEquals(1.0, result.doubleValue(), 1.0);
		assertEquals(1.0f, result.floatValue(), 1.0f);
		assertEquals(1, result.intValue());
		assertEquals(1L, result.longValue());
		assertEquals((short) 1, result.shortValue());
		assertEquals("1", result.toString());
	}

	public void testGetId_fixture30_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture30();
		Integer result = fixture.getId();
		// add additional test code here
		assertNotNull(result);
		assertEquals((byte) 1, result.byteValue());
		assertEquals(1.0, result.doubleValue(), 1.0);
		assertEquals(1.0f, result.floatValue(), 1.0f);
		assertEquals(1, result.intValue());
		assertEquals(1L, result.longValue());
		assertEquals((short) 1, result.shortValue());
		assertEquals("1", result.toString());
	}

	public void testGetMetadataPrefix_fixture1_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture1();
		String result = fixture.getMetadataPrefix();
		// add additional test code here
		assertEquals(null, result);
	}

	public void testGetMetadataPrefix_fixture2_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture2();
		String result = fixture.getMetadataPrefix();
		// add additional test code here
		assertEquals("", result);
	}

	public void testGetMetadataPrefix_fixture3_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture3();
		String result = fixture.getMetadataPrefix();
		// add additional test code here
		assertEquals("0123456789", result);
	}

	public void testGetMetadataPrefix_fixture4_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture4();
		String result = fixture.getMetadataPrefix();
		// add additional test code here
		assertEquals("0123456789", result);
	}

	public void testGetMetadataPrefix_fixture5_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture5();
		String result = fixture.getMetadataPrefix();
		// add additional test code here
		assertEquals("0123456789", result);
	}

	public void testGetMetadataPrefix_fixture6_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture6();
		String result = fixture.getMetadataPrefix();
		// add additional test code here
		assertEquals("0123456789", result);
	}

	public void testGetMetadataPrefix_fixture7_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture7();
		String result = fixture.getMetadataPrefix();
		// add additional test code here
		assertEquals("0123456789", result);
	}

	public void testGetMetadataPrefix_fixture8_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture8();
		String result = fixture.getMetadataPrefix();
		// add additional test code here
		assertEquals("Ant-1.0.txt", result);
	}

	public void testGetMetadataPrefix_fixture9_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture9();
		String result = fixture.getMetadataPrefix();
		// add additional test code here
		assertEquals("Ant-1.0.txt", result);
	}

	public void testGetMetadataPrefix_fixture10_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture10();
		String result = fixture.getMetadataPrefix();
		// add additional test code here
		assertEquals("Ant-1.0.txt", result);
	}

	public void testGetMetadataPrefix_fixture11_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture11();
		String result = fixture.getMetadataPrefix();
		// add additional test code here
		assertEquals("Ant-1.0.txt", result);
	}

	public void testGetMetadataPrefix_fixture12_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture12();
		String result = fixture.getMetadataPrefix();
		// add additional test code here
		assertEquals("0123456789", result);
	}

	public void testGetMetadataPrefix_fixture13_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture13();
		String result = fixture.getMetadataPrefix();
		// add additional test code here
		assertEquals("0123456789", result);
	}

	public void testGetMetadataPrefix_fixture14_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture14();
		String result = fixture.getMetadataPrefix();
		// add additional test code here
		assertEquals("0123456789", result);
	}

	public void testGetMetadataPrefix_fixture15_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture15();
		String result = fixture.getMetadataPrefix();
		// add additional test code here
		assertEquals("0123456789", result);
	}

	public void testGetMetadataPrefix_fixture16_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture16();
		String result = fixture.getMetadataPrefix();
		// add additional test code here
		assertEquals("0123456789", result);
	}

	public void testGetMetadataPrefix_fixture17_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture17();
		String result = fixture.getMetadataPrefix();
		// add additional test code here
		assertEquals("Ant-1.0.txt", result);
	}

	public void testGetMetadataPrefix_fixture18_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture18();
		String result = fixture.getMetadataPrefix();
		// add additional test code here
		assertEquals("Ant-1.0.txt", result);
	}

	public void testGetMetadataPrefix_fixture19_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture19();
		String result = fixture.getMetadataPrefix();
		// add additional test code here
		assertEquals("Ant-1.0.txt", result);
	}

	public void testGetMetadataPrefix_fixture20_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture20();
		String result = fixture.getMetadataPrefix();
		// add additional test code here
		assertEquals("Ant-1.0.txt", result);
	}

	public void testGetMetadataPrefix_fixture21_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture21();
		String result = fixture.getMetadataPrefix();
		// add additional test code here
		assertEquals("Ant-1.0.txt", result);
	}

	public void testGetMetadataPrefix_fixture22_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture22();
		String result = fixture.getMetadataPrefix();
		// add additional test code here
		assertEquals("0123456789", result);
	}

	public void testGetMetadataPrefix_fixture23_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture23();
		String result = fixture.getMetadataPrefix();
		// add additional test code here
		assertEquals("0123456789", result);
	}

	public void testGetMetadataPrefix_fixture24_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture24();
		String result = fixture.getMetadataPrefix();
		// add additional test code here
		assertEquals("0123456789", result);
	}

	public void testGetMetadataPrefix_fixture25_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture25();
		String result = fixture.getMetadataPrefix();
		// add additional test code here
		assertEquals("0123456789", result);
	}

	public void testGetMetadataPrefix_fixture26_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture26();
		String result = fixture.getMetadataPrefix();
		// add additional test code here
		assertEquals("Ant-1.0.txt", result);
	}

	public void testGetMetadataPrefix_fixture27_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture27();
		String result = fixture.getMetadataPrefix();
		// add additional test code here
		assertEquals("Ant-1.0.txt", result);
	}

	public void testGetMetadataPrefix_fixture28_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture28();
		String result = fixture.getMetadataPrefix();
		// add additional test code here
		assertEquals("Ant-1.0.txt", result);
	}

	public void testGetMetadataPrefix_fixture29_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture29();
		String result = fixture.getMetadataPrefix();
		// add additional test code here
		assertEquals("Ant-1.0.txt", result);
	}

	public void testGetMetadataPrefix_fixture30_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture30();
		String result = fixture.getMetadataPrefix();
		// add additional test code here
		assertEquals("Ant-1.0.txt", result);
	}

	public void testGetQuery_fixture1_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture1();
		String result = fixture.getQuery();
		// add additional test code here
		assertEquals(null, result);
	}

	public void testGetQuery_fixture2_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture2();
		String result = fixture.getQuery();
		// add additional test code here
		assertEquals("", result);
	}

	public void testGetQuery_fixture3_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture3();
		String result = fixture.getQuery();
		// add additional test code here
		assertEquals("0123456789", result);
	}

	public void testGetQuery_fixture4_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture4();
		String result = fixture.getQuery();
		// add additional test code here
		assertEquals("0123456789", result);
	}

	public void testGetQuery_fixture5_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture5();
		String result = fixture.getQuery();
		// add additional test code here
		assertEquals("0123456789", result);
	}

	public void testGetQuery_fixture6_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture6();
		String result = fixture.getQuery();
		// add additional test code here
		assertEquals("0123456789", result);
	}

	public void testGetQuery_fixture7_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture7();
		String result = fixture.getQuery();
		// add additional test code here
		assertEquals("0123456789", result);
	}

	public void testGetQuery_fixture8_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture8();
		String result = fixture.getQuery();
		// add additional test code here
		assertEquals("Ant-1.0.txt", result);
	}

	public void testGetQuery_fixture9_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture9();
		String result = fixture.getQuery();
		// add additional test code here
		assertEquals("Ant-1.0.txt", result);
	}

	public void testGetQuery_fixture10_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture10();
		String result = fixture.getQuery();
		// add additional test code here
		assertEquals("Ant-1.0.txt", result);
	}

	public void testGetQuery_fixture11_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture11();
		String result = fixture.getQuery();
		// add additional test code here
		assertEquals("Ant-1.0.txt", result);
	}

	public void testGetQuery_fixture12_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture12();
		String result = fixture.getQuery();
		// add additional test code here
		assertEquals("0123456789", result);
	}

	public void testGetQuery_fixture13_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture13();
		String result = fixture.getQuery();
		// add additional test code here
		assertEquals("0123456789", result);
	}

	public void testGetQuery_fixture14_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture14();
		String result = fixture.getQuery();
		// add additional test code here
		assertEquals("0123456789", result);
	}

	public void testGetQuery_fixture15_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture15();
		String result = fixture.getQuery();
		// add additional test code here
		assertEquals("0123456789", result);
	}

	public void testGetQuery_fixture16_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture16();
		String result = fixture.getQuery();
		// add additional test code here
		assertEquals("0123456789", result);
	}

	public void testGetQuery_fixture17_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture17();
		String result = fixture.getQuery();
		// add additional test code here
		assertEquals("Ant-1.0.txt", result);
	}

	public void testGetQuery_fixture18_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture18();
		String result = fixture.getQuery();
		// add additional test code here
		assertEquals("Ant-1.0.txt", result);
	}

	public void testGetQuery_fixture19_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture19();
		String result = fixture.getQuery();
		// add additional test code here
		assertEquals("Ant-1.0.txt", result);
	}

	public void testGetQuery_fixture20_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture20();
		String result = fixture.getQuery();
		// add additional test code here
		assertEquals("Ant-1.0.txt", result);
	}

	public void testGetQuery_fixture21_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture21();
		String result = fixture.getQuery();
		// add additional test code here
		assertEquals("Ant-1.0.txt", result);
	}

	public void testGetQuery_fixture22_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture22();
		String result = fixture.getQuery();
		// add additional test code here
		assertEquals("0123456789", result);
	}

	public void testGetQuery_fixture23_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture23();
		String result = fixture.getQuery();
		// add additional test code here
		assertEquals("0123456789", result);
	}

	public void testGetQuery_fixture24_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture24();
		String result = fixture.getQuery();
		// add additional test code here
		assertEquals("0123456789", result);
	}

	public void testGetQuery_fixture25_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture25();
		String result = fixture.getQuery();
		// add additional test code here
		assertEquals("0123456789", result);
	}

	public void testGetQuery_fixture26_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture26();
		String result = fixture.getQuery();
		// add additional test code here
		assertEquals("Ant-1.0.txt", result);
	}

	public void testGetQuery_fixture27_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture27();
		String result = fixture.getQuery();
		// add additional test code here
		assertEquals("Ant-1.0.txt", result);
	}

	public void testGetQuery_fixture28_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture28();
		String result = fixture.getQuery();
		// add additional test code here
		assertEquals("Ant-1.0.txt", result);
	}

	public void testGetQuery_fixture29_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture29();
		String result = fixture.getQuery();
		// add additional test code here
		assertEquals("Ant-1.0.txt", result);
	}

	public void testGetQuery_fixture30_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture30();
		String result = fixture.getQuery();
		// add additional test code here
		assertEquals("Ant-1.0.txt", result);
	}

	public void testGetQueryForCount_fixture1_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture1();
		String result = fixture.getQueryForCount();
		// add additional test code here
		assertEquals(null, result);
	}

	public void testGetQueryForCount_fixture2_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture2();
		String result = fixture.getQueryForCount();
		// add additional test code here
		assertEquals("", result);
	}

	public void testGetQueryForCount_fixture3_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture3();
		String result = fixture.getQueryForCount();
		// add additional test code here
		assertEquals("", result);
	}

	public void testGetQueryForCount_fixture4_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture4();
		String result = fixture.getQueryForCount();
		// add additional test code here
		assertEquals("0123456789", result);
	}

	public void testGetQueryForCount_fixture5_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture5();
		String result = fixture.getQueryForCount();
		// add additional test code here
		assertEquals("Ant-1.0.txt", result);
	}

	public void testGetQueryForCount_fixture6_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture6();
		String result = fixture.getQueryForCount();
		// add additional test code here
		assertEquals("", result);
	}

	public void testGetQueryForCount_fixture7_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture7();
		String result = fixture.getQueryForCount();
		// add additional test code here
		assertEquals("0123456789", result);
	}

	public void testGetQueryForCount_fixture8_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture8();
		String result = fixture.getQueryForCount();
		// add additional test code here
		assertEquals("Ant-1.0.txt", result);
	}

	public void testGetQueryForCount_fixture9_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture9();
		String result = fixture.getQueryForCount();
		// add additional test code here
		assertEquals("", result);
	}

	public void testGetQueryForCount_fixture10_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture10();
		String result = fixture.getQueryForCount();
		// add additional test code here
		assertEquals("0123456789", result);
	}

	public void testGetQueryForCount_fixture11_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture11();
		String result = fixture.getQueryForCount();
		// add additional test code here
		assertEquals("Ant-1.0.txt", result);
	}

	public void testGetQueryForCount_fixture12_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture12();
		String result = fixture.getQueryForCount();
		// add additional test code here
		assertEquals("", result);
	}

	public void testGetQueryForCount_fixture13_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture13();
		String result = fixture.getQueryForCount();
		// add additional test code here
		assertEquals("0123456789", result);
	}

	public void testGetQueryForCount_fixture14_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture14();
		String result = fixture.getQueryForCount();
		// add additional test code here
		assertEquals("Ant-1.0.txt", result);
	}

	public void testGetQueryForCount_fixture15_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture15();
		String result = fixture.getQueryForCount();
		// add additional test code here
		assertEquals("", result);
	}

	public void testGetQueryForCount_fixture16_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture16();
		String result = fixture.getQueryForCount();
		// add additional test code here
		assertEquals("0123456789", result);
	}

	public void testGetQueryForCount_fixture17_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture17();
		String result = fixture.getQueryForCount();
		// add additional test code here
		assertEquals("0123456789", result);
	}

	public void testGetQueryForCount_fixture18_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture18();
		String result = fixture.getQueryForCount();
		// add additional test code here
		assertEquals("Ant-1.0.txt", result);
	}

	public void testGetQueryForCount_fixture19_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture19();
		String result = fixture.getQueryForCount();
		// add additional test code here
		assertEquals("", result);
	}

	public void testGetQueryForCount_fixture20_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture20();
		String result = fixture.getQueryForCount();
		// add additional test code here
		assertEquals("0123456789", result);
	}

	public void testGetQueryForCount_fixture21_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture21();
		String result = fixture.getQueryForCount();
		// add additional test code here
		assertEquals("Ant-1.0.txt", result);
	}

	public void testGetQueryForCount_fixture22_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture22();
		String result = fixture.getQueryForCount();
		// add additional test code here
		assertEquals("", result);
	}

	public void testGetQueryForCount_fixture23_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture23();
		String result = fixture.getQueryForCount();
		// add additional test code here
		assertEquals("0123456789", result);
	}

	public void testGetQueryForCount_fixture24_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture24();
		String result = fixture.getQueryForCount();
		// add additional test code here
		assertEquals("Ant-1.0.txt", result);
	}

	public void testGetQueryForCount_fixture25_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture25();
		String result = fixture.getQueryForCount();
		// add additional test code here
		assertEquals("", result);
	}

	public void testGetQueryForCount_fixture26_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture26();
		String result = fixture.getQueryForCount();
		// add additional test code here
		assertEquals("0123456789", result);
	}

	public void testGetQueryForCount_fixture27_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture27();
		String result = fixture.getQueryForCount();
		// add additional test code here
		assertEquals("Ant-1.0.txt", result);
	}

	public void testGetQueryForCount_fixture28_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture28();
		String result = fixture.getQueryForCount();
		// add additional test code here
		assertEquals("", result);
	}

	public void testGetQueryForCount_fixture29_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture29();
		String result = fixture.getQueryForCount();
		// add additional test code here
		assertEquals("0123456789", result);
	}

	public void testGetQueryForCount_fixture30_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture30();
		String result = fixture.getQueryForCount();
		// add additional test code here
		assertEquals("Ant-1.0.txt", result);
	}

	public void testSetCreationDate_fixture1_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture1();
		Timestamp creationDate = new Timestamp(1591115236000L);
		fixture.setCreationDate(creationDate);
		// add additional test code here
	}

	public void testSetCreationDate_fixture2_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture2();
		Timestamp creationDate = new Timestamp(644344036000L);
		fixture.setCreationDate(creationDate);
		// add additional test code here
	}

	public void testSetCreationDate_fixture3_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture3();
		Timestamp creationDate = new Timestamp(946713599000L);
		fixture.setCreationDate(creationDate);
		// add additional test code here
	}

	public void testSetCreationDate_fixture4_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture4();
		Timestamp creationDate = new Timestamp(946713600000L);
		fixture.setCreationDate(creationDate);
		// add additional test code here
	}

	public void testSetCreationDate_fixture5_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture5();
		Timestamp creationDate = new Timestamp(946713600000L);
		fixture.setCreationDate(creationDate);
		// add additional test code here
	}

	public void testSetCreationDate_fixture6_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture6();
		Timestamp creationDate = new Timestamp(946713600000L);
		fixture.setCreationDate(creationDate);
		// add additional test code here
	}

	public void testSetCreationDate_fixture7_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture7();
		Timestamp creationDate = new Timestamp(946713600000L);
		fixture.setCreationDate(creationDate);
		// add additional test code here
	}

	public void testSetCreationDate_fixture8_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture8();
		Timestamp creationDate = new Timestamp(946713600000L);
		fixture.setCreationDate(creationDate);
		// add additional test code here
	}

	public void testSetCreationDate_fixture9_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture9();
		Timestamp creationDate = new Timestamp(946713600000L);
		fixture.setCreationDate(creationDate);
		// add additional test code here
	}

	public void testSetCreationDate_fixture10_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture10();
		Timestamp creationDate = new Timestamp(946713600000L);
		fixture.setCreationDate(creationDate);
		// add additional test code here
	}

	public void testSetCreationDate_fixture11_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture11();
		Timestamp creationDate = new Timestamp(946713600000L);
		fixture.setCreationDate(creationDate);
		// add additional test code here
	}

	public void testSetCreationDate_fixture12_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture12();
		Timestamp creationDate = new Timestamp(946713600000L);
		fixture.setCreationDate(creationDate);
		// add additional test code here
	}

	public void testSetCreationDate_fixture13_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture13();
		Timestamp creationDate = new Timestamp(946713600000L);
		fixture.setCreationDate(creationDate);
		// add additional test code here
	}

	public void testSetCreationDate_fixture14_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture14();
		Timestamp creationDate = new Timestamp(946713600000L);
		fixture.setCreationDate(creationDate);
		// add additional test code here
	}

	public void testSetCreationDate_fixture15_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture15();
		Timestamp creationDate = new Timestamp(946713600000L);
		fixture.setCreationDate(creationDate);
		// add additional test code here
	}

	public void testSetCreationDate_fixture16_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture16();
		Timestamp creationDate = new Timestamp(946713600000L);
		fixture.setCreationDate(creationDate);
		// add additional test code here
	}

	public void testSetCreationDate_fixture17_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture17();
		Timestamp creationDate = new Timestamp(946713600000L);
		fixture.setCreationDate(creationDate);
		// add additional test code here
	}

	public void testSetCreationDate_fixture18_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture18();
		Timestamp creationDate = new Timestamp(946713600000L);
		fixture.setCreationDate(creationDate);
		// add additional test code here
	}

	public void testSetCreationDate_fixture19_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture19();
		Timestamp creationDate = new Timestamp(946713600000L);
		fixture.setCreationDate(creationDate);
		// add additional test code here
	}

	public void testSetCreationDate_fixture20_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture20();
		Timestamp creationDate = new Timestamp(946713600000L);
		fixture.setCreationDate(creationDate);
		// add additional test code here
	}

	public void testSetCreationDate_fixture21_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture21();
		Timestamp creationDate = new Timestamp(946713600000L);
		fixture.setCreationDate(creationDate);
		// add additional test code here
	}

	public void testSetCreationDate_fixture22_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture22();
		Timestamp creationDate = new Timestamp(946713600000L);
		fixture.setCreationDate(creationDate);
		// add additional test code here
	}

	public void testSetCreationDate_fixture23_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture23();
		Timestamp creationDate = new Timestamp(946713600000L);
		fixture.setCreationDate(creationDate);
		// add additional test code here
	}

	public void testSetCreationDate_fixture24_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture24();
		Timestamp creationDate = new Timestamp(946713600000L);
		fixture.setCreationDate(creationDate);
		// add additional test code here
	}

	public void testSetCreationDate_fixture25_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture25();
		Timestamp creationDate = new Timestamp(946713600000L);
		fixture.setCreationDate(creationDate);
		// add additional test code here
	}

	public void testSetCreationDate_fixture26_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture26();
		Timestamp creationDate = new Timestamp(946713600000L);
		fixture.setCreationDate(creationDate);
		// add additional test code here
	}

	public void testSetCreationDate_fixture27_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture27();
		Timestamp creationDate = new Timestamp(946713600000L);
		fixture.setCreationDate(creationDate);
		// add additional test code here
	}

	public void testSetCreationDate_fixture28_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture28();
		Timestamp creationDate = new Timestamp(946713600000L);
		fixture.setCreationDate(creationDate);
		// add additional test code here
	}

	public void testSetCreationDate_fixture29_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture29();
		Timestamp creationDate = new Timestamp(946713600000L);
		fixture.setCreationDate(creationDate);
		// add additional test code here
	}

	public void testSetCreationDate_fixture30_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture30();
		Timestamp creationDate = new Timestamp(946713600000L);
		fixture.setCreationDate(creationDate);
		// add additional test code here
	}

	public void testSetId_fixture1_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture1();
		Integer id = Integer.valueOf(-1);
		fixture.setId(id);
		// add additional test code here
	}

	public void testSetId_fixture2_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture2();
		Integer id = Integer.valueOf(0);
		fixture.setId(id);
		// add additional test code here
	}

	public void testSetId_fixture3_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture3();
		Integer id = Integer.valueOf(1);
		fixture.setId(id);
		// add additional test code here
	}

	public void testSetId_fixture4_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture4();
		Integer id = Integer.valueOf(1);
		fixture.setId(id);
		// add additional test code here
	}

	public void testSetId_fixture5_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture5();
		Integer id = Integer.valueOf(1);
		fixture.setId(id);
		// add additional test code here
	}

	public void testSetId_fixture6_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture6();
		Integer id = Integer.valueOf(1);
		fixture.setId(id);
		// add additional test code here
	}

	public void testSetId_fixture7_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture7();
		Integer id = Integer.valueOf(1);
		fixture.setId(id);
		// add additional test code here
	}

	public void testSetId_fixture8_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture8();
		Integer id = Integer.valueOf(1);
		fixture.setId(id);
		// add additional test code here
	}

	public void testSetId_fixture9_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture9();
		Integer id = Integer.valueOf(1);
		fixture.setId(id);
		// add additional test code here
	}

	public void testSetId_fixture10_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture10();
		Integer id = Integer.valueOf(1);
		fixture.setId(id);
		// add additional test code here
	}

	public void testSetId_fixture11_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture11();
		Integer id = Integer.valueOf(1);
		fixture.setId(id);
		// add additional test code here
	}

	public void testSetId_fixture12_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture12();
		Integer id = Integer.valueOf(1);
		fixture.setId(id);
		// add additional test code here
	}

	public void testSetId_fixture13_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture13();
		Integer id = Integer.valueOf(1);
		fixture.setId(id);
		// add additional test code here
	}

	public void testSetId_fixture14_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture14();
		Integer id = Integer.valueOf(1);
		fixture.setId(id);
		// add additional test code here
	}

	public void testSetId_fixture15_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture15();
		Integer id = Integer.valueOf(1);
		fixture.setId(id);
		// add additional test code here
	}

	public void testSetId_fixture16_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture16();
		Integer id = Integer.valueOf(1);
		fixture.setId(id);
		// add additional test code here
	}

	public void testSetId_fixture17_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture17();
		Integer id = Integer.valueOf(1);
		fixture.setId(id);
		// add additional test code here
	}

	public void testSetId_fixture18_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture18();
		Integer id = Integer.valueOf(1);
		fixture.setId(id);
		// add additional test code here
	}

	public void testSetId_fixture19_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture19();
		Integer id = Integer.valueOf(1);
		fixture.setId(id);
		// add additional test code here
	}

	public void testSetId_fixture20_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture20();
		Integer id = Integer.valueOf(1);
		fixture.setId(id);
		// add additional test code here
	}

	public void testSetId_fixture21_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture21();
		Integer id = Integer.valueOf(1);
		fixture.setId(id);
		// add additional test code here
	}

	public void testSetId_fixture22_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture22();
		Integer id = Integer.valueOf(1);
		fixture.setId(id);
		// add additional test code here
	}

	public void testSetId_fixture23_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture23();
		Integer id = Integer.valueOf(1);
		fixture.setId(id);
		// add additional test code here
	}

	public void testSetId_fixture24_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture24();
		Integer id = Integer.valueOf(1);
		fixture.setId(id);
		// add additional test code here
	}

	public void testSetId_fixture25_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture25();
		Integer id = Integer.valueOf(1);
		fixture.setId(id);
		// add additional test code here
	}

	public void testSetId_fixture26_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture26();
		Integer id = Integer.valueOf(1);
		fixture.setId(id);
		// add additional test code here
	}

	public void testSetId_fixture27_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture27();
		Integer id = Integer.valueOf(1);
		fixture.setId(id);
		// add additional test code here
	}

	public void testSetId_fixture28_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture28();
		Integer id = Integer.valueOf(1);
		fixture.setId(id);
		// add additional test code here
	}

	public void testSetId_fixture29_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture29();
		Integer id = Integer.valueOf(1);
		fixture.setId(id);
		// add additional test code here
	}

	public void testSetId_fixture30_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture30();
		Integer id = Integer.valueOf(1);
		fixture.setId(id);
		// add additional test code here
	}

	public void testSetMetadataPrefix_fixture1_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture1();
		String metadataPrefix = "";
		fixture.setMetadataPrefix(metadataPrefix);
		// add additional test code here
	}

	public void testSetMetadataPrefix_fixture2_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture2();
		String metadataPrefix = "0123456789";
		fixture.setMetadataPrefix(metadataPrefix);
		// add additional test code here
	}

	public void testSetMetadataPrefix_fixture3_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture3();
		String metadataPrefix = "0123456789";
		fixture.setMetadataPrefix(metadataPrefix);
		// add additional test code here
	}

	public void testSetMetadataPrefix_fixture4_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture4();
		String metadataPrefix = "0123456789";
		fixture.setMetadataPrefix(metadataPrefix);
		// add additional test code here
	}

	public void testSetMetadataPrefix_fixture5_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture5();
		String metadataPrefix = "0123456789";
		fixture.setMetadataPrefix(metadataPrefix);
		// add additional test code here
	}

	public void testSetMetadataPrefix_fixture6_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture6();
		String metadataPrefix = "0123456789";
		fixture.setMetadataPrefix(metadataPrefix);
		// add additional test code here
	}

	public void testSetMetadataPrefix_fixture7_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture7();
		String metadataPrefix = "0123456789";
		fixture.setMetadataPrefix(metadataPrefix);
		// add additional test code here
	}

	public void testSetMetadataPrefix_fixture8_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture8();
		String metadataPrefix = "0123456789";
		fixture.setMetadataPrefix(metadataPrefix);
		// add additional test code here
	}

	public void testSetMetadataPrefix_fixture9_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture9();
		String metadataPrefix = "0123456789";
		fixture.setMetadataPrefix(metadataPrefix);
		// add additional test code here
	}

	public void testSetMetadataPrefix_fixture10_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture10();
		String metadataPrefix = "0123456789";
		fixture.setMetadataPrefix(metadataPrefix);
		// add additional test code here
	}

	public void testSetMetadataPrefix_fixture11_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture11();
		String metadataPrefix = "0123456789";
		fixture.setMetadataPrefix(metadataPrefix);
		// add additional test code here
	}

	public void testSetMetadataPrefix_fixture12_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture12();
		String metadataPrefix = "0123456789";
		fixture.setMetadataPrefix(metadataPrefix);
		// add additional test code here
	}

	public void testSetMetadataPrefix_fixture13_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture13();
		String metadataPrefix = "0123456789";
		fixture.setMetadataPrefix(metadataPrefix);
		// add additional test code here
	}

	public void testSetMetadataPrefix_fixture14_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture14();
		String metadataPrefix = "0123456789";
		fixture.setMetadataPrefix(metadataPrefix);
		// add additional test code here
	}

	public void testSetMetadataPrefix_fixture15_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture15();
		String metadataPrefix = "0123456789";
		fixture.setMetadataPrefix(metadataPrefix);
		// add additional test code here
	}

	public void testSetMetadataPrefix_fixture16_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture16();
		String metadataPrefix = "0123456789";
		fixture.setMetadataPrefix(metadataPrefix);
		// add additional test code here
	}

	public void testSetMetadataPrefix_fixture17_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture17();
		String metadataPrefix = "0123456789";
		fixture.setMetadataPrefix(metadataPrefix);
		// add additional test code here
	}

	public void testSetMetadataPrefix_fixture18_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture18();
		String metadataPrefix = "0123456789";
		fixture.setMetadataPrefix(metadataPrefix);
		// add additional test code here
	}

	public void testSetMetadataPrefix_fixture19_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture19();
		String metadataPrefix = "0123456789";
		fixture.setMetadataPrefix(metadataPrefix);
		// add additional test code here
	}

	public void testSetMetadataPrefix_fixture20_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture20();
		String metadataPrefix = "0123456789";
		fixture.setMetadataPrefix(metadataPrefix);
		// add additional test code here
	}

	public void testSetMetadataPrefix_fixture21_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture21();
		String metadataPrefix = "0123456789";
		fixture.setMetadataPrefix(metadataPrefix);
		// add additional test code here
	}

	public void testSetMetadataPrefix_fixture22_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture22();
		String metadataPrefix = "0123456789";
		fixture.setMetadataPrefix(metadataPrefix);
		// add additional test code here
	}

	public void testSetMetadataPrefix_fixture23_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture23();
		String metadataPrefix = "0123456789";
		fixture.setMetadataPrefix(metadataPrefix);
		// add additional test code here
	}

	public void testSetMetadataPrefix_fixture24_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture24();
		String metadataPrefix = "0123456789";
		fixture.setMetadataPrefix(metadataPrefix);
		// add additional test code here
	}

	public void testSetMetadataPrefix_fixture25_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture25();
		String metadataPrefix = "0123456789";
		fixture.setMetadataPrefix(metadataPrefix);
		// add additional test code here
	}

	public void testSetMetadataPrefix_fixture26_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture26();
		String metadataPrefix = "0123456789";
		fixture.setMetadataPrefix(metadataPrefix);
		// add additional test code here
	}

	public void testSetMetadataPrefix_fixture27_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture27();
		String metadataPrefix = "0123456789";
		fixture.setMetadataPrefix(metadataPrefix);
		// add additional test code here
	}

	public void testSetMetadataPrefix_fixture28_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture28();
		String metadataPrefix = "0123456789";
		fixture.setMetadataPrefix(metadataPrefix);
		// add additional test code here
	}

	public void testSetMetadataPrefix_fixture29_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture29();
		String metadataPrefix = "0123456789";
		fixture.setMetadataPrefix(metadataPrefix);
		// add additional test code here
	}

	public void testSetMetadataPrefix_fixture30_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture30();
		String metadataPrefix = "0123456789";
		fixture.setMetadataPrefix(metadataPrefix);
		// add additional test code here
	}

	public void testSetQuery_fixture1_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture1();
		String query = "";
		fixture.setQuery(query);
		// add additional test code here
	}

	public void testSetQuery_fixture2_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture2();
		String query = "0123456789";
		fixture.setQuery(query);
		// add additional test code here
	}

	public void testSetQuery_fixture3_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture3();
		String query = "0123456789";
		fixture.setQuery(query);
		// add additional test code here
	}

	public void testSetQuery_fixture4_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture4();
		String query = "0123456789";
		fixture.setQuery(query);
		// add additional test code here
	}

	public void testSetQuery_fixture5_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture5();
		String query = "0123456789";
		fixture.setQuery(query);
		// add additional test code here
	}

	public void testSetQuery_fixture6_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture6();
		String query = "0123456789";
		fixture.setQuery(query);
		// add additional test code here
	}

	public void testSetQuery_fixture7_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture7();
		String query = "0123456789";
		fixture.setQuery(query);
		// add additional test code here
	}

	public void testSetQuery_fixture8_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture8();
		String query = "0123456789";
		fixture.setQuery(query);
		// add additional test code here
	}

	public void testSetQuery_fixture9_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture9();
		String query = "0123456789";
		fixture.setQuery(query);
		// add additional test code here
	}

	public void testSetQuery_fixture10_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture10();
		String query = "0123456789";
		fixture.setQuery(query);
		// add additional test code here
	}

	public void testSetQuery_fixture11_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture11();
		String query = "0123456789";
		fixture.setQuery(query);
		// add additional test code here
	}

	public void testSetQuery_fixture12_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture12();
		String query = "0123456789";
		fixture.setQuery(query);
		// add additional test code here
	}

	public void testSetQuery_fixture13_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture13();
		String query = "0123456789";
		fixture.setQuery(query);
		// add additional test code here
	}

	public void testSetQuery_fixture14_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture14();
		String query = "0123456789";
		fixture.setQuery(query);
		// add additional test code here
	}

	public void testSetQuery_fixture15_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture15();
		String query = "0123456789";
		fixture.setQuery(query);
		// add additional test code here
	}

	public void testSetQuery_fixture16_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture16();
		String query = "0123456789";
		fixture.setQuery(query);
		// add additional test code here
	}

	public void testSetQuery_fixture17_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture17();
		String query = "0123456789";
		fixture.setQuery(query);
		// add additional test code here
	}

	public void testSetQuery_fixture18_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture18();
		String query = "0123456789";
		fixture.setQuery(query);
		// add additional test code here
	}

	public void testSetQuery_fixture19_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture19();
		String query = "0123456789";
		fixture.setQuery(query);
		// add additional test code here
	}

	public void testSetQuery_fixture20_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture20();
		String query = "0123456789";
		fixture.setQuery(query);
		// add additional test code here
	}

	public void testSetQuery_fixture21_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture21();
		String query = "0123456789";
		fixture.setQuery(query);
		// add additional test code here
	}

	public void testSetQuery_fixture22_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture22();
		String query = "0123456789";
		fixture.setQuery(query);
		// add additional test code here
	}

	public void testSetQuery_fixture23_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture23();
		String query = "0123456789";
		fixture.setQuery(query);
		// add additional test code here
	}

	public void testSetQuery_fixture24_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture24();
		String query = "0123456789";
		fixture.setQuery(query);
		// add additional test code here
	}

	public void testSetQuery_fixture25_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture25();
		String query = "0123456789";
		fixture.setQuery(query);
		// add additional test code here
	}

	public void testSetQuery_fixture26_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture26();
		String query = "0123456789";
		fixture.setQuery(query);
		// add additional test code here
	}

	public void testSetQuery_fixture27_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture27();
		String query = "0123456789";
		fixture.setQuery(query);
		// add additional test code here
	}

	public void testSetQuery_fixture28_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture28();
		String query = "0123456789";
		fixture.setQuery(query);
		// add additional test code here
	}

	public void testSetQuery_fixture29_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture29();
		String query = "0123456789";
		fixture.setQuery(query);
		// add additional test code here
	}

	public void testSetQuery_fixture30_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture30();
		String query = "0123456789";
		fixture.setQuery(query);
		// add additional test code here
	}

	public void testSetQueryForCount_fixture1_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture1();
		String queryForCount = "";
		fixture.setQueryForCount(queryForCount);
		// add additional test code here
	}

	public void testSetQueryForCount_fixture2_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture2();
		String queryForCount = "0123456789";
		fixture.setQueryForCount(queryForCount);
		// add additional test code here
	}

	public void testSetQueryForCount_fixture3_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture3();
		String queryForCount = "0123456789";
		fixture.setQueryForCount(queryForCount);
		// add additional test code here
	}

	public void testSetQueryForCount_fixture4_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture4();
		String queryForCount = "0123456789";
		fixture.setQueryForCount(queryForCount);
		// add additional test code here
	}

	public void testSetQueryForCount_fixture5_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture5();
		String queryForCount = "0123456789";
		fixture.setQueryForCount(queryForCount);
		// add additional test code here
	}

	public void testSetQueryForCount_fixture6_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture6();
		String queryForCount = "0123456789";
		fixture.setQueryForCount(queryForCount);
		// add additional test code here
	}

	public void testSetQueryForCount_fixture7_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture7();
		String queryForCount = "0123456789";
		fixture.setQueryForCount(queryForCount);
		// add additional test code here
	}

	public void testSetQueryForCount_fixture8_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture8();
		String queryForCount = "0123456789";
		fixture.setQueryForCount(queryForCount);
		// add additional test code here
	}

	public void testSetQueryForCount_fixture9_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture9();
		String queryForCount = "0123456789";
		fixture.setQueryForCount(queryForCount);
		// add additional test code here
	}

	public void testSetQueryForCount_fixture10_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture10();
		String queryForCount = "0123456789";
		fixture.setQueryForCount(queryForCount);
		// add additional test code here
	}

	public void testSetQueryForCount_fixture11_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture11();
		String queryForCount = "0123456789";
		fixture.setQueryForCount(queryForCount);
		// add additional test code here
	}

	public void testSetQueryForCount_fixture12_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture12();
		String queryForCount = "0123456789";
		fixture.setQueryForCount(queryForCount);
		// add additional test code here
	}

	public void testSetQueryForCount_fixture13_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture13();
		String queryForCount = "0123456789";
		fixture.setQueryForCount(queryForCount);
		// add additional test code here
	}

	public void testSetQueryForCount_fixture14_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture14();
		String queryForCount = "0123456789";
		fixture.setQueryForCount(queryForCount);
		// add additional test code here
	}

	public void testSetQueryForCount_fixture15_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture15();
		String queryForCount = "0123456789";
		fixture.setQueryForCount(queryForCount);
		// add additional test code here
	}

	public void testSetQueryForCount_fixture16_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture16();
		String queryForCount = "0123456789";
		fixture.setQueryForCount(queryForCount);
		// add additional test code here
	}

	public void testSetQueryForCount_fixture17_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture17();
		String queryForCount = "0123456789";
		fixture.setQueryForCount(queryForCount);
		// add additional test code here
	}

	public void testSetQueryForCount_fixture18_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture18();
		String queryForCount = "0123456789";
		fixture.setQueryForCount(queryForCount);
		// add additional test code here
	}

	public void testSetQueryForCount_fixture19_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture19();
		String queryForCount = "0123456789";
		fixture.setQueryForCount(queryForCount);
		// add additional test code here
	}

	public void testSetQueryForCount_fixture20_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture20();
		String queryForCount = "0123456789";
		fixture.setQueryForCount(queryForCount);
		// add additional test code here
	}

	public void testSetQueryForCount_fixture21_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture21();
		String queryForCount = "0123456789";
		fixture.setQueryForCount(queryForCount);
		// add additional test code here
	}

	public void testSetQueryForCount_fixture22_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture22();
		String queryForCount = "0123456789";
		fixture.setQueryForCount(queryForCount);
		// add additional test code here
	}

	public void testSetQueryForCount_fixture23_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture23();
		String queryForCount = "0123456789";
		fixture.setQueryForCount(queryForCount);
		// add additional test code here
	}

	public void testSetQueryForCount_fixture24_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture24();
		String queryForCount = "0123456789";
		fixture.setQueryForCount(queryForCount);
		// add additional test code here
	}

	public void testSetQueryForCount_fixture25_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture25();
		String queryForCount = "0123456789";
		fixture.setQueryForCount(queryForCount);
		// add additional test code here
	}

	public void testSetQueryForCount_fixture26_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture26();
		String queryForCount = "0123456789";
		fixture.setQueryForCount(queryForCount);
		// add additional test code here
	}

	public void testSetQueryForCount_fixture27_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture27();
		String queryForCount = "0123456789";
		fixture.setQueryForCount(queryForCount);
		// add additional test code here
	}

	public void testSetQueryForCount_fixture28_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture28();
		String queryForCount = "0123456789";
		fixture.setQueryForCount(queryForCount);
		// add additional test code here
	}

	public void testSetQueryForCount_fixture29_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture29();
		String queryForCount = "0123456789";
		fixture.setQueryForCount(queryForCount);
		// add additional test code here
	}

	public void testSetQueryForCount_fixture30_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture30();
		String queryForCount = "0123456789";
		fixture.setQueryForCount(queryForCount);
		// add additional test code here
	}

	public void testToString_fixture1_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture1();
		String result = fixture.toString();
		// add additional test code here
		assertEquals("id: null, creationDate: null, query: null, " +
				"queryForCount: null, metadataPrefix: null", result);
	}

	public void testToString_fixture2_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture2();
		String result = fixture.toString();
		// add additional test code here
		assertEquals("id: -1, creationDate: 2020-06-02 18:27:16.0, " +
				"query: , queryForCount: , metadataPrefix: ", result);
	}

	public void testToString_fixture3_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture3();
		String result = fixture.toString();
		// add additional test code here
		assertEquals("id: -1, creationDate: 2020-06-02 18:27:16.0, " +
				"query: 0123456789, queryForCount: , " +
				"metadataPrefix: 0123456789", result);
	}

	public void testToString_fixture4_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture4();
		String result = fixture.toString();
		// add additional test code here
		assertEquals("id: -1, creationDate: 2020-06-02 18:27:16.0, " +
				"query: 0123456789, queryForCount: 0123456789, " +
				"metadataPrefix: 0123456789", result);
	}

	public void testToString_fixture5_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture5();
		String result = fixture.toString();
		// add additional test code here
		assertEquals("id: -1, creationDate: 2020-06-02 18:27:16.0, " +
				"query: 0123456789, queryForCount: Ant-1.0.txt, " +
				"metadataPrefix: 0123456789", result);
	}

	public void testToString_fixture6_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture6();
		String result = fixture.toString();
		// add additional test code here
		assertEquals("id: 0, creationDate: 2020-06-02 18:27:16.0, " +
				"query: 0123456789, queryForCount: , " +
				"metadataPrefix: 0123456789", result);
	}

	public void testToString_fixture7_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture7();
		String result = fixture.toString();
		// add additional test code here
		assertEquals("id: 0, creationDate: 2020-06-02 18:27:16.0, " +
				"query: 0123456789, queryForCount: 0123456789, " +
				"metadataPrefix: 0123456789", result);
	}

	public void testToString_fixture8_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture8();
		String result = fixture.toString();
		// add additional test code here
		assertEquals("id: 0, creationDate: 2020-06-02 18:27:16.0, " +
				"query: Ant-1.0.txt, queryForCount: Ant-1.0.txt, " +
				"metadataPrefix: Ant-1.0.txt", result);
	}

	public void testToString_fixture9_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture9();
		String result = fixture.toString();
		// add additional test code here
		assertEquals("id: 1, creationDate: 2020-06-02 18:27:16.0, " +
				"query: Ant-1.0.txt, queryForCount: , " +
				"metadataPrefix: Ant-1.0.txt", result);
	}

	public void testToString_fixture10_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture10();
		String result = fixture.toString();
		// add additional test code here
		assertEquals("id: 1, creationDate: 2020-06-02 18:27:16.0, " +
				"query: Ant-1.0.txt, queryForCount: 0123456789, " +
				"metadataPrefix: Ant-1.0.txt", result);
	}

	public void testToString_fixture11_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture11();
		String result = fixture.toString();
		// add additional test code here
		assertEquals("id: 1, creationDate: 2020-06-02 18:27:16.0, " +
				"query: Ant-1.0.txt, queryForCount: Ant-1.0.txt, " +
				"metadataPrefix: Ant-1.0.txt", result);
	}

	public void testToString_fixture12_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture12();
		String result = fixture.toString();
		// add additional test code here
		assertEquals("id: -1, creationDate: 1990-06-02 18:27:16.0, " +
				"query: 0123456789, queryForCount: , " +
				"metadataPrefix: 0123456789", result);
	}

	public void testToString_fixture13_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture13();
		String result = fixture.toString();
		// add additional test code here
		assertEquals("id: -1, creationDate: 1990-06-02 18:27:16.0, " +
				"query: 0123456789, queryForCount: 0123456789, " +
				"metadataPrefix: 0123456789", result);
	}

	public void testToString_fixture14_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture14();
		String result = fixture.toString();
		// add additional test code here
		assertEquals("id: -1, creationDate: 1990-06-02 18:27:16.0, " +
				"query: 0123456789, queryForCount: Ant-1.0.txt, " +
				"metadataPrefix: 0123456789", result);
	}

	public void testToString_fixture15_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture15();
		String result = fixture.toString();
		// add additional test code here
		assertEquals("id: 0, creationDate: 1990-06-02 18:27:16.0, " +
				"query: 0123456789, queryForCount: , " +
				"metadataPrefix: 0123456789", result);
	}

	public void testToString_fixture16_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture16();
		String result = fixture.toString();
		// add additional test code here
		assertEquals("id: 0, creationDate: 1990-06-02 18:27:16.0, " +
				"query: 0123456789, queryForCount: 0123456789, " +
				"metadataPrefix: 0123456789", result);
	}

	public void testToString_fixture17_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture17();
		String result = fixture.toString();
		// add additional test code here
		assertEquals("id: 0, creationDate: 1990-06-02 18:27:16.0, " +
				"query: Ant-1.0.txt, queryForCount: 0123456789, " +
				"metadataPrefix: Ant-1.0.txt", result);
	}

	public void testToString_fixture18_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture18();
		String result = fixture.toString();
		// add additional test code here
		assertEquals("id: 0, creationDate: 1990-06-02 18:27:16.0, " +
				"query: Ant-1.0.txt, queryForCount: Ant-1.0.txt, " +
				"metadataPrefix: Ant-1.0.txt", result);
	}

	public void testToString_fixture19_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture19();
		String result = fixture.toString();
		// add additional test code here
		assertEquals("id: 1, creationDate: 1990-06-02 18:27:16.0, " +
				"query: Ant-1.0.txt, queryForCount: , " +
				"metadataPrefix: Ant-1.0.txt", result);
	}

	public void testToString_fixture20_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture20();
		String result = fixture.toString();
		// add additional test code here
		assertEquals("id: 1, creationDate: 1990-06-02 18:27:16.0, " +
				"query: Ant-1.0.txt, queryForCount: 0123456789, " +
				"metadataPrefix: Ant-1.0.txt", result);
	}

	public void testToString_fixture21_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture21();
		String result = fixture.toString();
		// add additional test code here
		assertEquals("id: 1, creationDate: 1990-06-02 18:27:16.0, " +
				"query: Ant-1.0.txt, queryForCount: Ant-1.0.txt, " +
				"metadataPrefix: Ant-1.0.txt", result);
	}

	public void testToString_fixture22_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture22();
		String result = fixture.toString();
		// add additional test code here
		assertEquals("id: -1, creationDate: 2000-01-01 08:59:59.0, " +
				"query: 0123456789, queryForCount: , " +
				"metadataPrefix: 0123456789", result);
	}

	public void testToString_fixture23_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture23();
		String result = fixture.toString();
		// add additional test code here
		assertEquals("id: -1, creationDate: 2000-01-01 08:59:59.0, " +
				"query: 0123456789, queryForCount: 0123456789, " +
				"metadataPrefix: 0123456789", result);
	}

	public void testToString_fixture24_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture24();
		String result = fixture.toString();
		// add additional test code here
		assertEquals("id: -1, creationDate: 2000-01-01 08:59:59.0, " +
				"query: 0123456789, queryForCount: Ant-1.0.txt, " +
				"metadataPrefix: 0123456789", result);
	}

	public void testToString_fixture25_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture25();
		String result = fixture.toString();
		// add additional test code here
		assertEquals("id: 0, creationDate: 2000-01-01 08:59:59.0, " +
				"query: 0123456789, queryForCount: , " +
				"metadataPrefix: 0123456789", result);
	}

	public void testToString_fixture26_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture26();
		String result = fixture.toString();
		// add additional test code here
		assertEquals("id: 0, creationDate: 2000-01-01 08:59:59.0, " +
				"query: Ant-1.0.txt, queryForCount: 0123456789, " +
				"metadataPrefix: Ant-1.0.txt", result);
	}

	public void testToString_fixture27_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture27();
		String result = fixture.toString();
		// add additional test code here
		assertEquals("id: 0, creationDate: 2000-01-01 08:59:59.0, " +
				"query: Ant-1.0.txt, queryForCount: Ant-1.0.txt, " +
				"metadataPrefix: Ant-1.0.txt", result);
	}

	public void testToString_fixture28_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture28();
		String result = fixture.toString();
		// add additional test code here
		assertEquals("id: 1, creationDate: 2000-01-01 08:59:59.0, " +
				"query: Ant-1.0.txt, queryForCount: , " +
				"metadataPrefix: Ant-1.0.txt", result);
	}

	public void testToString_fixture29_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture29();
		String result = fixture.toString();
		// add additional test code here
		assertEquals("id: 1, creationDate: 2000-01-01 08:59:59.0, " +
				"query: Ant-1.0.txt, queryForCount: 0123456789, " +
				"metadataPrefix: Ant-1.0.txt", result);
	}

	public void testToString_fixture30_1()
		throws Exception {
		ResumptionTokenDTO fixture = getFixture30();
		String result = fixture.toString();
		// add additional test code here
		assertEquals("id: 1, creationDate: 2000-01-01 08:59:59.0, "
				+ "query: Ant-1.0.txt, queryForCount: Ant-1.0.txt, "
				+ "metadataPrefix: Ant-1.0.txt", result);
	}

	protected void setUp()
		throws Exception {
		super.setUp();
		// add additional set up code here
	}

	protected void tearDown()
		throws Exception {
		super.tearDown();
		// Add additional tear down code here
	}

	public static void main(String[] args) {
		if (args.length == 0) {
			// Run all of the tests
			junit.textui.TestRunner.run(ResumptionTokenDTOTestCase.class);
		} else {
			// Run only the named tests
			TestSuite suite = new TestSuite("Selected tests");
			for (int i = 0; i < args.length; i++) {
				TestCase test = new ResumptionTokenDTOTestCase();
				test.setName(args[i]);
				suite.addTest(test);
			}
			junit.textui.TestRunner.run(suite);
		}
	}
}