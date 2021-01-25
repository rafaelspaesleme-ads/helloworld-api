package com.hello.world.controllers;

import com.hello.world.services.clis.CityService;
import com.hello.world.services.dtos.CityDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CityControllerTest {

    @Autowired
    private CityService cityService;

    @Test
    public void findAllByFUisPresente() {
        List<CityDTO> cityDTOList = cityService.findAllByFUInternal("Brasil", "português", "Rio de Janeiro");
        Assert.assertTrue("Era para esta presente", cityDTOList.size() > 0);
    }

    @Test
    public void findAllByZipCodeContains() {
        List<CityDTO> cityDTOList = cityService.findAllZipCodeInternal("25800", "BR");
        Assert.assertTrue("Era para esta presente", cityDTOList.size() > 0);
    }

    @Test
    public void findAllByZipCodeEspecification() {
        List<CityDTO> cityDTOList1 = cityService.findAllZipCodeInternal("25800-000", "BR");
        Assert.assertTrue("Era para esta presente", cityDTOList1.size() > 0);
    }
}
