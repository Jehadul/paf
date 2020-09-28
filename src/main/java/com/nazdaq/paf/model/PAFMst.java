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
@Table(name = "paf_mst")
public class PAFMst implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "paf_year_id", nullable = true)
	private PAFYear pafYear;

	@ManyToOne
	@JoinColumn(name = "employee_id", nullable = true)
	private Employee employee;

	@Column(name = "unit")
	private String unit;

	@Column(name = "duration")
	private String duration;

	@Column(name = "key_responsibilities")
	private String keyResponsibilities;

	@Column(name = "emp_evaluation", length = 1000)
	private String empEvaluation;

	@Column(name = "man_evaluation", length = 1000)
	private String manEvaluation;

	@Column(name = "emp_comments", length = 1000)
	private String empComments;

	@Column(name = "man_comments", length = 1000)
	private String manComments;

	@Column(name = "man_ratings")
	private Integer manRatings;

	@Column(name = "hr_ratings")
	private Integer hrRatings;

	@Column(name = "acknow_comments", length = 1000)
	private String acknowComments;

	@Column(name = "acknow_date")
	private String acknowDate;

	@Column(name = "status")
	private String status;

	// open = 1 or close = 0
	@Column(name = "paf_type")
	private String pafType;

	// common
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

	@Transient
	private String employeeId;

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

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getKeyResponsibilities() {
		return keyResponsibilities;
	}

	public void setKeyResponsibilities(String keyResponsibilities) {
		this.keyResponsibilities = keyResponsibilities;
	}

	public String getEmpEvaluation() {
		return empEvaluation;
	}

	public void setEmpEvaluation(String empEvaluation) {
		this.empEvaluation = empEvaluation;
	}

	public String getManEvaluation() {
		return manEvaluation;
	}

	public void setManEvaluation(String manEvaluation) {
		this.manEvaluation = manEvaluation;
	}

	public String getEmpComments() {
		return empComments;
	}

	public void setEmpComments(String empComments) {
		this.empComments = empComments;
	}

	public String getManComments() {
		return manComments;
	}

	public void setManComments(String manComments) {
		this.manComments = manComments;
	}

	public Integer getManRatings() {
		return manRatings;
	}

	public void setManRatings(Integer manRatings) {
		this.manRatings = manRatings;
	}

	public Integer getHrRatings() {
		return hrRatings;
	}

	public void setHrRatings(Integer hrRatings) {
		this.hrRatings = hrRatings;
	}

	public String getAcknowComments() {
		return acknowComments;
	}

	public void setAcknowComments(String acknowComments) {
		this.acknowComments = acknowComments;
	}

	public String getAcknowDate() {
		return acknowDate;
	}

	public void setAcknowDate(String acknowDate) {
		this.acknowDate = acknowDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPafType() {
		return pafType;
	}

	public void setPafType(String pafType) {
		this.pafType = pafType;
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

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

}
