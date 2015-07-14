'use strict';

app.controller('ApplicationController', function($scope, $rootScope, $timeout, $state, $modal, $location, Authentication, UserStorage, SegmentoStorage, EntidadeStorage, TipoServicoService) {


	
	$scope.init = function() {
		return Authentication.set();	
	};
	
	$rootScope.$on('LoginEvent', function(){ // QDO O LOGIN OCORRER, DEFINIR NO ESCOPO A VARIAVEL USUARIO.
		$rootScope.usuario = UserStorage.retrieve();
	});
	
	$rootScope.$on('SegmentoLoadedEvent', function(){ // QDO O CARREGAMENTO DO SEGMENTO OCORRER, DEFINIR NO ESCOPO A VARIAVEL SEGMENTO.
		$rootScope.segmento = angular.fromJson(SegmentoStorage.retrieve());
	});
	
	$rootScope.$on('EntidadeLoadedEvent', function(){ // QDO O CARREGAMENTO DO ENTIDADE OCORRER, DEFINIR NO ESCOPO A VARIAVEL ENTIDADE.
		$rootScope.entidade = angular.fromJson(EntidadeStorage.retrieve());
	});
	
	$rootScope.$on('AuthErrorEvent', function(){
		$location.path('/login');
	});
	
	$rootScope.$on('LogoutEvent', function(){
		$rootScope.usuario = '';
		$location.path('/login');
	});
	
	/**
     * FUNCOES AUTENTICACAO
     */
	
	$scope.logout = function() {
		$rootScope.fnLoading(true);
		Authentication.logout();
		$timeout(function() {
			$state.go('login');
			$rootScope.fnLoading(false);
		}, 1000);
	};
	
	$scope.isAuthenticated = function() {
		if (!$scope.user) {
			$scope.user = angular.copy(Authentication.user);	
		}			
		return Authentication.isAuthenticated();
	};
	
	$scope.isAuthorized =  function(role) {
		return Authentication.isAuthorized(role);
    };   
	
    /**
     * FUNCOES LOADING 
     */
    
    $rootScope.loading = 'loading-off';
    
    $rootScope.fnLoading = function(val) {
    	if (val) {
    		$rootScope.loading = 'loading-on';
    	}
    	else {
			$rootScope.loading = 'loading-off';
    	}    	
    };
    
    $rootScope.isLoading = function() {
    	console.log('loading: ' + $rootScope.loading);
    	return ($rootScope.loading === 'loading-on' ? true : false);
    };  
    
    /**
     * FUNCOES MENU
     */
    
    $rootScope.isSelected = function(item) {
    	if ($state.is(item)) {
    		return 'active';
    	}
    	return '';
    };
    
   
});