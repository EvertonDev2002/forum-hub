package com.ed.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.ed.forum.dto.LoginDTO;
import com.ed.forum.dto.TokenDTO;
import com.ed.forum.service.TokenService;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private TokenService tokenService;

  @PostMapping
  public ResponseEntity<TokenDTO> authenticate(@RequestBody LoginDTO loginDTO) {
    try {
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
      SecurityContextHolder.getContext().setAuthentication(authentication);

      TokenDTO tokenDTO = tokenService.gerarToken(loginDTO.getEmail());
      return ResponseEntity.ok(tokenDTO);

    } catch (AuthenticationException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
  }
}
