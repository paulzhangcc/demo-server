package com.paulzhangcc.demo;

import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.reader.Reader;
import com.aliyuncs.reader.ReaderFactory;

import java.util.Map;

/**
 *
 * @author: Paul Zhang
 * @date: 16:21 2017/12/27
 */
public class SmsChannelPriorityTest {
    public static void main(String[] args) throws ClientException {
        Reader instance = ReaderFactory.createInstance(FormatType.JSON);
        String request = "{\n" +
                "\t\"name\": \"zjf\",\n" +
                "\t\"age\": 18,\n" +
                "\t\"roles\": {\n" +
                "\t\t\"role1\": \"1\",\n" +
                "\t\t\"role2\": \"2\"\n" +
                "\t}\n" +
                "}";
        Map<String, String> person = instance.read(request, "");
        System.out.println(person);
    }
}
