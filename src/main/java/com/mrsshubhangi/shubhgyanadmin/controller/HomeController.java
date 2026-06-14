package com.mrsshubhangi.shubhgyanadmin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrsshubhangi.shubhgyanadmin.domain.TipContent;
import com.mrsshubhangi.shubhgyanadmin.service.TipContentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@Controller
public class HomeController {

    private final TipContentService service;
    private final ObjectMapper mapper;

    public HomeController(
            TipContentService service,
            ObjectMapper mapper) {

        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/")
    public String home() {
        try {
            // show count on home page
            // delegate to template via model if needed in future
            return "home";
        } catch (Exception e) {
            return "home";
        }
    }
}