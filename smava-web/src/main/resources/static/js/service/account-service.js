'use strict';

App.factory('AccountService', [
	'$http',
	'$q',
	'$location',
	function($http, $q, $location) {
		return {
			getAccounts : function() {
				return $http.get('account/').then(
						function(response) {
							return response.data;
						}, function(errResponse) {
							return $q.reject(errResponse);
						});
			},

			addAccount : function(account) {
				return $http
						.post('account/', account)
						.then(function(response) {
							return response.data;
						}, function(errResponse) {
							return $q.reject(errResponse);
						});
			},

			updateAccount : function(account, id) {
				return $http.put('account/' + id,
						account).then(function(response) {
					return response.data;
				}, function(errResponse) {
					return $q.reject(errResponse);
				});
			},

			deleteAccount: function(id) { 
				return $http.delete('account/' + id).then(
					function(response) { 
						return response.data; 
					},
					function(errResponse) { 
						return $q.reject(errResponse); 
					} 
				); 
			}
		};
	} 
]);
