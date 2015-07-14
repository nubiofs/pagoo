/**
 * 
 */

app.config(['$stateProvider', '$urlRouterProvider','$httpProvider', '$locationProvider', 
            
            function($stateProvider, $urlRouterProvider, $httpProvider, $locationProvider, $stateParams) {
	
	$stateProvider
		
		.state('login', {
			url: '/login',
			templateUrl: './login.html',
			controller: 'AuthCtrl',
			data: {
				security: false
			},
			resolve: {
			

			}			
		})	
	
	
		.state('home', { // PRIMEIRA VERSAO PAGINA INICIAL JA EH DE CRIAR EVENTO
			url: '/home',
			templateUrl: './novo.html', 
			controller: 'CobrancaController',
			data: {
				security: true
			},
			resolve: {
				loadTiposServicos: function() {
				}
			}
		})	
		
		.state('consultar', { 
			url: '/consultar',
			templateUrl: './consultar.html',
			controller: 'ConsultaCobrancaController',
			data: {
				security: true
			},
			resolve: {
				authPromise: function(Authentication) {
					
				}
			}
		})
		
		.state('sucessocobranca', { 
			url: '/sucessocobranca/:compra',
			templateUrl: './sucesso-novo.html', 
			controller : function($scope,$stateParams){
				console.log($stateParams);
				$scope.compra = angular.fromJson(atob($stateParams.compra));
				var cpfcnpj = JSON.stringify(angular.fromJson($scope.compra.comprador.cpfcnpj));
				$scope.showcpf = cpfcnpj.length == 11 ? true : false;
				$scope.showcnpj = cpfcnpj.length == 14 ? true : false;
			},
			data: {
				security: true
			},
			resolve: {
				authPromise: function(Authentication) {
					
				}
			}
		})			
		

		
		
	$urlRouterProvider.otherwise(function($injector, $location,Authentication){
		console.log(Authentication);
		$location.path('/login');
    });
		
}]);	