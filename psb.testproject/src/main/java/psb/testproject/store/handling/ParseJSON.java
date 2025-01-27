package psb.testproject.store.handling;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import psb.testproject.store.products.Chips;
import psb.testproject.store.products.Order;
import psb.testproject.store.products.Product;
import psb.testproject.store.products.WashingMashine;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ParseJSON {
    public void addOrderToJson(String filePath, Order order) {
        ArrayList<Order> orders = parseOrdersFromJSON(filePath);
        orders.add(order);

        JSONArray orderArray = new JSONArray();
        for (Order or : orders) {
            orderArray.add(makeJSONOrder(or));
        }

        try (FileWriter file = new FileWriter(filePath)) {
            file.write(orderArray.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Product> parseProductsFromJSON(String filePath) {
        ArrayList<Product> products = new ArrayList<>();
        JSONParser parser = new JSONParser();
        try {
            JSONArray prs = (JSONArray) parser.parse(new FileReader(filePath));
            for (Object product : prs) {
                JSONObject jsonProduct = (JSONObject) ((JSONObject) product).get("product");

                String name = (String) jsonProduct.get("name");
                double weight = (Double) jsonProduct.get("weight");
                double price = (Double) jsonProduct.get("price");

                JSONObject extraDetails = (JSONObject) jsonProduct.get("extraDetails");

                if (extraDetails == null) {
                    Product newpr = new Product(name, weight, price);
                    products.add(newpr);
                } else if (extraDetails.containsKey("ifDryer")) {
                    boolean ifDryer = (boolean) extraDetails.get("ifDryer");
                    WashingMashine newpr = new WashingMashine(name, weight, price, ifDryer);
                    products.add(newpr);
                } else if (extraDetails.containsKey("taste")) {
                    String taste = (String) extraDetails.get("taste");
                    Chips newpr = new Chips(name, weight, price, taste);
                    products.add(newpr);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Не удалось открыть файл");
        } catch (IOException | ParseException e) {
            System.out.println("product: ");
            e.printStackTrace();
        }
        return products;
    }

    public ArrayList<Order> parseOrdersFromJSON(String filePath) {
        ArrayList<Order> orders = new ArrayList<>();
        JSONParser parser = new JSONParser();

        try {
            JSONArray orderArr;
            if ((((JSONArray) parser.parse(new FileReader(filePath))).isEmpty())) {
                orderArr = new JSONArray();
            } else {
                orderArr = (JSONArray) parser.parse(new FileReader(filePath));
            }

            for (Object item : orderArr) { //one order
                String delivering = (String) ((JSONObject) item).get("delivering");

                JSONObject jsonProducts = (JSONObject) item;
                JSONArray productArr = (JSONArray) jsonProducts.get("products");
                ArrayList<Product> products = new ArrayList<>(); //products in one order


                for (Object product : productArr) { //one product
                    JSONObject jsonProduct = (JSONObject) ((JSONObject) product).get("product");

                    String name = (String) jsonProduct.get("name");
                    double weight = (Double) jsonProduct.get("weight");
                    double price = (Double) jsonProduct.get("price");

                    JSONObject extraDetails = (JSONObject) jsonProduct.get("extraDetails");

                    if (extraDetails == null) {
                        Product newpr = new Product(name, weight, price);
                        products.add(newpr);
                    } else if (extraDetails.containsKey("ifDryer")) {
                        boolean ifDryer = (boolean) extraDetails.get("ifDryer");
                        WashingMashine newpr = new WashingMashine(name, weight, price, ifDryer);
                        products.add(newpr);
                    } else if (extraDetails.containsKey("taste")) {
                        String taste = (String) extraDetails.get("taste");
                        Chips newpr = new Chips(name, weight, price, taste);
                        products.add(newpr);
                    }
                }
                orders.add(new Order(products, delivering));
            }
        } catch (FileNotFoundException e) {
            System.err.println("order: Не удалось найти файл");
        } catch (IOException | ParseException e) {
            System.out.println("order:");
            e.printStackTrace();
        }
        return orders;
    }

    public void editProductToJson(String filePath, ArrayList<Product> products) {
        JSONArray productArray = new JSONArray();
        for (Product pr : products) {
            productArray.add(makeJSONProduct(pr));
        }

        try (FileWriter file = new FileWriter(filePath)) {
            file.write(productArray.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //вспомогательные
    public JSONObject makeJSONOrder(Order order) {
        JSONObject ord = new JSONObject();
        ord.put("price", order.getSumPrice());
        ord.put("weight", order.getSumWeight());
        ord.put("delivering", new SimpleDateFormat("yyyy-MM-dd").format(order.getDelivering()));
        ord.put("products", makeJSONProducts(order.getProducts()));
        return ord;
    }

    public JSONArray makeJSONProducts(ArrayList<Product> prs) {
        JSONArray products = new JSONArray();
        try {
            for (Product pr : prs) {
                products.add(makeJSONProduct(pr));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    public JSONObject makeJSONProduct(Product product) {
        JSONObject pr = new JSONObject();
        try {
            JSONObject productDetails = new JSONObject();
            productDetails.put("name", product.getName());
            productDetails.put("weight", product.getWeight());
            productDetails.put("price", product.getPrice());
            if (product.getClass().equals(TypesOfProducts.WASHINGMASHINE.getClaass())) {
                JSONObject extraDetailswm = new JSONObject();
                extraDetailswm.put("ifDryer", ((WashingMashine) product).isIfDryer());
                productDetails.put("extraDetails", extraDetailswm);
            }
            if (product.getClass().equals(TypesOfProducts.CHIPSES.getClaass())) {
                JSONObject extraDetailsch = new JSONObject();
                extraDetailsch.put("taste", ((Chips) product).getTaste());
                productDetails.put("extraDetails", extraDetailsch);
            }
            pr.put("product", productDetails);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pr;
    }

}