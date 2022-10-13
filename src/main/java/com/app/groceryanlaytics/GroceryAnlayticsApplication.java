package com.app.groceryanlaytics;

import models.Item;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@SpringBootApplication
@RestController
public class GroceryAnlayticsApplication {

    private static final String FILE_NAME = "src/main/resources/static/vegetables.xlsx";
    private static HashMap<String, List<Item>> GROCERY_LIST = null;

    static {
        GroceryAnlayticsApplication.readExcel();
    }

    @GetMapping("/max-price")
    @CrossOrigin("http://localhost:3000/")
    public List<Item> getItemsMaxPrice(){
        List result = new ArrayList<>();
        for(List<Item> product : GROCERY_LIST.values()){
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
        List<Item> result = GROCERY_LIST.get(item);
        return result;
    }

    public static String readExcel(){
        System.out.println("hello static");
        GROCERY_LIST = new HashMap();
            try {
                FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
                Workbook workbook = new XSSFWorkbook(excelFile);
                Sheet datatypeSheet = workbook.getSheetAt(0);
                for(int i=1; i<datatypeSheet.getPhysicalNumberOfRows();i++) {
                    XSSFRow row = (XSSFRow) datatypeSheet.getRow(i);
                             Item item = new Item();
                             String itemPrice = String.valueOf(row.getCell(3));
                             String itemName = String.valueOf(row.getCell(1));
                             String itemDate = String.valueOf(row.getCell(2));
                             if(!itemPrice.equals("NULL") && !itemPrice.equals("0.0")) {
                                 if(itemDate.contains("-")){
                                     itemDate = itemDate.replace('-','/');
                                 }
                                 item.setPrice(Float.parseFloat(itemPrice));
                                 item.setName(itemName);
                                 item.setDate(itemDate);
                                 if (!GROCERY_LIST.containsKey(itemName)) {
                                     GROCERY_LIST.put(itemName, new ArrayList());
                                     GROCERY_LIST.get(itemName).add(item);
                                 }
                                 else {
                                     GROCERY_LIST.get(itemName).add(item);
                                 }
                             }

                }
                System.out.println("Key :: "+GROCERY_LIST.keySet().size());
                for(List<Item> product : GROCERY_LIST.values()) {
                    if (product.size() > 0) {
                        Collections.sort(product);
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
    return "Success" ;
    }

    public static void main(String[] args) {
        SpringApplication.run(GroceryAnlayticsApplication.class, args);
    }

}
