package org.example.GUI.Components.FormProducts;

public class ValidateInp {

    // Kiểm tra xem một chuỗi có rỗng hay không
    public static boolean isNotEmpty(String input) {
        return input != null && !input.trim().isEmpty();
    }

    // Kiểm tra xem một chuỗi có phải là số hợp lệ hay không
    public static boolean isValidNumber(String input) {
        if (input == null || input.trim().isEmpty()) {
            return false;
        }
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Kiểm tra xem một chuỗi có phải là số nguyên hợp lệ hay không
    public static boolean isValidInteger(String input) {
        if (input == null || input.trim().isEmpty()) {
            return false;
        }
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}