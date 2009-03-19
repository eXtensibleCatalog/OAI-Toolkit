/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package test.extensiblecatalog.OAIToolkit.DTOs;

import info.extensiblecatalog.OAIToolkit.DTOs.SetToRecordDTO;
import junit.framework.*;

/**
 * The class <code>SetToRecordsDTOTestCase</code> contains tests for the class <code>{@link SetToRecordDTO}</code>.
 *
 * @generatedBy CodePro at 2008.05.15. 18:03
 * @author kiru
 * @version $Revision: 1.0 $
 */
public class SetToRecordsDTOTestCase extends TestCase {
	private SetToRecordDTO fixture1;
	private SetToRecordDTO fixture2;
	private SetToRecordDTO fixture3;
	private SetToRecordDTO fixture4;
	private SetToRecordDTO fixture5;
	private SetToRecordDTO fixture6;
	private SetToRecordDTO fixture7;
	private SetToRecordDTO fixture8;
	private SetToRecordDTO fixture9;
	private SetToRecordDTO fixture10;
	public SetToRecordDTO getFixture1() throws Exception {
		if (fixture1 == null) {
			fixture1 = new SetToRecordDTO();
		}
		return fixture1;
	}

	public SetToRecordDTO getFixture2() throws Exception {
		if (fixture2 == null) {
			fixture2 = new SetToRecordDTO();
			fixture2.setRecordId(Integer.valueOf(-1));
			fixture2.setSetId(Integer.valueOf(-1));
		}
		return fixture2;
	}

	public SetToRecordDTO getFixture3() throws Exception {
		if (fixture3 == null) {
			fixture3 = new SetToRecordDTO();
			fixture3.setRecordId(Integer.valueOf(-1));
			fixture3.setSetId(Integer.valueOf(0));
		}
		return fixture3;
	}

	public SetToRecordDTO getFixture4() throws Exception {
		if (fixture4 == null) {
			fixture4 = new SetToRecordDTO();
			fixture4.setRecordId(Integer.valueOf(-1));
			fixture4.setSetId(Integer.valueOf(1));
		}
		return fixture4;
	}

	public SetToRecordDTO getFixture5() throws Exception {
		if (fixture5 == null) {
			fixture5 = new SetToRecordDTO();
			fixture5.setRecordId(Integer.valueOf(0));
			fixture5.setSetId(Integer.valueOf(-1));
		}
		return fixture5;
	}

	public SetToRecordDTO getFixture6() throws Exception {
		if (fixture6 == null) {
			fixture6 = new SetToRecordDTO();
			fixture6.setRecordId(Integer.valueOf(0));
			fixture6.setSetId(Integer.valueOf(0));
		}
		return fixture6;
	}

	public SetToRecordDTO getFixture7() throws Exception {
		if (fixture7 == null) {
			fixture7 = new SetToRecordDTO();
			fixture7.setRecordId(Integer.valueOf(0));
			fixture7.setSetId(Integer.valueOf(1));
		}
		return fixture7;
	}

	public SetToRecordDTO getFixture8() throws Exception {
		if (fixture8 == null) {
			fixture8 = new SetToRecordDTO();
			fixture8.setRecordId(Integer.valueOf(1));
			fixture8.setSetId(Integer.valueOf(-1));
		}
		return fixture8;
	}

	public SetToRecordDTO getFixture9() throws Exception {
		if (fixture9 == null) {
			fixture9 = new SetToRecordDTO();
			fixture9.setRecordId(Integer.valueOf(1));
			fixture9.setSetId(Integer.valueOf(0));
		}
		return fixture9;
	}

	public SetToRecordDTO getFixture10() throws Exception {
		if (fixture10 == null) {
			fixture10 = new SetToRecordDTO();
			fixture10.setRecordId(Integer.valueOf(1));
			fixture10.setSetId(Integer.valueOf(1));
		}
		return fixture10;
	}

	public void testEquals_fixture1_1() throws Exception {
		SetToRecordDTO fixture = getFixture1();
		SetToRecordDTO other = new SetToRecordDTO();
		boolean result = fixture.equals(other);
		// add additional test code here
		// An unexpected exception was thrown while executing this test:
		//    java.lang.NullPointerException
		//       at info.extensiblecatalog.OAIToolkit.DTOs.SetToRecordDTO.equals(SetToRecordDTO.java:27)
		assertTrue(result);
	}

