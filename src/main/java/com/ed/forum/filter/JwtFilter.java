package com.ed.forum.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

  @Value("${jwt.secret}")
  private String secret;

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain) throws ServletException, IOException {
    String token = request.getHeader("Authorization");
    if (token != null && token.startsWith("Bearer ")) {
      token = token.replace("Bearer ", "");
      try {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(secret))
            .build()
            .verify(token);

        String email = decodedJWT.getSubject();
        if (email != null) {
          var authentication = new UsernamePasswordAuthenticationToken(email, null, null);
          SecurityContextHolder.getContext().setAuthentication(authentication);
        }
      } catch (Exception e) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Unauthorized");
        return;
      }
    }
    System.out.println("Token ausente ou inválido. Liberando a requisição...");
    filterChain.doFilter(request, response);
  }
}
