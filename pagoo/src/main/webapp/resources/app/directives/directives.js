
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

/**
 * Diretiva para permitir download em base64
 * @see http://blog.techdev.de/an-angularjs-directive-to-download-pdf-files/
 */
app.directive('pdfDownload', function() {
    return {
        restrict: 'E',
        templateUrl: 'resources/templates/downloadTpl.html',
        scope: true,
        link: function(scope, element, attr) {
            var anchor = element.children()[0];
 
            // When the download starts, disable the link
            scope.$on('download-start', function() {
                $(anchor).attr('disabled', 'disabled');
            });
 
            // When the download finishes, attach the data to the link. Enable the link and change its appearance.
            scope.$on('downloaded', function(event, data) {
                $(anchor).attr({
                    href: 'data:application/pdf;base64,' + data,
                    download: attr.filename
                })
                    .removeAttr('disabled')
                    .text('Salvar')
                    .removeClass('btn-primary')
                    .addClass('btn-success');
 
                // Also overwrite the download pdf function to do nothing.
                scope.downloadPdf = function() {
                };
            });
        },
        controller: ['$scope', '$attrs', '$http', function($scope, $attrs, $http) {
            $scope.downloadPdf = function() {
                $scope.$emit('download-start');
                $http.get($attrs.url).then(function(response) {
                    $scope.$emit('downloaded', response.data);
                });
            };
        }] 
    };
 });
