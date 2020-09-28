<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<div class="content-wrapper" style="margin: 0px;">
	<section class="content-header">
		<h1>PAF List</h1>
		<ul class="top-links">
			<li><a class="btn btn-block btn-primary btn-xs"
				href="${pageContext.request.contextPath}/pafEligibleList"><i
					class="fa fa-list"></i>PAF Eligible List</a></li>
		</ul>
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
											<!-- all -->
											<a href="${pageContext.request.contextPath}/pafForm?id=${pafMst.id}"><i class="fa fa-pencil-square-o" aria-hidden="true" title="Edit"></i></a>											
											
											<sec:authorize access="hasRole('ROLE_ADMIN')">
												<!-- for hrd -->												
												<a href="#" onclick="openModifyPopup(${pafMst.id})"><i
													class="fa fa-cogs" aria-hidden="true" style="color:red;" title="Modify"> </i></a>												
												<c:if test="${pafMst.status == '6'}">
												<a href="#"
													onclick="return archiveFunction('${pageContext.request.contextPath}/p2SendBackToManager/${pafMst.id}', 'Do you want to send back to supervisor')"><i
													class="fa fa-mail-reply" aria-hidden="true" title="Send Back To Supervisor"></i></a>
												</c:if>	
											</sec:authorize>
											<!-- phase - 1 start -->
											<!-- for employee -->
											<c:if test="${pafMst.status == '1'}">
											<a href="#"
											onclick="return archiveFunction('${pageContext.request.contextPath}/p1SubmitToManager/${pafMst.id}', 'Do you want to submit?')"><i
												class="fa fa-send-o" aria-hidden="true" style="color:green;" title="Submit"></i></a>
											</c:if>
											<!-- for manager -->	
											<c:if test="${pafMst.status == '2'}">
											<a href="#"
											onclick="return archiveFunction('${pageContext.request.contextPath}/p1SendBackToEmployee/${pafMst.id}', 'Do you want to send back to employee?')"><i
												class="fa fa-mail-reply" style="color:red;" aria-hidden="true" title="Send Back To Employee"></i></a>
												
											<a href="#"
											onclick="return archiveFunction('${pageContext.request.contextPath}/p1Approve/${pafMst.id}', 'Do you want to Approve?')"><i
												class="fa fa-check" aria-hidden="true" title="Approve"></i></a>
											</c:if>
											<!-- phase - 1 end -->
											
											<!-- phase - 2 start -->
											<!-- for employee -->
											<c:if test="${pafMst.status == '4'}">
											<a href="#"
											onclick="return archiveFunction('${pageContext.request.contextPath}/p2SubmitToManager/${pafMst.id}', 'Do you want to submit?')"><i
												class="fa fa-send-o" aria-hidden="true" style="color:green;" title="Submit"></i></a>
											</c:if>
											<!-- for manager -->	
											<c:if test="${pafMst.status == '5'}">
											<a href="#"
											onclick="return archiveFunction('${pageContext.request.contextPath}/p2SendBackToEmployee/${pafMst.id}', 'Do you want to send back to employee?')"><i
												class="fa fa-mail-reply" aria-hidden="true" style="color:red;" title="Send Back To Employee"></i></a>
											
											<a href="#"
											onclick="return archiveFunction('${pageContext.request.contextPath}/p2SubmitToHrd/${pafMst.id}', 'Do you want to submit?')"><i
												class="fa fa-send-o" aria-hidden="true" title="Submit"></i></a>
											</c:if>
											<!-- for manager -->	
											<c:if test="${pafMst.status == '7'}">
											<a href="#"
											   onclick="return archiveFunction('${pageContext.request.contextPath}/p2Approve/${pafMst.id}', 'Do you want to Approve?')"><i
												class="fa fa-check-square" aria-hidden="true" style="color:green;" title="Approve"></i></a>
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
											<!-- phase - 2 end -->
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						
						<script type="text/javascript">	
							function openModifyPopup(mstId) {
								w2popup.open({
							        title     : 'Modify PAF',
							        body      : '<div class="w2ui-centered" style="line-height: 1.5">'+
							        '  	PAF Type: <select id="pafType" class="form-control"> <option value="1">OPEN</option><option value="2">SPECIAL OPEN</option> <option value="0">CLOSE</option> </select><br>'+
							        '  	Status: <select id="pafStatus" class="form-control">'+
							        	'<option value="1">P1 - To Employee</option>'+
							        	'<option value="2">P1 - To Supervisor</option>'+
							        	' <option value="4">P2 - To Employee</option> '+
							        	'<option value="5">P2 - To Supervisor Before Ratings</option>'+
							        	'<option value="7">P2 - To Supervisor After Ratings</option>'+
							        	'<option value="8">P2 - To Employee For Comments</option>'+
							        	'</select><br>'+
							        '  	<input class="form-control" value="'+mstId+'" id="pafMstId" type="hidden" style="margin-bottom: 5px">'+
							        '</div>',
							        buttons   : '<button class="w2ui-btn btn btn-danger btn-sm" onclick="w2popup.close();">Close</button> '+
							                    '<button class="w2ui-btn btn btn-success btn-primary paf_mst_modify_btn">Update</button>',
							        width     : 600,
							        height    : 260,
							        overflow  : 'hidden',
							        color     : '#333',
							        speed     : '0.3',
							        opacity   : '0.8',
							        modal     : true,
							        showClose : true,
							        showMax   : true,
							        onOpen    : function (event) { console.log('open'); },
							        onClose   : function (event) { console.log('close'); },
							        onMax     : function (event) { console.log('max'); },
							        onMin     : function (event) { console.log('min'); },
							        onKeydown : function (event) { console.log('keydown'); }
							    });
							}
							
							$(document).ready(function() {
								$(document).on("click", ".paf_mst_modify_btn",function() {
									var contextPath = $('#contextPath').val();				
									
									jQuery.ajax({
										type : "POST",
										url : contextPath+"/pafMstModify",
										dataType : 'json',
										async: false,
										data : {
											id : $('#pafMstId').val(),
											pafType : $('#pafType').val(),											
											status : $('#pafStatus').val()
										},
										success : function(res) {
											if (res == 'Success') {							
												window.location.reload();
												w2popup.close();							
											}
										},
									    error: function() {
									        w2alert("Server Error!!");
									    }
									});
								});
							});
	
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