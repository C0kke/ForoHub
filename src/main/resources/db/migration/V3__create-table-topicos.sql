CREATE TABLE topicos(
    id bigint not null auto_increment,
    titulo varchar(100) unique not null,
    mensaje varchar(200) unique not null,
    fecha_creacion date not null,
    estado varchar(100) not null,
    id_autor bigint not null,
    id_curso bigint not null,

    primary key(id),
    constraint topicos_id_autor foreign key(id_autor) references usuarios(id),
    constraint topicos_id_curso foreign key(id_curso) references cursos(id)
);