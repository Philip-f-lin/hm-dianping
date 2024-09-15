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
@TableName("tb_voucher")
public class Voucher implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主鍵
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 商店id
     */
    private Long shopId;

    /**
     * 代金券標題
     */
    private String title;

    /**
     * 副標題
     */
    private String subTitle;

    /**
     * 使用規則
     */
    private String rules;

    /**
     * 支付金額
     */
    private Long payValue;

    /**
     * 抵扣金額
     */
    private Long actualValue;

    /**
     * 優惠券類型
     */
    private Integer type;

    /**
     * 優惠券類型
     */
    private Integer status;
    /**
     * 庫存
     */
    @TableField(exist = false)
    private Integer stock;

    /**
     * 生效時間
     */
    @TableField(exist = false)
    private LocalDateTime beginTime;

    /**
     * 失效時間
     */
    @TableField(exist = false)
    private LocalDateTime endTime;

    /**
     * 創建時間
     */
    private LocalDateTime createTime;


    /**
     * 更新時間
     */
    private LocalDateTime updateTime;


}
