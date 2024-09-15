package com.hmdp.service.impl;

import cn.hutool.core.bean.BeanUtil;
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

import static com.hmdp.utils.SystemConstants.USER_NICK_NAME_PREFIX;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public Result sedCode(String phone, HttpSession session) {
        //1. 校驗手機號
        if (RegexUtils.isPhoneInvalid(phone)) {
            //2.如果不符合，回傳錯誤訊息
            return Result.fail("手機號碼格式錯誤");
        }

        //3. 符合，產生驗證碼
        String code = RandomUtil.randomNumbers(6);
        //4. 儲存驗證碼到session
        session.setAttribute("code",code);
        //5. 發送驗證碼
        //todo 可用AWS做
        log.debug("發送簡訊驗證碼成功，驗證碼:{}",code);
        //返回ok
        return Result.ok();
    }

    @Override
    public Result login(LoginFormDTO loginForm, HttpSession session) {
        //1. 校驗手機號碼
        String phone = loginForm.getPhone();
        if (RegexUtils.isPhoneInvalid(phone)) {
            return Result.fail("手機號碼格式錯誤");
        }
        //2. 校驗驗證碼
        Object cacheCode = session.getAttribute("code");
        String code = loginForm.getCode();
        if (cacheCode == null || !cacheCode.toString().equals(code)){
            //3. 不一致，報錯
            return Result.fail("驗證碼錯誤");
        }

        //4.一致，根據手機號碼查詢使用者
        User user = query().eq("phone", phone).one();

        //5. 判斷使用者是否存在
        if (user == null){
            //6. 不存在，創建新使用者
            user = createUserWithPhone(phone);
        }

        //7.儲存使用者資訊到session
        session.setAttribute("user",BeanUtil.copyProperties(user,UserDTO.class));
        return Result.ok();
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
