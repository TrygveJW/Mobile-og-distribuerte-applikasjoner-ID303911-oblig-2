package no.trygvejw.fant.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import no.trygvejw.fant.CurrentUser;
import no.trygvejw.fant.R;


public class UserFragment extends Fragment {

    Button login_out;
    Button new_user;
    TextView status;

    public UserFragment() {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FloatingActionButton fab = container.getRootView()
                                            .findViewById(R.id.fab);
        fab.hide();

        View root = inflater.inflate(R.layout.fragment_user, container, false);
        login_out = root.findViewById(R.id.user_login_out);
        new_user  = root.findViewById(R.id.user_create_user);
        status    = root.findViewById(R.id.user_status);

        if (CurrentUser.getInstance()
                       .isLoggedIn()) {
            login_out.setText(R.string.Log_out);
            if (CurrentUser.getInstance()
                           .getUser() != null) {
                status.setText(String.format("Logged in as: %s", CurrentUser.getInstance()
                                                                            .getUser()
                                                                            .getName()));
            }


            login_out.setOnClickListener(v -> {
                CurrentUser.getInstance()
                           .setJwt("");
                CurrentUser.getInstance()
                           .setLoggedIn(false);
                CurrentUser.getInstance()
                           .setUser(null);
                Navigation.findNavController(getView())
                          .popBackStack();

            });
        } else {
            login_out.setText(R.string.Log_in);

            login_out.setOnClickListener(v -> {
                NavDirections directions_new_user = UserFragmentDirections.actionUserToLogin();
                Navigation.findNavController(v).navigate(directions_new_user);
            });
        }


        new_user.setOnClickListener(v -> {
            NavDirections directions_new_user = UserFragmentDirections.actionUserToCreateUser();
            Navigation.findNavController(v).navigate(directions_new_user);

        });


        return root;
    }
}