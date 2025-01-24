package tests;

import org.junit.Before;
import psb.testproject.store.handling.ParseJSON;
import psb.testproject.store.handling.Processor;
import org.junit.Assert;
import org.junit.Test;
import psb.testproject.store.products.Chips;
import psb.testproject.store.products.Product;
import psb.testproject.store.products.WashingMashine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Scanner;
import java.util.stream.Collectors;

public class TestProcessor {
    Processor pr;
    ParseJSON pj;
    String ordersPath = "C:\\Users\\Anastasia\\IdeaProjects\\psb.testproject\\src\\main\\java\\psb\\testproject\\orders.json";
    String productsPath = "C:\\Users\\Anastasia\\IdeaProjects\\psb.testproject\\src\\main\\java\\psb\\testproject\\products.json";

    @Before
    public void setUp() throws Exception {
        pr = new Processor();
        pj = new ParseJSON();
    }

    //по меню
    @Test
    public void printOrderWithSumWeightAndPrice() {
        pr.printOrderWithSumWeightAndPrice();
    }

    @Test
    public void createAndGetOptimOrder() {
        pr.createOptimOrder();
        pr.getOptimOrder();
    }

    @Test
    public void getMinProductByPrice() {
        pr.getMinProductByPrice();
    }

    //генерирование данных
    @Test
    public void generateOrder() {
        pr.generateOrder();
    }

    @Test
    public void generateOrderBySum() {
        pr.generateOrderBySum(10);
    }

    @Test
    public void generateOrderBySumPlusReverse() throws Exception { //добавить обработчик, зацикливается
        try {
            pr.generateOrderBySum(10, 5);
        } catch (Exception e) {
            e.printStackTrace();
        }
        pr.generateOrderBySum(9, 10);
    }

    @Test
    public void generateOrderByCount() {
        pr.generateOrderByCount(3);
    }

    //edit
    @Test
    public void editName() {
    String fileNameOK = "C:\\Users\\Anastasia\\IdeaProjects\\psb.testproject\\src\\test\\java\\datatest\\NameOK.txt";
    String fileNoName = "C:\\Users\\Anastasia\\IdeaProjects\\psb.testproject\\src\\test\\java\\datatest\\NameNo.txt";
        try {
            System.out.println("TEST-EDIT-NAME-OK:");
            pr.editName(new Scanner(new File(fileNameOK)));

            System.out.println("\n\n\nTEST-EDIT-NO-NAME:");
            pr.editName(new Scanner(new File(fileNoName)));

        } catch (FileNotFoundException e) {
            System.err.println("Не удалось открыть файл");
        }
    }

    @Test
    public void editPrice(){
        String filePriceOK = "C:\\Users\\Anastasia\\IdeaProjects\\psb.testproject\\src\\test\\java\\datatest\\PriceOK.txt";
        String filePriceUnder0 = "C:\\Users\\Anastasia\\IdeaProjects\\psb.testproject\\src\\test\\java\\datatest\\PriceUnder0.txt";
        try {
            System.out.println("TEST-EDIT-PRICE-OK:");
            pr.editPrice(new Scanner(new File(filePriceOK)));

            System.out.println("\n\n\nTEST-EDIT-UNDER-0-PRICE:");
            pr.editPrice(new Scanner(new File(filePriceUnder0)));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void editWeight(){
        String fileWeightOK = "C:\\Users\\Anastasia\\IdeaProjects\\psb.testproject\\src\\test\\java\\datatest\\WeightOK.txt";
        String fileWeightUnder0 = "C:\\Users\\Anastasia\\IdeaProjects\\psb.testproject\\src\\test\\java\\datatest\\WeightUnder0.txt";
        try {
            System.out.println("TEST-EDIT-Weight-OK:");
            pr.editWeight(new Scanner(new File(fileWeightOK)));

            System.out.println("\n\n\nTEST-EDIT-UNDER-0-Weight:");
            pr.editWeight(new Scanner(new File(fileWeightUnder0)));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //sorting
    @Test
    public void sortingByDate() throws ParseException {
        Date testDate = new SimpleDateFormat("yyyy-MM-dd").parse("2025-01-13");
        pr.sortingByDate(pj.parseOrdersFromJSON(ordersPath), testDate);
    }

    @Test
    public void sortingByAlphName(){
        ArrayList<Product> sorted = pr.sortingByAlphName(pr.getAllProducts());
        for (Product p: sorted) {
            System.out.println(p);
        }
    }

    @Test
    public void sortingByMinPrice() {
        pr.sortingByMinPrice(pj.parseOrdersFromJSON(ordersPath), 10.0);
    }

    @Test
    public void sortingByMaxPrice() {
        pr.sortingByMaxPrice(pj.parseOrdersFromJSON(ordersPath), 17.0);
    }

    @Test
    public void sortingByWeight() {
        pr.sortingByWeight(pj.parseOrdersFromJSON(ordersPath));
    }

    @Test
    public void sortingByUniqName(){
        System.out.println(pr.sortingByUniqName(pj.parseProductsFromJSON(productsPath)));
    }


    @Test
    public void getProducts() {
        ArrayList<Product> actualProducts = new ArrayList<>();

        actualProducts.add(new WashingMashine("Стиралка Samsung", 12000, 3, true));
        actualProducts.add(new WashingMashine("Стиралка Anno", 12000, 1, true));
        actualProducts.add(new WashingMashine("Стиралка Zegbee", 12000, 7, true));
        actualProducts.add(new Chips("Чипсы Lays", 100, 5, "cheese"));
        actualProducts.add(new Chips("Чипсы Lays", 100, 2, "crab"));

        ArrayList<Product> expectedProducts = pr.getAllProducts();

        Assert.assertEquals(actualProducts, expectedProducts);
    }
}
