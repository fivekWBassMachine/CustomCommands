package net.ddns.fivek.customcommands.utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Utils {
    public static String arrayToString(String[] array) {
        Iterator iterator = Arrays.asList(array).iterator();
        String response = "";
        while (iterator.hasNext()) {
            response += iterator.next() + " ";
        }
        return response;
    }

    public static List<String> arrayToArryList(String[] array) {
        Iterator iterator = Arrays.asList(array).iterator();
        List<String> response = new ArrayList<String>();
        while (iterator.hasNext()) {
            response.add(iterator.next().toString());
        }
        return response;
    }
}