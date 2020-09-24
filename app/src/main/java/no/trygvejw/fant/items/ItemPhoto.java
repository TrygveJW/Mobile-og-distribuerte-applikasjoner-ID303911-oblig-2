package no.trygvejw.fant.items;


import java.net.URL;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import no.trygvejw.fant.FantApi;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItemPhoto {

    String id;

    String name;

    long filesize;
    String mimeType;

    SaleItem photoItem;

}
