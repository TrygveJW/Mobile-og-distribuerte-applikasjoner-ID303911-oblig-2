package no.trygvejw.fant.ui.home;

import android.app.usage.UsageEvents;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;

import no.trygvejw.fant.BuiItemFragment;
import no.trygvejw.fant.R;
import no.trygvejw.fant.items.ItemDB;
import no.trygvejw.fant.items.SaleItem;
import no.trygvejw.fant.ui.gallery.GalleryFragment;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private ArrayList<ItemData> dummyData;

    public ItemAdapter() {
        dummyData = new ArrayList<>();





    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ConstraintLayout layout = (ConstraintLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,  parent,false);

        ItemViewHolder viewHolder = new ItemViewHolder(layout);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        SaleItem saleItem = ItemDB.getInstance().getSaleItem((long) position);

        holder.setSaleItem(saleItem);

    }



    @Override
    public int getItemCount() {
        return ItemDB.getInstance().getSize();
    }

    public static class ItemData{
        public String name;
        public String price;
        public String desc;


        public ItemData(SaleItem saleItem) {
            this.name = name;
            this.price = price;
            this.desc = desc;
        }

    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder{

        public SaleItem saleItem;
        public ConstraintLayout layout;
        public TextView cardName;
        public TextView cardPrice;
        public TextView cardDesc;
        public ImageView imageView;


        public ItemViewHolder(final ConstraintLayout layout) {
            super(layout);
            this.layout = layout;

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HomeFragmentDirections.ActionNavHomeToBuiItemFragment2 directions = HomeFragmentDirections.actionNavHomeToBuiItemFragment2();

                    directions.setItemId(saleItem.getId());

                    //directions.setItemId(saleItem.getId());
                    directions.getArguments().putLong("itemId",saleItem.getId());
                    Navigation.findNavController(v).navigate(directions);
                }
            });

            imageView = (ImageView) layout.getViewById(R.id.item_image);
            cardName = (TextView) layout.getViewById(R.id.card_name);
            cardPrice = (TextView) layout.getViewById(R.id.card_price);
            cardDesc = (TextView) layout.getViewById(R.id.card_desc);
        }

        public void setSaleItem(SaleItem saleItem) {
            this.saleItem = saleItem;

            this.cardName.setText(saleItem.getTitle());
            this.cardPrice.setText(saleItem.getPrice().toString());
            this.cardDesc.setText(saleItem.getDescription());
            this.imageView.setImageResource(R.drawable.ic_launcher_foreground);
            //this.imageView.setImageURI(saleItem.getImageUri());
        }
    }
}
