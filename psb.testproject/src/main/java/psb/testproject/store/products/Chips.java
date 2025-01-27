package psb.testproject.store.products;

import java.util.Date;

public class Chips extends Product {
    String taste;
    public Chips(String name, double weight, double price, String taste){
        this.name = name;
        this.weight = weight;
        this.price = price;
        this.taste = taste;
    }
    @Override
    public String toString(){
        return "\nНазвание = " + name +
                ";\n вкус = " + taste +
                ";\n вес = " + weight +
                ";\n цена = " + price;
    }

    public String getTaste() {
        return taste;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true; // Сравниваем ссылки на один и тот же объект
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false; // Проверяем null и тип объекта
        }
        Chips product = (Chips) obj; // Приводим объект к типу Product
        return Double.compare(product.weight, weight) == 0 &&
                Double.compare(product.price, price) == 0 &&
                taste.equals(product.taste) &&
                name.equals(product.name); // Сравниваем поля
    }
}
