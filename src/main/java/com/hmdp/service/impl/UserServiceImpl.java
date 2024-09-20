package com.hmdp.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmdp.dto.LoginFormDTO;
import com.hmdp.dto.Result;
import com.hmdp.dto.UserDTO;
import com.hmdp.entity.User;
import com.hmdp.mapper.UserMapper;
import com.hmdp.service.IUserService;
import com.hmdp.utils.RegexUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.hmdp.utils.RedisConstants.*;
import static com.hmdp.utils.SystemConstants.USER_NICK_NAME_PREFIX;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public Result sendCode(String phone, HttpSession session) {
        //1. 校驗手機號
        if (RegexUtils.isPhoneInvalid(phone)) {
            //2.如果不符合，回傳錯誤訊息
            return Result.fail("手機號碼格式錯誤");
        }

        //3. 符合，產生驗證碼
        String code = RandomUtil.randomNumbers(6);

        //4. 儲存驗證碼到 redis // set key value ex 120
        stringRedisTemplate.opsForValue().set(LOGIN_CODE_KEY + phone, code, LOGIN_CODE_TTL, TimeUnit.MINUTES);

        //5. 發送驗證碼
        //todo 可用AWS做
        log.debug("發送簡訊驗證碼成功，驗證碼:{}",code);

        //返回ok
        return Result.ok();
    }

    @Override
    public Result login(LoginFormDTO loginForm, HttpSession session) {
        // 1. 校驗手機號碼
        String phone = loginForm.getPhone();
        if (RegexUtils.isPhoneInvalid(phone)) {
            // 2. 如果不符合返回錯誤訊息
            return Result.fail("手機號碼格式錯誤");
        }
        // 3. 從 redis 獲取驗證碼並校驗
        String cacheCode = stringRedisTemplate.opsForValue().get(LOGIN_CODE_KEY + phone);
        String code = loginForm.getCode();
        if (cacheCode == null || !cacheCode.equals(code)){
            // 不一致，報錯
            return Result.fail("驗證碼錯誤");
        }

        // 4. 一致，根據手機號碼查詢使用者
        User user = query().eq("phone", phone).one();

        // 5. 判斷使用者是否存在
        if (user == null){
            // 6. 不存在，創建新使用者
            user = createUserWithPhone(phone);
        }

        // 7. 儲存使用者資訊到 redis 中
        // 7.1. 隨機生成 token，作為登入令牌
        String token = UUID.randomUUID().toString(true);
        // 7.2. 將 User 對象轉為 Hash 儲存
        UserDTO userDTO = BeanUtil.copyProperties(user, UserDTO.class);
        Map<String, Object> userMap = BeanUtil.beanToMap(userDTO, new HashMap<>(),
                CopyOptions.create()
                        .setIgnoreNullValue(true)
                        .setFieldValueEditor((fieldName, fieldValue) -> fieldValue.toString()));
        // 7.3. 儲存
        String tokenKey = LOGIN_USER_KEY + token;
        log.debug("存儲時使用的 key 是: {}", tokenKey);
        stringRedisTemplate.opsForHash().putAll(tokenKey, userMap);
        // 7.4. 設置 token 有效期
        stringRedisTemplate.expire(tokenKey, LOGIN_USER_TTL, TimeUnit.MINUTES);
        // 8. 返回 token
        return Result.ok(tokenKey);
    }



    private User createUserWithPhone(String phone) {
        // 1.創建使用者
        User user = new User();
        user.setPhone(phone);
        user.setNickName(USER_NICK_NAME_PREFIX + RandomUtil.randomString(10));
        // 2.儲存使用者
        save(user);
        return user;
    }
}
