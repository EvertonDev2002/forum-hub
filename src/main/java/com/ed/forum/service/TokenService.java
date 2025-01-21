package com.ed.forum.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.ed.forum.dto.TokenDTO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.expiration}")
  private long expiration;

  public TokenDTO gerarToken(String email) {
    String token = JWT.create()
        .withSubject(email)
        .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
        .sign(Algorithm.HMAC256(secret));
    return new TokenDTO(token, "Bearer");
  }

  public boolean validarToken(String token) {
    if (token == null || !token.startsWith("Bearer ")) {
      return false;
    }

    try {
      String tokenSemPrefixo = token.replace("Bearer ", "");
      JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
      DecodedJWT decodedJWT = verifier.verify(tokenSemPrefixo);

      System.out.println("Token válido: " + decodedJWT.getSubject());
      return true;

    } catch (JWTVerificationException exception) {

      System.err.println("Falha ao validar token: " + exception.getMessage());
      return false;
    }
  }
}
