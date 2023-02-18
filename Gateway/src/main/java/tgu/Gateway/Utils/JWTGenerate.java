package tgu.Gateway.Utils;

import com.alibaba.fastjson2.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;

@Component
public class JWTGenerate {

    private final static long Expires = 60*60*1000;


    public String getTokenString(String authorization){
        if (!StringUtils.hasText(authorization)) {
            return null;
        }

        boolean valid = authorization.startsWith("Bearer ");
        if (!valid) {
            return null;
        }

        return authorization.replace("Bearer ", "");
    }
    public <T> T StringToObject(Class<T> tClass,Object o){
        return JSON.to(tClass,o);
    }

    public String ObjectToString(Object o){
        return JSON.toJSONString(o);
    }

    @Value("${jwt.private.key}")
    private String  privateKey;

    public Algorithm HMAC256(){
        return Algorithm.HMAC256(privateKey);
    }

    public String getToken(Object user){
        return JWT.create().withSubject(ObjectToString(user)).withExpiresAt(new Date(System.currentTimeMillis() + Expires))
                .sign(HMAC256());
    }

    public boolean verifier(String token){
        JWTVerifier verifier = JWT.require(HMAC256()).build();
        try {
            verifier.verify(token);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public <T> T TokenToObject(Class<T> tClass,String token){
        boolean j = verifier(token);
        if(!j) return null;
        JWTVerifier verifier = JWT.require(HMAC256()).build();
        return StringToObject(tClass,verifier.verify(token).getSubject());
    }


}
