package com.wt.traveldestinationrecommendation;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Utility {
    public final Map<String,String> categories ;
    Utility(){
        this.categories = new HashMap<>();
        categories.put("Category 1","churches");
        categories.put("Category 2","resorts");
        categories.put("Category 3","beaches");
        categories.put("Category 4","parks");
        categories.put("Category 5","theatres");
        categories.put("Category 6","museums");
        categories.put("Category 7","malls");
        categories.put("Category 8","zoo");
        categories.put("Category 9","restaurants");
        categories.put("Category 10","pubs/bars");
        categories.put("Category 11","local services");
        categories.put("Category 12","burger/pizza shops");
        categories.put("Category 13","hotels/other lodgings");
        categories.put("Category 14","juice bars");
        categories.put("Category 15","art galleries");
        categories.put("Category 16","dance clubs");
        categories.put("Category 17","swimming pools");
        categories.put("Category 18","gyms");
        categories.put("Category 19","bakeries");
        categories.put("Category 20","beauty & spas");
        categories.put("Category 21","cafes");
        categories.put("Category 22","view points");
        categories.put("Category 23","monuments");
        categories.put("Category 24","gardens");

    }
}
