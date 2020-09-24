package no.trygvejw.fant.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import no.trygvejw.fant.CurrentUser;
import no.trygvejw.fant.FantApi;
import no.trygvejw.fant.R;
import no.trygvejw.fant.api.GsonRequest;
import no.trygvejw.fant.api.VolleyHttpQue;
import no.trygvejw.fant.items.User;


public class CreateUserFragment extends Fragment {

    TextView username;
    TextView name;
    TextView email;
    TextView password;
    Button submit;

    ProgressBar progressBar;
    TextView status;




    public CreateUserFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        menu.clear();
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_create_user, container, false);

        FloatingActionButton fab = container.getRootView().findViewById(R.id.fab);
        fab.hide();

        username = root.findViewById(R.id.new_user_username);
        name = root.findViewById(R.id.new_user_name);
        email = root.findViewById(R.id.new_user_user_mail);
        password = root.findViewById(R.id.new_user_user_password);
        submit = root.findViewById(R.id.new_user_submitt_btn);
        progressBar = root.findViewById(R.id.loading);
        status = root.findViewById(R.id.new_user_error_msg);


        submit.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            sendCreateUserRequest();
        });







        return root;
    }

    private void sendCreateUserRequest(){


        final HashMap<String,String> jobj = new HashMap<>();
        jobj.put("uid", username.getText().toString());
        jobj.put("pwd", password.getText().toString());
        jobj.put("mail", email.getText().toString());
        jobj.put("name", name.getText().toString());
        GsonRequest<User> gsonRequest = new GsonRequest(
                FantApi.CREATE_USER_URL,
                Request.Method.POST,
                User.class,
                new HashMap<String, String>(),
                new Response.Listener<User>() {
                    @Override
                    public void onResponse(User response) {
                        progressBar.setVisibility(View.INVISIBLE);
                        CurrentUser.getInstance().setUser(response);
                        Navigation.findNavController(getView()).popBackStack();
                    }

                },
                new Response.ErrorListener(){

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.INVISIBLE);
                        status.setText("error you did somthing wrong");

                    }
                }
        ){
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return jobj;
            }
        };


        VolleyHttpQue.instance().addToRequestQue(gsonRequest);



    }

}