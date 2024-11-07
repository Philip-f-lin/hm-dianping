package com.hmdp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
@TableName("tb_blog")
public class Blog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主鍵
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 商家id
     */
    private Long shopId;
    /**
     * 使用者id
     */
    private Long userId;
    /**
     * 使用者圖案
     */
    @TableField(exist = false)
    private String icon;
    /**
     * 使用者姓名
     */
    @TableField(exist = false)
    private String name;

    /**
     * 是否按過讚
     */
    @TableField(exist = false)
    private Boolean isLike;

    /**
     * 標題
     */
    private String title;

    /**
     * 商店的照片，最多9張，多張以","隔開
     */
    private String images;

    /**
     * 商店的文字描述
     */
    private String content;

    /**
     * 按讚數量
     */
    private Integer liked;

    /**
     * 評論數量
     */
    private Integer comments;

    /**
     * 創建時間
     */
    private LocalDateTime createTime;

    /**
     * 更新時間
     */
    private LocalDateTime updateTime;


}
