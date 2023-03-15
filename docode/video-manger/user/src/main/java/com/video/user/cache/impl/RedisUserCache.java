package com.video.user.cache.impl;

import com.video.user.cache.UserCache;
import com.video.user.domain.dto.user.BaseUserDTO;
import com.video.user.util.SimpleDateUtils;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class RedisUserCache extends BaseRedisCache implements UserCache {

    private static final String STRING_USER_MAX_WITHDR_CONT_SIGN_IN_DAYS_KEY_TMPL = "spredis:user:mwcsd:%s";

    private static final String STRING_USER_CURRENT_WITHDR_CONT_SIGN_IN_DAYS_KEY_TMPL = "spredis:user:cwcsd:%s";

    private static final String STRING_USER_INVITATION_CODE_KEY_TMPL = "spredis:user:invcode:ui:%s";

    private static final String STRING_USER_INVITED_KEY_TMPL = "spredis:user:inved:ui:%s";

    private static final String STRING_USER_MAX_INVITATION_NOT_BROKEN_CONTINUOUS_SIGNIN_DAYS_KEY_TMPL = "spredis:user:max:invt:nbrk:ctn:sin:days:ui:%s";

    private static final String STRING_USER_INVITED_HAS_SIGN_IN_BROKEN_KEY_TMPL = "spredis:user:invt:hassin:broken:ui:%s";

//    private static final String STRING_USER_CONTINUS_SIGN_IN_INFO_KEY_TMPL = "spredis:user_continus_sign_in_info:ui:%s";

    private static final String STRING_USER_CREATE_TIME_BY_USER_ID_KEY_TMPL = "spredis:user:createtime:ui:%s";

    // private static final String STRING_USER_CREATE_TIME_KEY_TMPL = "spredis:user:create:time:%s";

    private static final String STRING_USER_DETAIL_BY_INVITATION_CODE_KEY_TMPL = "spredis:userdetail:invcd:%s";

    private static final String STRING_BASE_USER_BY_ID = "spredis:baseuser:id:%s";

    private String buildUserWithdrMaxContinuousSignInDaysKey(Long userId) {
        return String.format(STRING_USER_MAX_WITHDR_CONT_SIGN_IN_DAYS_KEY_TMPL, userId);
    }

    private String buildUserCurrentWithdrContinuousSignInDaysKey(Long userId) {
        return String.format(STRING_USER_CURRENT_WITHDR_CONT_SIGN_IN_DAYS_KEY_TMPL, userId);
    }

    private String buildUserInvitationCodeKey(Long userId) {
        return String.format(STRING_USER_INVITATION_CODE_KEY_TMPL, userId);
    }

    private String buildUserInvitedKey(Long userId) {
        return String.format(STRING_USER_INVITED_KEY_TMPL, userId);
    }

    private String buildMaxInvitedNotBrokenContinuousSignInDaysKey(Long userId) {
        return String.format(STRING_USER_MAX_INVITATION_NOT_BROKEN_CONTINUOUS_SIGNIN_DAYS_KEY_TMPL, userId);
    }

    private String buildInvitedHasSignInBrokenKey(Long userId) {
        return String.format(STRING_USER_INVITED_HAS_SIGN_IN_BROKEN_KEY_TMPL, userId);
    }

//    private String buildUserContinuousSignInInfoKey(Long userId) {
//        return String.format(STRING_USER_CONTINUS_SIGN_IN_INFO_KEY_TMPL, userId);
//    }

    private String buildCreateTimeKey(Long userId) {
        return String.format(STRING_USER_CREATE_TIME_BY_USER_ID_KEY_TMPL, userId);
    }

    private String buildUserDetailKey(String invitationCode) {
        return String.format(STRING_USER_DETAIL_BY_INVITATION_CODE_KEY_TMPL, invitationCode);
    }

    private String buildBaseUserByIdKey(Long id) {
        return String.format(STRING_BASE_USER_BY_ID, id);
    }

    @Override
    public Long getUserMaxWithdrawalContinuousSignInDays(Long userId) {
        String cacheKey = buildUserWithdrMaxContinuousSignInDaysKey(userId);
        String daysStr = get(cacheKey);
        if (daysStr == null) {
            return null;
        }
        return Long.valueOf(daysStr);
    }

    @Override
    public void setUserMaxWithdrawalContinuousSignInDaysExpire(Long userId, Long days) {
        String cacheKey = buildUserWithdrMaxContinuousSignInDaysKey(userId);
        setAndExpire(cacheKey, days);
    }

    @Override
    public void delUserMaxWithdrawalContinuousSignInDaysExpire(Long userId) {
        String cacheKey = buildUserWithdrMaxContinuousSignInDaysKey(userId);
        del(cacheKey);
    }

    @Override
    public Long getUserCurrentWithdrawalContinuousSignInDays(Long userId) {
        String cacheKey = buildUserCurrentWithdrContinuousSignInDaysKey(userId);
        String daysStr = get(cacheKey);
        if (daysStr == null) {
            return null;
        }
        return Long.valueOf(daysStr);
    }

    @Override
    public void setUserCurrentWithdrawalContinuousSignInDaysExpire(Long userId, Long days) {
        String cacheKey = buildUserCurrentWithdrContinuousSignInDaysKey(userId);
        setAndExpire(cacheKey, days);
    }

    @Override
    public void delUserCurrentWithdrawalContinuousSignInDays(Long userId) {
        String cacheKey = buildUserCurrentWithdrContinuousSignInDaysKey(userId);
        del(cacheKey);
    }

    @Override
    public String getUserInvitationCode(Long userId) {
        String cacheKey = buildUserInvitationCodeKey(userId);
        String code = get(cacheKey);
        if (ILLEGAL_VALUE.equals(code)) {
            return "";
        }
        return get(cacheKey);
    }

    @Override
    public void setUserInvitationCodeDetailExpire(Long userId, String invitationCode) {
        String script =
                "redis.call('set', KEYS[1], ARGV[1], 'PX', ARGV[3])\n" +
                "redis.call('set', KEYS[2], ARGV[2], 'PX', ARGV[3])";
        String invitationCodeCacheKey = buildUserInvitationCodeKey(userId);
        String userDetailCacheKey = buildUserDetailKey(invitationCode);

        eval(script, 2, invitationCodeCacheKey, userDetailCacheKey,
                invitationCode, Long.toString(userId), Long.toString(SimpleDateUtils.DAY));
    }

    @Override
    public void delUserInvitationDetailCode(Long userId) {
        // build invitation code key
        String invitationCodeCacheKey = buildUserInvitationCodeKey(userId);

        // build user detail by invitation code key
        String invitationCode = getUserInvitationCode(userId);
        String userDetailCacheKey = buildUserDetailKey(invitationCode);

        del(invitationCodeCacheKey, userDetailCacheKey);
    }

    @Override
    public Long getUserByInvitationCode(String invitationCode) {
        String cacheKey = buildUserDetailKey(invitationCode);
        String userIdStr = get(cacheKey);
        return userIdStr == null ? null : Long.valueOf(userIdStr);
    }

    @Override
    public Boolean isInvitedUser(Long userId) {
        String cacheKey = buildUserInvitedKey(userId);
        String invitedStr = get(cacheKey);
        if (invitedStr == null) {
            return null;
        }
        return Boolean.valueOf(invitedStr);
    }

    @Override
    public void setUserInvitedExpire(Long userId, boolean invitationCode) {
        String cacheKey = buildUserInvitedKey(userId);
        setAndExpire(cacheKey, invitationCode);
    }

    @Override
    public void delUserInvited(Long userId) {
        String cacheKey = buildUserInvitedKey(userId);
        del(cacheKey);
    }

    @Override
    public Long getMaxInvitedNotBrokenContinuousSignInDays(Long userId) {
        String cacheKey = buildMaxInvitedNotBrokenContinuousSignInDaysKey(userId);
        String daysStr = get(cacheKey);
        return daysStr == null ? null : Long.valueOf(daysStr);
    }

    @Override
    public void setMaxInvitedNotBrokenContinuousSignInDaysExpire(Long userId, Long days) {
        String cacheKey = buildMaxInvitedNotBrokenContinuousSignInDaysKey(userId);
        setAndExpire(cacheKey, days);
    }

    @Override
    public void delMaxInvitedNotBrokenContinuousSignInDays(Long userId) {
        String cacheKey = buildMaxInvitedNotBrokenContinuousSignInDaysKey(userId);
        del(cacheKey);
    }

    @Override
    public Boolean invitedHasSignInBroken(Long userId) {
        String cacheKey = buildInvitedHasSignInBrokenKey(userId);
        String hasBrokenStr = get(cacheKey);
        return hasBrokenStr == null ? null : Boolean.valueOf(hasBrokenStr);
    }

    @Override
    public void setInvitedHasSignInBrokenExpire(Long userId, Boolean hasBroken) {
        String cacheKey = buildInvitedHasSignInBrokenKey(userId);
        setAndExpire(cacheKey, hasBroken);
    }

    @Override
    public void delInvitedHasSignInBroken(Long userId) {
        String cacheKey = buildInvitedHasSignInBrokenKey(userId);
        del(cacheKey);
    }

    @Override
    public Date getUserCreateTimeById(Long userId){
        String cacheKey = buildCreateTimeKey(userId);
        return get(cacheKey, Date.class);
    }

    @Override
    public void setUserCreateTimeByIdExpire(Long userId, Date createTime) {
        String cacheKey = buildCreateTimeKey(userId);
        setAndExpire(cacheKey, createTime);
    }

    @Override
    public BaseUserDTO getBaseUserDTOById(Long id) {
        String cacheKey = buildBaseUserByIdKey(id);
        return get(cacheKey, BaseUserDTO.class);
    }

    @Override
    public void setBaseUserDTOByIdExpire(Long id, BaseUserDTO baseUserDTO) {
        String cacheKey = buildBaseUserByIdKey(id);
        setAndExpire(cacheKey, baseUserDTO);
    }

    @Override
    public void delBaseUserDTOById(Long id) {
        String cacheKey = buildBaseUserByIdKey(id);
        del(cacheKey);
    }
}
