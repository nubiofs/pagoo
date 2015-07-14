'use strict';

app.factory('Authentication', function($http, TokenStorage, UserStorage, $rootScope, EntidadeService, EntidadeStorage, SegmentoService, SegmentoStorage) {
	
	var _this = {
		user : ""
	};
	
	_this.login = function(user) {
		return $http.post(pagooContextRoot + '/rest/login', {
			username : user.username, 
			password : user.password
		}).success(function(result, status, headers) {
			console.log('X-AUTH-TOKEN: ' + headers('X-AUTH-TOKEN'));
			TokenStorage.store(headers('X-AUTH-TOKEN'));
			console.log('Usuario: ' + angular.toJson(result));
			UserStorage.store(angular.toJson(result));
			console.log(result.id);
			EntidadeService.getByIdUsuario({idusuario : result.id},function(res){ // RECUPERA ENTIDADE PELO ID DO USUARIO LOGADO.
				EntidadeStorage.store(angular.toJson(res)); // ARMAZENA A ENTIDADE PARA USO FUTURO.
				console.log("Armazenou Entidade do usuario na session storage.");
				$rootScope.$broadcast('EntidadeLoadedEvent');
			}).$promise.then(function(res) { 
				// sucesso recuperacao entidade
				console.log("promise res: " + angular.toJson(res));
				SegmentoService.getByIdUsuario({idusuario:result.id}, function(res){
					// callback
					SegmentoStorage.store(angular.toJson(res));
					console.log("Armazenou segmento do usuario na session storage:" + SegmentoStorage.retrieve());
				}).$promise.then(function(res){
					//sucesso recuperacao segmento
					console.log("promise res: " + angular.toJson(res));
					$rootScope.$broadcast('SegmentoLoadedEvent');
				}, function(error){
					//error
					console.error("Erro recuperando segmento! " + angular.toJson(error));
				});
		    },function(error){ 
		    	// error
				console.error("Erro recuperando entidade! " + angular.toJson(error));
			});

			$rootScope.$broadcast('LoginEvent');
		});
	};
	
	_this.logout = function() {
		console.log('Logging out!');
		TokenStorage.clear();
		UserStorage.clear();
		EntidadeStorage.clear();
		SegmentoStorage.clear();
		delete $rootScope.segmento;
		delete $rootScope.entidade;
		delete $rootScope.usuario;
		$rootScope.$broadcast('LogoutEvent');
		_this.user = "";		
	};
	
	_this.set = function() {
		return $http.get(pagooContextRoot + '/rest/users/current').success(function(user) { //RECUPERAR O USUARIO A PARTIR DA SESSION STORAGE (VER INTERCEPTOR)
			if (user.username !== 'anonymousUser') {
				_this.user = user;
				$rootScope.$broadcast('AuthByTokenOK');
			}
		}).error(function(data, status, headers, config) {
			$rootScope.$broadcast('AuthErrorEvent'); // DISPARA EVENTO PARA ERRO DE AUTENTICACAO
		}); 
	};
	
	_this.isAuthenticated = function() {
		return (_this.user) ? _this.user : false; 
	};
	
	_this.isAuthorized = function(role) {
		if (_this.user) {
			var i = _this.user.roles.length;
			while (i--) {
		       if (_this.user.roles[i] === role) {
		           return true;
		       }
		    }
		    return false;
		} return false;		
	};
	
	return _this;
	
});