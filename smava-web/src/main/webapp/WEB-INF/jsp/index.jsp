<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Test Assignment</title>
<style>
.bic.ng-valid {
	background-color: lightgreen;
}

.bic.ng-dirty.ng-invalid-required {
	background-color: red;
}

.bic.ng-dirty.ng-invalid-minlength {
	background-color: yellow;
}

.iban.ng-valid {
	background-color: lightgreen;
}

.iban.ng-dirty.ng-invalid-required {
	background-color: red;
}

.iban.ng-dirty.ng-invalid-minlength {
	background-color: yellow;
}
</style>
<link href="<c:url value='/css/ngToast.css' />" rel="stylesheet"></link>
<link href="<c:url value='/css/ngToast-animations.css' />" rel="stylesheet"></link>
<link href="<c:url value='/css/bootstrap.css' />" rel="stylesheet"></link>
<link href="<c:url value='/css/app.css' />" rel="stylesheet"></link>
</head>
<body ng-controller="AccountController as ctrl" ng-app="myApp" class="ng-cloak">
	<toast></toast>
	<div class="generic-container">
		<div class="panel panel-default">
			<div class="panel-heading">
				<span class="lead">Bank Account Form </span>
			</div>
			<div class="formcontainer">
				<form ng-submit="ctrl.submit()" name="myForm"
					class="form-horizontal">
					<input type="hidden" ng-model="ctrl.account.id" />
					<div class="row">
						<div class="form-group col-md-12">
							<label class="col-md-2 control-lable" for="bic">Business
								Identifier Code</label>
							<div class="col-md-7">
								<input type="text" ng-model="ctrl.account.bic" id="bic"
									class="bic form-control input-sm" placeholder="Enter BIC"
									required ng-minlength="3" />
								<div class="has-error" ng-show="myForm.$dirty">
									<span ng-show="myForm.bic.$error.required">This is a
										required field</span> <span ng-show="myForm.bic.$error.minlength">Minimum
										length required is 3</span> <span ng-show="myForm.bic.$invalid">This
										field is invalid </span>
								</div>
							</div>
						</div>
					</div>


					<div class="row">
						<div class="form-group col-md-12">
							<label class="col-md-2 control-lable" for="iban">International
								Bank Account Number</label>
							<div class="col-md-7">
								<input type="text" ng-model="ctrl.account.iban" id="iban"
									class="iban form-control input-sm"
									placeholder="Enter your IBAN" required ng-minlength="3" />
								<div class="has-error" ng-show="myForm.$dirty">
									<span ng-show="myForm.iban.$error.required">This is a
										required field</span> <span ng-show="myForm.iban.$error.minlength">Minimum
										length required is 3</span> <span ng-show="myForm.iban.$invalid">This
										field is invalid </span>
								</div>
							</div>
						</div>
					</div>

					<div class="row">
						<div class="form-actions floatRight">
							<input type="submit"
								value="{{!ctrl.account.id ? 'Add' : 'Update'}}"
								class="btn btn-primary btn-sm" ng-disabled="myForm.$invalid">
							<button type="button" ng-click="ctrl.reset()"
								class="btn btn-warning btn-sm" ng-disabled="myForm.$pristine">Reset
								Form</button>
						</div>
					</div>
				</form>
			</div>
		</div>
		<div class="panel panel-default">
			<!-- Default panel contents -->
			<div class="panel-heading">
				<span class="lead">List of Accounts </span>
			</div>
			<div class="tablecontainer">
				<table class="table table-hover">
					<thead>
						<tr>
							<th>ID.</th>
							<th>BIC</th>
							<th>IBAN</th>
							<th width="20%"></th>
						</tr>
					</thead>
					<tbody>
						<tr ng-repeat="a in ctrl.accounts">
							<td><span ng-bind="a.id"></span></td>
							<td><span ng-bind="a.bic"></span></td>
							<td><span ng-bind="a.iban"></span></td>
							<td>
								<button type="button" ng-click="ctrl.edit(a.id)"
									class="btn btn-success custom-width">Edit</button>
								<button type="button" ng-click="ctrl.remove(a.id)"
									class="btn btn-danger custom-width">Remove</button>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>

	<script src="<c:url value='/js/libs/angular.js' />"></script>
	<script src="<c:url value='/js/libs/angular-animate.js' />"></script>
	<script src="<c:url value='/js/libs/angular-sanitize.js' />"></script>
	<script src="<c:url value='/js/libs/ngToast.js' />"></script>
	
	<script src="<c:url value='/js/app.js' />"></script>
	<script src="<c:url value='/js/service/account-service.js' />"></script>
	<script src="<c:url value='/js/controller/account-controller.js' />"></script>
</body>
</html>