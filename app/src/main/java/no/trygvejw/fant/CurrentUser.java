package no.trygvejw.fant;

import android.view.View;

import androidx.navigation.Navigation;

import com.android.volley.Request;
import com.android.volley.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

import no.trygvejw.fant.api.GsonRequest;
import no.trygvejw.fant.api.VolleyHttpQue;
import no.trygvejw.fant.items.ItemDB;
import no.trygvejw.fant.items.SaleItem;
import no.trygvejw.fant.items.User;

public class CurrentUser {

    private boolean isLoggedIn = false;
    private User user;
    private String jwt;


    private static CurrentUser instance = null;

    public static CurrentUser getInstance(){
        if (instance == null){
            instance = new CurrentUser();

        }

        return instance;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void updateUserFromServer(){
        GsonRequest<User> gsonRequest = new GsonRequest(
                FantApi.CURRENT_USER_URL,
                Request.Method.GET,
                User.class,
                getAuthHeaders(),
                new Response.Listener<User>() {
                    @Override
                    public void onResponse(User response) {
                        user = response;
                        System.out.println(user.toString());

                    }

                },
                FantApi.emptyErrorListener
        );


        VolleyHttpQue.instance().addToRequestQue(gsonRequest);
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public HashMap<String,String> getAuthHeaders(){
        HashMap<String, String> ret = new HashMap<>();

        ret.put("Authorization", "Bearer " + jwt);
        return ret;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
