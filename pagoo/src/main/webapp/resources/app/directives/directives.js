
// Directive for footer html
app.directive('pagooFooter', function () {
    return {
        templateUrl: 'resources/templates/footer.html',
        restrict: 'E', // RESTRINGIR DIRETIVAS PARA ATRIBUTOS OU ELEMENTOS  (TAGS)
        controller: function ($scope) {
            $scope.copyright = 'Copyright © 2015 Infosolo Inc.';
        }
    }
});

//Directive for header html
app.directive('pagooHeader', function (Authentication, $location) {
    return {
        templateUrl: 'resources/templates/header.html',
        restrict: 'E', // RESTRINGIR DIRETIVAS PARA ATRIBUTOS OU ELEMENTOS  (TAGS)
        controller: function ($rootScope,$scope) {
            $scope.copyright = 'Copyright © 2015 Infosolo Inc.';
        },
        scope: {
        	user: '=',
        	entidade: '='
        },
        controller: function ($scope) {
        	console.log('pagooHeader controller');
        	$scope.logout = function(){
        		Authentication.logout();
        	}
        	
        },
        link:function($scope,$el){
        	console.log('pagooHeader link');
            $scope.usuario = angular.fromJson($scope.user);
        },        

    }
});