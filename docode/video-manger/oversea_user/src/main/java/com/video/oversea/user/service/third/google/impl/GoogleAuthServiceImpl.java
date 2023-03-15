package com.video.oversea.user.service.third.google.impl;

import com.video.oversea.user.domain.dto.third.google.GoogleUserDTO;
import com.video.oversea.user.domain.dto.third.google.GoogleUserVerifyDTO;
import com.video.oversea.user.service.third.google.GoogleAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class GoogleAuthServiceImpl implements GoogleAuthService {

    @Autowired
    protected RestTemplate restTemplate;

    @Override
    public GoogleUserDTO verify(String pkg, String idToken) {
        try {
            GoogleUserVerifyDTO googleUserVerifyDTO = new GoogleUserVerifyDTO();
            googleUserVerifyDTO.setPkg(pkg);
            googleUserVerifyDTO.setIdToken(idToken);
            // 请求下游接口
            // 重试机制
            ResponseEntity<GoogleUserDTO> responseEntity = null;
            for (int i = 0; i <= 2; i++) {
                try {
                    responseEntity =
                            restTemplate.postForEntity("http://119.13.88.37:8090/api/oversea/verify/user",
                                    googleUserVerifyDTO, GoogleUserDTO.class);

                    log.info("Pay notify downstream result:[{}], pay order id:[{}]",
                            responseEntity.getBody(), "id");

                    break;
                } catch (Exception e) {
                    log.error("Payment notify post request error. retry num:" + i + ", idToken:" + idToken, e);
                    continue;
                }
            }
            GoogleUserDTO googleUserDTO = responseEntity.getBody();
            return googleUserDTO;
        } catch (Exception e) {
            log.error("verify google user error", e);
            return null;
        }
    }
}
