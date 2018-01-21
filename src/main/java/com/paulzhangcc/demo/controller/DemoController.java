package com.paulzhangcc.demo.controller;


import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Applications;
import com.paulzhangcc.demo.dao.mysql.mapper.DemoDAO;
import com.paulzhangcc.demo.dao.mysql.model.DemoDO;
import com.paulzhangcc.demo.event.demo.DemoEvent;
import com.paulzhangcc.demo.service.DemoService;
import com.paulzhangcc.sharing.SharingProperties;
import com.paulzhangcc.sharing.message.SmsFacade;
import com.paulzhangcc.sharing.spring.ApplicationContextHelper;
import com.paulzhangcc.sharing.util.ExcelExportUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.LoggersEndpoint;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Paul Zhang
 * @date: 16:21 2017/12/27
 */
@RestController
public class DemoController {
    private static final Logger logger = LoggerFactory.getLogger(DemoController.class);
    @Autowired
    DemoService demoService;
    @Autowired
    SmsFacade smsFacade;
    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    RedisTemplate<String, String> stringRedisTemplate;

    @Autowired
    DemoDAO demoDAO;
    @Autowired
    ExcelExportUtils excelExportUtils;
    @Autowired
    SharingProperties sharingProperties;
    @Autowired
    DefaultKaptcha defaultKaptcha;
    @Autowired
    EurekaClient eurekaClient;
    @Autowired
    LoggersEndpoint loggersEndpoint;

    @RequestMapping("/test/db/insert")
    public String insert() {
        DemoDO demo = new DemoDO();
        demo.setAge(11);
        demo.setUserName("张呵呵");
        int insert = demoService.insert(demo);
        logger.info("插入成功:" + demo);
        if (insert == 1) {
            ApplicationContextHelper.applicationContext.publishEvent(new DemoEvent(this));
            return "Y";
        }
        return "N";
    }

    @RequestMapping("/test/redis/set")
    public boolean set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
        return true;
    }

    @RequestMapping("/test/redis/get")
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    @RequestMapping("/test/db/update")
    public int update() {
        DemoDO demoDO = new DemoDO();
        demoDO.setId(-1L);
        demoDO.setAge(1);
        return demoDAO.updateByPrimaryKeySelective(demoDO);
    }

    @RequestMapping("/test/mesage/send")
    public boolean sendMessage() {
        return smsFacade.sendVerificationCode("18600787844", "11111");
    }

    @RequestMapping("/test/config")
    public Boolean config() {
        return sharingProperties.isOpenThirdpartyService();
    }

    @RequestMapping(value = "/test/export", method = {RequestMethod.GET})
    public ResponseEntity<byte[]> exportExcel() throws IOException {
        List<Map<String, Object>> listData = new ArrayList<>();
        Map<String, Object> data = new HashMap<>();
        data.put("realname", "张三");
        data.put("mobile", "10000");
        listData.add(data);
        File file = excelExportUtils.export("demo.xls", listData);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", "demo.xls");
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
                headers, HttpStatus.CREATED);
    }


    @RequestMapping("/test/index")
    public String index() throws Exception {
        Applications applications = eurekaClient.getApplications();
        return "hello world!!!";
    }
}
