
create table tab_carros (
	carro_preco decimal(38,2), 
	carro_data_atualizacao datetime, 
	carro_data_criacao datetime, 
	carro_id bigint not null auto_increment, 
	carro_km bigint, 
	carro_usuario_id bigint not null, 
	carro_ano varchar(10), 
	carro_cidade varchar(50), 
	carro_codigo varchar(100), 
	carro_nome varchar(100) not null, 
	carro_status enum ('A_VENDA','EM_NEGOCIACAO','VENDIDO'), 
	primary key (carro_id)
) engine=InnoDB;


create table tab_fotos (
	foto_carro bigint not null, 
	foto_id bigint not null auto_increment, 
	foto_codigo varchar(100), 
	foto_nome varchar(255) not null, 
	foto_url varchar(255) not null, 
	foto_image mediumblob not null, 
	primary key (foto_id)
) engine=InnoDB;


create table tab_usuarios (
	data_cadastro datetime not null,
	usuario_id bigint not null auto_increment,
	email varchar(100) not null,
	nome varchar(100) not null,
	senha varchar(50) not null,
	primary key (usuario_id)
) engine=InnoDB;


alter table tab_carros add constraint FK_carro_usuario foreign key (carro_usuario_id) references tab_usuarios (usuario_id);
alter table tab_fotos add constraint FK_foto_carro foreign key (foto_carro) references tab_carros (carro_id);
