package com.andrewsha.marketplace.utils;

import java.util.HashMap;
import java.util.Map;
import com.andrewsha.marketplace.user.User;


public class ClassnameMapper {
    private static Map<String, Class<?>> classnameMap = new HashMap<String, Class<?>>();
    static {
        classnameMap.put("User", User.class);
    }

    public static Class<?> getClass(String className) {
        return classnameMap.get(className);
    }
}
