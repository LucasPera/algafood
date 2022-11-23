insert into cozinha (id,nome) values (1,'Tailandesa')
insert into cozinha (id,nome) values (2,'Indiana')

insert into restaurante (nome, taxa_frete, cozinha_id) values ('Peras thai', 10.0, 1)
insert into restaurante (nome, taxa_frete, cozinha_id) values ('thai Burguer', 15.0, 1)
insert into restaurante (nome, taxa_frete, cozinha_id) values ('Indian rai rai', 15.0, 2)

insert into forma_pagamento (descricao) values ("Debito")
insert into forma_pagamento (descricao) values ("Credito")

insert into permissao (id, nome, descricao) values (1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
insert into permissao (id, nome, descricao) values (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');

insert into estado (id, nome) values (1, "São Paulo")
insert into estado (id, nome) values (2, "Bahia")
insert into estado (id, nome) values (3, "Rio de Janeiro")

insert into cidade (nome, estado_id) values ("Jundiaí", 1)
insert into cidade (nome, estado_id) values ("Carapicuíba", 1)
insert into cidade (nome, estado_id) values ("Salvador", 2)
insert into cidade (nome, estado_id) values ("Vitória da Conquista", 2)