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
@TableName("tb_voucher_order")
public class VoucherOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主鍵
     */
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    /**
     * 下單的用戶id
     */
    private Long userId;

    /**
     * 購買的代金券id
     */
    private Long voucherId;

    /**
     * 支付方式 1：餘額支付；2：支付寶；3：微信
     */
    private Integer payType;

    /**
     * 訂單狀態，1：未支付；2：已支付；3：已核銷；4：已取消；5：退款中；6：已退款
     */
    private Integer status;

    /**
     * 下單時間
     */
    private LocalDateTime createTime;

    /**
     * 支付時間
     */
    private LocalDateTime payTime;

    /**
     * 核銷時間
     */
    private LocalDateTime useTime;

    /**
     * 退款時間
     */
    private LocalDateTime refundTime;

    /**
     * 更新時間
     */
    private LocalDateTime updateTime;


}
