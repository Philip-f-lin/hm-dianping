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
@TableName("tb_seckill_voucher")
public class SeckillVoucher implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 關聯的優惠券的id
     */
    @TableId(value = "voucher_id", type = IdType.INPUT)
    private Long voucherId;

    /**
     * 庫存
     */
    private Integer stock;

    /**
     * 創建時間
     */
    private LocalDateTime createTime;

    /**
     * 生效時間
     */
    private LocalDateTime beginTime;

    /**
     * 失效時間
     */
    private LocalDateTime endTime;

    /**
     * 更新時間
     */
    private LocalDateTime updateTime;


}
