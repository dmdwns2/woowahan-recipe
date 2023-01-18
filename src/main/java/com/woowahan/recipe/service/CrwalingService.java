package com.woowahan.recipe.service;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CrwalingService {

    public void webCrawling() {
        String preUrl = "https://www.10000recipe.com/recipe/"
        Document doc = null;
        List<String> contentList = null;
        List<String> ingredientList = null;

        try {
            for (int i = 6995600; i < 6995649; i++) {  // 50개만 crawling
                contentList = new ArrayList<>();
                ingredientList = new ArrayList<>();

                // title
                doc = Jsoup.connect(preUrl + i).get();
                try {
                    Thread.sleep(1000);  // Thread 1초 일시 정지
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }


                // recipe
                Elements content = doc.select("#contentList_area > div:nth-child(11) > div");

                for (int j = 1; j < content.size() - 2; j++) {
                    contentList.add(content.get(j).text());
                }

                /*
                 * Refactoring
                 * date : 2023-01-18
                 * desc : 양념이 있는 레시피와 없는 레시피가 있으므로 나중에 리팩토링 필요
                 */
                Elements ingredients = doc.select("#divConfirmedMaterialArea > ul:nth-child(1) > li");
                StringBuilder sb = new StringBuilder();
                for (int k = 0; k < ingredients.size(); k++) {
                    String ingreContents = ingredients.get(k).text()
                    ingredientList.add(ingreContents);
                    sb.append(ingreContents + " ");
                }
                log.info("재료 : {}" + sb.toString());  // 재료가 잘 불러와졌는지 확인

                // image
                Elements img = doc.select("#contents_area > div.view2_pic > div.centeredcrop img");
                String imgPath= img.attr("src");
                URL imgUrl = new URL(img.attr("src"));
                BufferedImage jpg = ImageIO.read(imgUrl);
                File file = new File("C:\\PJT\\woowahan\\img" + "\\" + i + ".jpg");
                ImageIO.write(jpg, "jpg", file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
