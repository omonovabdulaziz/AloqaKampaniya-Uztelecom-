package uz.pdp.aloqakampaniyasi.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import uz.pdp.aloqakampaniyasi.entity.Role;

import java.util.Date;
import java.util.Set;

@Component
public class JwtProvider {
    private final String maxfiySoz = "tokenUchun123";
    private final long expiredTime = 1000L * 60 * 60 * 24 * 100;

    public String generateToken(String username, Set<Role> roles) {
        Date date = new Date(System.currentTimeMillis() + expiredTime);
        return Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(date)
                .claim("roles", roles)
                .signWith(SignatureAlgorithm.HS512, maxfiySoz)
                .compact();
    }

    public String getEmailFromToken(String token) {
        try {
            String email = Jwts
                    .parser()
                    .setSigningKey(maxfiySoz)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            return email;
        } catch (Exception e) {
            return null;
        }
    }
}
