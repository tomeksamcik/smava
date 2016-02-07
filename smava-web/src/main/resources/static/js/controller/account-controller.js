'use strict';

App.controller('AccountController', [
	'$scope',
	'$timeout',
	'AccountService',
	'ngToast',
	function($scope, $timeout, AccountService, ngToast) {
		var self = this;
		self.account = {
			id : null,
			bic : '',
			iban : ''
		};
		self.accounts = [];

		self.getAccounts = function() {
			AccountService.getAccounts().then(function(d) {
				console.log(d);
				self.accounts = d;
			}, function(errResponse) {
				console.error('Error while fetching Accounts (' + errResponse.data.error + ')');
				ngToast.create({
					className: 'danger',
					content: '<b>Error while fetching Accounts</b><br/>' + errResponse.data.error
				});					
			});
		};

		self.addAccount = function(account) {
			AccountService.addAccount(account).then(
				function(response) {
					self.getAccounts();
					ngToast.create({
						className: 'success',
						content: '<b>Account has been successfully added</b>'
					});					
				},
				function(errResponse) {				
					console.error('Error while creating Account (' + errResponse.data.error + ')');
					ngToast.create({
						className: 'danger',
						content: '<b>Error while creating Account</b><br/>' + errResponse.data.error
					});					
				});
		};

		self.updateAccount = function(account, id) {
			AccountService.updateAccount(account, id).then(
				function(response) {
					self.getAccounts();
					ngToast.create({
						className: 'success',
						content: '<b>Account has been successfully updated</b>'
					});					
				},
				function(errResponse) {
					console.error('Error while updating Account (' + errResponse.data.error + ')');
					ngToast.create({
						className: 'danger',
						content: '<b>Error while updating Account</b><br/>' + errResponse.data.error
					});					
				});
		};

		self.deleteAccount = function(id) {
			AccountService.deleteAccount(id).then(
				function(response) {
					self.getAccounts();
					ngToast.create({
						className: 'success',
						content: '<b>Account has been successfully deleted</b>'
					});					
				},
				function(errResponse) {					
					console.error('Error while deleting Account (' + errResponse.data.error + ')');
					ngToast.create({
						className: 'danger',
						content: '<b>Error while deleting Account</b><br/>' + errResponse.data.error
					});					
				});
		};

		self.getAccounts();

		self.submit = function() {
			if (self.account.id == null) {
				console.log('Saving New Account', self.account);
				self.addAccount(self.account);
			} else {
				self.updateAccount(self.account, self.account.id);
				console.log('Account updated with id ', self.account.id);
			}
			self.reset();
		};

		self.edit = function(id) {
			console.log('id to be edited', id);
			for (var i = 0; i < self.accounts.length; i++) {
				if (self.accounts[i].id == id) {
					self.account = angular.copy(self.accounts[i]);
					break;
				}
			}
		};

		self.remove = function(id) {
			console.log('id to be deleted', id);
			for (var i = 0; i < self.accounts.length; i++) {
				if (self.accounts[i].id == id) {
					self.reset();
					break;
				}
			}
			self.deleteAccount(id);
		};

		self.reset = function() {
			self.account = {
				id : null,
				bic : '',
				iban : ''
			};
			$scope.myForm.$setPristine(); // reset Form
		};
		
	} 
]);