package com.hmdp.utils;

import com.hmdp.dto.UserDTO;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1. 取得session
        HttpSession session = request.getSession();
        //2. 取得session中的用戶
        Object user = session.getAttribute("user");
        //3. 判斷使用者是否存在
        if (user == null){
            //4. 不存在，攔截
            response.setStatus(401);
            return false;
        }

        //5. 存在 儲存使用者資訊到ThreadLocal
        UserHolder.saveUser((UserDTO) user);
        //6. 放行
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //移除使用者
        UserHolder.removeUser();
    }
}
