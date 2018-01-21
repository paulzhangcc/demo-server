package com.paulzhangcc.sharing.kaptcha;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.paulzhangcc.demo.controller.DemoController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.paulzhangcc.demo.constant.ConfigConsts.KAPTCHA_TOKEN;

/**
 * Created by paul on 2017/12/30.
 */
@Component
public class KaptchaTools {

    private static final Logger logger = LoggerFactory.getLogger(DemoController.class);
    @Autowired
    DefaultKaptcha defaultKaptcha;
    @Autowired
    RedisTemplate<String,String> redisTemplate;

    private static final String ROOT = "/";

    public void create(HttpServletResponse httpServletResponse) throws IOException {
        byte[] captchaChallengeAsJpeg = null;
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();

        //生产验证码字符串并保存到session中
        String createText = defaultKaptcha.createText();
        BufferedImage challenge = defaultKaptcha.createImage(createText);
        ImageIO.write(challenge, "jpg", jpegOutputStream);
        //定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
        String string = UUID.randomUUID().toString();
        Cookie cookie = new Cookie(KAPTCHA_TOKEN, string);
        cookie.setMaxAge(Integer.MAX_VALUE);
        cookie.setPath(ROOT);
        httpServletResponse.addCookie(cookie);
        httpServletResponse.setHeader(KAPTCHA_TOKEN,string);

        redisTemplate.opsForValue().set(KAPTCHA_TOKEN+string,createText);
        redisTemplate.expire(KAPTCHA_TOKEN+string,5, TimeUnit.MINUTES);

        httpServletResponse.setHeader("Cache-Control", "no-store");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);
        httpServletResponse.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream =
                httpServletResponse.getOutputStream();
        responseOutputStream.write(captchaChallengeAsJpeg);
        responseOutputStream.flush();
        responseOutputStream.close();
    }

    /**
     * 校验图形验证码
     * @param httpServletRequest 用来取凭证token
     * @param httpServletResponse 将cookie清除
     * @param kaptchaCode 如果为空，从reques中取名称为kaptchaCode
     * @return
     */
    public boolean check(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse,String kaptchaCode){
        if (kaptchaCode==null || kaptchaCode.length() == 0){
            kaptchaCode = httpServletRequest.getParameter("kaptchaCode");
        }
        if (kaptchaCode==null || kaptchaCode.length() == 0){
           return false;
        }

        String kaptchaCodeKey = httpServletRequest.getHeader(KAPTCHA_TOKEN);

        Cookie[] cookies = httpServletRequest.getCookies();
        for (Cookie cookie :cookies){
            if(KAPTCHA_TOKEN.equals(cookie.getName())){
                if (kaptchaCodeKey == null ){
                    kaptchaCodeKey=cookie.getValue();
                }
                cookie.setMaxAge(0);
                cookie.setPath(ROOT);
                httpServletResponse.addCookie(cookie);
                break;
            }
        }
        if (kaptchaCodeKey == null || kaptchaCodeKey.length()==0){
            return false;
        }

        String rightCode = redisTemplate.opsForValue().get(KAPTCHA_TOKEN + kaptchaCodeKey);
        if (rightCode!=null){
            redisTemplate.delete(KAPTCHA_TOKEN + kaptchaCodeKey);
        }

        if (kaptchaCode.equals(rightCode)){
            return true;
        }
        return false;
    }
}
