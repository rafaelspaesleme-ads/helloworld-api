package com.hello.world.controllers;

import com.hello.world.services.clis.ContinentService;
import com.hello.world.services.dtos.ContinentDTO;
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
import java.util.stream.Collectors;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ContinentControllerTest {
    @Autowired
    private ContinentService continentService;

    @Test
    public void findAllPresent() {
        Optional<?> continents = continentService.findAll();
        Assert.assertTrue("Retornou um valor presente!", continents.isPresent());
    }

    @Test
    public void findAllContinents() {
        List<ContinentDTO> continentes = continentService.findAllInternal();
        Assert.assertEquals("Retornou uma lista", 7, continentes.size());
    }

    @Test
    public void findByNameContinent() {
        Optional<ContinentDTO> africa = continentService.findAllInternal()
                .stream()
                .filter(continentDTO -> continentDTO.getName().equals("Africa"))
                .findFirst();
        Assert.assertTrue("Retornou continente escolhido", africa.isPresent());
    }

    @Test
    public void findAllNotPresent() {
        List<ContinentDTO> continentDTOList = continentService.findAllInternal()
                .stream()
                .filter(continentDTO -> continentDTO.getGeonameIdWorld().equals(5))
                .collect(Collectors.toList());

        Assert.assertTrue("Retornou um valor não presente!", continentDTOList.isEmpty());
    }
}
