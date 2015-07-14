'use strict';


app.factory('Functions', function($timeout, $modal) {
	
	var _function = {
		
		/**
		  funcao de ordenacao:
		  recebe a coluna selecionada
		  as colunas da tabela que podem ser selecionadas
		  e a variavel de ordenacao	
		**/ 
		tSort: function(column, columns, sort) {
			if (sort.column === column) {
	    		sort.descending = !sort.descending;
	    	} else {
	    		sort.column = column;
	    		sort.descending = false;
	    	} 
	    	
	    	angular.forEach(columns, function(obj){
	    		if (obj.type === column) {
	    			obj.classe = "ordered-by-this";
	    			if (sort.descending) {
	    				obj.arrow = "fa fa-caret-down";
	    			} else {
	    				obj.arrow = "fa fa-caret-up";
	    			}
	    		} else {
	    			obj.classe = "";
	    			obj.arrow = "";
	    		}
	    	});
		},			
		
		/**
		  funcao para abrir modal de confirmacao
		  recebe o objeto que sofrerá uma ação
		  a mensagem a ser exibida para responder sim ou nao
		  e a funcao a ser executada com o objeto
		**/ 
		mConfirmDialog: function(obj, msg, fn) {
			
			// initiate modal instance
			var modal = $modal.open({
				templateUrl: 'resources/pages/modals/exclude.modal.html',
				controller: 'FunctionsCtrl',
				size: 'sm',
				resolve: {
					object: function() {
						return obj;
					},
					text: function() {
						return msg;
					}
				}
			});
			
			// after modal action ended
			modal.result.then(function(obj) {				
				fn(obj);
			}, function() {});
		},
		
		/** 
		  retorna true se array estiver vazio ou nulo 
		  e false caso contrario
		**/ 
		isEmpty: function(array) {
			if (array == null) {
				return true;
			} else {
				if (array.length > 0) {
					return false;
				} else { 
					return true;
				}
			}			
		},
		
		toAmericanCalendar: function(date) {
			var stDate = date.split('/');
			var day = stDate[0];
			var month = stDate[1];
			var year = stDate[2];
			
			return month + "/" + day + "/" + year;
		}
	} 
	
	return _function;
});

app.controller('FunctionsCtrl', function($scope, $modalInstance, object, text) {
	
	$scope.obj = angular.copy(object);
	$scope.text = angular.copy(text);
	
	$scope.ok = function() {		
		$modalInstance.close($scope.obj);		
	};
	
	$scope.cancel = function() {
		$modalInstance.dismiss('cancel');
	};
	
});

