package core.controllers;

import core.db.Medicine;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.TreeMap;

public class StockController {
    public static ObservableList<String> medicamentTypeNames;
    public static TreeMap<String, Integer> medicamentType;
    public static ObservableList<Medicine> medicineList;
    public static ArrayList<String> medicineNames;
}
