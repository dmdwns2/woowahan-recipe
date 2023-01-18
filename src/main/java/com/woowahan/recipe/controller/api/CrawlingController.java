package com.woowahan.recipe.controller.api;


import com.woowahan.recipe.service.CrwalingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/crawling")
public class CrawlingController {

    private final CrwalingService crawlingService;

    @GetMapping("/setup")
    public void readSite() throws IOException {


    }

}
