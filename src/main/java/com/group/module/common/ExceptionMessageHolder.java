package com.group.module.common;

import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class ExceptionMessageHolder {

    private static final Properties properties = new Properties();

    static {
        final InputStream in = ExceptionMessageHolder.class.getClassLoader().getResourceAsStream("errors/exception-message.properties");
        try {
            properties.load(in);
        } catch (Throwable e) {
            log.error("Exception Code Properties could not be loaded", e);
        }

    }

    public static final String getMessage(String code) {
        return properties.getProperty(code) == null ? code : properties.getProperty(code);
    }

}