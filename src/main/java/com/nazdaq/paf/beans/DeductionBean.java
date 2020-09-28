package com.nazdaq.paf.beans;

import java.util.List;

public class DeductionBean {

	private List<DeductionSheet> deductionSheetList;

	private Integer paymentMonthId;

	public List<DeductionSheet> getDeductionSheetList() {
		return deductionSheetList;
	}

	public void setDeductionSheetList(List<DeductionSheet> deductionSheetList) {
		this.deductionSheetList = deductionSheetList;
	}

	public Integer getPaymentMonthId() {
		return paymentMonthId;
	}

	public void setPaymentMonthId(Integer paymentMonthId) {
		this.paymentMonthId = paymentMonthId;
	}

}
