
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Jwts;
import java.security.Key;
import java.util.Date;



@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;



    public String generateToken(String name){

        return Jwts.builder()
                .setSubject(name)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignKey() {

        return Keys.hmacShaKeyFor(secret.getBytes());

    }

}