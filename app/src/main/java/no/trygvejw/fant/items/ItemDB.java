package no.trygvejw.fant.items;

import android.content.ClipData;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import kotlin.math.UMathKt;
import no.trygvejw.fant.ui.home.ItemAdapter;

public class ItemDB {

    private HashMap<Long, SaleItem> items = new HashMap<>();

    private static ItemDB instance = null;

    public static ItemDB getInstance(){
        if (instance == null){
            instance = new ItemDB();

            for (long i = 0; i < 50; i++) {
                instance.items.put(i, new SaleItem(new Long(i), "title "+ i, "desc "+ i, new BigDecimal(i* 92048), null, null ));
            }
        }

        return instance;

    }


    private ItemDB(){

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
