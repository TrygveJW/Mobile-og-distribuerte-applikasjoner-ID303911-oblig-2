package no.trygvejw.fant.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import no.trygvejw.fant.CurrentUser;
import no.trygvejw.fant.FantApi;
import no.trygvejw.fant.R;
import no.trygvejw.fant.api.VolleyHttpQue;


public class LoginFragment extends Fragment {

    TextView username_view;
    TextView password_view;
    TextView status_view;

    Button submit_btn;

    ProgressBar progressBar;


    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        menu.clear();
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FloatingActionButton fab = container.getRootView()
                                            .findViewById(R.id.fab);
        fab.hide();

        View root = inflater.inflate(R.layout.fragment_login, container, false);

        username_view = root.findViewById(R.id.login_user_mail);
        password_view = root.findViewById(R.id.login_user_password);
        status_view   = root.findViewById(R.id.login_status);
        submit_btn    = root.findViewById(R.id.login_submitt_btn);
        progressBar   = root.findViewById(R.id.loading);

        submit_btn.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);

            sendLoginRequest(username_view.getText()
                                          .toString(), password_view.getText()
                                                                    .toString());
        });


        return root;
    }

    public void sendLoginRequest(String username,
                                 String pass) {
        Response.Listener<String> stringListener = response -> {
            // Display the first 500 characters of the response string.
            CurrentUser.getInstance()
                       .setJwt(response);
            CurrentUser.getInstance()
                       .setLoggedIn(true);
            CurrentUser.getInstance()
                       .updateUserFromServer();
            System.out.println(response);
            progressBar.setVisibility(View.INVISIBLE);
            Navigation.findNavController(getView())
                      .popBackStack();
            Navigation.findNavController(getView())
                      .popBackStack();
        };

        Response.ErrorListener errorListener = error -> {
            progressBar.setVisibility(View.INVISIBLE);
            status_view.setText("LOGGIN ERROR, wrong username or password");
        };

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                                                        String.format(FantApi.LOGIN_URL,
                                                                      username,
                                                                      pass),

                                                        stringListener, errorListener);


        System.out.println(FantApi.GET_ITEMS_URL);

        VolleyHttpQue.instance().addToRequestQue(stringRequest);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}