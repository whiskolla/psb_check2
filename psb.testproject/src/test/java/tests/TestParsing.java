package tests;

import org.junit.Before;
import org.junit.Test;
import psb.testproject.store.handling.ParseJSON;
import psb.testproject.store.handling.Processor;
import psb.testproject.store.log.ConsoleLogger;
import psb.testproject.store.products.Order;

import java.util.Date;

public class TestParsing {
    ParseJSON pj;
    Processor pr;
    ConsoleLogger logger = new ConsoleLogger();
    String ordersPath = "C:\\Users\\Anastasia\\IdeaProjects\\psb.testproject\\src\\main\\java\\psb\\testproject\\orders.json";
    String productsPath = "C:\\Users\\Anastasia\\IdeaProjects\\psb.testproject\\src\\main\\java\\psb\\testproject\\products.json";

    @Before
    public void setUp() throws Exception {
        pj = new ParseJSON();
        pr = new Processor();
    }

    @Test
    public void addOrderToJson(){
        Order order = new Order(pr.getAllProducts(), new Date());
        pj.addOrderToJson(ordersPath, order);
    }

    @Test
    public void parseProductsFromJSON(){
        pj.parseProductsFromJSON(productsPath);
    }

    @Test
    public void parseOrdersFromJSON(){
        pj.parseOrdersFromJSON(ordersPath);
    }
}
