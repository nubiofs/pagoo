'use strict';

/**
 * Controller responsável pela página de cobrancas
 */
app.controller('ConsultaCobrancaController', function($scope, $rootScope, $location, $timeout,TipoServicoService, ConsultaCobrancaService, dialogs, $state) {
	

	
	//$scope.consulta.idEntidade = $rootScope.entidade.id;
	
	
	
	// carrega combo com a lista de servicos
	
	$scope.listaTipoServicos = TipoServicoService.query({idSegmento:$rootScope.segmento.id}).$promise.then(function (result) {
	    $scope.listaTipoServicos = result;
	    console.log('Lista Serviso JSON promise!:' + angular.toJson($scope.listaTipoServicos));
	});
	
	$scope.cobrancas = ConsultaCobrancaService.queryByIdEntidade({idEntidade : $rootScope.entidade.id}).$promise.then(function (result) {
		$rootScope.fnLoading(true);
		$scope.cobrancas = result;
	    console.log('Consulta inicial de cobrancas realizadas:' + angular.toJson($scope.cobrancas));
	    $rootScope.fnLoading(false);
	},function(err){ // Fez consulta e retornou erro.
        console.error('$promise.then error: ' + angular.toJson(err));
    	$scope.err = err;
    	console.error('Erro: ' + angular.toJson(err));
    	dialogs.error('Erro','Erro consultando cobrancas: ' + angular.toJson(err));  
    	$rootScope.fnLoading(false); // finalizou com erro
    });
	
	$scope.consultarCobrancas = function(){
		$rootScope.fnLoading(true); // seta carregando
		$scope.cobrancas = [];
		$scope.consulta.idEntidade = $rootScope.entidade.id;
		console.log("Informacoes da consulta: " + angular.toJson($scope.consulta));
		ConsultaCobrancaService.queryByIdEntidade($scope.consulta, function(res){
			console.log('Executou:' + angular.toJson(res));
		}).$promise.then(function(res){ // consulta terminou ok.
            console.log('$promise.then success: ' + angular.toJson(res));
            $scope.cobrancas = res;
            $rootScope.fnLoading(false); // finalizou
        },function(err){ // Fez consulta e retornou erro.
            console.error('$promise.then error: ' + angular.toJson(err));
        	$scope.err = err;
        	console.error('Erro: ' + angular.toJson(err));
        	dialogs.error('Erro','Erro consultando cobrancas: ' + angular.toJson(err));  
        	$rootScope.fnLoading(false); // finalizou com erro
        });
		
	}

});