package no.trygvejw.fant.items;

import android.content.ClipData;

import com.android.volley.Response;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import kotlin.math.UMathKt;
import no.trygvejw.fant.FantApi;
import no.trygvejw.fant.api.GsonRequest;
import no.trygvejw.fant.api.VolleyHttpQue;
import no.trygvejw.fant.ui.home.ItemAdapter;

public class ItemDB {

    private HashMap<Long, SaleItem> items = new HashMap<>();
    private ArrayList<Long> sortedKeys = new ArrayList<>();

    private static ItemDB instance = null;

    public static ItemDB getInstance(){
        if (instance == null){
            instance = new ItemDB();

            for (long i = 0; i < 50; i++) {
                //instance.items.put(i, new SaleItem(new Long(i), "title "+ i, "desc "+ i, new BigDecimal(i* 92048), null, null ));
            }
        }

        return instance;

    }


    private ItemDB(){
        refreshDb();
    }

    public void refreshDb(){
        GsonRequest gsonRequest = new GsonRequest(
                FantApi.GET_ITEMS_URL,
                SaleItem[].class,
                new HashMap<String, String>(),
                new Response.Listener<SaleItem[]>() {
                    @Override
                    public void onResponse(SaleItem[] response) {
                        Arrays.stream(response).forEach(item -> items.put(item.getId(),item));
                        sortedKeys = items.keySet().stream().sorted((o1, o2) -> items.get(o1).getTitle().compareTo(items.get(o2).getTitle()) ).collect(Collectors.toCollection(ArrayList::new));
                    }
                },
                FantApi.emptyErrorListener
        );

        System.out.println(FantApi.GET_ITEMS_URL);

        VolleyHttpQue.instance().addToRequestQue(gsonRequest);
    }

    public SaleItem getSaleItemByIndex(int id){
        return items.get(sortedKeys.get(id));
    }


    public SaleItem getSaleItem(Long id){

        return items.get(id);
    }

    public boolean addNewItem(){
        // sen api herfra
        return false;
    }

    public int getSize(){
        return items.size();
    }
}
