package no.trygvejw.fant.items;

import com.android.volley.Request;
import com.android.volley.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

import no.trygvejw.fant.FantApi;
import no.trygvejw.fant.api.GsonRequest;
import no.trygvejw.fant.api.VolleyHttpQue;

public class ItemDB {

    private static ItemDB instance = null;
    private ArrayList<SaleItem> items = new ArrayList<>();
    private ArrayList<SaleItem> filtered_items = new ArrayList<>();

    private ItemDB() {
        refreshDb();
    }

    public static ItemDB getInstance() {
        if (instance == null) {
            instance = new ItemDB();

        }

        return instance;

    }

    public void refreshDb() {
        refreshDb(null);
    }

    public void refreshDb(Runnable callback) {
        GsonRequest gsonRequest = new GsonRequest(
                FantApi.GET_ITEMS_URL,
                Request.Method.GET,
                SaleItem[].class,
                new HashMap<String, String>(),
                new Response.Listener<SaleItem[]>() {
                    @Override
                    public void onResponse(SaleItem[] response) {
                        items.clear();
                        items.addAll(Arrays.asList(response));
                        items          = items.stream()
                                              .filter(saleItem -> saleItem.getItemBuyer() == null)
                                              .sorted((o1, o2) -> o1.getTitle()
                                                                    .compareTo(o2.getTitle()))
                                              .collect(Collectors.toCollection(ArrayList::new));
                        filtered_items = items;
                        if (callback != null) {
                            callback.run();
                        }
                    }
                },
                FantApi.emptyErrorListener
        );

        System.out.println(FantApi.GET_ITEMS_URL);

        VolleyHttpQue.instance().addToRequestQue(gsonRequest);
    }

    public SaleItem getSaleItemByIndex(int id) {
        return filtered_items.get(id);
    }


    public SaleItem getSaleItem(Long id) {

        return items.stream()
                    .filter(saleItem -> saleItem.getId()
                                                .equals(id))
                    .findFirst()
                    .get();
    }

    public ArrayList<SaleItem> getFiltered_items() {
        return filtered_items;
    }

    public void setFiltered_items(ArrayList<SaleItem> filtered_items) {
        this.filtered_items = filtered_items;
        //this.filtered_items.addAll(filtered_items);
    }

    public ArrayList<SaleItem> getItems() {
        return items;
    }

    public int getSize() {
        return filtered_items.size();
    }
}
