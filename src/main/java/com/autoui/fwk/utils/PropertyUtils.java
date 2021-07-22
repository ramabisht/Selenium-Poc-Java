package com.autoui.fwk.utils;

import com.autoui.fwk.reporting.Logger;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertyUtils {

    private static final Logger _logger = Logger.getLogger(PropertyUtils.class);


    public static Properties readProperties(String fileName) {
        Properties p = null;
        try {
            FileReader reader = new FileReader(fileName);
            p = new Properties();
            p.load(reader);
        } catch (FileNotFoundException fileNotFoundException) {
            _logger.error("Failed to find the file with provided address " + fileName);
        } catch (IOException ioException) {
            _logger.error("Failed to load the property file from location " + fileName);
        }

        return p;
    }

    ;
}
