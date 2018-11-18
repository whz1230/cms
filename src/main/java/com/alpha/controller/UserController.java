package com.alpha.controller;

import com.alpha.model.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * @author whz
 * @date
 */
@RestController
public class UserController {
    @RequestMapping("/hello")
    @ResponseBody
    public String hello() {
        User user = new User();
        user.setId("aa");
        user.setUserName("小明");
        System.out.println(user);
        return user.toString();
    }

    @RequestMapping("/")
    public void index(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.sendRedirect(request.getContextPath() + "/page/login/index.html");
    }

    @RequestMapping({"/user/getValcode"})
    public void getValcodes(HttpServletRequest request, HttpServletResponse response) {
        String codeChars = "0123456789";
        // 获得验证码集合的长度
        int charsLength = codeChars.length();
        // 关闭客户端浏览器的缓冲区
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        // 设置图形验证码的长和宽
        int width = 110, height = 30;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        Random random = new Random();
        g.setColor(getRandomColor(180, 250));

        g.fillRect(0, 0, width, height);

        g.setFont(new Font("Times New Roman", Font.ITALIC, height));

        g.setColor(getRandomColor(120, 180));
        // 用户保存最后随机生成的验证码
        StringBuffer validationCode = new StringBuffer();
        // 验证码的随机字体
        String[] fontNames = {"Times New Roman", "Book antiqua", "Arial"};

        // 随机生成4个验证码
        for (int i = 0; i < 4; i++) {
            // 随机设置当前验证码的字符的字体
            g.setFont(new Font(fontNames[random.nextInt(3)], Font.ITALIC, height));
            // 随机获得当前验证码的字符
            char codeChar = codeChars.charAt(random.nextInt(charsLength));
            validationCode.append(codeChar);
            // 随机设置当前验证码字符的颜色
            g.setColor(getRandomColor(10, 100));
            // 在图形上输出验证码字符，x和y都是随机生成的
            g.drawString(String.valueOf(codeChar), 16 * i + random.nextInt(7), height - random.nextInt(6));
        }
        // 设置session对象5分钟失效
        request.getSession().setMaxInactiveInterval(5 * 60);

        // 将验证码保存在session对象中,key为validation_code

        request.getSession().setAttribute("valCode", validationCode.toString());
        // 关闭Graphics对象

        g.dispose();
        try {
            ImageIO.write(image, "jpeg", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Color getRandomColor(int minColor, int maxColor) {
        Random random = new Random();
        if (minColor > 255) {
            minColor = 255;
        }
        if (maxColor > 255) {
            maxColor = 255;
        }
        // 获得r的随机颜色值
        int red = minColor + random.nextInt(maxColor - minColor);
        int green = minColor + random.nextInt(maxColor - minColor);
        int blue = minColor + random.nextInt(maxColor - minColor);
        return new Color(red, green, blue);
    }
}