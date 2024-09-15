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
@TableName("tb_shop")
public class Shop implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主鍵
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 商店名稱
     */
    private String name;

    /**
     * 商店類型的id
     */
    private Long typeId;

    /**
     * 商店圖片，多張圖片以','隔開
     */
    private String images;

    /**
     * 商圈，例如陸家嘴
     */
    private String area;

    /**
     * 地址
     */
    private String address;

    /**
     * 經度
     */
    private Double x;

    /**
     * 維度
     */
    private Double y;

    /**
     * 均價，取整數
     */
    private Long avgPrice;

    /**
     * 銷量
     */
    private Integer sold;

    /**
     * 評論數量
     */
    private Integer comments;

    /**
     * 評分，1~5分，乘10保存，避免小數
     */
    private Integer score;

    /**
     * 營業時間，例如 10:00-22:00
     */
    private String openHours;

    /**
     * 創建時間
     */
    private LocalDateTime createTime;

    /**
     * 更新時間
     */
    private LocalDateTime updateTime;


    @TableField(exist = false)
    private Double distance;
}
