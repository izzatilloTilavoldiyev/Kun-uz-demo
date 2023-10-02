package com.company.kunuzdemo.config.jwt;

import com.company.kunuzdemo.dtos.response.TokenDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.access.token.secret.key}")
    private String secretKey;

    @Value("${jwt.access.token.expiry}")
    private long accessTokenExpiry;

    @Value("${jwt.refresh.token.expiry}")
    private long refreshTokenExpiry;

    public TokenDTO generateToken(@NotNull String username) {
        return TokenDTO.builder()
                .accessToken(generateAccessToken(username))
                .accessTokenExpire(new Date(System.currentTimeMillis() + accessTokenExpiry))
                .refreshToken(generateRefreshToken(username))
                .refreshTokenExpire(new Date(System.currentTimeMillis() + refreshTokenExpiry))
                .build();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateAccessToken(@NotNull String  username) {
        return generateAccessToken(new HashMap<>(), username);
    }

    public String generateAccessToken(
            Map<String, Object> extraClaims,
            @NotNull String username
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + accessTokenExpiry))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(@NotNull String username) {
        return Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(new Date().getTime() + refreshTokenExpiry))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, String username) {
        final String currentUsername = extractUsername(token);
        return (currentUsername.equals(username)) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String getUsername(@NotNull String token) {
        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
