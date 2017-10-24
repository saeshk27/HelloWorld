package com.ezgo.apps.hw;

public class HelloWorld {
	
	public String returnStringAsSuch(String str) {
		return str;
	}

	public static void main(String[] args) {
		HelloWorld hw = new HelloWorld();
		System.out.println(hw.returnStringAsSuch("Hello World"));
	}

}
