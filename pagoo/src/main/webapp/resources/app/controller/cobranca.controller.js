'use strict';

/**
 * Controller responsável pela página de cobrancas
 */
app.controller('CobrancaController', function($scope, $rootScope, $location, $timeout,TipoServicoService, CobrancaService, dialogs, $state, SegmentoStorage) {
	// carrega combo com a lista de servicos
	$scope.listaTipoServicos = [];
	$scope.listaTipoServicos = TipoServicoService.query({idSegmento:$rootScope.segmento.id}).$promise.then(function (result) {
	    $scope.listaTipoServicos = result;
	    console.log('Lista Serviso JSON promise!:' + angular.toJson($scope.listaTipoServicos));
	});

	$scope.gerarBorderoBoletoCobranca = function(){
		
		console.log('Informações compra: ' + angular.toJson($scope.compra ));
		CobrancaService.save($scope.compra, function() {
		  //  console.log('Informações salvas: ' + angular.toJson(data));
		}).$promise.then(function(result){
            console.log('Result save: ' + angular.toJson(result));
            dialogs.notify('Sucesso','Cobrança gerada com sucesso.');
            $scope.success = result;
            var param = {'compra' : btoa(angular.toJson($scope.compra))}
            console.log('Valor Compra fromJson:' + angular.fromJson($scope.compra));
            console.log('Valor Compra toJson:' + angular.toJson($scope.compra));
            $state.go('sucessocobranca',param);
        }, function(err){
        	$scope.err = err;
        	console.error('Erro: ' + angular.toJson(err));
        	dialogs.error('Erro','Erro gerando cobrança: ' + angular.toJson(err));
        });
	}
});