-- SCRIPT INSERT TIPO SERVICOS
alter table tipo_servico alter column nome type character varying(100)


INSERT INTO tipo_servico(nome, descricao, valor, valor_repasse, id_segmento) VALUES ('Placa Automóvel (Par)', 'Placa Automóvel (Par)', 70.00, 4.00, 1);
INSERT INTO tipo_servico(nome, descricao, valor, valor_repasse, id_segmento) VALUES ('Placa Automóvel (Unidade)', 'Placa Automóvel (Unidade)', 35.00, 4.00, 1);
INSERT INTO tipo_servico(nome, descricao, valor, valor_repasse, id_segmento) VALUES ('Placa Reboque (Unidade)', 'Placa Reboque (Unidade)', 35.00, 4.00, 1);
INSERT INTO tipo_servico(nome, descricao, valor, valor_repasse, id_segmento) VALUES ('Placa Motocicleta (Unidade)', 'Placa Motocicleta (Unidade)', 30.00, 4.00, 1);
INSERT INTO tipo_servico(nome, descricao, valor, valor_repasse, id_segmento) VALUES ('Tarjeta Automóvel (Par)', 'Tarjeta Automóvel (Par)', 20.00, 4.00, 1);
INSERT INTO tipo_servico(nome, descricao, valor, valor_repasse, id_segmento) VALUES ('Tarjeta Automóvel (Unidade)', 'Tarjeta Automóvel (Unidade)', 10.00, 4.00, 1);
INSERT INTO tipo_servico(nome, descricao, valor, valor_repasse, id_segmento) VALUES ('Tarjeta Motocicleta (Unidade)', 'Tarjeta Motocicleta (Unidade)', 08.00, 4.00, 1);
INSERT INTO tipo_servico(nome, descricao, valor, valor_repasse, id_segmento) VALUES ('Placas e Tarjetas Automóvel (Par)', 'Placas e Tarjetas Automóvel (Par)', 90.00, 4.00, 1);
INSERT INTO tipo_servico(nome, descricao, valor, valor_repasse, id_segmento) VALUES ('Placas e Tarjetas Automóvel (Unidade)', 'Placas e Tarjetas Automóvel (Unidade)', 90.00, 4.00, 1);
INSERT INTO tipo_servico(nome, descricao, valor, valor_repasse, id_segmento) VALUES ('Placa e Tarjeta Motocicleta (Unidade)', 'Placa e Tarjeta Motocicleta (Unidade)', 38.00, 4.00, 1);


select * from tipo_servico