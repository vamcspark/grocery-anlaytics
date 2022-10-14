package com.app.groceryanlaytics.utilities;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import com.app.groceryanlaytics.models.Item;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
    private static final String FILE_NAME = "src/main/resources/static/vegetables.xlsx";

    public static HashMap readExcel() {
        System.out.println("READING EXCEL ONCE FOR ALL --- PLEASE WAIT :::::: ");
        HashMap<String, List<Item>> GROCERY_LIST = new HashMap();
        try {
            FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            for (int i = 1; i < datatypeSheet.getPhysicalNumberOfRows(); i++) {
                XSSFRow row = (XSSFRow) datatypeSheet.getRow(i);
                Item item = new Item();
                String itemPrice = String.valueOf(row.getCell(3));
                String itemName = String.valueOf(row.getCell(1));
                String itemDate = String.valueOf(row.getCell(2));
                if (!itemPrice.equals("NULL") && !itemPrice.equals("0.0")) {
                    if (itemDate.contains("-")) {
                        itemDate = itemDate.replace('-', '/');
                    }
                    item.setPrice(Float.parseFloat(itemPrice));
                    item.setName(itemName);
                    item.setDate(itemDate);
                    if (!GROCERY_LIST.containsKey(itemName)) {
                        GROCERY_LIST.put(itemName, new ArrayList());
                        GROCERY_LIST.get(itemName).add(item);
                    } else {
                        GROCERY_LIST.get(itemName).add(item);
                    }
                }

            }
            System.out.println("Total Number of Unique Items :::: " + GROCERY_LIST.keySet().size());
            for (List<Item> product : GROCERY_LIST.values()) {
                if (product.size() > 0) {
                    Collections.sort(product);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    return GROCERY_LIST;
}
}