package no.trygvejw.fant;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;

import java.util.Arrays;

import no.trygvejw.fant.api.Token;
import no.trygvejw.fant.api.VolleyHttpQue;
import no.trygvejw.fant.items.User;

public class FantApi {

    private static FantApi instance = null;

    public static FantApi instance(){
        if (instance == null){
            instance = new FantApi();
        }
        return instance;
    }

    public static final String BASE_URL = "http://192.168.0.168:8080";

    // -- auth -- //
    public static final String GET_PUBKEY_URL = BASE_URL + "/publickey.pem";
    public static final String CREATE_USER_URL = BASE_URL + "/auth/create";
    public static final String LOGIN_URL = BASE_URL + "/auth/login?uid=%s&pwd=%s";
    public static final String CHANGE_PASS_URL = BASE_URL + "/auth/changepassword";

    // -- api -- //
    public static final String ADD_ITEM_URL = BASE_URL + "/api/add-item";
    public static final String GET_ITEMS_URL = BASE_URL + "/api/items";
    public static final String GET_IMAGE_URL = BASE_URL + "/api/image/%s?width=%s";
    public static final String PURCHASE_ITEM_URL = BASE_URL + "/api/purchase?itemid=%s";

    public static final Response.ErrorListener emptyErrorListener = new Response.ErrorListener() {

        @Override
        public void onErrorResponse(VolleyError error) {
            error.printStackTrace();
            System.out.printf("HTTP ERROR: {}", error.getMessage());
            if (error.networkResponse != null){
                System.out.printf("\n{}\n", Arrays.toString(error.networkResponse.data));
            }


        }
    };


    public FantApi() {
        this.session = new CurrentSession();
    }

    private CurrentSession session;



    public void SendJwtRequest(Request request){
        try {
            request.getHeaders().put("Authorization", this.session.token);
        } catch (AuthFailureError e){e.printStackTrace();}

    }

    public void getPubKey(
            Response.Listener<String> res_listener,
            Response.ErrorListener errorListener){

        StringRequest request = new StringRequest(
                Request.Method.GET,
                GET_PUBKEY_URL,
                res_listener,
                errorListener);

        VolleyHttpQue.instance().addToRequestQue(request);
    }


    public boolean createUser(
            String username,
            String password,
            String mail,
            String name,
            Response.Listener<String> res_listener,
            Response.ErrorListener errorListener){


        StringRequest request = new StringRequest(
                Request.Method.PUT,
                CREATE_USER_URL,
                res_listener,
                errorListener);

        return true;
    }

    public void logInn(
            String username,
            String password,
            Response.Listener<String> res_listener,
            Response.ErrorListener errorListener){



    }




    private static class CurrentSession {
        private CurrentSession(){
            this.token = new Token();
        }

        public Token token;
        public boolean isLoggedIn = false;
        public User user;
    }

}
