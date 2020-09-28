<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="content-wrapper"  style="margin:0px;">
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>PAF Manage Detail</h1>
		<ul class="top-links">
			<li><a class="btn btn-block btn-primary btn-xs" href="${pageContext.request.contextPath}/pafManageList"><i
					class="fa fa-reorder"></i>PAF Manage List</a></li>
			<li><a class="btn btn-block btn-primary btn-xs" href="${pageContext.request.contextPath}/newPafManageForm"><i
					class="fa fa-plus"></i>Add new PAF Manage</a></li>

			<li><a class="btn btn-block btn-warning btn-xs" href="${pageContext.request.contextPath}/editPafManage/${pafManage.id}"><i class="fa fa-fw fa-edit"></i>Edit</a></li>
			<li><a class="btn btn-block btn-danger btn-xs" href="${pageContext.request.contextPath}/deleteCompany/${pafManage.id}"
				id="${pafManage.id}"
				onclick="return confirm('Are you sure you want to delete this record?');"><i
					class="fa fa-trash"></i>Delete</a></li>
		</ul>
	</section>

	<!-- Main content -->
	<section class="content">

		<div class="row">
			<div class="col-xs-12">

				<div class="box box-primary">
					<div class="box-header">
						<h3 class="box-title">PAF Manage Detail View</h3>
					</div>
					<!-- /.box-header -->
					<div class="box-body no-padding">
						<table class="table table-striped">
							<c:if test="${!empty pafManage.pafYear.name}">
								<tr>
									<th>PAF Year</th>
									<td>${pafManage.pafYear.name}</td>
								</tr>
							</c:if>
							<c:if test="${!empty pafManage.ph1OpenDate}">
								<tr>
									<th>Phase-1 Open Date</th>
									<td>${pafManage.ph1OpenDate}</td>
								</tr>
							</c:if>
							
							<c:if test="${!empty pafManage.ph1CloseDate}">
								<tr>
									<th>Phase-1 Close Date</th>
									<td>${pafManage.ph1CloseDate}</td>
								</tr>
							</c:if>
							
							<c:if test="${!empty pafManage.ph2OpenDate}">
								<tr>
									<th>Phase-2 Open Date</th>
									<td>${pafManage.ph2OpenDate}</td>
								</tr>
							</c:if>
							
							<c:if test="${!empty pafManage.ph2CloseDate}">
								<tr>
									<th>Phase-2 Close Date</th>
									<td>${pafManage.ph2CloseDate}</td>
								</tr>
							</c:if>
							
							<c:if test="${!empty pafManage.remarks}">
								<tr>
									<th>Remarks</th>
									<td>${pafManage.remarks}</td>
								</tr>
							</c:if>
							
							<c:if test="${!empty pafManage.createdBy}">
								<tr>
									<th>Created By</th>
									<td>${pafManage.createdBy}</td>
								</tr>
							</c:if>
							
							<c:if test="${!empty pafManage.createdDate}">
								<tr>
									<th>Created By</th>
									<td><fmt:formatDate pattern = "dd-MM-yyyy hh:mm:ss a" value = "${pafManage.createdDate}" /></td>
								</tr>
							</c:if>
							<c:if test="${!empty pafManage.modifiedBy}">
								<tr>
									<th>Modified By</th>
									<td>${pafManage.modifiedBy}</td>
								</tr>
							</c:if>
							
							<c:if test="${!empty pafManage.modifiedDate}">
								<tr>
									<th>Modified Date</th>
									<td><fmt:formatDate pattern = "dd-MM-yyyy hh:mm:ss a" value = "${pafManage.modifiedDate}" /></td>
								</tr>
							</c:if>

						</table>
					</div>
					<!-- /.box-body -->
				</div>
				<!-- /.box -->
			</div>
		</div>
	</section>
	<!-- /.content -->
</div>