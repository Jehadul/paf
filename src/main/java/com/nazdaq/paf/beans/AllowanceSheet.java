package com.nazdaq.paf.beans;

public class AllowanceSheet {

	private Integer id;

	private Integer recid = null;

	private Double allowArear = null;

	private Double overtime = null;

	private Double inchargeAllow = null;

	private Double allowOthers = null;

	private Integer paymentMonthId = null;

	private String remarks = null;

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

	public Double getAllowArear() {
		return allowArear;
	}

	public void setAllowArear(Double allowArear) {
		this.allowArear = allowArear;
	}

	public Double getOvertime() {
		return overtime;
	}

	public void setOvertime(Double overtime) {
		this.overtime = overtime;
	}

	public Double getInchargeAllow() {
		return inchargeAllow;
	}

	public void setInchargeAllow(Double inchargeAllow) {
		this.inchargeAllow = inchargeAllow;
	}

	public Double getAllowOthers() {
		return allowOthers;
	}

	public void setAllowOthers(Double allowOthers) {
		this.allowOthers = allowOthers;
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
