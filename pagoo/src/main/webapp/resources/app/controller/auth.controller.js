'use strict';

app.controller('AuthCtrl', function($scope, $rootScope, $location, $timeout, Authentication) {
	
	$rootScope.usuario = ''; // VARIAVEL DEVE SER ZERADA.
	
	$scope.$on('AuthError',function(){
		// EXECUTAR OPERAÇÕES QUANDO OCORRER ERRO DE AUTENTICAÇÃO!
	})

	$scope.login = function(user) {
		//alert(angular.toJson(user));
		$rootScope.fnLoading(true);
		Authentication.login(user).then(function(res) {			
			Authentication.set().then(function(res) {
				//alert(Authentication.isAuthenticated());
				//$location.path('/home');				
				$timeout(function() {
					$rootScope.fnLoading(false);
				}, 700);
			});			
			
		}, function(err) {
			$rootScope.$broadcast('AuthErrorEvent');
			$scope.err = err.status;
			$timeout(function() {
				$rootScope.fnLoading(false);
			}, 700);
		});
		
	};
	
	$scope.$on('SegmentoLoadedEvent',function(){
		$location.path('/home');
	})
	
});