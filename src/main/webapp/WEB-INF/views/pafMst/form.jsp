<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<input id="id" name="id" value="${pafManage.id}" type="hidden"
	class="form-control" />
<div class="form-group">
	<label for="name" class="col-sm-2 control-label"> <strong>
			PAF Year <span style="color: red">*</span>: </strong>
	</label>
	<div class="col-sm-6">
		<select id="pafYearId" name="pafYearId" class="form-control">
			<option value="">Select One</option>
			<c:forEach items="${pafYearList}" var="pafYear">
				<c:if test="${pafManage.pafYear.id eq pafYear.id}">
					<option value="${pafYear.id}" selected="selected">${pafYear.name}</option>
				</c:if>
				<c:if test="${pafManage.pafYear.id ne pafYear.id}">
					<option value="${pafYear.id}">${pafYear.name}</option>
				</c:if>
			</c:forEach>
		</select>
	</div>
</div>

<div class="form-group">
	<label for="ph1OpenDate" title="format: DD-MM-YYYY"
		class="col-sm-2 control-label"> <strong> Phase-1 Open Date <span
			style="color: red">*</span>:
	</strong>
	</label>
	<div class="col-sm-6">
		<input id="ph1OpenDate" name="ph1OpenDate" value="${pafManage.ph1OpenDate}"
			type="eu-date" class="form-control" title="format: DD-MM-YYYY" />
	</div>
</div>

<div class="form-group">
	<label for="ph1CloseDate" title="format: DD-MM-YYYY"
		class="col-sm-2 control-label"> <strong> Phase-1 Close Date <span
			style="color: red">*</span>:
	</strong>
	</label>
	<div class="col-sm-6">
		<input id="ph1CloseDate" name="ph1CloseDate" value="${pafManage.ph1CloseDate}"
			type="eu-date" class="form-control" title="format: DD-MM-YYYY" />
	</div>
</div>

<div class="form-group">
	<label for="ph2OpenDate" title="format: DD-MM-YYYY"
		class="col-sm-2 control-label"> <strong> Phase-2 Open Date <span
			style="color: red">*</span>:
	</strong>
	</label>
	<div class="col-sm-6">
		<input id="ph2OpenDate" name="ph2OpenDate" value="${pafManage.ph2OpenDate}"
			type="eu-date" class="form-control" title="format: DD-MM-YYYY" />
	</div>
</div>

<div class="form-group">
	<label for="ph2CloseDate" title="format: DD-MM-YYYY"
		class="col-sm-2 control-label"> <strong> Phase-2 Close Date <span
			style="color: red">*</span>:
	</strong>
	</label>
	<div class="col-sm-6">
		<input id="ph2CloseDate" name="ph2CloseDate" value="${pafManage.ph2CloseDate}"
			type="eu-date" class="form-control" title="format: DD-MM-YYYY" />
	</div>
</div>

<div class="form-group">
	<label for="remarks" class="col-sm-2 control-label"> <strong>Remarks
			: </strong>
	</label>
	<div class="col-sm-6">
		<textarea class="form-control" id="remarks" name="remarks">${pafManage.remarks}</textarea>
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

	$(function() {

		$('#fromDate', '#toDate')
				.on(
						"change",
						function() {
							if ($('#fromDate').val().trim().length == 8
									&& $('#toDate').val().trim().length == 8) {
								jQuery
										.ajax({
											type : "POST",
											url : "${pageContext.request.contextPath}/checkUniquePafYear",
											dataType : 'json',
											data : {
												fromDate : $('#fromDate').val(),
												toDate : $('#toDate').val(),
												id : $('#id').val()
											},
											success : function(res) {
												if (res.pafYearInfo != null) {
													$('#fromDate').val("");
													$('#toDate').val("");
													w2alert("This date range already exists. Please try with right value.");
												}
											}
										});
							}

						});

		
		// Initialize form validation on the registration form.
		// It has the name attribute "registration"
		$("form[name='form']").validate({
			// Specify validation rules
			rules : {
				// The key name on the left side is the name attribute
				// of an input field. Validation rules are defined
				// on the right side
				pafYearId : "required",
				ph1OpenDate : "required",
				ph1CloseDate : "required",
				ph2OpenDate : "required",
				ph2CloseDate : "required"
			},

			// Make sure the form is submitted to the destination defined
			// in the "action" attribute of the form when valid
			submitHandler : function(form) {
				form.submit();
			}
		});
	});
</script>