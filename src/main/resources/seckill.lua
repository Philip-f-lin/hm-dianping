-- 1. 參數列表
-- 1.1. 優惠券 id
local voucherId = ARGV[1]
-- 1.2. 使用者 id
local userId = ARGV[2]

-- 2. 數據 key
-- 2.1. 庫存 key
local stockKey = 'seckill:stock:' .. voucherId
-- 2.2. 訂單 key
local orderKey = 'seckill:order:' .. voucherId

-- 3. 腳本業務
-- 3.1. 判斷庫存是否充足 get stockKey
if(tonumber(redis.call('get', stockKey)) <= 0) then
    -- 3.2. 庫存不足，返回 1
    return 1
end
-- 3.2. 判斷使用者是否下單 SISMEMBER orderKey userId
if(redis.call('sismember', orderKey, userId) == 1) then
    -- 3.3. 存在，表示重複下單，返回 2
    return 2
end

-- 3.4. 扣減庫存 incrby stockKey -1
redis.call('incrby', stockKey, -1)
-- 3.5. 下單（保存使用者）sadd orderKey userId
redis.call('sadd', orderKey, userId)
return 0




