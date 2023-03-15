//package com.video.manager;
//
//import com.video.manager.domain.dto.CrabAddressDTO;
//import com.video.manager.service.CrabAddressService;
//import org.junit.Assert;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//public class CrabAddressServiceTest extends ApiApplicationTests {
//    @Autowired
//    private CrabAddressService crabAddressService;
//    @Test
//    public void testInsert(){
//        CrabAddressDTO crabAddressDTO = new CrabAddressDTO();
//        crabAddressDTO.setSource("guoguo");
//        crabAddressDTO.setUrl("http://spshare.pickdoki.cn/#/?id=85055&type=10&videoId=85055&userId=0&videoType=10&catId=251&appPackage=com.mg.ggvideo");
//        Assert.assertSame("failed",true,crabAddressService.insert(crabAddressDTO));
//    }
//}
