package no.trygvejw.fant;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.Arrays;

public class FantApi {


    public static final String BASE_URL = "https://trygvejw-fant.uials.no";

    // -- auth -- //
    public static final String GET_PUBKEY_URL = BASE_URL + "/publickey.pem";
    public static final String CREATE_USER_URL = BASE_URL + "/auth/create";
    public static final String LOGIN_URL = BASE_URL + "/auth/login?uid=%s&pwd=%s";
    public static final String CHANGE_PASS_URL = BASE_URL + "/auth/changepassword";
    public static final String CURRENT_USER_URL = BASE_URL + "/auth/currentuser";

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
}
