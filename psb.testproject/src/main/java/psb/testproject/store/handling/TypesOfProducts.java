package psb.testproject.store.handling;

import psb.testproject.store.products.Chips;
import psb.testproject.store.products.Product;
import psb.testproject.store.products.WashingMashine;

enum TypesOfProducts {
    WASHINGMASHINE(WashingMashine.class),
    CHIPSES(Chips.class);

    TypesOfProducts(Class<? extends Product> Class) {
        this.Claass = Class;
    }

    private Class<?> Claass;

    public Class<?> getClaass() {
        return Claass;
    }
}
