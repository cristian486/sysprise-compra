create table compra(
    id bigint not null auto_increment,
    documento varchar(200) not null unique,
    observacao varchar(500),
    data_de_criacao timestamp,
    data_de_recebimento date,
    status varchar(30) not null,
    habilitado boolean,
    fornecedor_id bigint,
    constraint comprapk primary key(id)
);


create table item_compra(
    id bigint not null auto_increment,
    compra_id bigint,
    produto_id bigint,
    quantidade numeric(8, 3),
    constraint item_comprapk primary key(id),
    constraint item_compra_comprafk foreign key(compra_id) references compra(id)
);