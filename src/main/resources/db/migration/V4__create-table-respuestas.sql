CREATE TABLE respuestas(
    id bigint not null auto_increment,
    mensaje varchar(100) not null,
    id_topico bigint not null,
    fecha_creacion DATE not null,
    id_autor bigint not null,
    solucion varchar(100) not null,

    primary key(id),
    constraint respuestas_id_topico foreign key(id_topico) references cursos(id),
    constraint respuestas_id_autor foreign key(id_autor) references usuarios(id)
)