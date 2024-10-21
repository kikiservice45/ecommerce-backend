package ecommerce.springbootecommerce.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.io.Decoders;
import org.springframework.stereotype.Component;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    private static final String SECRET = "S3cr3tK3yForJWT@2024!WithSpecialChar$AndNumber12345";  // Example of a secure secret key

    public String generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userName);
    }

    private String createToken(Map<String, Object> claims, String userName) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)  // Set the username as the subject of the token
                .setIssuedAt(new Date(System.currentTimeMillis()))  // Token creation time
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))  // Set token expiration to 10 hours
                .signWith(getSignKey(), SignatureAlgorithm.HS256)  // Sign the token with the secret key
                .compact();  // Compact the JWT to a URL-safe string
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);  // Corrected method name
    }

    public String extractUsername(String token) {

        return exctractClaim(token, Claims::getSubject);
    }

    private <T>  T exctractClaim(String token, Function<Claims, T> claimResolver ){
    final  Claims claims =exctractAllClaims(token);
    return claimResolver.apply(claims);


    }

    private Claims exctractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
    }

    private boolean isTokenExpired(String token) {

        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return exctractClaim(token, Claims::getExpiration);
    }

    public boolean validateToken(String token, UserDetails userDetails)
    {final String username =extractUsername(token);
        return (username.equals(userDetails.getUsername()) && isTokenExpired(token));

}}