package com.hmdp;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hmdp.dto.UserDTO;
import com.hmdp.entity.User;
import com.hmdp.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.hmdp.utils.RedisConstants.LOGIN_USER_KEY;

@SpringBootTest
public class UserLoginTest {

    @Resource
    private IUserService userService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Test
    void createLoginUserToken() throws IOException {
        // 採用字元緩衝流
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream("/Users/linzhuofei/Desktop/token.txt"), StandardCharsets.UTF_8));
            List<User> list = userService.list(new QueryWrapper<User>().last("limit 1000"));
            StringBuffer tokenBuffer = new StringBuffer();
            list.forEach(user -> {
                // 儲存使用者資訊到 Redis 中
                // 隨機產生 token，作為登入令牌
                String token = UUID.randomUUID().toString(true);
                // 將 User 物件轉換為 HashMap 存儲
                UserDTO userDTO = BeanUtil.copyProperties(user, UserDTO.class);
                // 都是 String 類型（RedisTemplate<String, String>）
                Map<String, Object> userMap = BeanUtil.beanToMap(userDTO, new HashMap<>(),
                        CopyOptions.create()
                                .setIgnoreNullValue(true)
                                .setFieldValueEditor((fieldName, fieldValue) -> fieldValue.toString()));
                // 儲存
                String tokenKey = LOGIN_USER_KEY + token;
                stringRedisTemplate.opsForHash().putAll(tokenKey, userMap);
                // 設定token有效期限 可以不設定
                // stringRedisTemplate.expire(tokenKey, 30L, TimeUnit.MINUTES);
                tokenBuffer.append(token);
                // 每寫入一個token就換行
                tokenBuffer.append("\r\n");
            });
            // 將產生的所有token 字串寫入字元流文件
            bufferedWriter.write(tokenBuffer.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            // 關閉流
            bufferedWriter.close();
        }
    }
}
