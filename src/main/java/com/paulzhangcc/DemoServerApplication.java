package com.paulzhangcc;

import com.paulzhangcc.sharing.mybatis.TestMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author: Paul Zhang
 * @date: 16:21 2017/12/27
 */
@SpringBootApplication
@EnableEurekaServer
//强制使用cglib
@EnableAspectJAutoProxy
@MapperScan({"com.paulzhangcc.demo.dao.mysql.mapper","com.paulzhangcc.sharing.mybatis"}) //注入mybatis配置文件
@ImportResource(locations={"classpath:spring/application*.xml"}) //映入spring配置
@EnableTransactionManagement
@EnableAsync
@ComponentScan("com.paulzhangcc")
public class DemoServerApplication  {
	private static final Logger logger = LoggerFactory.getLogger(DemoServerApplication.class);
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(DemoServerApplication.class, args);

		try {
			context.getBean(TestMapper.class).test();
		}catch (Exception e){
			logger.error("数据库连接失败,请查看配置！！！",e);
			System.exit(-1);
		}
		logger.info("数据库检测连接成功！！！");
		try {
			((RedisTemplate)(context.getBean("redisTemplate"))).opsForValue().get("test");
		}catch (Exception e){
			logger.error("redis连接失败,请查看配置！！！",e);
			System.exit(-1);
		}
		logger.info("redis检测连接成功！！！");
	}
//	@Bean
//	public TomcatEmbeddedServletContainerFactory tomcatFactory() {
//		TomcatEmbeddedServletContainerFactory tomcatFactory = new TomcatEmbeddedServletContainerFactory();
//		tomcatFactory.addContextCustomizers((context) -> {
//			StandardRoot standardRoot = new StandardRoot(context);
//			standardRoot.setCacheMaxSize(40 * 1024);
//			standardRoot.setCachingAllowed(false);
//		});
//		return tomcatFactory;
//	}
}
