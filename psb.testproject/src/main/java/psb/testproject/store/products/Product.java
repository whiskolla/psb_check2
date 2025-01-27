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
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Product product = (Product) obj;
        return Double.compare(product.weight, weight) == 0 &&
                Double.compare(product.price, price) == 0 &&
                name.equals(product.name);
    }
}
