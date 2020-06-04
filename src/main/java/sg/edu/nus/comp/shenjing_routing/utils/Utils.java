package sg.edu.nus.comp.shenjing_routing.utils;

import java.util.Arrays;

public class Utils {
    public static void fill(Object[][] array, Object element){
        for (int i = 0; i < array.length; i++) {
            Arrays.fill(array[i], element);
        }
    }

    public static void fill(boolean[][] array, boolean element){
        for (int i = 0; i < array.length; i++) {
            Arrays.fill(array[i], element);
        }
    }
}
