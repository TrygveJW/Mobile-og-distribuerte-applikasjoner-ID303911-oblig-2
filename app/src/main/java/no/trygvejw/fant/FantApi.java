package no.trygvejw.fant;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;

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

    private static final String BASE_URL = "http://localhost:8080";

    // -- auth -- //
    private static final String GET_PUBKEY_URL = BASE_URL + "/publickey.pem";
    private static final String CREATE_USER_URL = BASE_URL + "/auth/create";
    private static final String LOGIN_URL = BASE_URL + "/auth/login?uid=%s&pwd=%s";
    private static final String CHANGE_PASS_URL = BASE_URL + "/auth/changepassword";

    // -- api -- //
    private static final String ADD_ITEM_URL = BASE_URL + "/api/add-item";
    private static final String GET_ITEMS_URL = BASE_URL + "/api/items";
    private static final String GET_IMAGE_URL = BASE_URL + "/api/image/%s?width=%i";
    private static final String PURCHASE_ITEM_URL = BASE_URL + "/api/purchase?itemid=%i";

    public static final Response.ErrorListener emptyErrorListener = new Response.ErrorListener() {

        @Override
        public void onErrorResponse(VolleyError error) {

        }
    };


    public FantApi() {
        this.session = new CurrentSession();
    }

    private CurrentSession session;



    public void SendJwtRequest(Request request){
        try {
            request.getHeaders().put("Authorization", CurrentSession.getInstance().);
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

        StringRequest request = new StringRequest(
                Request.Method.PUT,
                LOGIN_URL,
                res_listener,
                errorListener);

    }



    public static void test(){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                response.length();
            }
        }, );



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
