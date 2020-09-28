package com.nazdaq.paf.model;

import java.io.Serializable;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "paf_manage")
public class PAFManage implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "paf_year_id", nullable = true)
	private PAFYear pafYear;

	@Column(name = "ph1_open_date")
	private String ph1OpenDate;

	@Column(name = "ph1_close_date")
	private String ph1CloseDate;

	@Column(name = "ph2_open_date")
	private String ph2OpenDate;

	@Column(name = "ph2_close_date")
	private String ph2CloseDate;

	@Column(name = "remarks")
	private String remarks;

	@Column(name = "created_by")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date")
	private Date createdDate;

	@Column(name = "modified_by")
	private String modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_date")
	private Date modifiedDate;

	@Transient
	private String pafYearId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public PAFYear getPafYear() {
		return pafYear;
	}

	public void setPafYear(PAFYear pafYear) {
		this.pafYear = pafYear;
	}

	public String getPh1OpenDate() {
		return ph1OpenDate;
	}

	public void setPh1OpenDate(String ph1OpenDate) {
		this.ph1OpenDate = ph1OpenDate;
	}

	public String getPh1CloseDate() {
		return ph1CloseDate;
	}

	public void setPh1CloseDate(String ph1CloseDate) {
		this.ph1CloseDate = ph1CloseDate;
	}

	public String getPh2OpenDate() {
		return ph2OpenDate;
	}

	public void setPh2OpenDate(String ph2OpenDate) {
		this.ph2OpenDate = ph2OpenDate;
	}

	public String getPh2CloseDate() {
		return ph2CloseDate;
	}

	public void setPh2CloseDate(String ph2CloseDate) {
		this.ph2CloseDate = ph2CloseDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getPafYearId() {
		return pafYearId;
	}

	public void setPafYearId(String pafYearId) {
		this.pafYearId = pafYearId;
	}

}
