package io.github.andres_vasquez.attendacelist.utils;

import java.util.regex.Pattern;

/**
 * Created by andresvasquez on 6/11/17.
 */

public class Utils {

    /**
     * Validation for names
     * @param name String name
     * @return true or false with regex
     */
    public static boolean isValidName(String name) {
        Pattern patron = Pattern.compile("^[a-zA-Z ]+$");
        return patron.matcher(name).matches();
    }
}
