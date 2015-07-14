'use strict';
/* SERVICES RELACIONADOS A CONSULTA COBRANCA*/

// Service que retorna Tipos de Servicos a serem cobrados. Alterar para outro arquivo JS quando necessario.
app.factory('ConsultaCobrancaService', function ($resource) {
    var ConsultaCobranca = $resource(pagooContextRoot+'/rest/cobranca/entidade/:idEntidade', {
    	idEntidade: '@idEntidade'
    },{
    	queryByIdEntidade:{method:'POST', param:{idEntidade:'@idEntidade'},  isArray:true,url: pagooContextRoot+'/rest/cobranca/entidade/:idEntidade'}
    }); // configura o rest
    return ConsultaCobranca;

});