package com.ed.forum.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ed.forum.model.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {
}
