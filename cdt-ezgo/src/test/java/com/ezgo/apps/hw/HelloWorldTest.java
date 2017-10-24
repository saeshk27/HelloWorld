package com.ezgo.apps.hw;

import static org.junit.Assert.*;

import org.junit.Test;

public class HelloWorldTest {
	
	HelloWorld hw= new HelloWorld();

	@Test
	public void testReturnStringAsSuch() {
		String obj = "SatesH";
		assertEquals(obj, hw.returnStringAsSuch(obj));
	}

}
