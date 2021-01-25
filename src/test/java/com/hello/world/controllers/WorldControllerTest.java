package com.hello.world.controllers;

import com.hello.world.services.clis.WorldService;
import com.hello.world.utils.functions.TemplatesFunction;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class WorldControllerTest {

    @Autowired
    private WorldService worldService;
    @Autowired
    private TemplatesFunction templatesFunction;

    @Test
    public void findOrSaveWithParam() {
        Optional<?> terra = worldService.findOrSave("Terra");

        Assert.assertTrue("Terra existe!", terra.isPresent());
    }

    @Test
    public void findOrSaveNotParam() {
        Optional<?> terra = worldService.findOrSave(null);

        Assert.assertTrue("Terra existe!", terra.isPresent());
    }

    @Test
    public void startProject() {
        String pageHtml = templatesFunction.initalPageHtml();

        Assert.assertTrue("Terra existe!", !pageHtml.isEmpty());
    }

    @Test
    public void startProjectNull() {
        String pageHtml = templatesFunction.initalPageHtml();

        Assert.assertFalse("Terra existe!", pageHtml.isEmpty());
    }


    @Test
    public void findOrSaveWithParamNotPlanet() {
        Optional<?> terra = worldService.findOrSave("Marte");

        Assert.assertTrue("Não é o planeta terra", terra.isEmpty());
    }
}
