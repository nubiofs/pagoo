'use strict';

app.factory('SegmentoService', function ($resource) {
    var Segmento = $resource(pagooContextRoot+'/rest/segmentos/:id', {
        id: '@id'
    },{
    	getByIdUsuario:{method:'GET', params: {idusuario:'@idusuario'},url: pagooContextRoot+'/rest/segmentos/usuario/:idusuario'}
    }); // configura o rest
    return Segmento;

});