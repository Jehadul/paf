package com.nazdaq.paf.util;

import java.util.Calendar;
import java.util.Date;

public class TestMain {

	public static void main(String[] args) {
		String a = "01-07-2017";
		String b = "30-06-2018";
		String fromYear = a.substring(a.length() -4, a.length());
		String toYear = b.substring(b.length() -4, b.length());
		
		// TODO Auto-generated method stub
		//Date now = new Date();
		//Calendar calc = Calendar.getInstance();
		//calc.setTime(now);
		//Integer currentYear = calc.get(Calendar.YEAR);
		
		System.out.println(fromYear+"-"+toYear);
	}
	
	private Integer myMathTest(Integer x) {
		return ((x*x)-9)/(x-3);
	}

}
