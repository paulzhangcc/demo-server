package com.paulzhangcc.demo.controller;

import com.paulzhangcc.demo.constant.CmsConsts;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * @author paul
 * @description
 * @date 2018/1/3
 */
@RestController
public class CmsController {
    private static final Logger logger = LoggerFactory.getLogger(CmsController.class);
    /**
     *
     * @param cmsEnumName  com.paulzhangcc.demo.constant.CmsConsts.CmsEnum#name()
     * @param httpServletResponse
     * @throws Exception
     */
    @RequestMapping(CmsConsts.ACCESS_PREFIX_0+"/{name}/{imageName:.*}")
    public void viewPicture(@PathVariable("name") String cmsEnumName ,@PathVariable("imageName") String imageName,HttpServletResponse httpServletResponse) throws Exception {
        if (imageName !=null && imageName.contains("../")){
            throw new IllegalAccessException("Illegal image name");
        }
        try {
            File file = new File(CmsConsts.CmsEnum.valueOf(cmsEnumName).getStorageLocationPrefix() + imageName);
            httpServletResponse.getOutputStream().write(
                    FileUtils.readFileToByteArray(file));
            httpServletResponse.getOutputStream().close();
        }catch (Exception e){
            logger.error(e.getMessage(),e.fillInStackTrace());
            httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
