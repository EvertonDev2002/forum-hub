package com.ed.forum.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

  private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);
  @Value("${jwt.secret}")
  private String secret;

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain) throws ServletException, IOException {
    String token = request.getHeader("Authorization");

    if (token == null || !token.startsWith("Bearer ")) {
      logger.debug("Token ausente ou inválido. Liberando a requisição...");
      filterChain.doFilter(request, response);
      return;
    }

    token = token.substring(7);

    try {

      DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(secret))
          .build()
          .verify(token);

      String email = decodedJWT.getSubject();
      if (email != null) {

        logger.debug("Token validado para o usuário: {}", email);

        var authentication = new UsernamePasswordAuthenticationToken(email, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    } catch (JWTVerificationException e) {

      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.getWriter().write("Unauthorized: Invalid or expired token");
      return;
    } catch (Exception e) {
      logger.error("Erro ao validar token JWT: {}", e.getMessage());
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      response.getWriter().write("Internal Server Error");
      return;
    }

    filterChain.doFilter(request, response);
  }
}
