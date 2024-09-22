package com.hmdp.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hmdp.dto.Result;
import com.hmdp.entity.Shop;
import com.hmdp.service.IShopService;
import com.hmdp.utils.SystemConstants;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/shop")
public class ShopController {

    @Resource
    public IShopService shopService;

    /**
     * 根據id查詢商店信息
     * @param id 商店id
     * @return 商店詳情數據
     */
    @GetMapping("/{id}")
    public Result queryShopById(@PathVariable("id") Long id) {
        return shopService.queryById(id);
    }

    /**
     * 新增商店資訊
     * @param shop 商店數據
     * @return 商店id
     */
    @PostMapping
    public Result saveShop(@RequestBody Shop shop) {
        // 寫入資料庫
        shopService.save(shop);
        // 返回商店id
        return Result.ok(shop.getId());
    }

    /**
     * 更新商店資訊
     * @param shop 商店數據
     * @return 無
     */
    @PutMapping
    public Result updateShop(@RequestBody Shop shop) {
        // 寫入資料庫
        shopService.updateById(shop);
        return Result.ok();
    }

    /**
     * 依商店類型分頁查詢商店資訊
     * @param typeId 商店類型
     * @param current 頁碼
     * @return 商店列表
     */
    @GetMapping("/of/type")
    public Result queryShopByType(
            @RequestParam("typeId") Integer typeId,
            @RequestParam(value = "current", defaultValue = "1") Integer current
    ) {
        // 根據類型分頁查詢
        Page<Shop> page = shopService.query()
                .eq("type_id", typeId)
                .page(new Page<>(current, SystemConstants.DEFAULT_PAGE_SIZE));
        // 傳回數據
        return Result.ok(page.getRecords());
    }

    /**
     * 依商店名稱關鍵字分頁查詢商店資訊
     * @param name 商店名稱關鍵字
     * @param current 頁碼
     * @return 商店列表
     */
    @GetMapping("/of/name")
    public Result queryShopByName(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "current", defaultValue = "1") Integer current
    ) {
        // 根據類型分頁查詢
        Page<Shop> page = shopService.query()
                .like(StrUtil.isNotBlank(name), "name", name)
                .page(new Page<>(current, SystemConstants.MAX_PAGE_SIZE));
        // 傳回數據
        return Result.ok(page.getRecords());
    }
}
