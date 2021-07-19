package com.automacent.fwk.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;

public class TestClass {

    public static void main(String args[]) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String fileLocation = "C:\\Users\\anshumans\\Downloads\\automacent-fwk-core-rama\\src\\test\\resources\\mcmp\\VRA\\e2eCentOS77CompositeVRA82.json";
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileLocation));
        LinkedHashMap<String, Object> fileData = mapper.readValue(bufferedReader, LinkedHashMap.class);
        ((LinkedHashMap)fileData.get("Order Parameters")).forEach((k,v) -> {
            System.out.println("Key :" + k);
            System.out.println("Value :" + v);
            ((LinkedHashMap)((LinkedHashMap)v).get("Main Parameters")).forEach((k1,v1) -> {
                System.out.println("Key1 :" + k1);
                System.out.println("Value1 :" + v1);
            });
        });
    }
}
