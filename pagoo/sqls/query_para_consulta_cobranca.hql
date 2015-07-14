select s from Servico s
inner join fetch s.tipoServico tpsrv
inner join fetch s.evento as ev
inner join fetch ev.comprador as compr
