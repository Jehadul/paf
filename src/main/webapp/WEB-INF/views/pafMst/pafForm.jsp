<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page session="true"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

</head>
<body>
	<div class="content-wrapper" style="margin: 0px;">
		<!-- Content Header (Page header) -->
		<section class="content-header">
		<ul class="nav nav-tabs">
			<li class="active"><a href="#tab-general" data-toggle="tab"
				aria-expanded="true">Personal Info</a></li>
			<li class=""><a href="#tab-goals" data-toggle="tab"
				aria-expanded="false">Goals</a></li>
			<li class=""><a href="#tab-performance" data-toggle="tab"
				aria-expanded="false">Overall Performance</a></li>
			<li class=""><a href="#tab-others" data-toggle="tab"
				aria-expanded="false">Development &amp; Acknowledgement </a></li>


		</ul>
		</section>

		<div class="tab-content">
			<div class="tab-pane active" id="tab-general">
				<div class="row">
					<div class="col-md-8">
						<div class="table-responsive">
							<table class="table">
								<thead>
									<tr>
										<th style="width: 100px">Name :</th>
										<th>${pafMst.employee.name}</th>
									</tr>
									<tr>
										<th style="width: 100px">ID :</th>
										<th>${pafMst.employee.lxnId}</th>
									</tr>
									<tr>
										<th style="width: 100px">Designation :</th>
										<th>${pafMst.employee.designation}</th>
									</tr>
									<tr>
										<th style="width: 100px">DOJ :</th>
										<th>${pafMst.employee.dateJoined}</th>
									</tr>
									<tr>
										<th style="width: 100px">Department :</th>
										<th>${pafMst.employee.department}</th>
									</tr>
								</thead>
							</table>
						</div>
					</div>
					<div class="col-md-4">
						<div class="table-responsive">
							<table class="table">
								<thead>
									<tr>
										<th style="width: 80px">Year :</th>
										<th>${pafYear}</th>
									</tr>
									<tr>
										<th style="width: 80px">Unit <span class="error">*</span> :</th>
										<th><input type="text" class="form-control" id="unit"
											value="${pafMst.unit}"></th>
									</tr>
									<tr>
										<th style="width: 80px">Duration :</th>
										<th>${duration}</th>
									</tr>

								</thead>
							</table>
						</div>
					</div>
					<input type="hidden" id="mst_id" value="${pafMst.id}">
				</div>
				<div class="row">
					<div class="col-md-12"><h4>Key Areas of Responsibilities :</h4></div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<textarea cols="80" id="editor1" name="editor1" rows="10">${pafMst.keyResponsibilities}</textarea>
					</div>
				</div>
				<div class="row">&nbsp;</div>
				<div class="row">
					<div class="col-md-2 col-sm-12" style="text-align: center;">
					<label class="control-label" >Employee Evaluation <span class="error">*</span> :</label>
						<!-- <h5>Employee Evaluation <span class="error">*</span></h5> -->
					</div>
					<div class="col-md-4 col-sm-12">
						<textarea rows="5" cols="" id="empEvaluation" class="form-control">${pafMst.empEvaluation}</textarea>
					</div>
					<div class="col-md-2 col-sm-12" style="text-align: center;">
						<h5>Manager Evaluation :</h5>
					</div>
					<div class="col-md-4 col-sm-12">
						<textarea rows="5" cols="" id="manEvaluation" class="form-control">${pafMst.manEvaluation}</textarea>
					</div>
				</div>
				<div class="row">&nbsp;</div>
				<button class="btn btn-info form-control personal_info_btn">Updated</button>

			</div>
			<div class="tab-pane " id="tab-goals">
				<div class="row">
					<c:forEach items="${goals}" varStatus="ggg" var="goal">
						<div class="col-sm-12 border" style="margin-top:10px;">
							<div class="col-sm-2"> <h4>Goal-${ggg.count} : </h4></div>
							<div class="col-sm-8">${goal.goal}</div>
							<div class="col-sm-2">
								<button title="Edit" class="btn btn-success btn-sm" onclick="editGoalPopup('${goal.id}', '${goal.goal}', '${goal.dueDate}', '${goal.status}', '${goal.completeDate}', '${goal.empEval}', '${goal.manEval}')">
								<span class="fa fa-edit"></span>
								</button>|
								<a title="Delete" class="btn btn-danger btn-xs" href="${pageContext.request.contextPath}/deleteGoal/${goal.id}"
									id="goals${goal.id}" onclick="return confirm('Are you sure you want to delete this record?');">
									<i class="fa fa-trash"></i></a>
							</div>
						</div>
						<div class="col-sm-12 border border-primary">
							<div class="col-sm-2">Due Date : </div>
							<div class="col-sm-2">${goal.dueDate}</div>
							<div class="col-sm-2">Status : </div>
							<div class="col-sm-2">${goal.status}</div>
							<div class="col-sm-2">Completion Date : </div>
							<div class="col-sm-2">${goal.completeDate}</div>
						</div>
						
						<div class="col-sm-12 border">
							<div class="col-sm-2">Employee Evaluation : </div>
							<div class="col-sm-4">${goal.empEval}</div>
							<div class="col-sm-2">Manager Evaluation : </div>
							<div class="col-sm-4">${goal.manEval}</div>
						</div>
						<div class="row">&nbsp;</div>
						<div class="row">&nbsp;</div>
					</c:forEach>
					<div class="col-sm-12" style="margin-left: 15px">
						<button class="btn btn-primary btn-sm" onclick="newGoalPopup()"> <span class="fa fa-plus"></span> Add More...</button>
					</div>
					
				</div>
				<!-- <div class="row">&nbsp;</div>
				<button class="btn btn-info form-control personal_info_btn">Updated</button> -->
			</div>
			<div class="tab-pane " id="tab-performance">
				<div class="row">
					<div class="col-md-2 col-sm-12" style="text-align: center;">
						<label class="control-label" >Employee's Comments :</label>						
					</div>
					<div class="col-md-10 col-sm-12">
						<textarea rows="5" cols="" id="empComments" class="form-control">${pafMst.empComments}</textarea>
					</div>					
				</div>
				<div class="row">&nbsp;</div>
				<div class="row">
					<div class="col-md-2 col-sm-12" style="text-align: center;">
						<label class="control-label" >Manager's Comments :</label>						
					</div>
					<div class="col-md-10 col-sm-12">
						<textarea rows="5" cols="" id="manComments" class="form-control">${pafMst.manComments}</textarea>
					</div>
				</div>
				<div class="row">&nbsp;</div>
				<div class="row">
					<div class="col-md-2 col-sm-12" style="text-align: center;">
						<label class="control-label" >Manager's Rating 1-5 (Scale) :</label>						
					</div>
					<div class="col-md-10 col-sm-12">
						<table class="table table-striped">
							<tr>
								<td></td>
								<td>1 = Poor</td>
								<td>2 = Average</td>
								<td>3 = Good</td>
								<td>4 = Very Good</td>
								<td>5 = Excellent</td>
							</tr>
							<tr>
								<td></td>
								<td><input type="radio" name="manRatings" value="1" id="m1"
									title="Poor" ${pafMst.manRatings=='1'?'checked':''}/></td>
									<td><input type="radio" name="manRatings" value="2" id="m2"
									title="Average" ${pafMst.manRatings=='2'?'checked':''}/></td>
									<td><input type="radio" name="manRatings" value="3" id="m3"
									title="Good" ${pafMst.manRatings=='3'?'checked':''}/></td>
									<td><input type="radio" name="manRatings" value="4" id="m4"
									title="Very Good" ${pafMst.manRatings=='4'?'checked':''}/></td>
									<td><input type="radio" name="manRatings" value="5" id="m5"
									title="Excellent" ${pafMst.manRatings=='5'?'checked':''}/>
							</tr>
						</table>
					</div>
				</div>
				
				<div class="row">&nbsp;</div>
				<div class="row">
					<div class="col-md-2 col-sm-12" style="text-align: center;">
						<label class="control-label" >HRD's Rating 1-5 (Scale) :</label>						
					</div>
					<div class="col-md-10 col-sm-12">
						<table class="table table-striped">
							<tr>
								<td></td>
								<td>1 = Poor</td>
								<td>2 = Average</td>
								<td>3 = Good</td>
								<td>4 = Very Good</td>
								<td>5 = Excellent</td>
							</tr>
							<tr>
								<td></td>
								<td><input type="radio" name="hrRatings" value="1" id="q1"
									title="Poor" ${pafMst.hrRatings=='1'?'checked':''}/></td>
									<td><input type="radio" name="hrRatings" value="2" id="q2"
									title="Average" ${pafMst.hrRatings=='2'?'checked':''} /></td>
									<td><input type="radio" name="hrRatings" value="3" id="q3"
									title="Good" ${pafMst.hrRatings=='3'?'checked':''} /></td>
									<td><input type="radio" name="hrRatings" value="4" id="q4"
									title="Very Good" ${pafMst.hrRatings=='4'?'checked':''} /></td>
									<td><input type="radio" name="hrRatings" value="5" id="q5"
									title="Excellent" ${pafMst.hrRatings=='5'?'checked':''} />
							</tr>
						</table>
					</div>
				</div>				
				<div class="row">&nbsp;</div>
				<button class="btn btn-info form-control personal_info_btn">Updated</button>
			</div>
			<div class="tab-pane " id="tab-others">
				<div class="row">
					<div class="col-md-2 col-sm-12" style="text-align: center;">
						<label class="control-label" >Development Items :</label>
					</div>
					<div class="col-md-10 col-sm-12">
						<div class="col-md-12 col-sm-12">
							<div class="col-md-1 col-sm-12">Sl No</div>
							<div class="col-md-7 col-sm-12">Item</div>
							<div class="col-md-2 col-sm-12">Status</div>
							<div class="col-md-2 col-sm-12">Action</div>
						</div>
						<c:forEach items="${pafDevItems}" var="pafDevItem" varStatus="pafDev">
						<div class="col-md-12 col-sm-12" style="margin-top: 5px;">
							<div class="col-md-1 col-sm-12">${pafDev.count}</div>
							<div class="col-md-7 col-sm-12">${pafDevItem.name}</div>
							<div class="col-md-2 col-sm-12">${pafDevItem.status}</div>
							<div class="col-md-2 col-sm-12">
								
							<button title="Edit" class="btn btn-success btn-sm" onclick="editDevItemPopup('${pafDevItem.id}', '${pafDevItem.name}', '${pafDevItem.status}')">
								<span class="fa fa-edit"></span>
							</button>|
							<a title="Delete" class="btn btn-danger btn-xs" href="${pageContext.request.contextPath}/deleteDevItem/${pafDevItem.id}"
								id="devItems${pafDevItem.id}" onclick="return confirm('Are you sure you want to delete this record?');">
								<i class="fa fa-trash"></i></a>
							</div>
						</div>
						</c:forEach>
						<button class="btn btn-primary btn-sm" onclick="newItemPopup()"> <span class="fa fa-plus"></span> Add More...</button>
					</div>
					
					
				</div>
				<div class="row">&nbsp;</div>
				<div class="row">
					<div class="col-md-2 col-sm-12" style="text-align: center;">
						<label class="control-label" >Comments :</label>						
					</div>
					<div class="col-md-4 col-sm-12">
						<textarea rows="5" cols="" id="acknowComments" class="form-control">${pafMst.acknowComments}</textarea>
					</div>
					
					<div class="col-md-2 col-sm-12" style="text-align: center;">
						<label class="control-label" >Date :</label>						
					</div>
					<div class="col-md-4 col-sm-12">
						<input type="eu-date" id="acknowDate" class="form-control">
					</div>
				</div>
				
				<div class="row">&nbsp;</div>
				<button class="btn btn-info form-control personal_info_btn">Updated</button>
			</div>
		</div>
	</div>
	<script>
	$(function() {
		var month = (new Date()).getMonth() + 1;
		var year = (new Date()).getFullYear();
		// US Format
		$('input[type=eu-date]').w2field('date', {
			format : 'dd-mm-yyyy'
		});

		$('input[type=eu-date1]').w2field('date', {
			format : 'dd-mm-yyyy',
			end : $('input[type=eu-date2]')
		});
		$('input[type=eu-date2]').w2field('date', {
			format : 'dd-mm-yyyy',
			start : $('input[type=eu-date1]')
		});

	});
	
		CKEDITOR.plugins.addExternal('simplebox',
				'${pageContext.request.contextPath}/resource/ckEditor/simplebox/',
				'plugin.js');

		CKEDITOR.replace('editor1',{
			// Add the Simple Box plugin.
			extraPlugins : 'simplebox',
			// Besides editor's main stylesheet load also simplebox styles.
			// In the usual case they can be added to the main stylesheet.
			contentsCss : ['${pageContext.request.contextPath}/resource/ckEditor/contents.css',
						   '${pageContext.request.contextPath}/resource/ckEditor/contents.css' ],
							// Set height to make more content visible.
							height : 300
						});
	</script>
	<script type="text/javascript">
		$(document).ready(function() {
			$(document).on("click", ".personal_info_btn",function() {
				var desc = CKEDITOR.instances['editor1'].getData();
				var contextPath = $('#contextPath').val();
				jQuery.ajax({
					type : "POST",
					url : contextPath+"/updatePafMst",
					dataType : 'json',
					data : {
						id : $('#mst_id').val(),
						manEvaluation:$('#manEvaluation').val(),empEvaluation:$('#empEvaluation').val(),
						keyResponsibilities : desc,
						unit : $('#unit').val(),
						empComments : $('#empComments').val(),
						manComments : $('#manComments').val(),
						manRatings : $('input[name=manRatings]:checked').val(),
						hrRatings : $('input[name=hrRatings]:checked').val(),
						acknowComments : $('#acknowComments').val(),
						acknowDate : $('#acknowDate').val()
					},
					success : function(res) {
						if (res == 'Success') {
							w2alert("Your given data successfully updated.");
						}
					},
				    error: function() {
				    	w2alert("Server Error!!");
				    }
				});
			});
		});
		
		$(document).ready(function() {
			$(document).on("click", ".dev_item_btn",function() {
				var contextPath = $('#contextPath').val();				
				
				jQuery.ajax({
					type : "POST",
					url : contextPath+"/addOrUpdateDevItem",
					dataType : 'json',
					async: false,
					data : {
						pafMstId : $('#mst_id').val(),
						id : $('#itemId').val(),
						name : $('#itemName').val(),
						status : $('#itemStatus').val()
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
			$(document).on("click", ".goal_btn",function() {
				var contextPath = $('#contextPath').val();				
				
				jQuery.ajax({
					type : "POST",
					url : contextPath+"/addOrUpdateGoal",
					dataType : 'json',
					async: false,
					data : {
						pafMstId : $('#mst_id').val(),
						id : $('#goalId').val(),
						goal : $('#goal').val(),
						dueDate : $('#dueDate').val(),
						completeDate : $('#completeDate').val(),
						empEval : $('#empEval').val(),
						manEval : $('#manEval').val(),
						status : $('#goalStatus').val()
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
	</script>
	
	<script type="text/javascript">
	
	function newGoalPopup() {
	    w2popup.open({
	        title     : 'Add New Goal',
	        body      : '<div class="w2ui-centered" style="line-height: 1.5">'+
	        '  	Goal: <textarea id="goal" cols="" rows="" class="form-control" style="margin-bottom: 5px"> </textarea><br>'+
	        '  	Due Date: <input id="dueDate" class="form-control" style="margin-bottom: 5px"><br>'+
	        '  	Status: <input id="goalStatus" class="form-control" style="margin-bottom: 5px"><br>'+
	        '  	Completion Date: <input id="completeDate" class="form-control" style="margin-bottom: 5px"><br>'+
	        '  	Employee Evaluation: <textarea id="empEval" cols="" rows="" class="form-control" style="margin-bottom: 5px"> </textarea><br>'+
	        '  	Manager Evaluation: <textarea id="manEval" cols="" rows="" class="form-control" style="margin-bottom: 5px"> </textarea><br>'+
	        '  	<input class="form-control" id="goalId" type="hidden" style="margin-bottom: 5px">'+
	        '</div>',
	        buttons   : '<button class="w2ui-btn btn btn-danger btn-sm" onclick="w2popup.close();">Close</button> '+
	                    '<button class="w2ui-btn btn btn-success btn-primary goal_btn">Add</button>',
	        width     : 700,
	        height    : 600,
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
	
	function editGoalPopup(id, goal, dueDate, status, completeDate, empEval, manEval) {
	    w2popup.open({
	        title     : 'Edit Goal',
	        body      : '<div class="w2ui-centered" style="line-height: 1.5">'+
	        '  	Goal: <textarea cols="" rows="" id="goal" class="form-control" style="margin-bottom: 5px">'+goal+'</textarea><br>'+
	        '  	Due Date: <input class="form-control" id="dueDate" value="'+dueDate+'" style="margin-bottom: 5px"><br>'+
	        '  	Status: <input class="form-control" id="goalStatus" value="'+status+'" style="margin-bottom: 5px"><br>'+
	        '  	Completion Date: <input id="completeDate" class="form-control" value="'+completeDate+'" style="margin-bottom: 5px"><br>'+
	        '  	Employee Evaluation: <textarea id="empEval" cols="" rows="" class="form-control" style="margin-bottom: 5px">'+empEval+'</textarea><br>'+
	        '  	Manager Evaluation: <textarea id="manEval" cols="" rows="" class="form-control" style="margin-bottom: 5px">'+manEval+'</textarea><br>'+
	        '  	<input class="form-control" id="goalId" type="hidden" value="'+id+'" style="margin-bottom: 5px">'+
	        '</div>',
	        buttons   : '<button class="w2ui-btn btn btn-danger btn-sm" onclick="w2popup.close();">Close</button> '+
	        			'<button class="w2ui-btn btn btn-success btn-primary goal_btn">Update</button>',
	        width     : 700,
	        height    : 600,
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
	
function newItemPopup() {
    w2popup.open({
        title     : 'Add New Development Item',
        body      : '<div class="w2ui-centered" style="line-height: 1.5">'+
        '  	Item: <textarea id="itemName" cols="" rows="" class="form-control" style="margin-bottom: 5px"> </textarea><br>'+
        '  	Status: <input id="itemStatus" class="form-control" style="margin-bottom: 5px"><br>'+
        '  	<input class="form-control" id="itemId" type="hidden" style="margin-bottom: 5px">'+
        '</div>',
        buttons   : '<button class="w2ui-btn btn btn-danger btn-sm" onclick="w2popup.close();">Close</button> '+
        			'<button class="w2ui-btn btn btn-success btn-primary dev_item_btn">Add</button>',
        width     : 500,
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

function editDevItemPopup(itemId, itemName, itemStatus){
	w2popup.open({
        title     : 'Edit Development Item',
        body      : '<div class="w2ui-centered" style="line-height: 1.5">'+
        '  	Item: <textarea cols="" id="itemName" rows="" class="form-control" style="margin-bottom: 5px">'+itemName+'</textarea><br>'+
        '  	Status: <input id="itemStatus" class="form-control"  value="'+itemStatus+'" style="margin-bottom: 5px"><br>'+
        '  	<input class="form-control" id="itemId" type="hidden" value="'+itemId+'" style="margin-bottom: 5px">'+
        '</div>',
        buttons   : '<button class="w2ui-btn btn btn-danger btn-sm" onclick="w2popup.close();">Close</button> '+
        			'<button class="w2ui-btn btn btn-success btn-primary dev_item_btn">Update</button>',
        width     : 500,
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
</script>
</body>
</html>