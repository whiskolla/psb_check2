package psb.testproject.store.handling;

import psb.testproject.store.products.Order;
import psb.testproject.store.products.Product;

import java.util.ArrayList;

public interface IProcessable {
    void printOrderWithSumWeightAndPrice();
    Order createOptimOrder();
    ArrayList<Product> getMinProductByPrice();
    void getOptimOrder();
}
