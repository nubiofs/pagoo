'use strict';

app.factory('HttpRequestInterceptor', function($q, $window, $location) {
	
	return function(promise) {
		var succcess = function(response) {
			return response;
		};
		
		var error = function(response) {
			if (response.status === 401) {
				$location.path('/login');
			} 
			if (response.status === 403) {
				$location.path('/login');
			}
			if (response.status === 404) {
				$location.path('/404');
			}
			
			return $q.reject(response);
		};
		
	    return promise.then(success, error);		
	};
	
});