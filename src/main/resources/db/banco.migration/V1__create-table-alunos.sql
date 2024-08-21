create table alunos(

                       id bigint not null auto_increment,
                       nome varchar(100) not null,
                       email varchar(100) not null unique,
                       matricula varchar(20) unique ,
                       primary key(id)
);
