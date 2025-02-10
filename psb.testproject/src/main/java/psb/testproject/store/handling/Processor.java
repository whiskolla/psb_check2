package psb.testproject.store.handling;

import psb.testproject.store.products.Product;
import psb.testproject.store.log.ConsoleLogger;
import psb.testproject.store.products.Chips;
import psb.testproject.store.products.Order;
import psb.testproject.store.products.WashingMashine;

import java.util.*;
import java.util.stream.Collectors;

public class Processor implements IProcessable, IGeneratable, IEditable {
    ConsoleLogger logger = new ConsoleLogger();
    ArrayList<Product> allProducts;
    ArrayList<Order> orders;
    Order optimOrder;
    ArrayList<Product> generatedOrder = new ArrayList<>();
    ArrayList<WashingMashine> washingMashines = new ArrayList<>();
    ArrayList<Chips> chipses = new ArrayList<>();
    ParseJSON parseJSON = new ParseJSON();
    String pathProducts = "C:\\Users\\Anastasia\\IdeaProjects\\psb_check2\\psb.testproject\\src\\main\\java\\psb\\testproject\\products.json";
    String pathOrders = "C:\\Users\\Anastasia\\IdeaProjects\\psb_check2\\psb.testproject\\src\\main\\java\\psb\\testproject\\orders.json";

