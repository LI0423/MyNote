package com.video.manager.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.video.entity.BlackUserDO;
import com.video.manager.domain.dto.BlackUserDTO;
import com.video.manager.domain.dto.BlackUserViewDTO;
import com.video.manager.domain.entity.BlackUserViewDO;
import com.video.manager.mapper.BlackUserMapper;
import com.video.manager.service.BlackUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class BlackUserServiceImpl implements BlackUserService {

    @Autowired
    private BlackUserMapper blackUserMapper;


    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public BlackUserDTO find(String token) {
        return BlackUserService.convertTo(blackUserMapper.selectById(token));
    }

    @Override
    public void add(BlackUserDTO blackUserDTO) {

        BlackUserDTO oldBlackUserDTO = find(blackUserDTO.getToken());

        if (Objects.nonNull(oldBlackUserDTO)) {
            return;
        }

        BlackUserDO blackUserDO = new BlackUserDO();

        BeanUtils.copyProperties(blackUserDTO, blackUserDO);

        blackUserDO.setBDesc(blackUserDTO.getDesc());

        blackUserDO.setCreateTime(new Date());

        blackUserMapper.insert(blackUserDO);

        String key = String.format("spredis:buser:tk:%s", blackUserDTO.getToken());

        redisTemplate.opsForValue().set(key, JSONObject.toJSONString(blackUserDTO));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addBatch(List<BlackUserDTO> blackUserDTOList) {
        if (Objects.isNull(blackUserDTOList) || blackUserDTOList.size() == 0) {
            return;
        }

        Map<String, String> keys = new HashMap<>();
        List<BlackUserDO> blackUserDOList = Optional
                .of(blackUserDTOList)
                .map(List::stream)
                .orElseGet(Stream::empty)
                .map(blackUserDTO -> {
                    try {
                        keys.put(String.format("spredis:buser:tk:%s", blackUserDTO.getToken()), objectMapper.writeValueAsString(blackUserDTO));
                    } catch (JsonProcessingException e) {
                        log.error("parse json err!", e);
                    }
                    BlackUserDO blackUserDO = new BlackUserDO();
                    BeanUtils.copyProperties(blackUserDTO, blackUserDO);

                    blackUserDO.setBDesc(blackUserDTO.getDesc());
                    blackUserDO.setCreateTime(new Date());

                    return blackUserDO;
                })
                .collect(Collectors.toList());

        //todo 因为不是高频操作 临时先轮库解决 后面优化
        blackUserDOList.forEach(blackUserDO -> {
            try {
                blackUserMapper.insert(blackUserDO);
            } catch (Exception e) {
                log.error(String.format("ban user error! user token is %s", blackUserDO.getToken()), e);
            }

        });
        redisTemplate.opsForValue().multiSet(keys);
    }


    @Override
    public List<BlackUserViewDTO> list() {
        List<BlackUserViewDO> blackUserViewDOS = blackUserMapper.selectBlackUserViewList();
        return Optional.ofNullable(blackUserViewDOS)
                .orElse(new ArrayList<>())
                .stream()
                .map(BlackUserService::convertTo)
                .collect(Collectors.toList());
    }
}
