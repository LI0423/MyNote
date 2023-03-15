package com.video.user.cache;

import com.video.user.domain.dto.user.BaseUserDTO;

import java.util.Date;

public interface UserCache {

    Long getUserMaxWithdrawalContinuousSignInDays(Long userId);

    void setUserMaxWithdrawalContinuousSignInDaysExpire(Long userId, Long days);

    void delUserMaxWithdrawalContinuousSignInDaysExpire(Long userId);


    Long getUserCurrentWithdrawalContinuousSignInDays(Long userId);

    void setUserCurrentWithdrawalContinuousSignInDaysExpire(Long userId, Long days);

    void delUserCurrentWithdrawalContinuousSignInDays(Long userId);

    String getUserInvitationCode(Long userId);

//    void setUserInvitationCodeExpire(Long userId, String invitationCode);

    void setUserInvitationCodeDetailExpire(Long userId, String invitationCode);

    void delUserInvitationDetailCode(Long userId);

    Long getUserByInvitationCode(String invitationCode);

    Boolean isInvitedUser(Long userId);

    void setUserInvitedExpire(Long userId, boolean invitationCode);

    void delUserInvited(Long userId);

    Long getMaxInvitedNotBrokenContinuousSignInDays(Long userId);

    void setMaxInvitedNotBrokenContinuousSignInDaysExpire(Long userId, Long days);

    void delMaxInvitedNotBrokenContinuousSignInDays(Long userId);

    Boolean invitedHasSignInBroken(Long userId);

    void setInvitedHasSignInBrokenExpire(Long userId, Boolean hasBroken);

    void delInvitedHasSignInBroken(Long userId);

    Date getUserCreateTimeById(Long userId);

    void setUserCreateTimeByIdExpire(Long userId, Date createTime);

    /**
     * 通过id从缓存中获取BaseUserDTO对象
     * @param id 用户id
     * @return BaseUserDTO对象
     */
    BaseUserDTO getBaseUserDTOById(Long id);

    /**
     * 将BaseUserDTO对象设置到缓存中
     * @param id 用户id
     * @param baseUserDTO BaseUserDTO对象
     */
    void setBaseUserDTOByIdExpire(Long id, BaseUserDTO baseUserDTO);

    /**
     * 删除缓存
     * @param id 用户id
     */
    void delBaseUserDTOById(Long id);
}
