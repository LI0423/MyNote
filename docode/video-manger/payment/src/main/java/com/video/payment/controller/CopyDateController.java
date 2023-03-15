package com.video.payment.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.video.entity.AppDO;
import com.video.entity.AppPaymentConfigDO;
import com.video.entity.AppPaymentConfigMappingDO;
import com.video.entity.AppPaymentWeChatConfigDO;
import com.video.entity.PayOrderChannelEnum;
import com.video.payment.mapper.AppMapper;
import com.video.payment.mapper.AppPaymentConfigMapper;
import com.video.payment.mapper.AppPaymentConfigMappingMapper;
import com.video.payment.mapper.AppPaymentWeChatConfigMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/copy")
public class CopyDateController {

    @Autowired
    private AppMapper appMapper;

    @Autowired
    private AppPaymentWeChatConfigMapper appPaymentWeChatConfigMapper;

    @Autowired
    private AppPaymentConfigMappingMapper appPaymentConfigMappingMapper;

    @Autowired
    private AppPaymentConfigMapper appPaymentConfigMapper;


    @GetMapping("/wechat")
    public String copyToWechat() {
        List<AppDO> appDOS = appMapper.selectAllApp();
        for (int i = 0; i < appDOS.size(); i++) {
            AppPaymentWeChatConfigDO appPaymentWeChatConfigDO = new AppPaymentWeChatConfigDO();
            BeanUtils.copyProperties(appDOS.get(i), appPaymentWeChatConfigDO);
            appPaymentWeChatConfigDO.setAppId(appDOS.get(i).getId());
            appPaymentWeChatConfigDO.setId(null);
            appPaymentWeChatConfigMapper.insert(appPaymentWeChatConfigDO);
            // 付款
            AppPaymentConfigMappingDO appPaymentConfigMappingDO = new AppPaymentConfigMappingDO();
            appPaymentConfigMappingDO.setAppId(appDOS.get(i).getId());
            appPaymentConfigMappingDO.setMerchantId(appPaymentWeChatConfigDO.getId());
            appPaymentConfigMappingDO.setStatus(0);
            appPaymentConfigMappingDO.setMappingType(0);
            appPaymentConfigMappingDO.setMerchantType(PayOrderChannelEnum.WE_CHAT.getCode());
            appPaymentConfigMappingMapper.insert(appPaymentConfigMappingDO);

            // 收款
            AppPaymentConfigMappingDO appPaymentConfigMappingDO1 = new AppPaymentConfigMappingDO();
            appPaymentConfigMappingDO1.setAppId(appDOS.get(i).getId());
            appPaymentConfigMappingDO1.setMerchantId(appPaymentWeChatConfigDO.getId());
            appPaymentConfigMappingDO1.setStatus(0);
            appPaymentConfigMappingDO1.setMappingType(1);
            appPaymentConfigMappingDO1.setMerchantType(PayOrderChannelEnum.WE_CHAT.getCode());
            appPaymentConfigMappingMapper.insert(appPaymentConfigMappingDO1);

        }
        return "success";
    }

    @GetMapping("/ali")
    public String writeAliMapping() {
        List<AppPaymentConfigDO> appPaymentConfigDOS = appPaymentConfigMapper.selectList(new QueryWrapper<AppPaymentConfigDO>());
        for (int i = 0; i < appPaymentConfigDOS.size(); i++) {
            // 付款
            AppPaymentConfigMappingDO appPaymentConfigMappingDO = new AppPaymentConfigMappingDO();
            appPaymentConfigMappingDO.setAppId(appPaymentConfigDOS.get(i).getAppId());
            appPaymentConfigMappingDO.setMerchantId(appPaymentConfigDOS.get(i).getId());
            appPaymentConfigMappingDO.setStatus(0);
            appPaymentConfigMappingDO.setMappingType(0);
            appPaymentConfigMappingDO.setMerchantType(PayOrderChannelEnum.ALI_PAY.getCode());
            appPaymentConfigMappingMapper.insert(appPaymentConfigMappingDO);

            // 收款
            AppPaymentConfigMappingDO appPaymentConfigMappingDO1 = new AppPaymentConfigMappingDO();
            appPaymentConfigMappingDO.setAppId(appPaymentConfigDOS.get(i).getAppId());
            appPaymentConfigMappingDO.setMerchantId(appPaymentConfigDOS.get(i).getId());
            appPaymentConfigMappingDO1.setStatus(0);
            appPaymentConfigMappingDO1.setMappingType(1);
            appPaymentConfigMappingDO1.setMerchantType(PayOrderChannelEnum.ALI_PAY.getCode());
            appPaymentConfigMappingMapper.insert(appPaymentConfigMappingDO1);
        }
        return "ok";
    }
}
