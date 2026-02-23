import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        
        while (isRunning) {
            System.out.println("1. Проверка попадания точки в заштрихованную область");
            // System.out.println("2. Вычисление выражения с разными типами данных");
            System.out.println("3. Запустить внутренние тесты для задачи 1");
            System.out.println("0. Выход");

            int choice = getValidInt(scanner, "Выберите пункт меню (0-3): ");
            switch (choice) {
            case 1:
                runAreaCheck(scanner);
                break;
            // case 2:
            //     calculateExpression();
            //     break;
            case 3:
                runAreaTests();
                break;
            case 0:
                System.out.println("Завершение работы программы.");
                isRunning = false;
                break;
            default:
                System.out.println("Ошибка: неверный пункт меню.");
        }
        }

       
          scanner.close();
    }

    private static boolean isPointInArea(double x, double y) {
        return (x * x + y * y <= 1) && (y >= -x - 1);
    }

    private static void runAreaCheck(Scanner scanner) {
        double x = getValidDouble(scanner, "Введите координату X: ");
        double y = getValidDouble(scanner, "Введите координату Y: ");

        boolean result = isPointInArea(x, y);
        if (result) {
            System.out.println("Точка принадлежит области.");
        } else {
            System.out.println("Точка не принадлежит области");
        }
    }

    // private static void calculateExpression() {

    // }

    // Вспомогательные методы для безопасного ввода
    private static double getValidDouble(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();

            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Ошибка ввода, ожидается вещественное число.");
            }
        }
    }

    private static int getValidInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Ошибка ввода, ожидается целое число.");
            }
        }
    }

    // Автоматизированные тесты для первой задачи
    private static void runAreaTests() {
        System.out.println("Запуск тестов для isPointInArea:");

        testAssert("Тест 1 (0, 0)", isPointInArea(0, 0), true);
        testAssert("Тест 2 (2, 2)", isPointInArea(2, 2), false);
        testAssert("Тест 3 (-0.8, -0.8)", isPointInArea(-0.8, -0.8), false);
        testAssert("Тест 4 (-0.5, -0.5)", isPointInArea(-0.5, -0.5), true);
        testAssert("Тест 5 (1, 1)", isPointInArea(0, 0), true);

        System.out.println("Тестирование завершено.");
    }

    private static void testAssert(String testName, boolean actual, boolean expected) {
        if (actual == expected) {
            System.out.println("[УСПЕХ] " + testName);
        } else {
            System.out.println("[ОШИБКА] " + testName + ". Ожидалось: " + expected + ", получено: " + actual);
        }
    }
}

