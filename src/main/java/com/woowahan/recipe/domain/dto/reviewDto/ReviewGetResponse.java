package com.woowahan.recipe.domain.dto.reviewDto;

import com.woowahan.recipe.domain.entity.ReviewEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewGetResponse {
    private Long reviewId;
    private String username;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime lastModified;

    public static ReviewGetResponse toReviewGetResponse(ReviewEntity review) {
        return ReviewGetResponse.builder()
                .reviewId(review.getReviewId())
                .username(review.getUser().getUserName())
                .content(review.getContent())
                .createdDate(review.getCreatedDate())
                .lastModified(review.getLastModifiedDate())
                .build();
    }

}
