package com.ed.forum.service;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ed.forum.model.Usuario;
import com.ed.forum.repository.UsuarioRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Usuario usuario = usuarioRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    return new org.springframework.security.core.userdetails.User(
        usuario.getEmail(),
        usuario.getSenha(),
        usuario.getPerfis().stream()
            .map(perfil -> new SimpleGrantedAuthority(perfil.getNome()))
            .collect(Collectors.toList()));
  }
}
