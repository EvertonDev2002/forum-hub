package com.ed.forum.controller;

import com.ed.forum.model.Topico;
import com.ed.forum.service.TopicoService;
import com.ed.forum.service.TokenService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
@Validated
public class TopicoController {

    private final TopicoService topicoService;
    private final TokenService tokenService;

    public TopicoController(TopicoService topicoService, TokenService tokenService) {
        this.topicoService = topicoService;
        this.tokenService = tokenService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Topico> criarTopico(@RequestBody @Valid Topico topico,
            @RequestHeader("Authorization") String token) {
        if (!tokenService.validarToken(token)) {
            return ResponseEntity.status(401).build();
        }
        Topico salvo = topicoService.salvar(topico);
        return ResponseEntity.status(201).body(salvo);
    }

    @GetMapping
    public ResponseEntity<List<Topico>> listarTopicos() {
        List<Topico> topicos = topicoService.listarTodos();
        return ResponseEntity.ok(topicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Topico> detalharTopico(@PathVariable @Min(1) Long id) {
        Optional<Topico> topico = topicoService.buscarPorId(id);
        return topico.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarTopico(@PathVariable Long id) {
        Optional<Topico> topico = topicoService.buscarPorId(id);
        if (topico.isPresent()) {
            topicoService.deletar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
        String mensagem = "Erro de validação: " + ex.getMessage();
        return ResponseEntity.badRequest().body(mensagem);
    }
}
