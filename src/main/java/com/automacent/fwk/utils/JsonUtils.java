package com.automacent.fwk.utils;

import com.automacent.fwk.reporting.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class JsonUtils {

    private static final Logger _logger = Logger.getLogger(JsonUtils.class);
    private static JSONObject jsonObject = new JSONObject();

    /**
     * Convert String to JSON pretty print
     *
     * @param jsonString JSON String to be converted
     * @return Pretty String
     */
    public static String getPrettyString(String jsonString) {
        try {
            JSONParser parser = new JSONParser();
            jsonObject = (JSONObject) parser.parse(jsonString);
            return jsonObject.toJSONString();
        } catch (ParseException e) {
            _logger.warn(String.format("Error parsing Json String as pretty %s ", jsonString), e);
            return "";
        }
    }

    /**
     * Method can be used to escape such reserved keywords in a JSON String
     *
     * @param jsonString JSON String to be converted
     * @return String
     */
    public static String escapeText(String jsonString) {
        try {
            return jsonObject.escape(jsonString);
        } catch (Exception e) {
            _logger.warn(String.format("Error escaping the text %s ", jsonString), e);
            return "";
        }
    }

    /**
     * Read data from File
     *
     * @param fileLocation
     * @return JSONObject
     */
    public JSONObject readJsonFromFile(String fileLocation) {
        try {
            _logger.info(String.format("Reading JSON data from file:", fileLocation));
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileLocation));
            jsonObject = (JSONObject) new Gson().fromJson(bufferedReader, HashMap.class);
        } catch (FileNotFoundException fileNotFoundException) {
            _logger.error(String.format("File not found at location %s ", fileLocation), fileNotFoundException);
        } catch (IOException ioException) {
            _logger.error(String.format("Error reading the data from file %s ", fileLocation), ioException);
        } catch (Exception exception) {
            try {
                jsonObject = (JSONObject) new JSONParser().parse(new FileReader(fileLocation));
            } catch (FileNotFoundException fileNotFoundException) {
                _logger.error(String.format("File not found at location %s ", fileLocation), fileNotFoundException);
            } catch (IOException ioException) {
                _logger.error(String.format("Error reading the data from file %s ", fileLocation), ioException);
            } catch (ParseException parseException) {
                _logger.error(String.format("Error parsing the data from file %s ", fileLocation), parseException);
                try {
                    jsonObject = (JSONObject) new JSONValue().parse(new FileReader(fileLocation));
                } catch (Exception exception1) {
                    _logger.error(String.format("Error getting the data from file %s ", fileLocation), exception1);
                }
            }
            _logger.error(String.format("Error getting the data from file %s ", fileLocation), exception);
        }
        return jsonObject;
    }

    public String getJsonString(Map<String, String> inputMap) throws JsonProcessingException {
        String convertedValue = "";
        try {
            convertedValue = new ObjectMapper().writeValueAsString(inputMap);
        } catch (JsonProcessingException jsonProcessingException) {
            _logger.error(String.format("Error getting the data from map %s ", inputMap), jsonProcessingException);
        }

        return convertedValue;
    }


    /**
     * Get JSONObject into HashMap
     *
     * @param json
     * @return
     */
    public static Map<String, Object> jsonToMap(JSONObject json) {
        Map<String, Object> retMap = new HashMap<String, Object>();

        if (json != null) {
            retMap = toMap(json);
        }
        return retMap;
    }

    public static Map<String, Object> toMap(JSONObject object) {
        Map<String, Object> map = new HashMap<String, Object>();

        Iterator<String> keysItr = (Iterator<String>) object.keySet();
        while (keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = object.get(key);

            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }

    public static List<Object> toList(JSONArray array) {
        List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < array.size(); i++) {
            Object value = array.get(i);
            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }

}
