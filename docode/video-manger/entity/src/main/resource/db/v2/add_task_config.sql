INSERT INTO task_config(id, c_key, value, task_id)
VALUES
-- 看视频免费领任务配置
(1319178031814627329, 'round_max', '10', 1319178030044631042),
(1319185394344906753, 'random_reward_min', '50', 1319178030044631042),
(1319185395880022017, 'random_reward_max', '100', 1319178030044631042),
-- 任务勋章任务配置
(1319542800962777089, 'titles', '连续活跃2天,连续活跃4天,连续活跃7天,连续活跃30天,连续活跃60天,连续活跃90天', 1319542799134060545),
(1319542801222823937, 'numbers', '1660,2220,4230,7560,41980,123180', 1319542799134060545),
(1319542801449316353, 'active_days', '2,4,7,30,60,90', 1319542799134060545),
-- 金币大转盘任务配置
(1320683524848488450, 'round_max', '10', 1320683522227048450),
(1320683524982706178, 'numbers', '1.5,2,5,0.5,0.8,0.9,1,1.2,0.2,0.8,1.2,1.5,0.5,1,0.9,1.2', 1320683522227048450),
(1320683525125312513, 'chances', '3,1,0,5,10,10,30,5,2,6,3,2,3,10,8,2', 1320683522227048450);

-- 提现任务新增配置
INSERT INTO task_config(id, c_key, value, task_id)
VALUES
(1324646722652061697, 'amount_1', '30', 1310198923613904898),
(1324646726208831489, 'amount_2', '500', 1310198923613904898),
(1324646726313689090, 'amount_3', '1500', 1310198923613904898),
(1324646726376603649, 'amount_4', '3000', 1310198923613904898),
(1324646726435323905, 'amount_5', '5000', 1310198923613904898),
(1324646726666010626, 'amount_6', '10000', 1310198923613904898);