package psb.testproject.store.handling;

public interface IGeneratable { //генератор заказов
    void generateOrder(); //случайный
    void generateOrderBySum(double maxSum, double minSum) throws Exception; //заказ от суммы до суммы
    void generateOrderBySum(double maxSum); //заказ не больше суммы
    void generateOrderByCount(int maxCount); // кол-во <= maxSum
}
