package com.paulzhangcc.tools.mybatis;

import com.paulzhangcc.tools.mybatis.generator.CustomMyBatisGenerator;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author: Paul Zhang
 * @date: 16:21 2017/12/27
 */
public class StartUp {
    /**
     * 读取resource目录下的配置文件  注意生成是xml 要删除  否则生成的会追加报错
     */
    public static final String CONFIG_MYSQL = "generatorConfig-mysql.xml";
    public static final String CONFIG_ORACLE = "generatorConfig-oracle.xml";
    public static void main(String[] args) throws URISyntaxException {
        try {
            List<String> warnings = new ArrayList<String>();
            boolean overwrite = true;
            //属性进行判断，是否合并（即追加）
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream is = classloader.getResourceAsStream(CONFIG_MYSQL);
            ConfigurationParser cp = new ConfigurationParser(warnings);
            Configuration config = cp.parseConfiguration(is);

            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            CustomMyBatisGenerator myBatisGenerator = new CustomMyBatisGenerator(config, callback, warnings);

            myBatisGenerator.generate(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("success!!!");
    }
}
