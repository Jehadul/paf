package com.nazdaq.paf.beans;

import java.util.List;

public class PFProfitBean {

	private List<PFProfitSheet> pfProfitSheetList;

	private Integer paymentMonthId;	

	public List<PFProfitSheet> getPfProfitSheetList() {
		return pfProfitSheetList;
	}

	public void setPfProfitSheetList(List<PFProfitSheet> pfProfitSheetList) {
		this.pfProfitSheetList = pfProfitSheetList;
	}

	public Integer getPaymentMonthId() {
		return paymentMonthId;
	}

	public void setPaymentMonthId(Integer paymentMonthId) {
		this.paymentMonthId = paymentMonthId;
	}

}
