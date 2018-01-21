package com.paulzhangcc.sharing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author paul
 * @description
 * @date 2018/1/2
 */
@Component
public class Quartz {
    private static final Logger logger = LoggerFactory.getLogger(Quartz.class);
    public void demo(){
        logger.debug("==========>跑批测试开始!");
        logger.debug("==========>跑批测试结束!");
    }
}
