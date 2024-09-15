package com.hmdp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_shop_type")
public class ShopType implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主鍵
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 類型名稱
     */
    private String name;

    /**
     * 圖示
     */
    private String icon;

    /**
     * 順序
     */
    private Integer sort;

    /**
     * 創建時間
     */
    @JsonIgnore
    private LocalDateTime createTime;

    /**
     * 更新時間
     */
    @JsonIgnore
    private LocalDateTime updateTime;


}
