package no.trygvejw.fant.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

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
        FloatingActionButton fab = container.getRootView().findViewById(R.id.fab);
        fab.hide();

        View root = inflater.inflate(R.layout.fragment_login, container, false);

        username_view = (TextView) root.findViewById(R.id.login_user_mail);
        password_view = (TextView) root.findViewById(R.id.login_user_password);
        status_view = (TextView) root.findViewById(R.id.login_status);
        submit_btn = root.findViewById(R.id.login_submitt_btn);
        progressBar = root.findViewById(R.id.loading);

        submit_btn.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);

            sendLoginRequest(username_view.getText().toString(), password_view.getText().toString());
        });




        return root;
    }

    public void sendLoginRequest(String username, String pass){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, String.format(FantApi.LOGIN_URL, username, pass),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        CurrentUser.getInstance().setJwt(response);
                        CurrentUser.getInstance().setLoggedIn(true);
                        CurrentUser.getInstance().updateUserFromServer();
                        System.out.println(response);
                        progressBar.setVisibility(View.INVISIBLE);
                        Navigation.findNavController(getView()).popBackStack();
                        Navigation.findNavController(getView()).popBackStack();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.INVISIBLE);
                status_view.setText("LOGGIN ERROR, wrong username or password");
            }
        });




        System.out.println(FantApi.GET_ITEMS_URL);

        VolleyHttpQue.instance().addToRequestQue(stringRequest);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*




        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = view.findViewById(R.id.new_user_email);
        final EditText passwordEditText = view.findViewById(R.id.new_user_password);
        final Button loginButton = view.findViewById(R.id.login);
        final ProgressBar loadingProgressBar = view.findViewById(R.id.loading);

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });

         */
    }

/*    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        if (getContext() != null && getContext().getApplicationContext() != null) {
            Toast.makeText(getContext().getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
        }
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        if (getContext() != null && getContext().getApplicationContext() != null) {
            Toast.makeText(
                    getContext().getApplicationContext(),
                    errorString,
                    Toast.LENGTH_LONG).show();
        }
    }*/

}