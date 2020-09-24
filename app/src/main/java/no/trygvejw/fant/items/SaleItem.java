package no.trygvejw.fant.items;

import java.math.BigDecimal;
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
