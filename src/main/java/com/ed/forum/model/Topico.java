package com.ed.forum.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Data
public class Topico {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @NotBlank
  private String titulo;

  @NotBlank
  private String mensagem;

  @CreationTimestamp
  @Column(name = "data_criacao")
  private LocalDateTime dataCriacao;

  @NotBlank
  private String status;

  @ManyToOne
  @JoinColumn(name = "autor_id")
  private Usuario autor;

  @ManyToOne
  @JoinColumn(name = "curso_id")
  private Curso curso;

  @OneToMany(mappedBy = "topico")
  private List<Resposta> respostas = new ArrayList<>();
}
