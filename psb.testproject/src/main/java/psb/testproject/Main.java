package psb.testproject;

import psb.testproject.store.handling.Processor;
import psb.testproject.store.log.ConsoleLogger;
import psb.testproject.timer.ITimer;
import psb.testproject.timer.Timer;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        Timer timer = new Timer();
        Processor pr = new Processor();
        ConsoleLogger logger = new ConsoleLogger();

        int menu, editmenu;

        while (true) {
            printMenu();
            menu = in.nextInt();
            switch (menu) {
                case (1):
                    pr.printOrderWithSumWeightAndPrice();
                    logger.logInfo("товары выведены");
                    break;
                case (2):
                    pr.createOptimOrder();
                    break;
                case (3):
                    pr.getOptimOrder();
                    break;
                case (4):
                    pr.getMinProductByPrice();
                    logger.logInfo("ВСЕ ОК");
                    break;
                case (5):
                    pr.generateOrder();
                    logger.logInfo("ВСЕ ОК");
                    break;
                case (6):
                    timer.start();
                        System.out.println("Введите сумму:");
                        int sum = in.nextInt();
                    timer.stop(new ITimer() {
                        @Override
                        public void time(long time) {
                            logger.log(time + " мс шло обдумывание суммы");
                        }
                    });
                    pr.generateOrderBySum(sum);
                    logger.logInfo("ВСЕ ОК");
                    break;
                case (7):
                    timer.start();
                    System.out.println("Введите суммы (от и до):");
                    int sumStart = in.nextInt();
                    sum = in.nextInt();
                    timer.stop(new ITimer() {
                        @Override
                        public void time(long time) {
                            logger.log(time + " мс шло обдумывание двух сумм");
                        }
                    });
                    pr.generateOrderBySum(sumStart, sum);
                    logger.logInfo("ВСЕ ОК");
                    break;
                case (8):
                    timer.start();
                    System.out.println("Введите кл-во):");
                    int count = in.nextInt();
                    timer.stop(new ITimer() {
                        @Override
                        public void time(long time) {
                            logger.log(time + " мс шло обдумывание количества");
                        }
                    });
                    pr.generateOrderByCount(count);
                    logger.logInfo("ВСЕ ОК");
                    break;
                case (9):
                    loop: while (true) {
                        printEditMenu();
                        editmenu = in.nextInt();
                        switch (editmenu) {
                            case (1):
                                System.out.println("изменить название");
                                logger.logWarning("Название будет навсегда изменено");
                                pr.editName(in);
                                break;
                            case (2):
                                System.out.println("изменить цену");
                                logger.logWarning("Название будет навсегда изменено");
                                pr.editPrice(in);
                                break;
                            case (3):
                                System.out.println("изменить вес");
                                logger.logWarning("Название будет навсегда изменено");
                                pr.editWeight(in);
                                break;
                            case (4):
                                logger.log("выход в меню");
                                break loop;
                            default:
                                logger.logError("Введено неправильное число!");
                        }
                    }
                    break;
                case (10):
                    logger.log("ПРОГРАММА ЗАВЕРШЕНА");
                    System.exit(10);
                default:
                    logger.logError("Введено неправильное число!");
            }
        }
    }
    public static void printMenu() {
        System.out.println(
                "\nВЫБОР ДЕЙСТВИЯ:\n1. Показать заказ на все товары, общую стоимость и вес\n" +
                "2. Рассчитать оптимальный заказ\n" +
                "3. Вывести оптимальный заказ на экран\n" +
                "4. Показать самые дешевые товары\n" +
                "5. Сгенерировать заказ\n" +
                "6. Сгенерировать заказ до суммы\n" +
                "7. Сгенерировать заказ от суммы до суммы\n" +
                "8. Сгенерировать заказ по количеству товаров\n" +
                "9. Отредактировать товар\n" +
                "10. Выйти из приложения");
    }
    public static void printEditMenu() {
        System.out.println(
                "\nВЫБОР ДЕЙСТВИЯ:\n1. Изменить название\n" +
                        "2. Изменить вес\n" +
                        "3. Изменить цену\n" +
                        "4. Выйти в главное меню");
    }
}