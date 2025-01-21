package com.ed.forum.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ed.forum.model.Topico;
import com.ed.forum.repository.TopicoRepository;
import java.util.List;
import java.util.Optional;

@Service
public class TopicoService {
  private final TopicoRepository topicoRepository;

  public TopicoService(TopicoRepository topicoRepository) {
    this.topicoRepository = topicoRepository;
  }

  @Transactional
  public Topico salvar(Topico topico) {
    if (topicoRepository.existsByTituloAndMensagem(topico.getTitulo(), topico.getMensagem())) {
      throw new IllegalArgumentException("Tópico duplicado: título e mensagem já existem.");
    }
    return topicoRepository.save(topico);
  }

  public List<Topico> listarTodos() {
    return topicoRepository.findAll();
  }

  public Optional<Topico> buscarPorId(Long id) {
    return topicoRepository.findById(id);
  }

  @Transactional
  public void deletar(Long id) {
    if (!topicoRepository.existsById(id)) {
      throw new IllegalArgumentException("Tópico não encontrado.");
    }
    topicoRepository.deleteById(id);
  }
}