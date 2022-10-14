package com.app.groceryanlaytics;

import com.app.groceryanlaytics.models.Item;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.app.groceryanlaytics.utilities.ExcelReader;
import java.util.*;

@SpringBootApplication
@RestController
public class GroceryAnlayticsApplication {

    private static HashMap<String, List<Item>> GROCERIES = null;

    static {
        if(null == GROCERIES){
            GROCERIES = ExcelReader.readExcel();
        }
    }

    @GetMapping("/max-price")
    @CrossOrigin("http://localhost:3000/")
    public List<Item> getItemsMaxPrice(){
        List result = new ArrayList<>();
        for(List<Item> product : GROCERIES.values()){
                if(product.size() > 0){
                    Item item = new Item();
                    int length = product.size();
                    item.setDate(product.get(length-1).getDate());
                    item.setName(product.get(length-1).getName());
                    item.setPrice(product.get(length-1).getPrice());
                    result.add(item);
                }
        }
        return result;
    }

    @GetMapping("/item-price-history/{item}")
    @CrossOrigin("http://localhost:3000/")
    public List getItemsPriceHistory(@PathVariable String item){
        List<Item> result = GROCERIES.get(item);
        return result;
    }

    public static void main(String[] args) {
        SpringApplication.run(GroceryAnlayticsApplication.class, args);
    }

}
