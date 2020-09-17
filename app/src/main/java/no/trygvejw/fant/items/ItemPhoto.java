package no.trygvejw.fant.items;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
