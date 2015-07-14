// servico para gravar o token apos autenticado
app.factory('TokenStorage', function() {
	
	var storageKey = 'auth_token';
	
	return {
		// guarda o token na sessao
		store : function(token) {
			return sessionStorage.setItem(storageKey, token);
		},
		// recupera o token da sessao
		retrieve : function() {
			return sessionStorage.getItem(storageKey);
		},
		// limpa o token, usado para logout
		clear : function() {
			return sessionStorage.removeItem(storageKey);
		}
	};
});

// Service para gravar o usuario logado.
app.factory('UserStorage', function() {
	
	var storageKey = 'logged_user';
	
	return {
		// guarda o token na sessao
		store : function(token) {
			return sessionStorage.setItem(storageKey, token);
		},
		// recupera o token da sessao
		retrieve : function() {
			return sessionStorage.getItem(storageKey);
		},
		// limpa o token, usado para logout
		clear : function() {
			return sessionStorage.removeItem(storageKey);
		}
	};
});


//Service para gravar a entidade a qual o usuário faz parte.
// Entidade é a empresa a qual faz parte
app.factory('EntidadeStorage', function() {
	
	var storageKey = 'logged_entidade';
	
	return {
		// guarda o token na sessao
		store : function(token) {
			return sessionStorage.setItem(storageKey, token);
		},
		// recupera o token da sessao
		retrieve : function() {
			return sessionStorage.getItem(storageKey);
		},
		// limpa o token, usado para logout
		clear : function() {
			return sessionStorage.removeItem(storageKey);
		}
	};
});

//Service para gravar o segmento o qual o usuário faz parte.
//Segmento é o ramo como por exemplo: placas, despachantes, auto-escolas, clínicas, etc.
app.factory('SegmentoStorage', function() {
	
	var storageKey = 'logged_segmento';
	
	return {
		// guarda o token na sessao
		store : function(token) {
			return sessionStorage.setItem(storageKey, token);
		},
		// recupera o token da sessao
		retrieve : function() {
			return sessionStorage.getItem(storageKey);
		},
		// limpa o token, usado para logout
		clear : function() {
			return sessionStorage.removeItem(storageKey);
		}
	};
});


app.config(function($httpProvider) {	
	
	$httpProvider.interceptors.push('TokenAuthInterceptor');
	$httpProvider.interceptors.push('HttpRequestInterceptor');
	
});

app.run(function($rootScope, $state, $location, $http, $timeout, Authentication, UserStorage, SegmentoStorage, EntidadeStorage) {
	
	// Set Usuario Logado:
	
	$rootScope.usuario = angular.fromJson(UserStorage.retrieve()); // NO RUN RECUPERAR USUARIO LOGADO.
	$rootScope.segmento = angular.fromJson(SegmentoStorage.retrieve()); // NO RUN RECUPERAR SEGMENTO LOGADO.
	$rootScope.entidade = angular.fromJson(EntidadeStorage.retrieve()); // NO RUN RECUPERAR ENTIDADE LOGADO.
	
	//console.log($rootScope.usuario);
	
	Authentication.set().then(function(res) {		
		$rootScope.$on('$stateChangeStart', function (event, toState, toParams) {
			if (!Authentication.isAuthenticated()) {
				if ($state.current.data.security) {					
					$location.path('/login');
				} else {
					if ($state.is('login')) {
						if (toState.name !== 'home') {
							event.preventDefault();
						}						
					} else if ($state.is('home')) {
						if (toState.name !== 'login') {
							event.preventDefault();
						}
					} 					
				}				
			}		
		});	
		
	});

});
