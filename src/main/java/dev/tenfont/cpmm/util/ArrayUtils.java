package dev.tenfont.cpmm.util;

public class ArrayUtils {

    @SuppressWarnings("unchecked")
    public static <T> Class<? super T> getSuperclass(Class<? extends T>[] classes) {
        if (classes.length == 0)
            return Object.class;
        Class<? super T> currentSuperclass = null;
        for (Class<? extends T> c : classes) {
            if (currentSuperclass == null) {
                currentSuperclass = (Class<? super T>) c.getSuperclass();
                continue;
            }
            while (!currentSuperclass.isAssignableFrom(c))
                currentSuperclass = currentSuperclass.getSuperclass();
        }
        return currentSuperclass;
    }

}
