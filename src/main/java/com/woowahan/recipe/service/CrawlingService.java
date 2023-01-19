package com.woowahan.recipe.service;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Service
@Slf4j
public class CrawlingService {

    @Transactional
    public HashSet<String> ingredientCrawling() throws IOException {
        String preUrl = "https://www.10000recipe.com/recipe/";
        Document doc = null;
        List<String> contentList = null;
        HashSet<String> ingredientList = new HashSet<>();

        // TODO: 2023-01-19 6899902 ~ 6995623 크롤링 하는 법 다시 생각해보기 (현재 SocketException 으로 Connection reset)
        // TODO: 2023-01-19 6899992 ~ 6995623 👉 1시간 넘게 걸림
        for (int i = 6995663; i <= 6995673; i++) {  // 재료 crawling
            // contentList = new ArrayList<>();

            // title
            doc = Jsoup.connect(preUrl + i).get();
            try {
                Thread.sleep(1000);  // Thread 1초 일시 정지
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }

            Elements ingredients = doc.select("#divConfirmedMaterialArea > ul:nth-child(1) > li > a");

            System.out.println(i);
            ingredientList.addAll(createIngreList(ingredients, ingredientList));
            System.out.println(ingredientList);
        }
        if(ingredientList.contains("구매")) {
            ingredientList.remove("구매");
        }
        return ingredientList;
    }

    public HashSet<String> createIngreList(Elements contents, HashSet<String> ingredientList) {
        /*
         * Refactoring
         * date : 2023-01-18
         * desc : 양념이 있는 레시피와 없는 레시피가 있으므로 나중에 리팩토링 필요
         */
//        StringBuilder sb = new StringBuilder();
        for (int k = 0; k < contents.size(); k++) {
            /*String[] ingreArr = contents.get(k).text().split(" ");
            String food = ingreArr[0];
            String amount = "";
            if (ingreArr.length > 2) {
                amount = ingreArr[2];
            }*/
            String food = contents.get(k).text();
            ingredientList.add(food);
//            sb.append(food + " ");
        }
//        log.info("재료 : " + sb.toString().trim());  // 재료가 잘 불러와졌는지 확인
//        System.out.println(ingredientList);
        return ingredientList;
    }

    @Transactional
    public void productCrawling(HashSet<String> ingredientHash) throws IOException {
        System.out.println(ingredientHash);
        String preUrl = "https://www.coupang.com/np/search?component=";
        Document doc = null;

        for (String ingredient : ingredientHash) {
            System.out.println(ingredient);
            // title
            doc = Jsoup.connect(preUrl + ingredient + "&channel=user").get();
            try {
                Thread.sleep(1000);  // Thread 1초 일시 정지
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }

            Elements thumbnail = doc.select("#productList > li > a > dl > dt.image > img");
            for (int i = 0; i < thumbnail.size(); i++) {
                URL imgPath = new URL("https:" + thumbnail.attr("src"));
                BufferedImage jpg = ImageIO.read(imgPath);
                String today = new SimpleDateFormat("yyMMddSS").format(new Date());
                File file = new File("C:\\PJT\\woowahan\\img\\" + ingredient + "\\" + today + ".jpg");
                ImageIO.write(jpg, "jpg", file);
            }
        }
    }
}
