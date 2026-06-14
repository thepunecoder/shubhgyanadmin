package com.mrsshubhangi.shubhgyanadmin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrsshubhangi.shubhgyanadmin.domain.TipContent;
import com.mrsshubhangi.shubhgyanadmin.service.TipContentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Controller
public class TipContentController {

    private final TipContentService service;
    private final ObjectMapper mapper;

    public TipContentController(TipContentService service,
                                ObjectMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }


    // Upload JSON (file upload form posts to /upload - handled here)
    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file,
                         Model model) {

        try {
            List<TipContent> tips = Arrays.asList(
                    mapper.readValue(file.getInputStream(), TipContent[].class)
            );

            service.saveAll(tips);
            model.addAttribute("message", "Uploaded " + tips.size() + " tips");

        } catch (Exception e) {
            model.addAttribute("message", "Error: " + e.getMessage());
        }

        return "upload";
    }

    // LIST
    @GetMapping("/tips")
    public String list(Model model) throws Exception {
        model.addAttribute("tips", service.getAll());
        return "tips";
    }

    // Show CREATE form
    @GetMapping("/tips/create")
    public String createPage(Model model) {
        model.addAttribute("tip", new TipContent());
        return "create-tip";
    }

    // Handle CREATE form submit
    @PostMapping("/tips/create")
    public String createTip(@ModelAttribute TipContent tip) throws Exception {

        if (tip.getId() == null || tip.getId().isBlank()) {
            tip.setId(UUID.randomUUID().toString());
        }

        // set defaults on create
        tip.setCreatedAt(System.currentTimeMillis());
        tip.setViews(0);
        tip.setLikes(0);
        tip.setShareCount(0);

        service.save(tip);

        return "redirect:/tips";
    }

    // Show EDIT page
    @GetMapping("/tips/edit/{id}")
    public String editPage(@PathVariable String id,
                           Model model) throws Exception {

        model.addAttribute("tip", service.getById(id));
        return "edit-tip";
    }

    // Handle UPDATE submit
    @PostMapping("/tips/update/{id}")
    public String update(@PathVariable String id,
                         @ModelAttribute TipContent tip) throws Exception {

        // ensure id matches path
        tip.setId(id);

        // preserve createdAt when updating existing
        TipContent existing = service.getById(id);
        if (existing != null) {
            tip.setCreatedAt(existing.getCreatedAt());
            // if numeric counters are not provided, preserve existing values
            if (tip.getViews() == 0) tip.setViews(existing.getViews());
            if (tip.getLikes() == 0) tip.setLikes(existing.getLikes());
            if (tip.getShareCount() == 0) tip.setShareCount(existing.getShareCount());
        } else {
            // fallback - set createdAt if missing
            if (tip.getCreatedAt() == 0) {
                tip.setCreatedAt(System.currentTimeMillis());
            }
        }

        service.save(tip);
        return "redirect:/tips";
    }

    // DELETE
    @GetMapping("/tips/delete/{id}")
    public String delete(@PathVariable String id) throws Exception {
        service.delete(id);
        return "redirect:/tips";
    }

    // Download all tips as JSON
    @GetMapping(value = "/tips/download-json")
    public @ResponseBody byte[] downloadAllAsJson() throws Exception {
        List<TipContent> tips = service.getAll();
        return mapper.writeValueAsBytes(tips);
    }
}
