package com.hmdp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_user_info")
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主鍵，用戶id
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

    /**
     * 城市名稱
     */
    private String city;

    /**
     * 個人介紹，不要超過128個字符
     */
    private String introduce;

    /**
     * 粉絲數量
     */
    private Integer fans;

    /**
     * 關注的人的數量
     */
    private Integer followee;

    /**
     * 性別，0：男，1：女
     */
    private Boolean gender;

    /**
     * 生日
     */
    private LocalDate birthday;

    /**
     * 積分
     */
    private Integer credits;

    /**
     * 會員級別，0~9級,0代表未開通會員
     */
    private Boolean level;

    /**
     * 創建時間
     */
    private LocalDateTime createTime;

    /**
     * 更新時間
     */
    private LocalDateTime updateTime;


}
