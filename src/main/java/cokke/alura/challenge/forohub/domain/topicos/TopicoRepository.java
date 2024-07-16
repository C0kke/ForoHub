package cokke.alura.challenge.forohub.domain.topicos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {
    Optional<Topico> findByTitulo(String titulo);

    Page<Topico> findTop10ByOrderByFechaCreacionAsc(Pageable paginacion);

    @Query("""
        select t from Topico t
        join Curso c
        on c.id = t.curso.id
        where c.id = :nombre
    """)
    Page<Topico> encontrarPorNombreCurso(String nombre, Pageable paginacion);
}
