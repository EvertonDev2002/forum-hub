package com.ed.forum.repository;

import com.ed.forum.model.Topico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {

  boolean existsByTituloAndMensagem(String titulo, String mensagem);

  @NonNull
  Optional<Topico> findById(@NonNull Long id);
}
