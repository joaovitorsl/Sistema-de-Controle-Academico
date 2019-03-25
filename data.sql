CREATE TABLE PESSOA(
	NOME VARCHAR(50),
    CPF VARCHAR(11),
    DATA_NASC DATE,
    MATRICULA INT NOT NULL PRIMARY KEY,
    TELEFONE VARCHAR(20),
    EMAIL VARCHAR(50),
    SENHA VARCHAR(30)
);

CREATE TABLE ALUNO(
    MATRICULA INT NOT NULL PRIMARY KEY,
	fk_turma integer
)INHERITS(PESSOA);

CREATE TABLE FUNCIONARIO(
	SALARIO FLOAT,
    CARGA_H INTEGER
)INHERITS(PESSOA);

CREATE TABLE PROFESSOR(
    MATRICULA INT NOT NULL PRIMARY KEY,
	AREA VARCHAR(30),
    NIVEL VARCHAR(30) 
)INHERITS(FUNCIONARIO);

CREATE TABLE TECNICO(
    MATRICULA INT NOT NULL PRIMARY KEY,
	SETOR VARCHAR(30)
)INHERITS(FUNCIONARIO);

CREATE TABLE TURMA(
	CODIGO INT NOT NULL PRIMARY KEY,
    NOME VARCHAR(40)
);

ALTER TABLE ALUNO
	ADD CONSTRAINT FK_TURMA FOREIGN KEY (fk_turma) REFERENCES TURMA (CODIGO);

create table diarios( cod_dos_diarios int not null primary key);

create table diario(
    codigo int not null primary key,
    disciplina varchar(15),
    professor varchar(30),
    fk_aluno integer,
    fk_turma integer,
    fk_notas integer
    );
	
create table notas(
	codigo int not null primary key,
    mat integer,
    B1 float,
    B2 float,
    B3 float,
    B4 float
);

alter table diario add constraint fk_turma foreign key(fk_turma) references turma (codigo);
alter table diario add constraint fk_aluno foreign key(fk_aluno) references ALUNO (MATRICULA);
alter table diario add constraint fk_notas foreign key(fk_notas) references notas (codigo);
alter table NOTAS add constraint fk_mat foreign key (mat) references ALUNO (MATRICULA);

create table funcao(
	id int not null primary key,
	nome_func varchar(20)
);

alter table pessoa add column id_func integer;
alter table pessoa add constraint fk_func foreign key(id_func) references funcao (id);

insert into funcao values(1, 'ALUNO');
insert into funcao values(2, 'PROFESSOR');
insert into funcao values(3, 'TÉCNICO');