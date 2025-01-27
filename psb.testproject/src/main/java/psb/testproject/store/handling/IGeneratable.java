package psb.testproject.store.handling;

import psb.testproject.store.products.Order;

public interface IGeneratable { //генератор заказов
    Order generateOrder(); //случайный
    Order generateOrderBySum(double maxSum, double minSum) throws Exception; //заказ от суммы до суммы
    Order generateOrderBySum(double maxSum); //заказ не больше суммы
    Order generateOrderByCount(int maxCount); // кол-во <= maxSum
}
