'use strict';

app.factory('UtilsService', function($http) {	
	
	var o = {
			status: []
		};	
		
		o.listarStatus = function() {
			return $http.get('/api/status').success(function(data) {
				angular.copy(data, o.status);
			});
		};
		
		return o;
});