package com.video.manager.controller;

import com.video.manager.domain.common.ResponseResult;
import com.video.manager.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: mango
 * @description:
 * @author: laojiang
 * @create: 2020-10-22 15:58
 **/
@RestController
@Slf4j
@RequestMapping("/api/v1/address")
public class AddressController {

    private AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public ResponseResult get(){

        return ResponseResult.success(addressService.findProvince());
    }

}
