package com.nazdaq.paf.beans;

import java.util.List;

public class AllowanceBean {

	private List<AllowanceSheet> allowanceSheetList;

	private Integer paymentMonthId;

	public List<AllowanceSheet> getAllowanceSheetList() {
		return allowanceSheetList;
	}

	public void setAllowanceSheetList(List<AllowanceSheet> allowanceSheetList) {
		this.allowanceSheetList = allowanceSheetList;
	}

	public Integer getPaymentMonthId() {
		return paymentMonthId;
	}

	public void setPaymentMonthId(Integer paymentMonthId) {
		this.paymentMonthId = paymentMonthId;
	}

}
