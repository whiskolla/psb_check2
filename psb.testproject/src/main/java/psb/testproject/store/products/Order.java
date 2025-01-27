package psb.testproject.store.products;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.text.ParseException;
import java.util.Objects;

public class Order {
    ArrayList<Product> products;
    double sumWeight = 0;
    double sumPrice = 0;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    Date delivering;

    public Order(ArrayList<Product> products, Date delivering) {
        this.products = products;
        this.delivering = delivering;
        calculateSumAndWeight();
    }

    public Order(ArrayList<Product> products, String delivering) {
        this.products = products;
        calculateSumAndWeight();
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            this.delivering = dateFormat.parse(delivering);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void calculateSumAndWeight() {
        for (Product p : products) {
            sumWeight += p.getWeight();
            sumPrice += p.getPrice();
        }
    }

    public void printProductsInOrder() {
        for (Product p : products) {
            System.out.println(p.toString());
        }
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public boolean isEmpty() {
        return products.isEmpty();
    }

    public double getSumPrice() {
        return sumPrice;
    }

    public double getSumWeight() {
        return sumWeight;
    }

    public Date getDelivering() {
        return delivering;
    }

    @Override
    public String toString(){
        return "\nproducts: " + products +
                "\nsumWeight: " + sumWeight +
                "\nsumPrice: " + sumPrice +
                "\ndelivering: " + delivering;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Order order = (Order) obj;
        return Double.compare(order.sumWeight, sumWeight) == 0 &&
                Double.compare(order.sumPrice, sumPrice) == 0 &&
                Objects.equals(order.products, products) &&
                Objects.equals(order.delivering, delivering);
    }
}
