package com.hmdp.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmdp.dto.Result;
import com.hmdp.entity.ShopType;
import com.hmdp.mapper.ShopTypeMapper;
import com.hmdp.service.IShopTypeService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
public class ShopTypeServiceImpl extends ServiceImpl<ShopTypeMapper, ShopType> implements IShopTypeService {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Override
    public Result queryList() {

        String shopTypeJson = stringRedisTemplate.opsForValue().get("shopType");
        if (StrUtil.isNotBlank(shopTypeJson)){
            List<ShopType> typeList = JSONUtil.parseArray(shopTypeJson).toList(ShopType.class);
            return Result.ok(typeList);
        }
        List<ShopType> typeList = query().orderByAsc("sort").list();
        if (typeList == null || typeList.isEmpty()){
            return Result.fail("商店不存在");
        }
        stringRedisTemplate.opsForValue().set("shopType", JSONUtil.toJsonStr(typeList));
        return Result.ok(typeList);
    }
}
