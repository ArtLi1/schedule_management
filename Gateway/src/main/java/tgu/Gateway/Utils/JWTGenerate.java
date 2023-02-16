package tgu.Gateway.Utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTGenerate {

    private final static long Expires = 60*60*1000;


    @Value("${jwt.private.key}")
    private String  privateKey;

    public Algorithm HMAC256(){
        return Algorithm.HMAC256(privateKey);
    }

    public String getToken(String username){
        return JWT.create().withSubject(username).withExpiresAt(new Date(System.currentTimeMillis() + Expires))
                .sign(HMAC256());
    }

    public DecodedJWT decoder(String token){
        JWTVerifier verifier = JWT.require(HMAC256()).build();
        return verifier.verify(token);
    }


}