	public void testEquals_fixture2_1() throws Exception {
		SetToRecordDTO fixture = getFixture2();
		SetToRecordDTO other = new SetToRecordDTO();
		boolean result = fixture.equals(other);
		// add additional test code here
		assertEquals(false, result);
	}

	public void testEquals_fixture3_1() throws Exception {
		SetToRecordDTO fixture = getFixture3();
		SetToRecordDTO other = new SetToRecordDTO();
		boolean result = fixture.equals(other);
		// add additional test code here
		assertEquals(false, result);
	}

	public void testEquals_fixture4_1() throws Exception {
		SetToRecordDTO fixture = getFixture4();
		SetToRecordDTO other = new SetToRecordDTO();
		boolean result = fixture.equals(other);
		// add additional test code here
		assertEquals(false, result);
	}

	public void testEquals_fixture5_1() throws Exception {
		SetToRecordDTO fixture = getFixture5();
		SetToRecordDTO other = new SetToRecordDTO();
		boolean result = fixture.equals(other);
		// add additional test code here
		assertEquals(false, result);
	}

	public void testEquals_fixture6_1() throws Exception {
		SetToRecordDTO fixture = getFixture6();
		SetToRecordDTO other = new SetToRecordDTO();
		boolean result = fixture.equals(other);
		// add additional test code here
		assertEquals(false, result);
	}

	public void testEquals_fixture7_1() throws Exception {
		SetToRecordDTO fixture = getFixture7();
		SetToRecordDTO other = new SetToRecordDTO();
		boolean result = fixture.equals(other);
		// add additional test code here
		assertEquals(false, result);
	}

	public void testEquals_fixture8_1() throws Exception {
		SetToRecordDTO fixture = getFixture8();
		SetToRecordDTO other = new SetToRecordDTO();
		boolean result = fixture.equals(other);
		// add additional test code here
		assertEquals(false, result);
	}

	public void testEquals_fixture9_1() throws Exception {
		SetToRecordDTO fixture = getFixture9();
		SetToRecordDTO other = new SetToRecordDTO();
		boolean result = fixture.equals(other);
		// add additional test code here
		assertEquals(false, result);
	}

	public void testEquals_fixture10_1() throws Exception {
		SetToRecordDTO fixture = getFixture10();
		SetToRecordDTO other = new SetToRecordDTO();
		boolean result = fixture.equals(other);
		// add additional test code here
		assertEquals(false, result);
	}

	public void testGetId_fixture1_1() throws Exception {
		SetToRecordDTO fixture = getFixture1();
		Integer result = fixture.getRecordId();
		// add additional test code here
		assertEquals(null, result);
	}

