package no.trygvejw.fant.ui;

import android.annotation.SuppressLint;
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
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import no.trygvejw.fant.CurrentUser;
import no.trygvejw.fant.FantApi;
import no.trygvejw.fant.R;
import no.trygvejw.fant.api.VolleyHttpQue;
import no.trygvejw.fant.items.ItemDB;
import no.trygvejw.fant.items.SaleItem;
import no.trygvejw.fant.ui.MainFragment.HomeFragmentArgs;


public class BuyItemFragment extends Fragment {
    TextView name;
    TextView price;
    TextView desc;

    TextView loginWarning;
    Button buyButton;

    ProgressBar progressBar;

    SaleItem item;

    public BuyItemFragment() {
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

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FloatingActionButton fab = container.getRootView()
                                            .findViewById(R.id.fab);
        fab.hide();


        View root = inflater.inflate(R.layout.fragment_buy_item, container, false);


        long a = HomeFragmentArgs.fromBundle(getArguments())
                                 .getItemId();
        System.out.println(a);
        item = ItemDB.getInstance()
                     .getSaleItem(a);

        name  = root.findViewById(R.id.buy_name);
        price = root.findViewById(R.id.buy_price);
        desc  = root.findViewById(R.id.buy_desc);

        loginWarning = root.findViewById(R.id.buy_login_waring);
        buyButton    = root.findViewById(R.id.buy_button);
        progressBar  = root.findViewById(R.id.loading);

        if (CurrentUser.getInstance()
                       .isLoggedIn()) {
            if (CurrentUser.getInstance()
                           .getUser()
                           .getUserid()
                           .equals(item.getItemOwner()
                                       .getUserid())) {
                loginWarning.setText("cant buy own item");
                buyButton.setActivated(false);
            } else {
                buyButton.setOnClickListener(v -> {
                    progressBar.setVisibility(View.VISIBLE);
                    buyItem();


                });
            }


        } else {
            loginWarning.setText("Logg inn to buy items");
            buyButton.setActivated(false);
        }


        NetworkImageView imageView = root.findViewById(R.id.buy_image);


        if (!item.getItemImages()
                 .isEmpty()) {

            String url = String.format(FantApi.GET_IMAGE_URL,
                                       item.getItemImages()
                                           .get(0)
                                           .getId(),
                                       imageView.getMeasuredWidth());
            imageView.setImageUrl(url, VolleyHttpQue.instance()
                                                    .getImageLoader());
        }

        name.setText(item.getTitle());
        price.setText(item.getPrice()
                          .toString());
        desc.setText(item.getDescription());


        return root;
    }

    private void buyItem() {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                String.format(FantApi.PURCHASE_ITEM_URL, item.getId()),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        progressBar.setVisibility(View.INVISIBLE);
                        Navigation.findNavController(getView())
                                  .popBackStack();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}