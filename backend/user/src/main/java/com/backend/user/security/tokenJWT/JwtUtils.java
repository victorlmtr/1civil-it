package com.backend.user.security.tokenJWT;

import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Base64;

public class JwtUtils {

    //Decode the key from configuration
    public static SecretKey getSecretKey(String base64EncodedKey) {

        byte[] decodedKey = Base64.getDecoder().decode(base64EncodedKey);

        return Keys.hmacShaKeyFor(decodedKey);
    }
}