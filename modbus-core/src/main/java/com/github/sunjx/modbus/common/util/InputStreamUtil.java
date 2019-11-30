package com.github.sunjx.modbus.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;


public class InputStreamUtil {

    protected static String path = System.getProperty("user.dir");

    public static InputStream getStream(String resourceName) throws FileNotFoundException {
        InputStream input = null;
        input = getFileStream(path + "/" + resourceName);
        if (input == null) {
            input = getClassResourceStream(resourceName);
        }
        if (input == null) {
            input = getClassLoaderResourceStream(resourceName);
        }

        return input;
    }

    public static InputStream getFileStream(String resourceName) throws FileNotFoundException {
        if ((new File(resourceName)).exists()) {
            return new FileInputStream(resourceName);
        }
        return null;
    }

    public static InputStream getClassResourceStream(String resourceName) {
        return getClassResourceStream(InputStreamUtil.class, resourceName);
    }

    public static <T> InputStream getClassResourceStream(Class<T> cls, String resourceName) {
        return cls.getResourceAsStream(resourceName);
    }

    public static InputStream getClassLoaderResourceStream(String resourceName) {
        return InputStreamUtil.class.getClassLoader().getResourceAsStream(resourceName);
    }


}

