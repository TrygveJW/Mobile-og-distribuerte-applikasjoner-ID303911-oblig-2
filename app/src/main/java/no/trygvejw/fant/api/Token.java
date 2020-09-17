package no.trygvejw.fant.api;

import android.net.sip.SipSession;
import android.util.Base64;

import com.android.volley.Response;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.RSAKeyProvider;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.concurrent.Semaphore;

import no.trygvejw.fant.FantApi;

public class Token {


    private JWTVerifier verifier;

    private String currentToken;


    public Token() {

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    System.out.println(response);
                    X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(response.getBytes());
                    System.out.println(pubKeySpec.getFormat());
                    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                    RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(pubKeySpec);

                    //RSAPublicKey publicKey = RSAPublicKeySpec
                    Algorithm algorithm = Algorithm.RSA256(publicKey, null);
                    verifier = JWT.require(algorithm).build();
                } catch (InvalidKeySpecException | NoSuchAlgorithmException e){}
            }
        };

        FantApi.instance().getPubKey(listener, FantApi.emptyErrorListener);
    }

    public boolean isCurrentTokenValid(){
        try {
            verifier.verify(currentToken);
            return true;
        } catch (JWTVerificationException e){
            return false;
        }
    }

    public String getCurrentToken() {
        return currentToken;
    }

    public void setCurrentToken(String currentToken) {
        this.currentToken = currentToken;
    }
}
