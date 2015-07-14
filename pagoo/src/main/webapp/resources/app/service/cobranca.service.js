'use strict';



/* SERVICES RELACIONADOS A COBRANCA*/

// Service que retorna Tipos de Servicos a serem cobrados. Alterar para outro arquivo JS quando necessario.
app.factory('TipoServicoService', function ($resource) {
    var TipoServico = $resource(pagooContextRoot+'/rest/tipoServico/servicosDoSegmento/:idSegmento/:idServico', {
        idSegmento: '@idSegmento', idServico: '@idServico'
    }); // configura o rest
    return TipoServico;

});

// Service resource relacionado à cobranca.
app.factory('CobrancaService', function ($resource) {
    var Cobranca = $resource(pagooContextRoot+'/rest/cobranca/:id', {
        id: '@id'
    }); // configura o rest
    return Cobranca;

});