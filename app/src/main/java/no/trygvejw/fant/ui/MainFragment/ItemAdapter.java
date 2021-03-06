package no.trygvejw.fant.ui.MainFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

import no.trygvejw.fant.FantApi;
import no.trygvejw.fant.R;
import no.trygvejw.fant.api.VolleyHttpQue;
import no.trygvejw.fant.items.ItemDB;
import no.trygvejw.fant.items.SaleItem;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> implements Filterable {


    public ItemAdapter() {

    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                             int viewType) {
        ConstraintLayout layout = (ConstraintLayout) LayoutInflater.from(parent.getContext())
                                                                   .inflate(R.layout.list_item,
                                                                            parent,
                                                                            false);

        ItemViewHolder viewHolder = new ItemViewHolder(layout);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        SaleItem saleItem = ItemDB.getInstance().getSaleItemByIndex(position);

        holder.setSaleItem(saleItem);

    }


    @Override
    public int getItemCount() {
        return ItemDB.getInstance().getSize();
    }


    @Override
    public Filter getFilter() {
        // https://howtodoandroid.com/search-filter-recyclerview-android/
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    ItemDB.getInstance().setFiltered_items(ItemDB.getInstance().getItems());
                } else {
                    ArrayList<SaleItem> filteredList = new ArrayList<>();
                    for (SaleItem item : ItemDB.getInstance()
                                               .getItems()) {
                        if (item.getTitle()
                                .toLowerCase()
                                .contains(charString.toLowerCase())) {
                            filteredList.add(item);
                        }
                    }
                    ItemDB.getInstance()
                          .setFiltered_items(filteredList);
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = ItemDB.getInstance().getFiltered_items();
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                ItemDB.getInstance()
                      .setFiltered_items((ArrayList<SaleItem>) filterResults.values);

                notifyDataSetChanged();
            }
        };
    }


    public static class ItemData {
        public String name;
        public String price;
        public String desc;


        public ItemData(SaleItem saleItem) {
            this.name  = name;
            this.price = price;
            this.desc  = desc;
        }

    }


    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        public SaleItem saleItem;
        public ConstraintLayout layout;
        public TextView cardName;
        public TextView cardPrice;
        public TextView cardDesc;
        public NetworkImageView imageView;


        public ItemViewHolder(final ConstraintLayout layout) {
            super(layout);
            this.layout = layout;

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HomeFragmentDirections.ActionNavHomeToBuiItemFragment2 directions = HomeFragmentDirections
                            .actionNavHomeToBuiItemFragment2();

                    directions.setItemId(saleItem.getId());

                    //directions.setItemId(saleItem.getId());
                    directions.getArguments()
                              .putLong("itemId", saleItem.getId());
                    Navigation.findNavController(v)
                              .navigate(directions);
                }
            });

            imageView = (NetworkImageView) layout.getViewById(R.id.item_image);
            cardName = (TextView) layout.getViewById(R.id.card_name);
            cardPrice = (TextView) layout.getViewById(R.id.card_price);
            cardDesc = (TextView) layout.getViewById(R.id.card_desc);
        }

        public void setSaleItem(SaleItem saleItem) {
            this.saleItem = saleItem;

            if (saleItem != null) {
                this.cardName.setText(saleItem.getTitle());
                this.cardPrice.setText(saleItem.getPrice()
                                               .toString());
                this.cardDesc.setText(saleItem.getDescription());

                if (!saleItem.getItemImages()
                             .isEmpty()) {

                    String url = String.format(FantApi.GET_IMAGE_URL,
                                               saleItem.getItemImages()
                                                       .get(0)
                                                       .getId(),
                                               imageView.getMeasuredWidth());
                    this.imageView.setImageUrl(url, VolleyHttpQue.instance()
                                                                 .getImageLoader());
                }


            }


            //this.imageView.setImageURI(saleItem.getImageUri());
        }
    }
}
