package util;

public class StarterMenuValidator {

    public static boolean isValidNumericInput(String number) {
        return number.matches("\\d+");
    }

    public static boolean isValidPositiveNumber(String number) {
        if (isValidNumericInput(number)) {
            int num = Integer.parseInt(number);
            return num > 0;
        }
        return false;
    }

    public static boolean validateMenu(String number) {
        return isValidPositiveNumber(number);
    }
}