package com.objects.marketbridge.domains.review.controller;

import com.objects.marketbridge.common.responseobj.ApiResponse;
import com.objects.marketbridge.common.security.annotation.AuthMemberId;
import com.objects.marketbridge.common.security.annotation.UserAuthorize;
import com.objects.marketbridge.domains.review.dto.*;
import com.objects.marketbridge.domains.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    //리뷰 서베이 선택창 조회
    @GetMapping("/review-survey-options/{productId}")
    @UserAuthorize
    public ApiResponse<List<ReviewSurveyQuestionAndOptionsDto>> getReviewSurveyQuestionAndOptionsList
        (@PathVariable("productId") Long productId){
        List<ReviewSurveyQuestionAndOptionsDto> response
                = reviewService.getReviewSurveyQuestionAndOptionsList(productId);
        return ApiResponse.ok(response);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<ReviewIdDto> createReview
        (@Valid @RequestBody CreateReviewDto request, @AuthMemberId Long memberId) {
        reviewService.createReview(request, memberId);
        return ApiResponse.create();
    }

    @PatchMapping("")
    @UserAuthorize
    public ApiResponse<Void> updateReview(@RequestBody UpdateReviewDto request) {
        reviewService.updateReview(request);
        return ApiResponse.ok(null);
    }

    @DeleteMapping("/{reviewId}")
    @UserAuthorize
    public ApiResponse<Void> deleteReview(@PathVariable("reviewId") Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ApiResponse.ok(null);
    }

    //상품별 리뷰 리스트 조회(createdAt 최신순 내림차순 정렬 또는 likes 많은순 내림차순 정렬)
    //http://localhost:8080/product/1/reviews?page=0&sortBy=createdAt
    //http://localhost:8080/product/1/reviews?page=0&sortBy=liked
    @GetMapping("/product/{productId}")
    public ApiResponse<Void> getReviews(
            @PathVariable("productId") Long productId,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "sortBy", defaultValue = "createdAt") String sortBy) {

//        Pageable pageRequest = PageRequest.of(page, 5, Sort.by(sortBy).descending());
//        Page<ReviewWholeInfoDto> response = reviewService.getProductReviews(productId, pageRequest, sortBy);
        return ApiResponse.ok(null);
    }


    //리뷰아이디로 리뷰상세 단건 조회
//    @GetMapping("/review/{reviewId}")
//    public ApiResponse<ReviewSingleReadDto> getReview
//        (@PathVariable("reviewId") Long reviewId, @AuthMemberId Long memberId) {
//        ReviewSingleReadDto response = reviewService.getReview(reviewId, memberId);
//        return ApiResponse.ok(response);
//    }


//TODO: 리뷰리스트 조회, 리뷰조회, 상품별 리뷰 갯수, 리뷰 좋아요, mypage에 작성할 리뷰, 작성한 리뷰 리스트


//    //LIKE관련//
//    //회원별 리뷰 리스트 조회(createdAt 최신순 내림차순 정렬 또는 likes 많은순 내림차순 정렬)
//    //http://localhost:8080/member/1/reviews?page=0&sortBy=createdAt
//    //http://localhost:8080/member/1/reviews?page=0&sortBy=liked
//    @GetMapping("/member/{memberId}/reviews")
//    @UserAuthorize
//    public ApiResponse<Page<ReviewWholeInfoDto>> getMemberReviews(
//            @PathVariable("memberId") Long memberId,
//            @RequestParam(name = "page", defaultValue = "0") int page,
//            @RequestParam(name = "sortBy", defaultValue = "createdAt") String sortBy) {
//
//        Pageable pageRequest = PageRequest.of(page, 5, Sort.by(sortBy).descending());
//        Page<ReviewWholeInfoDto> response = reviewService.getMemberReviews(memberId, pageRequest, sortBy);
//        return ApiResponse.ok(response);
//    }



    //상품별 리뷰 총갯수 조회
    @GetMapping("/product/{productId}/reviews-count")
    public ApiResponse<ReviewsCountDto> getProductReviewsCount(@PathVariable("productId") Long productId){
        ReviewsCountDto reviewsCountDto = reviewService.getProductReviewsCount(productId);
        return ApiResponse.ok(reviewsCountDto);
    }



    //회원별 리뷰 총갯수 조회
    @GetMapping("/member/{memberId}/reviews-count")
    @UserAuthorize
    public ApiResponse<ReviewsCountDto> getMemberReviewsCount
        (@PathVariable("memberId") Long memberId){
        ReviewsCountDto reviewsCountDto = reviewService.getMemberReviewsCount(memberId);
        return ApiResponse.ok(reviewsCountDto);
    }




    //review_like upsert(없으면 create, 있으면 delete)
    @PostMapping("/{reviewId}/like")
    public ApiResponse<Void> upsertReviewLike
        (@PathVariable("reviewId") Long reviewId, @AuthMemberId Long memberId) {
        reviewService.upsertReviewLike(reviewId, memberId);
        return ApiResponse.ok(null);
    }

//    @DeleteMapping("/{reviewId}")
//    @UserAuthorize
//    public ApiResponse<Void> deleteReview(@PathVariable("reviewId") Long reviewId) {
//        reviewService.deleteReview(reviewId);
//        return ApiResponse.ok(null);
//    }



//    //LIKE관련//
//    //리뷰 좋아요 총갯수 조회
//    @GetMapping("/review/{reviewId}/likes/count")
//    public ApiResponse<ReviewLikesCountDto> countReviewLikes
//    (@PathVariable("reviewId") Long reviewId) {
//        ReviewLikesCountDto response = reviewService.countReviewLikes(reviewId);
//        return ApiResponse.ok(response);
//    }
}