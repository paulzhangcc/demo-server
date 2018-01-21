package com.paulzhangcc.demo;

import com.paulzhangcc.demo.tool.Page;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testDemoController() throws Exception {

        MvcResult result = mockMvc.perform(post("/test/config"))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();// 返回执行请求的结果
        System.out.println("=======================================");
        System.out.println(result.getResponse().getContentAsString());
        System.out.println("=======================================");

    }

    @Autowired
    UserDAO userDAO;

    @Test
    public void testPage() {


        Page<UserDO, UserDO> userDOPage = new Page<>();
        userDOPage.currentPage=2;
        userDOPage.setPageSize(2);
        userDOPage.setRecordCount(50);

        UserDO userDO = new UserDO();
        userDO.setUserId(50L);



    }


}
