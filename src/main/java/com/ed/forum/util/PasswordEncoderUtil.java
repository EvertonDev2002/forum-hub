package com.ed.forum.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// Apenas para criar uma senhha criptografada
public class PasswordEncoderUtil {
  public static void main(String[] args) {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    String rawPassword = "123";
    String encodedPassword = encoder.encode(rawPassword);
    System.out.println(encodedPassword);
  }
}
