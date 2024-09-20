package com.hmdp.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.hmdp.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.hmdp.utils.RedisConstants.LOGIN_USER_KEY;
import static com.hmdp.utils.RedisConstants.LOGIN_USER_TTL;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public LoginInterceptor(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 獲取請求頭中的 token
        String token = request.getHeader("authorization");
        if (StrUtil.isBlank(token)) {
            // 不存在，攔截，返回 401 狀態碼
            response.setStatus(401);
            return false;
        }
        // 2. 基於 token 獲取 redis 使用者
        String key = token;
        log.debug("取數據時使用的 key 是: {}", key);
        Map<Object, Object> userMap = stringRedisTemplate.opsForHash().entries(key);
        // 3. 判斷使用者是否存在
        if (userMap.isEmpty()){
            //4. 不存在，攔截，返回 401 狀態碼
            response.setStatus(401);
            return false;
        }
        // 5. 將查詢到的 Hash 數據轉為 UserDTO 對象
        UserDTO userDTO = BeanUtil.fillBeanWithMap(userMap, new UserDTO(), false);
        // 6. 存在，儲存使用者資訊到ThreadLocal
        UserHolder.saveUser(userDTO);
        // 7. 刷新 token 有效期間
        stringRedisTemplate.expire(key, LOGIN_USER_TTL, TimeUnit.MINUTES);
        // 8. 放行
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //移除使用者
        UserHolder.removeUser();
    }
}
