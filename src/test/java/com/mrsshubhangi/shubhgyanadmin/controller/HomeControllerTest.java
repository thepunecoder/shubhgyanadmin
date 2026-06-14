package com.mrsshubhangi.shubhgyanadmin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrsshubhangi.shubhgyanadmin.service.TipContentService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HomeControllerTest {

    @Test
    void homeShouldReturnHomeView() {
        TipContentService service = null;
        ObjectMapper mapper = null;
        HomeController controller = new HomeController(service, mapper);

        String view = controller.home();
        assertEquals("home", view);
    }
}

