package com.hello.world.controllers;

import com.hello.world.services.clis.FederativeUnitsService;
import com.hello.world.services.dtos.FederativeUnitsDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Optional;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class FederativeUnitesControllerTest {

    @Autowired
    private FederativeUnitsService federativeUnitsService;

    @Test
    public void findAllByNationIsPresent() {
        Optional<?> states = federativeUnitsService.findAllByNation("Brazil", "english");
        Assert.assertTrue("Era para os estados estarem presentes", states.isPresent());
    }

    @Test
    public void findAllByNation() {
        List<FederativeUnitsDTO> states = federativeUnitsService.findAllByNationInternal("Angola", "english");
        Assert.assertTrue("Era pra retornar uma lista preenchida.", states.size() > 0);
    }

    @Test
    public void findAllByNationPrecision() {
        List<FederativeUnitsDTO> states = federativeUnitsService.findAllByNationInternal("Brasil", "português");
        Assert.assertEquals("Era pra vir a lista com todos os estados brasileiros", 27, states.size());
    }
}
