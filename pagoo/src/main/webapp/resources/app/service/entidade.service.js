'use strict';

app.factory('EntidadeService', function ($resource) {
    var Entidade = $resource(pagooContextRoot+'/rest/entidades/:id', {
        id: '@id'
    },{
    	getByIdUsuario:{method:'GET', params: {email:'@idusuario'},url: pagooContextRoot+'/rest/entidades/usuario/:idusuario'}
    }); // configura o rest
    return Entidade;

});