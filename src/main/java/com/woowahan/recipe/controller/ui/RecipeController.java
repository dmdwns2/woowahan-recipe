package com.woowahan.recipe.controller.ui;

import com.woowahan.recipe.domain.dto.cartDto.CartItemListReqDto;
import com.woowahan.recipe.domain.dto.cartDto.CartItemReq;
import com.woowahan.recipe.domain.dto.itemDto.ItemListForRecipeResDto;
import com.woowahan.recipe.domain.dto.recipeDto.*;
import com.woowahan.recipe.domain.dto.reviewDto.ReviewCreateRequest;
import com.woowahan.recipe.service.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/recipes")
@Slf4j
public class RecipeController {

    private final RecipeService recipeService;
    private final FindService findService;
    private final ReviewService reviewService;
    private final CartService cartService;
    private final S3Uploader s3Uploader;

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("recipeCreateReqDto", new RecipeCreateReqDto());
        return "recipe/createForm";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute RecipeCreateReqDto form, BindingResult result, Authentication authentication,MultipartFile file) throws IOException {
        if (result.hasErrors()) {
            return "recipe/createForm";
        }
        String userName = authentication.getName();
        // image upload
        String filePath = "recipes"; // 파일경로
        String recipeImagePath = s3Uploader.upload(file, filePath);
        form.setFilePath(recipeImagePath);
        recipeService.createRecipe(form, userName);
        return "redirect:/recipes/list";
    }

    @GetMapping("/update/{recipeId}")
    public String updateForm(Model model, @PathVariable Long recipeId) {

        model.addAttribute("recipeUpdateReqDto", recipeService.findRecipe(recipeId));
        model.addAttribute("recipeId", recipeId);
        return "recipe/updateForm";
    }

    @PostMapping("/update/{recipeId}")
    public String update(@Valid @ModelAttribute RecipeUpdateReqDto form, BindingResult result, @PathVariable Long recipeId, RedirectAttributes redirectAttributes, Authentication authentication, MultipartFile file) throws IOException {
        if (result.hasErrors()) {
            return "recipe/updateForm";
        }
        String userName = authentication.getName();
        // image upload
        String filePath = "recipes"; // 파일경로
        String recipeImagePath = s3Uploader.upload(file, filePath);
        form.setRecipeImagePath(recipeImagePath);
        RecipeUpdateResDto resDto = recipeService.updateRecipe(form, recipeId, userName);
        redirectAttributes.addAttribute("recipeId", resDto.getRecipeId());
        return "redirect:/recipes/{recipeId}";
    }

    @GetMapping("/delete/{recipeId}")
    public String delete(@PathVariable Long recipeId, Authentication authentication) {
        String userName = authentication.getName();
        recipeService.deleteRecipe(recipeId, userName);
        return "redirect:/recipes/list";
    }


    @GetMapping("/{recipeId}/likes")
    public String pushLike(@PathVariable Long recipeId, Authentication authentication) {
        String userName = authentication.getName();
        recipeService.pushLikes(recipeId, userName);
        return "redirect:/recipes/{recipeId}";
    }

    @GetMapping("/list")
    public String list(Model model, @PageableDefault(size = 5, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<RecipePageResDto> allRecipes = recipeService.findAllRecipes(pageable);

        // pagination
        return paging(model, allRecipes);
    }

    @GetMapping("/my")
    public String myRecipes(Authentication authentication, Model model, @PageableDefault(size = 20, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
        String userName = authentication.getName();
        Page<RecipePageResDto> myRecipes = recipeService.myRecipes(pageable, userName);

        int nowPage = myRecipes.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, myRecipes.getTotalPages());
        int lastPage = myRecipes.getTotalPages();

        model.addAttribute("myRecipes", myRecipes);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("lastPage", lastPage);
        return "user/my/myRecipes";
    }

    @GetMapping("/likes/my")
    public String myLikeRecipe(Model model, Authentication authentication, @PageableDefault(size = 20) Pageable pageable) {
        Page<RecipeFindResDto> myLikeRecipeList = findService.findMyLikeRecipe(authentication.getName(), pageable);

        int nowPage = myLikeRecipeList.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, myLikeRecipeList.getTotalPages());
        int lastPage = myLikeRecipeList.getTotalPages();

        model.addAttribute("myLikeRecipeList", myLikeRecipeList);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("lastPage", lastPage);
        return "user/my/myLikeRecipe";
    }

    /**
     * 레시피 검색
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/search")
    public String search(Model model, @ModelAttribute RecipeSearchReqDto recipeSearchReqDto, @PageableDefault(size = 5, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<RecipePageResDto> allRecipes = recipeService.searchRecipes(recipeSearchReqDto.getKeyword(), pageable);

        return paging(model, allRecipes);
    }


    @Getter
    @AllArgsConstructor
    class SearchResponse {
        private final List<String> results;
    }

    /**
     * 재료검색
     *
     * @param keyword
     * @return
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/searchItem")
    @ResponseBody
    public SearchResponse searchItem(@RequestParam String keyword) {
        // TODO: support paging
        Page<ItemListForRecipeResDto> allItems = recipeService.searchItemPage(keyword, PageRequest.of(0, 100));
        return new SearchResponse(allItems
                .stream()
                .map(ItemListForRecipeResDto::getName)
                .collect(Collectors.toList()));
    }

    /**
     * TODO : 2023-01-31 레시피 페이징 중복 코드 정리
     *
     * @param model
     * @param allRecipes
     * @return
     */
    private String paging(Model model, Page<RecipePageResDto> allRecipes) {
        int nowPage = allRecipes.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, allRecipes.getTotalPages());
        int lastPage = allRecipes.getTotalPages();

        model.addAttribute("allRecipes", allRecipes);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("lastPage", lastPage);
        return "recipe/recipeList";
    }

    @PostMapping("/{recipeId}/reviews")
    public String createReview(@PathVariable Long recipeId,
                               @Valid @ModelAttribute ReviewCreateRequest reviewCreateRequest
            , BindingResult result,
                               Authentication authentication) {
        if (result.hasErrors()) {
            return "recipe/createForm";
        }
        String userName = authentication.getName();
        reviewService.createReview(recipeId, reviewCreateRequest, userName);
        return "redirect:/recipes/{recipeId}";
    }

    @PostMapping("/update/{recipeId}/reviews/{reviewId}")
    public String updateReview(@PathVariable Long recipeId, @PathVariable Long reviewId,
                               @Valid @ModelAttribute ReviewCreateRequest reviewCreateRequest,
                               BindingResult result,
                               Authentication authentication) {
        if (result.hasErrors()) {
            return "recipe/createForm";
        }
        String userName = authentication.getName();
        reviewService.updateReview(recipeId, reviewId, reviewCreateRequest, userName);
        return "redirect:/recipes/{recipeId}";
    }

    @PostMapping("/{recipeId}/reviews/{reviewId}")
    public String deleteReview(@PathVariable Long recipeId, @PathVariable Long reviewId,
                               Authentication authentication) {
        reviewService.deleteReview(recipeId, reviewId, authentication.getName());
        return "redirect:/recipes/{recipeId}";
    }

    /*@PostMapping("/cart/{recipeId}")
    public String addCartItemList(@PathVariable Long recipeId, @ModelAttribute CartItemListReqDto cartItemListReqDto,
                              Authentication authentication) {
        cartService.addCartItemList(cartItemListReqDto, authentication.getName());

        return "redirect:/recipes/{recipeId}";
    }*/

    @GetMapping("/{recipeId}")
    public String findRecipe(@PathVariable Long recipeId, Model model) {
        log.info("삭제 시도");
        recipeService.updateView(recipeId); // 조회수 증가
        RecipeFindResDto recipe = recipeService.findRecipe(recipeId);
        model.addAttribute("reviewCreateRequest", new ReviewCreateRequest());
        model.addAttribute("cartItemListReqDto", new CartItemListReqDto());
        model.addAttribute("recipeId", recipeId);
        model.addAttribute("recipe", recipe);
        model.addAttribute("cartItemReq", new CartItemReq(recipe.getItems().get(0).getItem().getId(), 1)); // 장바구니 담기 위해 필요
        return "recipe/recipeDetailList";
    }

    /**
     * 장바구니에 재료 담기
     */
    @PostMapping("/carts")
    public String addCartItemList(@ModelAttribute CartItemReq cartItemReq, Model model,
                                  Authentication authentication, HttpServletRequest request) {
        model.addAttribute("cartItemReq", cartItemReq);
        log.info("장바구니 아이템 요청");
        cartService.addCartItem(cartItemReq, authentication.getName());
        log.info("장바구니 서비스 다녀옴");
        return "redirect:/carts";
    }
}

