package com.hello.world.controllers;

import com.hello.world.services.clis.ContinentService;
import com.hello.world.services.clis.NationService;
import com.hello.world.services.dtos.NationDTO;
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
public class NationControllerTest {
    @Autowired
    private NationService nationService;
    @Autowired
    private ContinentService continentService;

    @Test
    public void findAllNationPerContinentAndNativeLandExist() {
        Optional<?> nations = nationService.findAllByContinent("Africa", "english");

        Assert.assertTrue("Há países neste continente", nations.isPresent());
    }

    @Test
    public void findAllNationPerContinentAndNativeLandNotExist() {
        List<NationDTO> nations = nationService.findAllByContinentInternal("Africo", "Italiano", continentService.findAllInternal());
        Assert.assertTrue("Não retornou lista de países do continente digitado pela lingua nativa do usuário", nations == null);
    }

    @Test
    public void findAllNations() {

        List<NationDTO> nationDTOList = nationService.findAll();
        nationDTOList.forEach(nationDTO -> {
            System.out.println("Nação: " + nationDTO.getName());
            System.out.println("Nação: " + nationDTO.getFlag());
        });
        Assert.assertEquals("Lista de países dos quatro cantos da terra.", 250, nationDTOList.size());
    }
}
