<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="content-wrapper"  style="margin:0px;">
    <section class="content-header">
        <h1>
            PAF Eligible Employee List
        </h1>
        <ul class="top-links">
           <li><a class="btn btn-block btn-primary btn-xs" href="${pageContext.request.contextPath}/pafMstList"><i
                    class="fa fa-list"></i>PAF List</a></li>
        </ul>
    </section>

    <!-- Main content -->
    <section class="content">

        <!-- Serch box -->
        <!-- /.box -->

        <div class="row">
            <div class="col-xs-12">
                <div class="box box-primary">
                    <div class="box-body table-responsive no-padding">
                    
                    	<!-- ----------Start table ----------- -->
                    		<div id="main" style="width: 100%; height: 400px; padding: 10px;"></div>
							<hr />
							<button class="w2ui-btn" onclick="allEligibleEmployee(w2ui.grid.getSelection(id))">Send PAF</button>
							
							<script type="text/javascript">
								function allEligibleEmployee(employeeIds) {									
									var contextPath = $('#contextPath').val();
									var path = contextPath+'/sentPafToEligibleEmployee';
									var params = {
											employeeIds : employeeIds
										}
									
									if(employeeIds.length > 0){
										formSubmitGetPost(path, params, "POST");
										w2alert('Please Wait...');
									}
									
									//
								}
															
								var config = {
									grid : {
										name : 'grid',
										show : {
											footer : true,
											toolbar : true,
											lineNumbers : true,
											selectColumn : true
										},
										multiSearch : true,
										multiSelect : true,
							
										searches : [ {
											field : 'id',
											caption : 'ID ',
											type : 'int'
										}, {
											field : 'name',
											caption : 'Employee Name',
											type : 'text'
										}, {
											field : 'lxnId',
											caption : 'Employee ID',
											type : 'text'
										}],
							
										columns : [ {
											field : 'id',
											caption : 'ID',
											size : '50px'
											//sortable : true,
											//searchable : 'int',
											//resizable : true
										}, {
											field : 'lxnId',
											caption : 'Employee ID',
											size : '100px',
											sortable : true,
											searchable : 'text',
											resizable : true
										}, {
											field : 'name',
											caption : 'Employee Name',
											size : '240px',
											sortable : true,
											searchable : 'text',
											resizable : true
										}, {
											field : 'designation',
											caption : 'Designation',
											size : '150px',
											sortable : true,
											searchable : 'text',
											resizable : true
										}, {
											field : 'department',
											caption : 'Department',
											size : '150px',
											sortable : true,
											searchable : 'text',
											resizable : true
										}, {
											field : 'contactNo',
											caption : 'Contact No',
											size : '150px',
											sortable : true,
											searchable : 'text',
											resizable : true
										}, {
											field : 'email',
											caption : 'Email ID',
											size : '240px',
											sortable : true,
											searchable : 'text',
											resizable : true
										}, {
											field : 'supervisior',
											caption : 'Supervisor',
											size : '240px',
											sortable : true,
											searchable : 'text',
											resizable : true
										}]
									}
								};
							
								$(function() {
							
									var contextPath = $('#contextPath').val();
							
									var cData = {
										id : '1'
									}
							
									$.ajax({
										type : "POST",
										url : contextPath + "/getPafEligibleList",
										data : JSON.stringify(cData),
										contentType : "application/json",
										success : function(data) {
											var cData = JSON.parse(data);
											$().w2grid(config.grid);
											for (var i = 0; i < cData.length; i++) {
												w2ui['grid'].records.push({
													//recid : i + 1,
													recid : cData[i].id,
													id : cData[i].id,
													name : cData[i].name,
													designation : cData[i].designation,
													department : cData[i].department,
													contactNo : cData[i].personalPhone,
													email : cData[i].email,
													supervisior : cData[i].supervisorName,
													lxnId : cData[i].lxnId													
												});
											}
							
											w2ui.grid.refresh();
											w2ui['grid'].hideColumn('id');
											$('#main').w2render('grid');
										},
										error : function(err) {
											w2alert("Employee Load Failed...");
										}
							
									});
								});
								
								
								$(document).ready(function(){
									$("#collapseBtn").click(function(){										
										$('#collapseIcn').toggleClass('fa-plus fa-minus');
										$(this).toggleClass('btn-info btn-warning');
									});
								});
							</script>
                    	<!-- ----------End Table ------------- -->
                    </div><!-- /.box-body -->
                </div><!-- /.box -->
            </div>
        </div>
        
    </section><!-- /.content -->
</div>