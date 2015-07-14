select servico from Servico as servico
join fetch servico.tipoServico tpsrv
join fetch servico.evento as ev
join fetch ev.entidade as ent
join fetch ev.comprador as compr
join fetch ev.cobrancas as cob
where 0=0
and ent.id = 1
and compr.nome like '%A%'
and compr.cpgcnpj= '89507274120'
and servico.placa = 'ASD1231'
and tpsrv.id = 10