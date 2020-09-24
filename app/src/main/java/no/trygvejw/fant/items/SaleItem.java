package no.trygvejw.fant.items;

import android.net.Uri;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URL;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SaleItem {

    private Long id;

    private String title;
    private String description;
    private BigDecimal price;


    private List<ItemPhoto> itemImages;


    private User itemOwner;
    private User itemBuyer;

}
