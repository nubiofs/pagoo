//definir globalmente context root.
var pagooContextRoot = '/pagoo';

var app = angular.module('pagoo', [
                                         
	'ui.router', 
	'ngResource', 
	'ui.bootstrap',
	'ui.utils.masks',
	'ngMask',
	'smart-table',
	'ui.bootstrap.collapse', 
	'ui.bootstrap.dropdown',
	'ui.bootstrap.typeahead',
	'ui.bootstrap.tpls',
	'dialogs.main',
	'idf.br-filters',
	'ngSanitize'


]);
