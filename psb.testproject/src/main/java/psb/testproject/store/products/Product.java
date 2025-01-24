package psb.testproject.store.products;

public class Product {
    String name;
    double weight;
    double price;
    public Product(){}
    public Product(String name, double weight, double price){
        this.name = name;
        this.weight = weight;
        this.price = price;
    }

    public double getWeight() {
        return weight;
    }
    public double getPrice() {
        return price;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString(){
        return "\nНазвание = " + name +
                ";\n вес = " + weight +
                ";\n цена = " + price;
    }
}
