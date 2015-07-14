INSERT INTO usuario(
            id, id_perfil, id_entidade, cpfcnpj, email, endereco, complemento, 
            cidade, estado, cep, telefone, celular, data_inclusao, senha)
    VALUES (?, ?, ?, ?, ?, ?, 
            ?, ?, ?, ?, ?, ?, ?);

insert into segmento(nome,descricao) values('FABRIPLACAS','Fabricante de Placas') 
select * from segmento

INSERT INTO entidade(id_segmento, cpfcnpj,       email_responsavel,    endereco,         complemento, cidade,    estado, cep, telefone, celular, data_inclusao)
             VALUES (1,          '69827931172', 'davidfdr@gmail.com', 'SQN 212 BL A 214', '214',      'Brasília', 'DF' ,  '70864010', '6133236808', '6191538042', NOW());
select * from entidade
insert into perfil(nome) values('Associacao') 

INSERT INTO usuario(id_perfil, id_entidade, cpfcnpj    , email,            endereco, complemento, cidade, estado, cep, telefone, celular, data_inclusao, senha)
    VALUES         (1        , 1          , 69827931172, 'david.reis@infosolo.com.br', 'SQN 212 BL A 214', '214',      'Brasília', 'DF' ,  '70864010', '6133236808', '6191538042', NOW(), 'wodmkv12');

    INSERT INTO usuario(id_perfil, id_entidade, cpfcnpj    , email,            endereco, complemento, cidade, estado, cep, telefone, celular, data_inclusao, senha)
    VALUES         (2        , 1          , 66872474505, 'david.reis@infosolo.com.br', 'SQN 212 BL A 214', '214',      'Brasília', 'DF' ,  '70864010', '6133236808', '6191538042', NOW(), 'wodmkv12');

select * from usuario
