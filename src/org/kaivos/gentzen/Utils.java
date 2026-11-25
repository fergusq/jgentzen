package org.kaivos.gentzen;

import java.util.stream.Collectors;

import java.util.Map;
import java.util.stream.Collector;

public final class Utils {
    public static <T extends Map.Entry<? extends K, ? extends U>, K, U> Collector<T, ?, Map<K, U>> fromEntries() {
        return Collectors.<T, K, U>toMap(Map.Entry::getKey, Map.Entry::getValue);
    }

    public static String parseString(String string) {
        if (!string.startsWith("\"") || !string.endsWith("\"")) {
            throw new RuntimeException("cannot parse string " + string);
        }
        string = string.substring(1, string.length()-1);
        String result = "";
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == '\\') {
                i++;
                result += switch (string.charAt(i)) {
                    case 'n' -> "\n";
                    case 'r' -> "\r";
                    case 't' -> "\t";
                    default -> string.charAt(i);
                };
            } else {
                result += string.charAt(i);
            }
        }
        return result;
    }
}
