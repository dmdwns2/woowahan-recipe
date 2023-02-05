package com.woowahan.recipe.service;

import com.woowahan.recipe.domain.dto.itemDto.ItemDetailResDto;
import com.woowahan.recipe.domain.dto.recipeDto.RecipeFindResDto;
import com.woowahan.recipe.domain.dto.userDto.UserResponse;
import com.woowahan.recipe.domain.entity.ItemEntity;
import com.woowahan.recipe.domain.entity.RecipeEntity;
import com.woowahan.recipe.domain.entity.UserEntity;
import com.woowahan.recipe.exception.AppException;
import com.woowahan.recipe.exception.ErrorCode;
import com.woowahan.recipe.repository.ItemRepository;
import com.woowahan.recipe.repository.RecipeCustomRepository;
import com.woowahan.recipe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class FindService {

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final RecipeCustomRepository recipeCustomRepository;

    public UserResponse findUserName(String userName) {
        UserEntity user = userRepository.findByUserName(userName).orElseThrow(() -> {
            throw new AppException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage());
        });

        return UserResponse.toUserResponse(user);
    }

    public ItemDetailResDto findItemName(Long id) {
        ItemEntity item = itemRepository.findById(id).orElseThrow(() -> {
            throw new AppException((ErrorCode.ITEM_NOT_FOUND), ErrorCode.ITEM_NOT_FOUND.getMessage());
        });
        return ItemDetailResDto.from(item);
    }

    public Page<RecipeFindResDto> findMyLikeRecipe(String userName, Pageable pageable) {
        Page<RecipeEntity> pages = recipeCustomRepository.findByUserAndLove(userName, pageable);
        return pages.map(RecipeEntity::from);
    }

}
