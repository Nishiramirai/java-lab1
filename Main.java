import java.util.Scanner;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        
        while (isRunning) {
            System.out.println("1. Проверка попадания точки в заштрихованную область");
            System.out.println("2. Вычисление выражения с разными типами данных");
            System.out.println("3. Запустить внутренние тесты для задачи 1");
            System.out.println("0. Выход");

            int choice = getValidInt(scanner, "Выберите пункт меню (0-3): ");
            switch (choice) {
            case 1:
                runAreaCheck(scanner);
                break;
            case 2:
                calculateExpression();
                break;
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

    // Первое задание
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

    // Второе задание
    private static void calculateExpression() {
        System.out.println("Вычисляем значение выражения для a = 1000, b = 0.0001");

        float floatResult = calculateWithFloat(1000.0f, 0.0001f);
        double doubleResult = calculateWithDouble(1000.0, 0.0001);
        BigDecimal bigDecimalResult = calculateWithBigDecimal(new BigDecimal("1000.0"), new BigDecimal("0.0001"));

        System.out.println("Результат (float):      " + floatResult);
        System.out.println("Результат (double):     " + doubleResult);
        System.out.println("Результат (BigDecimal): " + bigDecimalResult);
    }

    private static float calculateWithFloat(float a, float b) {
        float sum4 = (a + b) * (a + b) * (a + b) * (a + b); // (a+b)^4
        float a4 = a * a * a * a; // a^4
        float b4 = b * b * b * b; // b^4
        
        // Формула: ((a+b)^4 - (a^4 + 6a^2b^2 + 4ab^3)) / (b^4 + 4a^3b)
        float numerator = sum4 - (a4 + 6 * a * a * b * b + 4 * a * b * b * b);
        float denominator = b4 + 4 * a * a * a * b;

        return numerator / denominator;
    }

    private static double calculateWithDouble(double a, double b) {
        // Формула: ((a+b)^4 - (a^4 + 6a^2b^2 + 4ab^3)) / (b^4 + 4a^3b)
        double numerator = Math.pow(a + b, 4) - (Math.pow(a, 4) + 6 * Math.pow(a, 2) * Math.pow(b, 2) + 4 * a * Math.pow(b, 3));
        double denominator = Math.pow(b, 4) + 4 * Math.pow(a, 3) * b;

        return numerator / denominator;
    }

    private static BigDecimal calculateWithBigDecimal(BigDecimal a, BigDecimal b) {
        // Константы из формулы (цифры 6 и 4)
        BigDecimal six = new BigDecimal("6");
        BigDecimal four = new BigDecimal("4");

        // Левая часть: (a + b)^4
        BigDecimal leftPart = a.add(b).pow(4); 
        
        // Правая часть (в скобках): a^4 + 6*a^2*b^2 + 4*a*b^3
        BigDecimal a4 = a.pow(4);
        BigDecimal a2b2_x6 = six.multiply(a.pow(2)).multiply(b.pow(2)); // 6a^2b^2
        BigDecimal ab3_x4 = four.multiply(a).multiply(b.pow(3));        // 4ab^3
        
        BigDecimal rightPart = a4.add(a2b2_x6).add(ab3_x4); 
        
        BigDecimal numerator = leftPart.subtract(rightPart);

        // Знаменатель. Формула: b^4 + 4*a^3*b
        BigDecimal b4 = b.pow(4);
        BigDecimal a3b_x4 = four.multiply(a.pow(3)).multiply(b); // 4a^3b
        
        BigDecimal denominator = b4.add(a3b_x4);

        // Делим числитель на знаменатель. 
        return numerator.divide(denominator, java.math.RoundingMode.HALF_UP);
    }

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

    // Автоматизированные тесты для первого задания
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

