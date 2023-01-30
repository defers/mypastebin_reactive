package com.defers.mypastebin.util;

import java.lang.reflect.InvocationTargetException;

public class ExceptionUtils {
    public static <T extends Exception> void throwException(Class<T> ex,
                                                            String msg,
                                                            Object... args) throws T {
        String msgException = MessagesUtils.getFormattedMessage(msg, args);
        T newEx = newInstanceException(ex, msgException);
        throw newEx;
    }

    private static <T extends Exception> T newInstanceException(Class<T> ex, String msgException) {
        T newEx = null;
        try {
            Class[] cArg = new Class[1];
            cArg[0] = String.class;
            newEx = ex.getConstructor(cArg).newInstance(msgException);
        }catch (NoSuchMethodException |
                IllegalAccessException |
                InvocationTargetException |
                IllegalArgumentException |
                InstantiationException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't create instance exception");
        }
        return newEx;
    }
}
