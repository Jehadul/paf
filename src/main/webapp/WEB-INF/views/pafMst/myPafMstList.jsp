<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<div class="content-wrapper" style="margin: 0px;">
	<section class="content-header">
		<h1>My PAF</h1>		
	</section>

	<!-- Main content -->
	<section class="content">
		<div class="row">
			<div class="col-xs-12">
				<div class="box box-primary">
					<div class="box-body table-responsive no-padding">					
						<!-- ----------Start table ----------- -->
						<table class="table table-striped display" id="table_id">
							<thead>
								<tr style="background-color: #428bca;">
									<th class="text-center">Sl NO.</th>
									<th class="text-center">PAF Year</th>
									<th class="text-center">Employee Name</th>
									<th class="text-center">Designation</th>
									<th class="text-center">Department</th>
									<th class="text-center">Status</th>
									<th class="text-center">Type</th>																			
									<th class="text-center">Action</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${pafMstList}" var="pafMst" varStatus="tr">
									<tr class="row_no_${tr.count}">

										<td class="text-center">
											<a href="${pageContext.request.contextPath}/showPafMst/${pafMst.id}">${tr.count}</a>											
										</td>
										<td class="text-center">${pafMst.pafYear.name}</td>
										
										<td class="text-center">${pafMst.employee.name}</td>
										<td class="text-center">${pafMst.employee.designation}</td>
										<td class="text-center">${pafMst.employee.department}</td>
										<td class="text-center">
											<c:if test="${pafMst.status == '1'}">
												<span class="btn btn-primary btn-sm"> P1 - Waiting for Employee Response </span>
											</c:if>
											<c:if test="${pafMst.status == '2'}">
												<span class="btn btn-info btn-sm"> P1 - Waiting for Supervisor Approve </span>
											</c:if>
											<c:if test="${pafMst.status == '3'}">
												<span class="btn btn-success btn-sm"> P1 - Approved </span>
											</c:if>
											<c:if test="${pafMst.status == '4'}">
												<span class="btn btn-primary btn-sm"> P2 - Waiting for Employee Response </span>
											</c:if>
											<c:if test="${pafMst.status == '5'}">
												<span class="btn btn-basic btn-sm"> P2 - Waiting for Supervisor Response </span>
											</c:if>
											<c:if test="${pafMst.status == '6'}">
												<span class="btn btn-warning btn-sm"> P2 - Waiting for HRD Ratings </span>
											</c:if>
											<c:if test="${pafMst.status == '7'}">
												<span class="btn btn-default btn-sm"> P2 - Waiting for Supervisor Approve </span>
											</c:if>
											<c:if test="${pafMst.status == '8'}">
												<span class="btn btn-info btn-sm"> P2 - Waiting for Employee Comment </span>
											</c:if>
											<c:if test="${pafMst.status == '9'}">
												<span class="btn btn-success btn-sm"> P2 - Employee Agreed &amp; Closed </span>
											</c:if>
											<c:if test="${pafMst.status == '10'}">
												<span class="btn btn-danger btn-sm"> P2 - Employee Disagreed </span>
											</c:if>											
										</td>
										<td class="text-center">										
											<c:if test="${pafMst.pafType == '0'}">
												<span class="btn btn-danger btn-sm"> CLOSED </span>
											</c:if>
											<c:if test="${pafMst.pafType == '1'}">
												<span class="btn btn-success btn-sm"> OPEN </span>
											</c:if>
											
											<c:if test="${pafMst.pafType == '2'}">
												<span class="btn btn-info btn-sm"> SPEACIAL OPEN </span>
											</c:if>											
										</td>
										<td class="text-center">											
											<!-- phase - 1 start -->
											<!-- for employee -->
											<c:if test="${pafMst.pafType == '1' or pafMst.pafType == '2'}">
												<c:if test="${pafMst.status == '1'}">
												<a href="${pageContext.request.contextPath}/pafForm?id=${pafMst.id}"><i class="fa fa-pencil-square-o" aria-hidden="true" title="Edit"></i></a>
												<a href="#"
												onclick="return archiveFunction('${pageContext.request.contextPath}/p1SubmitToManager/${pafMst.id}', 'Do you want to submit?')"><i
													class="fa fa-send-o" aria-hidden="true" style="color:green;" title="Submit"></i></a>
												</c:if>											
												<!-- phase - 1 end -->
												
												<!-- phase - 2 start -->
												<!-- for employee -->
												<c:if test="${pafMst.status == '4'}">
												<a href="${pageContext.request.contextPath}/pafForm?id=${pafMst.id}"><i class="fa fa-pencil-square-o" aria-hidden="true" title="Edit"></i></a>
												<a href="#"
												onclick="return archiveFunction('${pageContext.request.contextPath}/p2SubmitToManager/${pafMst.id}', 'Do you want to submit?')"><i
													class="fa fa-send-o" aria-hidden="true" style="color:green;" title="Submit"></i></a>
												</c:if>
												
												<!-- for employee -->	
												<c:if test="${pafMst.status == '8'}">
												<a href="#"
												onclick="return archiveFunction('${pageContext.request.contextPath}/p2Agree/${pafMst.id}', 'Do you want to Agree with Ratings?')"><i
													class="fa fa-check-circle" aria-hidden="true" style="color:green;" title="Agree"></i></a>
													
												<a href="#"
												onclick="return archiveFunction('${pageContext.request.contextPath}/p2Disagree/${pafMst.id}', 'Do you want to Agree with Ratings?')"><i
													class="fa fa-ban" aria-hidden="true" style="color:red;" title="Disagree"></i></a>
												</c:if>
											</c:if>
											<!-- phase - 2 end -->
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						
						<script type="text/javascript">	
							$(document).ready(function() {
								$('#table_id').DataTable({
									"aoColumnDefs": [
									      { "sClass": "my_class", "aTargets": [0]}
									 ]
								});
							});

							$(document).ready(function() {
								$("#collapseBtn").click(function() {
									$('#collapseIcn').toggleClass('fa-plus fa-minus');
									$(this).toggleClass('btn-info btn-warning');
								});
							});
						</script>
						<!-- ----------End Table ------------- -->
					</div>
					<!-- /.box-body -->
				</div>
				<!-- /.box -->
			</div>
		</div>

	</section>
	<!-- /.content -->
</div>