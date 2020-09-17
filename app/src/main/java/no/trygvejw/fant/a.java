package no.trygvejw.fant;

import io.jsonwebtoken.Jwts;
import no.trygvejw.fant.api.Token;
import no.trygvejw.fant.items.User;

public class a {

    private static a instance = null;


    public static a getInstance(){
        if (instance == null){
            instance = new a();
        }

        return instance;

    }

    private a(){
        this.token = new Token();
    }

    private Token token;
    private boolean isLoggedIn = false;
    private User user;

}
