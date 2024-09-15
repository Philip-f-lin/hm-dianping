package com.hmdp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主鍵
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 手機號碼
     */
    private String phone;

    /**
     * 密碼，加密存儲
     */
    private String password;

    /**
     * 暱稱，預設是隨機字符
     */
    private String nickName;

    /**
     * 使用者頭像
     */
    private String icon = "";

    /**
     * 創建時間
     */
    private LocalDateTime createTime;

    /**
     * 更新時間
     */
    private LocalDateTime updateTime;


}
