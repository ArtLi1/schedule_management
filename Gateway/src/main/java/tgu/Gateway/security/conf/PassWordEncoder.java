package tgu.Gateway.security.conf;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;


@Component
public class PassWordEncoder implements PasswordEncoder {

    private String salt = "";

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getSalt() {
        return salt;
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return DigestUtils.md5DigestAsHex((salt + rawPassword.toString()).getBytes());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encode(rawPassword).equals(encodedPassword);
    }
}
