package no.trygvejw.fant.items;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.json.bind.annotation.JsonbTransient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    String userid;

    String email;

    Timestamp version;

    Date created;

    List<SaleItem> items;

    String name;


    //List<Group> groups;


    //Map<String,String> properties = new HashMap<>();
}
