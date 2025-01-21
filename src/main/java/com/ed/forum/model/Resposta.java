package com.ed.forum.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
public class Resposta {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  private String mensagem;

  @CreationTimestamp
  @Column(name = "data_criacao")
  private LocalDateTime dataCriacao;

  @NotBlank
  private Boolean solucao;

  @ManyToOne
  @JoinColumn(name = "autor_id")
  private Usuario autor;

  @ManyToOne
  @JoinColumn(name = "topico_id")
  private Topico topico;

}
