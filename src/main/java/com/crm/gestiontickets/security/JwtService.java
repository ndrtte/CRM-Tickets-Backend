package com.crm.gestiontickets.security;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.crm.gestiontickets.agente.entity.Agente;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expiration;

    private Key getSignKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String generarToken(Agente agente) {
        return Jwts.builder()
                .setSubject(agente.getUsuario())
                .claim("idAgente", agente.getIdAgente())
                .claim("rol", agente.getRol().getNombre())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extraerUsuario(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean esTokenValido(String token, String usuario) {
        String usuarioToken = extraerUsuario(token);
        return usuarioToken.equals(usuario) && !esTokenExpirado(token);
    }

    private boolean esTokenExpirado(String token) {
        Date expiracion = Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();

        return expiracion.before(new Date());
    }
}
