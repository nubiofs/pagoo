var site_prefix = '/pagoo';
var app = angular.module('pagoo',['ngRoute', 'ngResource']);

app.config(['$resourceProvider', function ($resourceProvider) {
    // Don't strip trailing slashes from calculated URLs
    $resourceProvider.defaults.stripTrailingSlashes = false;
}]);

/* SERVICES */

// Criação do serviço para Usuários
app.factory('UsersService', function ($resource) {
    var usersService = $resource(site_prefix+'/rest/users/:id', {
        id: '@id'
    }, {
    	loadByEmailAndPassword:{method:'POST', params: {email:'@email',password:'@password'},url: site_prefix+'/rest/users/:email'}
    }); // configura o rest
    return usersService;

});


/* CONTROLLERS */
app.controller('PagooListController', function ($scope,UsersService) {
    $scope.users = false;
    $scope.users = UsersService.query();
    $scope.detailUser = function(id){
        $scope.$broadcast('loadUserDetail',{idDetalhar:id});
    }
    
});

app.controller('UserDetailsController', function ($scope,UsersService) {
    $scope.userDetail = false;
    $scope.$on('loadUserDetail', function(event,args) {
    	alert(args);
    	$scope.userDetail = UsersService.get({id:args.idDetalhar});
    });
});



