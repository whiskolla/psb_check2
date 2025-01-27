package psb.testproject.store.handling;

import psb.testproject.store.products.Product;

import java.util.Scanner;

public interface IEditable {
    Product editName(Scanner in);
    Product editPrice(Scanner in);
    Product editWeight(Scanner in);
}