    public Processor() {
        allProducts = parseJSON.parseProductsFromJSON(pathProducts);
        orders = parseJSON.parseOrdersFromJSON(pathOrders);

        for (int i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getClass().equals(TypesOfProducts.WASHINGMASHINE.getClaass())) {
                washingMashines.add((WashingMashine) allProducts.get(i));
            }
            if (allProducts.get(i).getClass().equals(TypesOfProducts.CHIPSES.getClaass())) {
                chipses.add((Chips) allProducts.get(i));
            }
        }
    }

    //по меню
    @Override
    public void printOrderWithSumWeightAndPrice() {
        int sumWeight = 0;
        int sumPrice = 0;
        for (Product p : allProducts) {
            sumWeight += p.getWeight();
            sumPrice += p.getPrice();
            System.out.println(p);
        }
        System.out.println("\nобщий вес продуктов - " + sumWeight + "\nобщая цена продуктов - " + sumPrice);
    }

    @Override
    public Order createOptimOrder() {
        ArrayList<Product> optimOrder = new ArrayList<>();
        optimOrder.add(getOptimProductByPrice(washingMashines));
        optimOrder.add(getOptimProductByPrice(chipses));
        this.optimOrder = new Order(optimOrder, new Date());
        logger.logInfo("Заказ сформирован");
        return this.optimOrder;
    }

    @Override
    public void getOptimOrder() {
        if (optimOrder.isEmpty()) {
            System.out.println("No created optimal order");
        } else {
            System.out.println("Optimal order: ");
            for (Product p : optimOrder.getProducts()) {
                System.out.println(p.toString());
            }
        }
    }

    @Override
    public ArrayList<Product> getMinProductByPrice() {
        ArrayList<Product> prs = new ArrayList<>();
        Product pr = sortMinPrice(washingMashines);
        System.out.println("wm: " + pr);
        prs.add(pr);
        pr = sortMinPrice(chipses);
        System.out.println("ch: " + pr);
        prs.add(pr);
        return prs;
    }

    //генерирование данных
    @Override
    public Order generateOrder() {
        generatedOrder.clear();
        int count = (int) (Math.random() * (allProducts.size() - 1 + 1) + 1);
        System.out.println("count: " + count);
        for (int i = 0; i < count; i++) {
            int num = (int) (Math.random() * (allProducts.size() - 1 + 1) + 0);
            System.out.println("num: " + num);
            if (!(generatedOrder.contains(allProducts.get(num)))) {
                generatedOrder.add(allProducts.get(num));
                System.out.println("added: " + allProducts.get(num));
            }
        }
        ParseJSON parseJSON = new ParseJSON();
        Order newOrder = new Order(generatedOrder, new Date());
        parseJSON.addOrderToJson("C:\\Users\\Anastasia\\IdeaProjects\\psb_check2\\psb.testproject\\src\\main\\java\\psb\\testproject\\orders.json", newOrder);
        return newOrder;
    }

    @Override
    public Order generateOrderBySum(double maxSum) {
        generatedOrder.clear();
        double orderSum = 0;
        boolean isAdd = true;
        while (isAdd) {
            int num = (int) (Math.random() * (allProducts.size() - 1 + 1) + 0);
            if (maxSum - orderSum >= allProducts.get(num).getPrice()) {
                orderSum += allProducts.get(num).getPrice();
                if (!(generatedOrder.contains(allProducts.get(num)))) {
                    generatedOrder.add(allProducts.get(num));
                    System.out.println("added: " + allProducts.get(num));
                }
            } else {
                isAdd = false;
            }
        }
        ParseJSON parseJSON = new ParseJSON();
        Order newOrder = new Order(generatedOrder, new Date());
        parseJSON.addOrderToJson("C:\\Users\\Anastasia\\IdeaProjects\\psb_check2\\psb.testproject\\src\\main\\java\\psb\\testproject\\orders.json", newOrder);
        return newOrder;
    }

    @Override
    public Order generateOrderBySum(double minSum, double maxSum) throws Exception {
        if (minSum >= maxSum) {
            throw new Exception("мин сумма больше макс");
        }
        generatedOrder.clear();
        double orderSum = 0;
        boolean isAdd = true;
        while (isAdd) {
            int num = (int) (Math.random() * (allProducts.size() - 1 + 1) + 0);
            if (maxSum - orderSum >= allProducts.get(num).getPrice()) {
                orderSum += allProducts.get(num).getPrice();
                if (!(generatedOrder.contains(allProducts.get(num)))) {
                    generatedOrder.add(allProducts.get(num));
                    System.out.println("added: " + allProducts.get(num));
                }
            } else {
                if (minSum <= orderSum) {
                    isAdd = false;
                } else {
                    System.out.println("deleted: " + allProducts.get(num).getName());
                    generatedOrder.remove(allProducts.get(num));
                    orderSum -= allProducts.get(num).getPrice();
                }
            }
        }
        System.out.println("orderSum: " + orderSum);
        ParseJSON parseJSON = new ParseJSON();
        Order newOrder = new Order(generatedOrder, new Date());
        parseJSON.addOrderToJson("C:\\Users\\Anastasia\\IdeaProjects\\psb_check2\\psb.testproject\\src\\main\\java\\psb\\testproject\\orders.json", newOrder);
        return newOrder;
    }

    @Override
    public Order generateOrderByCount(int maxCount) {
        generatedOrder.clear();
        int count = (int) (Math.random() * (maxCount) + 1);
        System.out.println("count: " + count);
        for (int i = 0; i < count; i++) {
            int num = (int) (Math.random() * (allProducts.size() - 1 + 1) + 0);
            System.out.println("num: " + num);
            if (!(generatedOrder.contains(allProducts.get(num)))) {
                generatedOrder.add(allProducts.get(num));
                System.out.println("added: " + allProducts.get(num));
            }
        }
        ParseJSON parseJSON = new ParseJSON();
        Order newOrder = new Order(generatedOrder, new Date());
        parseJSON.addOrderToJson("C:\\Users\\Anastasia\\IdeaProjects\\psb_check2\\psb.testproject\\src\\main\\java\\psb\\testproject\\orders.json", newOrder);
        return newOrder;
    }

    //редактирование
    @Override
    public Product editName(Scanner in) {
        try {
            for (int i = 0; i < allProducts.size(); i++) {
                System.out.println(i + 1 + ": " + allProducts.get(i).getName());
            }
            System.out.println("Выбрать товар для смены:");
            int i = in.nextInt() - 1;
            if (i >= allProducts.size()) {
                logger.logError("Слишком большое число");
            } else {
                System.out.println("Новое название:" + in.nextLine());
                String name = in.nextLine();
                if (name.isEmpty()) {
                    logger.logError("не введено имя");
                    throw new Exception();
                } else {
                    Product newP;
                    if (allProducts.get(i).getClass().equals(TypesOfProducts.WASHINGMASHINE.getClaass())) {
                        newP = new WashingMashine(name, allProducts.get(i).getWeight(), allProducts.get(i).getPrice(), ((WashingMashine) allProducts.get(i)).isIfDryer());
                    } else if (allProducts.get(i).getClass().equals(TypesOfProducts.CHIPSES.getClaass())) {
                        newP = new Chips(name, allProducts.get(i).getWeight(), allProducts.get(i).getPrice(), ((Chips) allProducts.get(i)).getTaste());
                    } else {
                        newP = new Product(name, allProducts.get(i).getWeight(), allProducts.get(i).getPrice());
                    }
                    allProducts.remove(i);
                    allProducts.add(newP);
                    //parseJSON.editProductToJson(pathProducts, allProducts);
                    System.out.println("Новый товар:\n" + newP);
                    return newP;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Product editPrice(Scanner in) {
        for (int i = 0; i < allProducts.size(); i++) {
            System.out.println(i + 1 + ": " + allProducts.get(i).getName());
            System.out.println(i + 1 + ": " + allProducts.get(i).getPrice());
        }
        System.out.println("Выбрать товар для смены:");
        int i = in.nextInt() - 1;
        if (i >= allProducts.size()) {
            logger.logError("Слишком большое число");
        } else {
            System.out.println("Новая цена:");
            try {
                double price = in.nextDouble();
                System.out.println("price: " + price);
                if (price <= 0) {
                    throw new Exception("неверный ввод, цена не может быть меньше 0");
                } else {
                    allProducts.get(i).setPrice(price);
                    return allProducts.get(i);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

    @Override
    public Product editWeight(Scanner in) {
        for (int i = 0; i < allProducts.size(); i++) {
            System.out.println(i + 1 + ": " + allProducts.get(i).getName());
            System.out.println(i + 1 + ": " + allProducts.get(i).getWeight());
        }
        System.out.println("Выбрать товар для смены:");
        int i = in.nextInt() - 1;
        if (i >= allProducts.size()) {
            logger.logError("Слишком большое число");
        } else {
            System.out.println("Новый вес:");
            try {
                double weight = in.nextDouble();
                System.out.println("weight: " + weight);
                if (weight <= 0) {
                    throw new Exception("неверный ввод, вес не может быть меньше 0");
                } else {
                    allProducts.get(i).setWeight(weight);
                    return allProducts.get(i);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

    //сортировка заказов
    public List<Order> sortingByMinPrice(ArrayList<Order> order, double minPrice) { //больше заданной
        System.out.println("-------------------БОЛЬШЕ-МИН-ЦЕНЫ-" + minPrice + "----------------");
        return order.stream()
                .filter(ord -> (ord.getSumPrice() > minPrice))
                .collect(Collectors.toList());
    }

    public List<Order> sortingByMaxPrice(ArrayList<Order> order, double maxPrice) { //меньше заданной
        System.out.println("-------------------МЕНЬШЕ-МАКС-ЦЕНЫ-" + maxPrice + "-------------------");
        return order.stream()
                .filter(ord -> (ord.getSumPrice() < maxPrice))
                .collect(Collectors.toList());
    }

    public List<Order> sortingByWeight(ArrayList<Order> order) {
        System.out.println("--------------------SORTING-BY-WEIGHT---------------");
        return order.stream()
                .sorted(Comparator.comparingDouble(Order::getSumWeight))
                .collect(Collectors.toList());
    }

    public List<Order> sortingByDate(ArrayList<Order> order, Date date) {
        System.out.println("--------------------SORTING-BY-DATE---------------");
        return order.stream()
                .sorted(Comparator.comparing(Order::getDelivering))
                .filter(ord -> (ord.getDelivering().compareTo(date) <= 0))
                .collect(Collectors.toList());
    }

    //вспомогательные
    public Product getOptimProductByPrice(List<? extends Product> products) {
        double sumPrice = 0;
        for (Product p : products) {
            sumPrice += p.getPrice();
        }
        double avgPrice = sumPrice / products.size();
        double avg = Math.abs(products.get(0).getPrice() - avgPrice);
        int id = 0;
        for (int i = 1; i < products.size(); i++) {
            if (avg > Math.abs(products.get(i).getPrice() - avgPrice)) {
                avg = Math.abs(products.get(i).getPrice() - avgPrice);
                id = i;
            }
        }
        return products.get(id);
    }

    public List<Product> sortingByUniqName(ArrayList<Product> products) {
        Set<String> names = new HashSet<>();
        return products.stream()
                .filter(p -> names.add(p.getName()))
                .collect(Collectors.toList());
    }

    public ArrayList<Product> sortingByAlphName(ArrayList<Product> products) { //алфавитный порядок
        ArrayList<Product> sortedProducts = (ArrayList<Product>) products.stream()
                .sorted(Comparator.comparing(Product::getName))
                .collect(Collectors.toList());
        return sortedProducts;
    }

    public Product sortMinPrice(List<? extends Product> products) { //id минимальной цены у товара
        double min = products.get(0).getPrice();
        int id = 0;
        for (int i = 1; i < products.size(); i++) {
            if (min > products.get(i).getPrice()) {
                min = products.get(i).getPrice();
                id = i;
            }
        }
        return products.get(id);
    }

    public ArrayList<Product> getAllProducts() {
        return allProducts;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }
}







