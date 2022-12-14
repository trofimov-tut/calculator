import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введи задачу");
        String input = scanner.nextLine();
        System.out.println(calc(input));
    }

    public static String calc(String input){
        String[] strings = input.split("\s+"); // strings - массив из символов задачки
        if (strings.length!=3){
            throw new RuntimeException("Это выражение не соотетствует требованиям");
            }
        String value1 = strings[0];
        String operation = strings[1];
        String value2 = strings[2];
        String output;
        try {
            int v1Arabic = Integer.parseInt(value1);
            int v2Arabic = Integer.parseInt(value2);
            int result = calculation(v1Arabic, v2Arabic, operation);
            output = String.valueOf(result);
        }
        catch (NumberFormatException e){
            int roman1 = romanToArabic(value1);
            int roman2 = romanToArabic(value2);
            int result = calculation(roman1, roman2, operation);
            output = arabicToRoman(result);

        }

        return output;
    }

    public static int calculation(int value1, int value2, String operation) {
        int result;
        if ((value1>10)||(value1<1)||(value2>10)||(value2<1)){
            throw new RuntimeException("Это выражение не соотетствует требованиям");
            }
        switch (operation){
            case "+":
                result = value1 + value2;
                break;
            case "-":
                result = value1 - value2;
                break;
            case "*":
                result = value1 * value2;
                break;
            case "/":
                result = value1 / value2;
                break;
            default:
                throw new RuntimeException("Это выражение не соотетствует требованиям");
        }
        return result;

    }
    public static int romanToArabic(String input) {
        String romanNumeral = input.toUpperCase();
        int result = 0;
        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();
        int i = 0;
        while ((romanNumeral.length() > 0) && (i < romanNumerals.size())) {
            RomanNumeral symbol = romanNumerals.get(i);
            if (romanNumeral.startsWith(symbol.name())) {
                result += symbol.getValue();
                romanNumeral = romanNumeral.substring(symbol.name().length());
            } else {
                i++;
            }
        }
        if (romanNumeral.length() > 0) {
            throw new RuntimeException("Это выражение не соотетствует требованиям");
        }
        return result;
    }
    public static String arabicToRoman(int number) {
        if ((number <= 0)) {
            throw new RuntimeException("т.к. в римской системе нет отрицательных чисел");
        }
        if (number > 4000) {
            throw new RuntimeException("т.к. число не в диапозоне [1;3999]");
        }

        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();

        int i = 0;
        StringBuilder sb = new StringBuilder();

        while ((number > 0) && (i < romanNumerals.size())) {
            RomanNumeral currentSymbol = romanNumerals.get(i);
            if (currentSymbol.getValue() <= number) {
                sb.append(currentSymbol.name());
                number -= currentSymbol.getValue();
            } else {
                i++;
            }
        }
        return sb.toString();
    }
    enum RomanNumeral {
        I(1), IV(4), V(5), IX(9), X(10),
        XL(40), L(50), XC(90), C(100);

        private int value;

        RomanNumeral(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static List<RomanNumeral> getReverseSortedValues() {
            return Arrays.stream(values())
                    .sorted(Comparator.comparing((RomanNumeral e) -> e.value).reversed())
                    .collect(Collectors.toList());
        }
    }
}
