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
@TableName("tb_blog_comments")
public class BlogComments implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主鍵
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 使用者id
     */
    private Long userId;

    /**
     * 搜尋商店 ID
     */
    private Long blogId;

    /**
     * 關聯的1級評論id，如果是一級評論，則值為0
     */
    private Long parentId;

    /**
     * 回覆的評論id
     */
    private Long answerId;

    /**
     * 回覆的內容
     */
    private String content;

    /**
     * 按讚數
     */
    private Integer liked;

    /**
     * 狀態，0：正常，1：被檢舉，2：禁止查看
     */
    private Boolean status;

    /**
     * 創建時間
     */
    private LocalDateTime createTime;

    /**
     * 更新時間
     */
    private LocalDateTime updateTime;


}
