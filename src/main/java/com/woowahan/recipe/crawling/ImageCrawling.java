package com.woowahan.recipe.crawling;

import com.woowahan.recipe.domain.dto.Response;
import com.woowahan.recipe.service.CrawlingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashSet;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/crawling")
public class ImageCrawling {

    private final CrawlingService crawlingService;

    @GetMapping
    public Response<String> getIngredients() throws IOException {
        HashSet<String> ingredientHash = crawlingService.ingredientCrawling();
        crawlingService.productCrawling(ingredientHash);
//        crawlingService.productCrawling(new HashSet<String>());
        return Response.success("크롤링 성공 완료");
    }
}
