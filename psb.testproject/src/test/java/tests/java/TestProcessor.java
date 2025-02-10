package tests.java;

import org.junit.Before;
import psb.testproject.store.handling.ParseJSON;
import psb.testproject.store.handling.Processor;
import org.junit.Assert;
import org.junit.Test;
import psb.testproject.store.products.Chips;
import psb.testproject.store.products.Order;
import psb.testproject.store.products.Product;
import psb.testproject.store.products.WashingMashine;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TestProcessor {
    Processor pr;
    ParseJSON pj;
    Order expectedOrder26; //26
    Order expectedOrder13; //13
    Order expectedOrder14; //14
    Order expectedOrder15; //15
    String ordersPath = "C:\\Users\\Anastasia\\IdeaProjects\\psb_check2\\psb.testproject\\src\\main\\java\\psb\\testproject\\orders.json";
    String productsPath = "C:\\Users\\Anastasia\\IdeaProjects\\psb_check2\\psb.testproject\\src\\main\\java\\psb\\testproject\\products.json";

    @Before
    public void setUp() throws Exception {
        pr = new Processor();
        pj = new ParseJSON();
        makeExpectedOrders();
    }

    //по меню
    @Test
    public void createAndGetOptimOrder() {
        boolean isGenerated = false;
        Order generatedOrder = pr.createOptimOrder();
        if (!generatedOrder.isEmpty()) {
            isGenerated = true;
        }
        Assert.assertTrue(isGenerated);
        pr.getOptimOrder();

    }

    @Test
    public void getMinProductByPrice() {
        ArrayList<Product> actualProducts = pr.getMinProductByPrice();
        ArrayList<Product> expectedProducts = new ArrayList<>();
        expectedProducts.add(new WashingMashine("Стиралка Zegbee", 180.0, 7.0, false));
        expectedProducts.add(new Chips("Чипсы Lays", 80.0, 3.0, "cheese"));
        Assert.assertEquals(expectedProducts, actualProducts);
    }

    //генерирование данных
    @Test
    public void generateOrder() {
        boolean isGenerated = false;
        Order generatedOrder = pr.generateOrder();
        if (!generatedOrder.isEmpty()) {
            isGenerated = true;
        }
        Assert.assertTrue(isGenerated);
    }

    @Test
    public void generateOrderBySum() {
        boolean isGenerated = false;
        Order generatedOrder = pr.generateOrderBySum(10);
        if (!generatedOrder.isEmpty()) {
            isGenerated = true;
        }
        Assert.assertTrue(isGenerated);
    }

    @Test
    public void generateOrderBySumPlusReverse() throws Exception { //добавить обработчик, зацикливается
        try {
            pr.generateOrderBySum(10, 5);
        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean isGenerated = false;
        Order generatedOrder = pr.generateOrderBySum(9, 10);
        if (!generatedOrder.isEmpty()) {
            isGenerated = true;
        }
        Assert.assertTrue(isGenerated);
    }

    @Test
    public void generateOrderByCount() {
        boolean isGenerated = false;
        Order generatedOrder = pr.generateOrderByCount(3);
        if (!generatedOrder.isEmpty()) {
            isGenerated = true;
        }
        Assert.assertTrue(isGenerated);
    }

    //edit
    @Test
    public void editNameOK() {
        String fileNameOK = "C:\\Users\\Anastasia\\IdeaProjects\\psb_check2\\psb.testproject\\src\\test\\java\\datatest\\NameOK.txt";
        try {
            System.out.println("TEST-EDIT-NAME-OK:");
            Product newProduct = pr.editName(new Scanner(new File(fileNameOK)));
            Assert.assertEquals("Чипсы Lays+", newProduct.getName());
        } catch (FileNotFoundException e) {
            System.err.println("Не удалось открыть файл");
        }
    }

    @Test
    public void editNameNo() {
        String fileNoName = "C:\\Users\\Anastasia\\IdeaProjects\\psb_check2\\psb.testproject\\src\\test\\java\\datatest\\NameNo.txt";
        try {
            System.out.println("\n\n\nTEST-EDIT-NO-NAME:");
            Product newProduct = pr.editName(new Scanner(new File(fileNoName)));
            Assert.assertNull(newProduct);
        } catch (FileNotFoundException e) {
            System.err.println("Не удалось открыть файл");
        }
    }

    @Test
    public void editPriceOk() {
        String filePriceOK = "C:\\Users\\Anastasia\\IdeaProjects\\psb_check2\\psb.testproject\\src\\test\\java\\datatest\\PriceOK.txt";
        try {
            System.out.println("TEST-EDIT-PRICE-OK:");
            pr.editPrice(new Scanner(new File(filePriceOK)));
            Product newProduct = pr.editPrice(new Scanner(new File(filePriceOK)));
            Assert.assertEquals(8.2, newProduct.getPrice(), 0.0);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void editPriceNo() {
        String filePriceUnder0 = "C:\\Users\\Anastasia\\IdeaProjects\\psb_check2\\psb.testproject\\src\\test\\java\\datatest\\PriceUnder0.txt";
        try {
            System.out.println("\n\n\nTEST-EDIT-UNDER-0-PRICE:");
            Product newProduct = pr.editPrice(new Scanner(new File(filePriceUnder0)));
            Assert.assertNull(newProduct);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void editWeightOk() {
        String fileWeightOK = "C:\\Users\\Anastasia\\IdeaProjects\\psb_check2\\psb.testproject\\src\\test\\java\\datatest\\WeightOK.txt";
        try {
            System.out.println("TEST-EDIT-Weight-OK:");
            Product newProduct = pr.editWeight(new Scanner(new File(fileWeightOK)));
            Assert.assertEquals(3.0, newProduct.getWeight(), 0.0);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void editWeightNo() {
        String fileWeightUnder0 = "C:\\Users\\Anastasia\\IdeaProjects\\psb_check2\\psb.testproject\\src\\test\\java\\datatest\\WeightUnder0.txt";
        try {
            System.out.println("\n\n\nTEST-EDIT-UNDER-0-Weight:");
            Product newProduct = pr.editWeight(new Scanner(new File(fileWeightUnder0)));
            Assert.assertNull(newProduct);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //sorting
    @Test
    public void sortingByDate() throws ParseException {
        Date testDate = new SimpleDateFormat("yyyy-MM-dd").parse("2025-01-13");
        ArrayList<Order> sorted = (ArrayList<Order>) pr.sortingByDate(pj.parseOrdersFromJSON(ordersPath), testDate);
        ArrayList<Order> expectedOrders = new ArrayList<>();

        expectedOrders.add(expectedOrder26);
        expectedOrders.add(expectedOrder13);
        Assert.assertEquals(expectedOrders, sorted);
    }

    @Test
    public void sortingByAlphName() {
        ArrayList<Product> sorted = pr.sortingByAlphName(pr.getAllProducts());
        ArrayList<Product> actualProducts = new ArrayList<>();
        actualProducts.add(new WashingMashine("Стиралка Anno", 100.0, 15.0, false));
        actualProducts.add(new WashingMashine("Стиралка Samsung", 180.0, 37.0, true));
        actualProducts.add(new WashingMashine("Стиралка Zegbee", 180.0, 7.0, false));
        actualProducts.add(new Chips("чипсы Lays", 100.0, 5.0, "crab"));
        actualProducts.add(new Chips("чипсы Lays", 80.0, 3.0, "cheese"));

        Assert.assertEquals(sorted, actualProducts);
    }

    @Test
    public void sortingByMinPrice() {
        ArrayList<Order> actualOrders = (ArrayList<Order>) pr.sortingByMinPrice(pj.parseOrdersFromJSON(ordersPath), 10.0);

        ArrayList<Order> expectedOrders = new ArrayList<>();
        expectedOrders.add(expectedOrder13);
        expectedOrders.add(expectedOrder14);
        expectedOrders.add(expectedOrder15);

        Assert.assertEquals(expectedOrders, actualOrders);
    }

    @Test
    public void sortingByMaxPrice() {
        ArrayList<Order> actualOrders = (ArrayList<Order>) pr.sortingByMaxPrice(pj.parseOrdersFromJSON(ordersPath), 17.0);
        ArrayList<Order> expectedOrders = new ArrayList<>();
        expectedOrders.add(expectedOrder13);
        expectedOrders.add(expectedOrder26);
        Assert.assertEquals(expectedOrders, actualOrders);
    }

    @Test
    public void sortingByWeight() {
        ArrayList<Order> actualOrders = (ArrayList<Order>) pr.sortingByWeight(pj.parseOrdersFromJSON(ordersPath));
        ArrayList<Order> expectedOrders = new ArrayList<>();
        makeExpectedOrders();
        expectedOrders.add(expectedOrder26);
        expectedOrders.add(expectedOrder13);
        expectedOrders.add(expectedOrder14);
        expectedOrders.add(expectedOrder15);
        Assert.assertEquals(expectedOrders, actualOrders);
    }

    @Test
    public void sortingOrdersByUniqName() {
        ArrayList<Order> orders = pr.getOrders();
        ArrayList<Order> actualOrders = new ArrayList<>();
        for (Order order : orders) {
            ArrayList<Product> products = (ArrayList<Product>) pr.sortingByUniqName(order.getProducts());
            if (order.getProducts().size() == (products.size())) {
                actualOrders.add(order);
            }
        }

        ArrayList<Order> expectedOrders = new ArrayList<>();
        expectedOrders.add(expectedOrder13);
        expectedOrders.add(expectedOrder26);
        expectedOrders.add(expectedOrder14);
        Assert.assertEquals(expectedOrders, actualOrders);
    }

    @Test
    public void sortingByUniqName() {
        ArrayList<Product> actualProducts = (ArrayList<Product>) pr.sortingByUniqName(pj.parseProductsFromJSON(productsPath));
        ArrayList<Product> expectedProducts = new ArrayList<>();
        expectedProducts.add(new WashingMashine("Стиралка Samsung", 180.0, 37.0, true));
        expectedProducts.add(new WashingMashine("Стиралка Anno", 100.0, 15.0, false));
        expectedProducts.add(new WashingMashine("Стиралка Zegbee", 180.0, 7.0, false));
        expectedProducts.add(new Chips("чипсы Lays", 100.0, 5.0, "crab"));

        Assert.assertEquals(expectedProducts, actualProducts);
    }

    @Test
    public void getProducts() {
        ArrayList<Product> actualProducts = new ArrayList<>();
        actualProducts.add(new WashingMashine("Стиралка Samsung", 180.0, 37.0, true));
        actualProducts.add(new WashingMashine("Стиралка Anno", 100.0, 15.0, false));
        actualProducts.add(new WashingMashine("Стиралка Zegbee", 180.0, 7.0, false));
        actualProducts.add(new Chips("Чипсы Lays", 100.0, 5.0, "crab"));
        actualProducts.add(new Chips("Чипсы Lays", 80.0, 3.0, "cheese"));

        ArrayList<Product> expectedProducts = pr.getAllProducts();
        Assert.assertEquals(expectedProducts, actualProducts);
    }

    public void makeExpectedOrders() {
        ArrayList<Product> expectedProducts2 = new ArrayList<>();
        expectedProducts2.add(new WashingMashine("стиральная машина Sumsung", 10.0, 1.0, true));
        expectedProducts2.add(new Chips("Чипсы Lays", 190.0, 5.0, "cheese"));
        expectedProducts2.add(new WashingMashine("стиральная машина Zegbee", 180.0, 7.0, false));
        expectedOrder13 = new Order(expectedProducts2, "2025-01-13");

        ArrayList<Product> expectedProducts1 = new ArrayList<>();
        expectedProducts1.add(new WashingMashine("стиральная машина Zegbee", 180.0, 7.0, true));
        expectedProducts1.add(new WashingMashine("стиральная машина Anno", 90.0, 3.0, true));
        expectedOrder26 = new Order(expectedProducts1, "2024-12-26");

        ArrayList<Product> expectedProducts3 = new ArrayList<>();
        expectedProducts3.add(new Chips("Чипсы Lays", 190.0, 5.0, "cheese"));
        expectedProducts3.add(new WashingMashine("стиральная машина Anno", 190.0, 13.0, true));
        expectedOrder14 = new Order(expectedProducts3, "2025-01-14");

        ArrayList<Product> expectedProducts4 = new ArrayList<>();
        expectedProducts4.add(new Chips("Чипсы Lays", 190.0, 5.0, "cheese"));
        expectedProducts4.add(new Chips("Чипсы Lays", 100.0, 4.0, "crab"));
        expectedProducts4.add(new WashingMashine("стиральная машина Anno", 90.0, 3.0, false));
        expectedProducts4.add(new WashingMashine("стиральная машина Zegbee", 180.0, 7.0, true));
        expectedOrder15 = new Order(expectedProducts4, "2025-01-15");
    }
}
