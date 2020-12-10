package com.example.demo;

import com.alibaba.fastjson.JSON;
import org.apache.commons.collections4.MapUtils;

import java.util.Map;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2020-12-10
 */
public class MapUtilsTest {

    public static void main(String[] args) {
        String record = "{\n" +
                "    \"type\":2,\n" +
                "    \"task_id\":\"329316069065658040948\",\n" +
                "    \"url\":\"https://www.shpgx.com\",\n" +
                "    \"status\":2,\n" +
                "    \"group\":\"groupId=233e5490-cbfa-11ea-a5a9-005056bd2002#entityId=1644#version=571#autoCheck=0\",\n" +
                "    \"total\":1,\n" +
                "    \"statusCode\":200,\n" +
                "    \"openStatus\":true\n" +
                "}";
        Map<String, Object> map = JSON.parseObject(record, Map.class);
        boolean openStatus = MapUtils.getBooleanValue(map, "openStatus", true);
        System.out.println(openStatus);

        int code = openStatus ? 0 : 1;
        System.out.println(code);
    }
}
