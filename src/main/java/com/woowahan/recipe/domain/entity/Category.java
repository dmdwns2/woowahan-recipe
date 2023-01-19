package com.woowahan.recipe.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Category {

    FRUIT("과일", 1),
    VEGETABLE("채소", 2),
    GRAINS("잡곡", 3),
    NUT("견과", 4),
    FRESH("정육/계란", 5),
    SEAFOOD("해산물", 6),
    MILK("유제품", 7),
    SIDEDISH("반찬", 8),
    DRINK("생수/음료", 9),
    TEA("커피/원두/차", 10),
    NOODLE("면류/통조림", 11),
    SEASONING("양념", 12),
    BAKERY("베이커리", 13),
    JAM("잼", 14)
    ;

    private String viewName;
    private Integer code;

}
