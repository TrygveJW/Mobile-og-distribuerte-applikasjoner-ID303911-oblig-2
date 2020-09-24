package no.trygvejw.fant;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import no.trygvejw.fant.api.VolleyHttpQue;
import no.trygvejw.fant.items.ItemDB;
import no.trygvejw.fant.items.SaleItem;
import no.trygvejw.fant.ui.home.HomeFragment;
import no.trygvejw.fant.ui.home.HomeFragmentArgs;
import no.trygvejw.fant.ui.home.HomeFragmentDirections;
import no.trygvejw.fant.ui.home.ItemAdapter;


public class BuiItemFragment extends Fragment {


    public BuiItemFragment() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FloatingActionButton fab = container.getRootView().findViewById(R.id.fab);
        fab.hide();

        View root = inflater.inflate(R.layout.fragment_buy_item, container, false);


        long a = HomeFragmentArgs.fromBundle(getArguments()).getItemId();
        System.out.println(a);
        SaleItem saleItem = ItemDB.getInstance().getSaleItem(a);

        TextView name = root.findViewById(R.id.buy_name);
        TextView price = root.findViewById(R.id.buy_price);
        TextView desc = root.findViewById(R.id.buy_desc);

        NetworkImageView imageView = root.findViewById(R.id.buy_image);

        if (!saleItem.getItemImages().isEmpty()){

            String url = String.format(FantApi.GET_IMAGE_URL, saleItem.getItemImages().get(0).getId(), 0);
            imageView.setImageUrl(url, VolleyHttpQue.instance().getImageLoader());
        }

        name.setText(saleItem.getTitle());
        price.setText(saleItem.getPrice().toString());
        desc.setText(saleItem.getDescription());




        return root;
    }
}