package com.nazdaq.paf.beans;

public class DeductionSheet {

	private Integer id;

	private Integer recid = null;

	private Double fineAmount = null;

	private Double deductStamp = null;

	private Integer paymentMonthId;

	private String remarks;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRecid() {
		return recid;
	}

	public void setRecid(Integer recid) {
		this.recid = recid;
	}

	public Double getFineAmount() {
		return fineAmount;
	}

	public void setFineAmount(Double fineAmount) {
		this.fineAmount = fineAmount;
	}

	public Double getDeductStamp() {
		return deductStamp;
	}

	public void setDeductStamp(Double deductStamp) {
		this.deductStamp = deductStamp;
	}

	public Integer getPaymentMonthId() {
		return paymentMonthId;
	}

	public void setPaymentMonthId(Integer paymentMonthId) {
		this.paymentMonthId = paymentMonthId;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