	public void testGetId_fixture2_1() throws Exception {
		SetToRecordDTO fixture = getFixture2();
		Integer result = fixture.getRecordId();
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

	public void testGetId_fixture3_1() throws Exception {
		SetToRecordDTO fixture = getFixture3();
		Integer result = fixture.getRecordId();
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

	public void testGetId_fixture4_1() throws Exception {
		SetToRecordDTO fixture = getFixture4();
		Integer result = fixture.getRecordId();
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

	public void testGetId_fixture5_1() throws Exception {
		SetToRecordDTO fixture = getFixture5();
		Integer result = fixture.getRecordId();
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

	public void testGetId_fixture6_1() throws Exception {
		SetToRecordDTO fixture = getFixture6();
		Integer result = fixture.getRecordId();
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

	public void testGetId_fixture7_1() throws Exception {
		SetToRecordDTO fixture = getFixture7();
		Integer result = fixture.getRecordId();
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

	public void testGetId_fixture8_1() throws Exception {
		SetToRecordDTO fixture = getFixture8();
		Integer result = fixture.getRecordId();
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

	public void testGetId_fixture9_1() throws Exception {
		SetToRecordDTO fixture = getFixture9();
		Integer result = fixture.getRecordId();
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

	public void testGetId_fixture10_1() throws Exception {
		SetToRecordDTO fixture = getFixture10();
		Integer result = fixture.getRecordId();
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

	public void testGetSetId_fixture1_1() throws Exception {
		SetToRecordDTO fixture = getFixture1();
		Integer result = fixture.getSetId();
		// add additional test code here
		assertEquals(null, result);
	}

	public void testGetSetId_fixture2_1() throws Exception {
		SetToRecordDTO fixture = getFixture2();
		Integer result = fixture.getSetId();
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

	public void testGetSetId_fixture3_1() throws Exception {
		SetToRecordDTO fixture = getFixture3();
		Integer result = fixture.getSetId();
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

	public void testGetSetId_fixture4_1() throws Exception {
		SetToRecordDTO fixture = getFixture4();
		Integer result = fixture.getSetId();
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

	public void testGetSetId_fixture5_1() throws Exception {
		SetToRecordDTO fixture = getFixture5();
		Integer result = fixture.getSetId();
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

	public void testGetSetId_fixture6_1() throws Exception {
		SetToRecordDTO fixture = getFixture6();
		Integer result = fixture.getSetId();
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

	public void testGetSetId_fixture7_1() throws Exception {
		SetToRecordDTO fixture = getFixture7();
		Integer result = fixture.getSetId();
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

	public void testGetSetId_fixture8_1() throws Exception {
		SetToRecordDTO fixture = getFixture8();
		Integer result = fixture.getSetId();
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

	public void testGetSetId_fixture9_1() throws Exception {
		SetToRecordDTO fixture = getFixture9();
		Integer result = fixture.getSetId();
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

	public void testGetSetId_fixture10_1() throws Exception {
		SetToRecordDTO fixture = getFixture10();
		Integer result = fixture.getSetId();
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

	public void testSetId_fixture1_1() throws Exception {
		SetToRecordDTO fixture = getFixture1();
		Integer id = Integer.valueOf(-1);
		fixture.setRecordId(id);
		// add additional test code here
	}

	public void testSetId_fixture2_1() throws Exception {
		SetToRecordDTO fixture = getFixture2();
		Integer id = Integer.valueOf(0);
		fixture.setRecordId(id);
		// add additional test code here
	}

	public void testSetId_fixture3_1() throws Exception {
		SetToRecordDTO fixture = getFixture3();
		Integer id = Integer.valueOf(1);
		fixture.setRecordId(id);
		// add additional test code here
	}

	public void testSetId_fixture4_1() throws Exception {
		SetToRecordDTO fixture = getFixture4();
		Integer id = Integer.valueOf(1);
		fixture.setRecordId(id);
		// add additional test code here
	}

	public void testSetId_fixture5_1() throws Exception {
		SetToRecordDTO fixture = getFixture5();
		Integer id = Integer.valueOf(1);
		fixture.setRecordId(id);
		// add additional test code here
	}

	public void testSetId_fixture6_1() throws Exception {
		SetToRecordDTO fixture = getFixture6();
		Integer id = Integer.valueOf(1);
		fixture.setRecordId(id);
		// add additional test code here
	}

	public void testSetId_fixture7_1() throws Exception {
		SetToRecordDTO fixture = getFixture7();
		Integer id = Integer.valueOf(1);
		fixture.setRecordId(id);
		// add additional test code here
	}

	public void testSetId_fixture8_1() throws Exception {
		SetToRecordDTO fixture = getFixture8();
		Integer id = Integer.valueOf(1);
		fixture.setRecordId(id);
		// add additional test code here
	}

	public void testSetId_fixture9_1() throws Exception {
		SetToRecordDTO fixture = getFixture9();
		Integer id = Integer.valueOf(1);
		fixture.setRecordId(id);
		// add additional test code here
	}

	public void testSetId_fixture10_1() throws Exception {
		SetToRecordDTO fixture = getFixture10();
		Integer id = Integer.valueOf(1);
		fixture.setRecordId(id);
		// add additional test code here
	}

	public void testSetId_fixture2_2() throws Exception {
		SetToRecordDTO fixture = getFixture2();
		Integer id = Integer.valueOf(-1);
		fixture.setRecordId(id);
		// add additional test code here
	}

	public void testSetId_fixture3_2() throws Exception {
		SetToRecordDTO fixture = getFixture3();
		Integer id = Integer.valueOf(0);
		fixture.setRecordId(id);
		// add additional test code here
	}

	public void testSetId_fixture4_2() throws Exception {
		SetToRecordDTO fixture = getFixture4();
		Integer id = Integer.valueOf(0);
		fixture.setRecordId(id);
		// add additional test code here
	}

	public void testSetId_fixture5_2() throws Exception {
		SetToRecordDTO fixture = getFixture5();
		Integer id = Integer.valueOf(0);
		fixture.setRecordId(id);
		// add additional test code here
	}

	public void testSetId_fixture6_2() throws Exception {
		SetToRecordDTO fixture = getFixture6();
		Integer id = Integer.valueOf(0);
		fixture.setRecordId(id);
		// add additional test code here
	}

	public void testSetId_fixture7_2() throws Exception {
		SetToRecordDTO fixture = getFixture7();
		Integer id = Integer.valueOf(0);
		fixture.setRecordId(id);
		// add additional test code here
	}

	public void testSetId_fixture8_2() throws Exception {
		SetToRecordDTO fixture = getFixture8();
		Integer id = Integer.valueOf(0);
		fixture.setRecordId(id);
		// add additional test code here
	}

	public void testSetId_fixture9_2() throws Exception {
		SetToRecordDTO fixture = getFixture9();
		Integer id = Integer.valueOf(0);
		fixture.setRecordId(id);
		// add additional test code here
	}

	public void testSetId_fixture10_2() throws Exception {
		SetToRecordDTO fixture = getFixture10();
		Integer id = Integer.valueOf(0);
		fixture.setRecordId(id);
		// add additional test code here
	}

	public void testSetId_fixture1_2() throws Exception {
		SetToRecordDTO fixture = getFixture1();
		Integer id = Integer.valueOf(1);
		fixture.setRecordId(id);
		// add additional test code here
	}

	public void testSetId_fixture3_3() throws Exception {
		SetToRecordDTO fixture = getFixture3();
		Integer id = Integer.valueOf(-1);
		fixture.setRecordId(id);
		// add additional test code here
	}

	public void testSetId_fixture4_3() throws Exception {
		SetToRecordDTO fixture = getFixture4();
		Integer id = Integer.valueOf(-1);
		fixture.setRecordId(id);
		// add additional test code here
	}

	public void testSetId_fixture5_3() throws Exception {
		SetToRecordDTO fixture = getFixture5();
		Integer id = Integer.valueOf(-1);
		fixture.setRecordId(id);
		// add additional test code here
	}

	public void testSetId_fixture6_3() throws Exception {
		SetToRecordDTO fixture = getFixture6();
		Integer id = Integer.valueOf(-1);
		fixture.setRecordId(id);
		// add additional test code here
	}

	public void testSetId_fixture7_3() throws Exception {
		SetToRecordDTO fixture = getFixture7();
		Integer id = Integer.valueOf(-1);
		fixture.setRecordId(id);
		// add additional test code here
	}

	public void testSetId_fixture8_3() throws Exception {
		SetToRecordDTO fixture = getFixture8();
		Integer id = Integer.valueOf(-1);
		fixture.setRecordId(id);
		// add additional test code here
	}

	public void testSetId_fixture9_3() throws Exception {
		SetToRecordDTO fixture = getFixture9();
		Integer id = Integer.valueOf(-1);
		fixture.setRecordId(id);
		// add additional test code here
	}

	public void testSetId_fixture10_3() throws Exception {
		SetToRecordDTO fixture = getFixture10();
		Integer id = Integer.valueOf(-1);
		fixture.setRecordId(id);
		// add additional test code here
	}

	public void testSetId_fixture1_3() throws Exception {
		SetToRecordDTO fixture = getFixture1();
		Integer id = Integer.valueOf(0);
		fixture.setRecordId(id);
		// add additional test code here
	}

	public void testSetId_fixture2_3() throws Exception {
		SetToRecordDTO fixture = getFixture2();
		Integer id = Integer.valueOf(1);
		fixture.setRecordId(id);
		// add additional test code here
	}

	public void testSetSetId_fixture1_1() throws Exception {
		SetToRecordDTO fixture = getFixture1();
		Integer setId = Integer.valueOf(-1);
		fixture.setSetId(setId);
		// add additional test code here
	}

	public void testSetSetId_fixture2_1() throws Exception {
		SetToRecordDTO fixture = getFixture2();
		Integer setId = Integer.valueOf(0);
		fixture.setSetId(setId);
		// add additional test code here
	}

	public void testSetSetId_fixture3_1() throws Exception {
		SetToRecordDTO fixture = getFixture3();
		Integer setId = Integer.valueOf(1);
		fixture.setSetId(setId);
		// add additional test code here
	}

	public void testSetSetId_fixture4_1() throws Exception {
		SetToRecordDTO fixture = getFixture4();
		Integer setId = Integer.valueOf(1);
		fixture.setSetId(setId);
		// add additional test code here
	}

	public void testSetSetId_fixture5_1() throws Exception {
		SetToRecordDTO fixture = getFixture5();
		Integer setId = Integer.valueOf(1);
		fixture.setSetId(setId);
		// add additional test code here
	}

	public void testSetSetId_fixture6_1() throws Exception {
		SetToRecordDTO fixture = getFixture6();
		Integer setId = Integer.valueOf(1);
		fixture.setSetId(setId);
		// add additional test code here
	}

	public void testSetSetId_fixture7_1() throws Exception {
		SetToRecordDTO fixture = getFixture7();
		Integer setId = Integer.valueOf(1);
		fixture.setSetId(setId);
		// add additional test code here
	}

	public void testSetSetId_fixture8_1() throws Exception {
		SetToRecordDTO fixture = getFixture8();
		Integer setId = Integer.valueOf(1);
		fixture.setSetId(setId);
		// add additional test code here
	}

	public void testSetSetId_fixture9_1() throws Exception {
		SetToRecordDTO fixture = getFixture9();
		Integer setId = Integer.valueOf(1);
		fixture.setSetId(setId);
		// add additional test code here
	}

	public void testSetSetId_fixture10_1() throws Exception {
		SetToRecordDTO fixture = getFixture10();
		Integer setId = Integer.valueOf(1);
		fixture.setSetId(setId);
		// add additional test code here
	}

	public void testSetSetId_fixture2_2() throws Exception {
		SetToRecordDTO fixture = getFixture2();
		Integer setId = Integer.valueOf(-1);
		fixture.setSetId(setId);
		// add additional test code here
	}

	public void testSetSetId_fixture3_2() throws Exception {
		SetToRecordDTO fixture = getFixture3();
		Integer setId = Integer.valueOf(0);
		fixture.setSetId(setId);
		// add additional test code here
	}

	public void testSetSetId_fixture4_2() throws Exception {
		SetToRecordDTO fixture = getFixture4();
		Integer setId = Integer.valueOf(0);
		fixture.setSetId(setId);
		// add additional test code here
	}

	public void testSetSetId_fixture5_2() throws Exception {
		SetToRecordDTO fixture = getFixture5();
		Integer setId = Integer.valueOf(0);
		fixture.setSetId(setId);
		// add additional test code here
	}

	public void testSetSetId_fixture6_2() throws Exception {
		SetToRecordDTO fixture = getFixture6();
		Integer setId = Integer.valueOf(0);
		fixture.setSetId(setId);
		// add additional test code here
	}

	public void testSetSetId_fixture7_2() throws Exception {
		SetToRecordDTO fixture = getFixture7();
		Integer setId = Integer.valueOf(0);
		fixture.setSetId(setId);
		// add additional test code here
	}

	public void testSetSetId_fixture8_2() throws Exception {
		SetToRecordDTO fixture = getFixture8();
		Integer setId = Integer.valueOf(0);
		fixture.setSetId(setId);
		// add additional test code here
	}

	public void testSetSetId_fixture9_2() throws Exception {
		SetToRecordDTO fixture = getFixture9();
		Integer setId = Integer.valueOf(0);
		fixture.setSetId(setId);
		// add additional test code here
	}

	public void testSetSetId_fixture10_2() throws Exception {
		SetToRecordDTO fixture = getFixture10();
		Integer setId = Integer.valueOf(0);
		fixture.setSetId(setId);
		// add additional test code here
	}

	public void testSetSetId_fixture1_2() throws Exception {
		SetToRecordDTO fixture = getFixture1();
		Integer setId = Integer.valueOf(1);
		fixture.setSetId(setId);
		// add additional test code here
	}

	public void testSetSetId_fixture3_3() throws Exception {
		SetToRecordDTO fixture = getFixture3();
		Integer setId = Integer.valueOf(-1);
		fixture.setSetId(setId);
		// add additional test code here
	}

	public void testSetSetId_fixture4_3() throws Exception {
		SetToRecordDTO fixture = getFixture4();
		Integer setId = Integer.valueOf(-1);
		fixture.setSetId(setId);
		// add additional test code here
	}

	public void testSetSetId_fixture5_3() throws Exception {
		SetToRecordDTO fixture = getFixture5();
		Integer setId = Integer.valueOf(-1);
		fixture.setSetId(setId);
		// add additional test code here
	}

	public void testSetSetId_fixture6_3() throws Exception {
		SetToRecordDTO fixture = getFixture6();
		Integer setId = Integer.valueOf(-1);
		fixture.setSetId(setId);
		// add additional test code here
	}

	public void testSetSetId_fixture7_3() throws Exception {
		SetToRecordDTO fixture = getFixture7();
		Integer setId = Integer.valueOf(-1);
		fixture.setSetId(setId);
		// add additional test code here
	}

	public void testSetSetId_fixture8_3() throws Exception {
		SetToRecordDTO fixture = getFixture8();
		Integer setId = Integer.valueOf(-1);
		fixture.setSetId(setId);
		// add additional test code here
	}

	public void testSetSetId_fixture9_3() throws Exception {
		SetToRecordDTO fixture = getFixture9();
		Integer setId = Integer.valueOf(-1);
		fixture.setSetId(setId);
		// add additional test code here
	}

	public void testSetSetId_fixture10_3() throws Exception {
		SetToRecordDTO fixture = getFixture10();
		Integer setId = Integer.valueOf(-1);
		fixture.setSetId(setId);
		// add additional test code here
	}

	public void testSetSetId_fixture1_3() throws Exception {
		SetToRecordDTO fixture = getFixture1();
		Integer setId = Integer.valueOf(0);
		fixture.setSetId(setId);
		// add additional test code here
	}

	public void testSetSetId_fixture2_3() throws Exception {
		SetToRecordDTO fixture = getFixture2();
		Integer setId = Integer.valueOf(1);
		fixture.setSetId(setId);
		// add additional test code here
	}

	public void testToString_fixture1_1() throws Exception {
		SetToRecordDTO fixture = getFixture1();
		String result = fixture.toString();
		// add additional test code here
		assertEquals("null, null", result);
	}

	public void testToString_fixture2_1() throws Exception {
		SetToRecordDTO fixture = getFixture2();
		String result = fixture.toString();
		// add additional test code here
		assertEquals("-1, -1", result);
	}

	public void testToString_fixture3_1() throws Exception {
		SetToRecordDTO fixture = getFixture3();
		String result = fixture.toString();
		// add additional test code here
		assertEquals("-1, 0", result);
	}

	public void testToString_fixture4_1() throws Exception {
		SetToRecordDTO fixture = getFixture4();
		String result = fixture.toString();
		// add additional test code here
		assertEquals("-1, 1", result);
	}

	public void testToString_fixture5_1() throws Exception {
		SetToRecordDTO fixture = getFixture5();
		String result = fixture.toString();
		// add additional test code here
		assertEquals("0, -1", result);
	}

	public void testToString_fixture6_1() throws Exception {
		SetToRecordDTO fixture = getFixture6();
		String result = fixture.toString();
		// add additional test code here
		assertEquals("0, 0", result);
	}

	public void testToString_fixture7_1() throws Exception {
		SetToRecordDTO fixture = getFixture7();
		String result = fixture.toString();
		// add additional test code here
		assertEquals("0, 1", result);
	}

	public void testToString_fixture8_1() throws Exception {
		SetToRecordDTO fixture = getFixture8();
		String result = fixture.toString();
		// add additional test code here
		assertEquals("1, -1", result);
	}

	public void testToString_fixture9_1() throws Exception {
		SetToRecordDTO fixture = getFixture9();
		String result = fixture.toString();
		// add additional test code here
		assertEquals("1, 0", result);
	}

	public void testToString_fixture10_1() throws Exception {
		SetToRecordDTO fixture = getFixture10();
		String result = fixture.toString();
		// add additional test code here
		assertEquals("1, 1", result);
	}

	protected void setUp() throws Exception {
		super.setUp();
		// add additional set up code here
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		// Add additional tear down code here
	}

	public static void main(String[] args) {
		if (args.length == 0) {
			// Run all of the tests
			junit.textui.TestRunner.run(SetToRecordsDTOTestCase.class);
		} else {
			// Run only the named tests
			TestSuite suite = new TestSuite("Selected tests");
			for (int i = 0; i < args.length; i++) {
				TestCase test = new SetToRecordsDTOTestCase();
				test.setName(args[i]);
				suite.addTest(test);
			}
			junit.textui.TestRunner.run(suite);
		}
	}
}