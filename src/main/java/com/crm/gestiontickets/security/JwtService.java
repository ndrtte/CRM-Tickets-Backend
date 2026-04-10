package com.crm.gestiontickets.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    public String generarToken(String usuario) {
        return Jwts.builder()
                .setSubject(usuario) 
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration)) 
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                .compact();
    }

    public String extraerUsuario(String token) {
        return extraerTodosLosClaims(token).getSubject();
    }

    public boolean esTokenValido(String token, UserDetails userDetails) {

        final String usuario = extraerUsuario(token);

        return usuario.equals(userDetails.getUsername())
                && !estaExpirado(token);
    }

    private boolean estaExpirado(String token) {
        return extraerTodosLosClaims(token)
                .getExpiration()
                .before(new Date());
    }

    private Claims extraerTodosLosClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private <T> T extraerClaim(String token, java.util.function.Function<Claims, T> claimsResolver) {
        Claims claims = extraerTodosLosClaims(token);
        return claimsResolver.apply(claims);
    }

    public String extraerAuthorities(String token) {
        return extraerClaim(token, claims -> claims.get("authorities", String.class));
    }
}

