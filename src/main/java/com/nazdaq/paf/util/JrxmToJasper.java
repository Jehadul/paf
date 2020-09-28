package com.nazdaq.paf.util;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;


public class JrxmToJasper {
	public static void main(String[] args) throws JRException {
		// TODO Auto-generated method stub
		
        JasperCompileManager.compileReportToFile(
        		"D:\\stsworkspace\\TravelManagement\\src\\main\\resources\\AccountWrkOrder.jrxml", 
        		"D:\\AccountWrkOrder.jasper");
     }
	
}
