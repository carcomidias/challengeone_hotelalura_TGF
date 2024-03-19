CREATE DATABASE hotel_alura_br;

use hotel_alura_br;

create table reservas (
id int not null auto_increment,
data_entrada date not null,
data_saida date not null,
forma_de_pag varchar(50) not null,
valor varchar(50),
primary key (id)
);

create table hospedes (
id int not null auto_increment,
nome varchar(50) not null,
sobrenome varchar(50) not null,
data_nascimento date not null,
nacionalidade varchar (50) not null,
telefone varchar (50) not null,
id_reserva int not null,
primary key (id),
foreign key (id_reserva) references reservas(id)
);

create table usuarios (
nome varchar(50),
senha varchar(50)
);

insert into usuarios (nome, senha) values ('fabiolopes','123456');
insert into usuarios (nome, senha) values ('admin','admin');

ALTER TABLE hospedes
ADD CONSTRAINT fk_hospede_reserva
FOREIGN KEY (id_reserva)
REFERENCES reservas(id)
ON DELETE CASCADE;