package com.video.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.video.entity.UserAuthDO;
import com.video.entity.UserIdTokenMapping;
import com.video.user.cache.UserAuthCache;
import com.video.user.domain.dto.user.UserAuthDTO;
import com.video.user.exception.BusinessException;
import com.video.user.exception.ErrorCodeEnum;
import com.video.user.service.UserAuthService;
import com.video.user.util.IdentifierGeneratorUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class UserAuthServiceImpl extends BaseServiceImpl implements UserAuthService {

    private static final String ACCESS_TOKEN_PREFIX = "MGTSK";

    @Autowired
    private UserAuthCache userAuthCache;

    @Override
    public UserAuthDTO findByAccessToken(String accessToken) {
        UserAuthDTO userAuthDTO = userAuthCache.getUserAuthDtoByAccessToken(accessToken);
        if (userAuthDTO == null) {
            UserAuthDO codition = new UserAuthDO();
            codition.setAccessToken(accessToken);
            QueryWrapper<UserAuthDO> queryWrapper = new QueryWrapper<>(codition);
            List<UserAuthDO> userAuthDOList =
                    userAuthMapper.selectList(queryWrapper);
            if (userAuthDOList.isEmpty()) {
                userAuthCache.setUserAuthDtoByAccessTokenExpire(accessToken, null);
                log.warn("UserAuthDO object cannot be obtained through access token, access token:{}", accessToken);
                return null;
            }
            if (userAuthDOList.size() > 1) {
                log.warn("More than one UserAuthDO object obtained through access token, access token:{}", accessToken);
            }
            UserAuthDO userAuthDO = userAuthDOList.get(0);
            userAuthDTO = new UserAuthDTO();
            BeanUtils.copyProperties(userAuthDO, userAuthDTO);
            userAuthCache.setUserAuthDtoByAccessTokenExpire(accessToken, userAuthDTO);
        } else {
            if (userAuthDTO.getAccessToken() == null) {
                log.warn("UserAuthDO object cannot be obtained through access token, access token:{}", accessToken);
                return null;
            }
        }
        return userAuthDTO;
    }

    @Override
    public UserAuthDTO findByOpenId(String openId) {
        UserAuthDTO userAuthDTO = userAuthCache.getUserAuthDtoByOpenId(openId);
        if (userAuthDTO == null) {
            UserAuthDO codition = new UserAuthDO();
            codition.setOpenId(openId);
            QueryWrapper<UserAuthDO> queryWrapper = new QueryWrapper<>(codition);
            List<UserAuthDO> userAuthDOList =
                    userAuthMapper.selectList(queryWrapper);
            if (userAuthDOList.isEmpty()) {
                userAuthCache.setUserAuthDtoByOpenId(openId, null);
                log.debug("UserAuthDO object cannot be obtained through open id, open id:{}", openId);
                return null;
            }
            if (userAuthDOList.size() > 1) {
                log.warn("More than one UserAuthDO object obtained through open id, open id:{}", openId);
            }
            UserAuthDO userAuthDO = userAuthDOList.get(0);
            userAuthDTO = new UserAuthDTO();
            BeanUtils.copyProperties(userAuthDO, userAuthDTO);
            userAuthCache.setUserAuthDtoByOpenIdExpire(openId, userAuthDTO);
        } else {
            if (userAuthDTO.getOpenId() == null) {
                log.debug("UserAuthDO object cannot be obtained through open id from cache, open id:{}", openId);
                return null;
            }
        }
        return userAuthDTO;
    }

    @Override
    public UserAuthDTO findByTokenAndAppId(String token, Long appId) {
        UserAuthDTO userAuthDTO = userAuthCache.getUserAuthDtoByTokenAndAppId(token, appId);
        if (userAuthDTO == null) {
            UserAuthDO codition = new UserAuthDO();
            codition.setToken(token);
            codition.setAppId(appId);
            QueryWrapper<UserAuthDO> queryWrapper =
                    new QueryWrapper<>(codition)
                            .orderByAsc("create_time")
                            .last("limit 1");
            UserAuthDO userAuthDO = userAuthMapper.selectOne(queryWrapper);
            if (userAuthDO == null) {
                userAuthCache.setUserAuthDtoByTokenAndAppIdExpire(token, appId, null);
                log.warn("UserAuthDO object cannot be obtained through token, token:{}", token);
                return null;
            }
            userAuthDTO = new UserAuthDTO();
            BeanUtils.copyProperties(userAuthDO, userAuthDTO);
            userAuthCache.setUserAuthDtoByTokenAndAppIdExpire(token, appId, userAuthDTO);
        } else {
            if (userAuthDTO.getToken() == null) {
                log.warn("UserAuthDO object cannot be obtained through token, token:{}", token);
                return null;
            }
        }
        return userAuthDTO;
    }


    private String generatAccessToken() {
        return ACCESS_TOKEN_PREFIX + IdentifierGeneratorUtils.nextId();
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED)
    public void create(Long userId, String token, Long appId) {
        if (userId == null || token == null) {
            throw new IllegalArgumentException("required parameters are missing");
        }

        String accessToken = generatAccessToken();

        UserAuthDO userAuthDO = new UserAuthDO();

        userAuthDO.setUserId(userId);
        userAuthDO.setToken(token);
        userAuthDO.setAccessToken(accessToken);
        userAuthDO.setAppId(appId);

        long currentTimeMillis = System.currentTimeMillis();
        userAuthDO.setCreateTime(currentTimeMillis);
        userAuthDO.setLastModifyTime(currentTimeMillis);

        // 清空缓存
        userAuthCache.delUserAuthDtoByAccessToken(accessToken);
        userAuthCache.delUserAuthDtoByTokenAndAppId(token, appId);
        // 添加到数据库中
        userAuthMapper.insert(userAuthDO);
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED)
    public void createByOpenId(Long userId, String openId, Long appId, String thirdPartyAccessToken) {
        if (userId == null || openId == null) {
            throw new IllegalArgumentException("required parameters are missing");
        }

        String accessToken = generatAccessToken();
        long currentTimeMillis = System.currentTimeMillis();

        UserAuthDO userAuthDO = new UserAuthDO();

        userAuthDO.setUserId(userId);
        userAuthDO.setOpenId(openId);
        userAuthDO.setAccessToken(accessToken);
        userAuthDO.setAppId(appId);
        userAuthDO.setThirdPartyAccessToken(thirdPartyAccessToken);
        userAuthDO.setCreateTime(currentTimeMillis);
        userAuthDO.setLastModifyTime(currentTimeMillis);

        // 添加到数据库中
        userAuthMapper.insert(userAuthDO);

        // 清空缓存
        userAuthCache.delUserAuthDtoByAccessToken(accessToken);
        userAuthCache.delUserAuthDtoByOpenId(openId);
    }

    @Override
    public void logout(String accessToken) {
        // TODO:先啥都不做,等用户调用登录接口就刷新accessToken了
//        UserAuthDO userAuthDO = getUserAuthDtoByAccessToken(accessToken);
        // 拦截器保证accessToken能获取到对应的内容
        UserAuthDTO userAuthDto = findByAccessToken(accessToken);
        userAuthCache.delUserAuthDtoByAccessToken(userAuthDto.getAccessToken());
        userAuthCache.delUserAuthDtoByOpenId(userAuthDto.getOpenId());
        userAuthCache.delUserAuthDtoByTokenAndAppId(userAuthDto.getToken(), userAuthDto.getAppId());
//        userCache.delUserDtoByAccessToken(accessToken);
//        userAuthDO.setAccessToken(null);
//        userAuthMapper.updateById(userAuthDO);
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED)
    public void bind(Long userId, Long appId, String token, String openId, String thirdPartyAccessToken) {
        UserAuthDO condition = new UserAuthDO();
        condition.setUserId(userId);
        UserAuthDO userAuthDO = userAuthMapper
                .selectOne(new QueryWrapper<>(condition)
                        .last("limit 1"));
        userAuthDO.setOpenId(openId);
        userAuthDO.setThirdPartyAccessToken(thirdPartyAccessToken);

        UserAuthDTO userAuthDTO = findByTokenAndAppId(token, appId);
        userAuthCache.delUserAuthDtoByAccessToken(userAuthDTO.getAccessToken());
        userAuthCache.delUserAuthDtoByTokenAndAppId(token, appId);
        userAuthCache.delUserAuthDtoByOpenId(openId);
        userAuthMapper.updateById(userAuthDO);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public String updateAccessToken(String openId) {

        // 删除缓存
        UserAuthDTO userAuthDto = findByOpenId(openId);
        userAuthCache.delUserAuthDtoByOpenId(userAuthDto.getOpenId());
        userAuthCache.delUserAuthDtoByAccessToken(userAuthDto.getAccessToken());
        userAuthCache.delUserAuthDtoByTokenAndAppId(userAuthDto.getToken(), userAuthDto.getAppId());

        String accessToken = generatAccessToken();
        UserAuthDO userAuthDO = new UserAuthDO();
        userAuthDO.setAccessToken(accessToken);
        userAuthDO.setLastModifyTime(System.currentTimeMillis());
        int rows = userAuthMapper.update(userAuthDO, new UpdateWrapper<UserAuthDO>().eq("open_id", openId));
        if (rows <= 0) {
            throw new BusinessException(ErrorCodeEnum.USER_AUTH_INFO_NOT_FOUND);
        }
        return accessToken;
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED)
    public void saveUserIdTokenMapping(String pkg, Long userId, String token) {
        Integer mappingCount = queryMappingRecordCount(pkg, userId, token);
        if (Objects.isNull(mappingCount) || mappingCount == 0) {
            UserIdTokenMapping userIdTokenMapping = new UserIdTokenMapping();
            userIdTokenMapping.setPkg(pkg);
            userIdTokenMapping.setUserId(userId);
            userIdTokenMapping.setToken(token);
            userIdTokenMapping.setCreateTime(System.currentTimeMillis());
            userIdTokenMappingMapper.insert(userIdTokenMapping);
            userAuthCache.delLatestToken(pkg, userId);
        }
    }

    public Integer queryMappingRecordCount(String pkg, Long userId, String token) {
        Integer count = userAuthCache.getUserIdTokenMapping(pkg, userId, token);
        if (Objects.isNull(count)) {
            UserIdTokenMapping userIdTokenMapping = new UserIdTokenMapping();
            userIdTokenMapping.setPkg(pkg);
            userIdTokenMapping.setUserId(userId);
            userIdTokenMapping.setToken(token);
            count = userIdTokenMappingMapper.selectCount(new QueryWrapper<>(userIdTokenMapping));
            if (Objects.nonNull(count) && count > 0) {
                userAuthCache.setUserIdTokenMapping(pkg, userId, token, count);
            }
        }
        return count;
    }

    @Override
    public String getLatestToken(String pkg, Long userId) {
        String token = userAuthCache.getLatestToken(pkg, userId);
        if (Strings.isBlank(token)) {
            token = userIdTokenMappingMapper.selelctLatestToken(pkg, userId);
            if (Strings.isNotBlank(token)) {
                userAuthCache.setLatestToken(pkg, userId, token);
            }
        }
        return token;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserAuthDO createAuth(Long userId, String token, Long appId) {
        if (userId == null || token == null) {
            throw new IllegalArgumentException("required parameters are missing");
        }

        String accessToken = generatAccessToken();

        UserAuthDO userAuthDO = new UserAuthDO();

        userAuthDO.setUserId(userId);
        userAuthDO.setToken(token);
        userAuthDO.setAccessToken(accessToken);
        userAuthDO.setAppId(appId);

        long currentTimeMillis = System.currentTimeMillis();
        userAuthDO.setCreateTime(currentTimeMillis);
        userAuthDO.setLastModifyTime(currentTimeMillis);
        // 添加到数据库中
        userAuthMapper.insert(userAuthDO);
        return userAuthDO;
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public UserAuthDTO updateAccessTokenByUserId(Long userId) {
        // 删除缓存
        UserAuthDO oldUserAuthDO = userAuthMapper.selectByUserId(userId);
        userAuthCache.delUserAuthDtoByAccessToken(oldUserAuthDO.getAccessToken());

        String accessToken = generatAccessToken();
        UserAuthDO userAuthDO = new UserAuthDO();
        userAuthDO.setAccessToken(accessToken);
        userAuthDO.setLastModifyTime(System.currentTimeMillis());
        int rows = userAuthMapper.update(userAuthDO, new UpdateWrapper<UserAuthDO>().eq("user_id", oldUserAuthDO.getUserId()));
        if (rows <= 0) {
            throw new BusinessException(ErrorCodeEnum.USER_AUTH_INFO_NOT_FOUND);
        }

        // 清空缓存
        userAuthCache.delUserAuthDtoByAccessToken(accessToken);
        UserAuthDTO userAuthDTO = new UserAuthDTO();
        BeanUtils.copyProperties(userAuthDO, userAuthDTO);
        return userAuthDTO;
    }

}
