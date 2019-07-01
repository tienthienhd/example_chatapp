package com.tima.ai.example.chat.controllers;

import io.jsonwebtoken.*;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

public class AuthenticateController extends Controller {

    private static String secret_key = "d5KyiTft1VTnQpZhqOaPnhNLEeTfKUv4rN71UI103UbIGaEkqDDrtaw8wpOJiVI1";
    private long ttlMillis = 24*60*60*1000;

    public AuthenticateController() {
        super();
    }

    public boolean checkAuth(String token){
        Claims claims = this.decodeJWT(token);
        String username = claims.getId();
        long expiration = claims.getExpiration().getTime();
        long now = System.currentTimeMillis();
        if (expiration - now <= 0){
            return false;
        }
        String tokendb = Controller.mongoCon.getToken(username);
        if(token.equals(tokendb)){
            return true;
        }
        return false;
    }

    public String getAddress(String username){
        return Controller.mongoCon.getAddress(username);
    }

    public boolean updateAddress(String username, String address){
        return Controller.mongoCon.updateAddress(username, address);
    }

    public String login(String username, String password){
        boolean done = Controller.mongoCon.login(username, password);
        if(!done){
            return null;
        }
        String token = this.createJWT(username);
        Controller.mongoCon.updateToken(token, username);
        return token;
    }

    public boolean logout(String username){
        return Controller.mongoCon.logout(username);
    }

    public boolean signUp(String username, String password){
        boolean done = Controller.mongoCon.createAccount(username, password);
        return done;
    }

    private String createJWT(String username){
        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);



        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secret_key);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder().setId(username)
                .setIssuedAt(now)
                .signWith(signingKey, signatureAlgorithm);
        //if it has been specified, let's add the expiration
        if (ttlMillis > 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

    public Claims decodeJWT(String jwt) {
        //This line will throw an exception if it is not a signed JWS (as expected)
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(secret_key))
                .parseClaimsJws(jwt).getBody();
//        System.out.println("ID: " + claims.getId());
//        System.out.println("Expiration: " + claims.getExpiration());
        return claims;
    }

}
