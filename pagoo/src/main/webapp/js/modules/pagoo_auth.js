/* CONTROLLERS */
// Controller Principal da Aplicação
app.controller('PagooController', function ($scope, USER_ROLES,AuthService) {
  $scope.currentUser = null;
  $scope.authenticated = null;
  $scope.userRoles = USER_ROLES;
  $scope.isAuthorized = AuthService.isAuthorized;
  $scope.setCurrentUser = function (user) {
    $scope.currentUser = user;
  };
  $scope.setAuthenticated = function (isAuth) {
	    $scope.authenticated = isAuth;
	    alert('$scope.authenticated: ' + $scope.authenticated);
  };
})



app.controller('LoginController', function($scope, $rootScope, AUTH_EVENTS,	AuthService) {
	$scope.credentials = {
		email : '',
		senha : ''
	};
	$scope.login = function(credentials) {
		AuthService.login(credentials).then(function(user) {
			$rootScope.$broadcast(AUTH_EVENTS.loginSuccess);
			$scope.setCurrentUser(user);
			$scope.setAuthenticated(AuthService.isAuthenticated());
		}, function() {
			$scope.setAuthenticated(AuthService.isAuthenticated());
			$rootScope.$broadcast(AUTH_EVENTS.loginFailed);
		});
	};

})


// https://medium.com/opinionated-angularjs/techniques-for-authentication-in-angularjs-applications-7bbf0346acec
app.factory('AuthService', function($http, Session) {
	var authService = {};

	authService.login = function(credentials) {
		return $http.post(site_prefix + '/rest/users/auth', credentials).then(
				function(res) {
					alert(angular.toJson(res.data));
					Session.create(res.data.id, res.data.email,res.data.nome, res.data.role);
					return res.data;
				},
				function(res){
					alert('Erro Autenticando' + res);
				});
	};

	authService.isAuthenticated = function() {
		//alert('Autenticado: ' + !!Session.id );
		return !!Session.id;
	};

	authService.isAuthorized = function(authorizedRoles) {
		//alert('isAuthorized: ' + authorizedRoles);
		if (!angular.isArray(authorizedRoles)) {
			authorizedRoles = [ authorizedRoles ];
		}
		return (authService.isAuthenticated() && authorizedRoles.indexOf(Session.role) !== -1);
	};

	return authService;
})

// Store session info
app.service('Session', function () {
	  this.create = function (id, email, nome, role) {
	    this.id = id;
	    this.email = email;
	    this.nome = nome;
	    this.role = role;
	  };
	  this.destroy = function () {
	    this.id = null;
	    this.email = null;
	    this.nome = null;
	    this.role = null;
	  };
})



/* CONSTANTES - TODO TRANSFORMAR EM UM SERVLET */

app.constant('USER_ROLES', {
	all : '*',
	administrador : 'Administrador',
	associacao : 'Associacao',
	guest : 'guest'
})


app.constant('AUTH_EVENTS', {
	loginSuccess : 'auth-login-success',
	loginFailed : 'auth-login-failed',
	logoutSuccess : 'auth-logout-success',
	sessionTimeout : 'auth-session-timeout',
	notAuthenticated : 'auth-not-authenticated',
	notAuthorized : 'auth-not-authorized'
})	



