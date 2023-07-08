package com.imongjeomong.imongjeomongserver.review.model.service;

import com.imongjeomong.imongjeomongserver.attraction.model.repository.AttractionRepository;
import com.imongjeomong.imongjeomongserver.dto.ReviewDto;
import com.imongjeomong.imongjeomongserver.entity.Attraction;
import com.imongjeomong.imongjeomongserver.entity.Member;
import com.imongjeomong.imongjeomongserver.entity.Review;
import com.imongjeomong.imongjeomongserver.entity.ReviewImage;
import com.imongjeomong.imongjeomongserver.entity.common.EditTime;
import com.imongjeomong.imongjeomongserver.exception.CommonException;
import com.imongjeomong.imongjeomongserver.exception.CustomExceptionStatus;
import com.imongjeomong.imongjeomongserver.exception.UnAuthenticationException;
import com.imongjeomong.imongjeomongserver.member.model.repository.MemberRepository;
import com.imongjeomong.imongjeomongserver.review.model.repository.ReviewImageRepository;
import com.imongjeomong.imongjeomongserver.review.model.repository.ReviewRepository;
import com.imongjeomong.imongjeomongserver.util.AwsS3Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewImageRepository reviewImageRepository;
    private final AttractionRepository attractionRepository;
    private final MemberRepository memberRepository;

    private final AwsS3Util awsS3Util;

    @Override
    @Transactional
    public void saveReview(ReviewDto reviewDto, MultipartFile image) throws IOException {
        String imageUrl = awsS3Util.s3SaveFile(image);

        // db 에 저장할 데이터 조회
        Attraction attraction = attractionRepository.findById(reviewDto.getAttractionId())
                .orElseThrow(() -> new CommonException(CustomExceptionStatus.ATTRACTION_NOT_FOUND));
        Member member = memberRepository.findById(reviewDto.getMemberId())
                .orElseThrow(() -> new CommonException(CustomExceptionStatus.AUTHENTICATION_MEMBER_IS_NULL));

        // Review 저장
        Review review = new Review();
        review.setAttraction(attraction);
        review.setMember(member);
        review.setContent(reviewDto.getContent());
        EditTime editTime = new EditTime();
        editTime.setCreateTime(LocalDateTime.now());
        review.setEditTime(editTime);
        reviewRepository.save(review);

        // Review Image 저장
        ReviewImage reviewImage = new ReviewImage();
        reviewImage.setReview(review);
        reviewImage.setImagePath(imageUrl);
        reviewImageRepository.save(reviewImage);
    }

    @Override
    @Transactional
    public List<ReviewDto> getReviewList(Long attractionId) {
        List<ReviewDto> resultList = new ArrayList<>();

        List<Review> reviewList = reviewRepository.findByAttractionId(attractionId);

        for (Review review : reviewList) {
            ReviewDto reviewDto = ReviewDto.builder()
                    .reviewId(review.getId())
                    .memberId(review.getMember().getId())
                    .memberName(review.getMember().getNickname())
                    .attractionId(attractionId)
                    .imagePath(review.getReviewImageList().get(0).getImagePath())
                    .content(review.getContent())
                    .createTime(review.getEditTime().getCreateTime())
                    .build();
            resultList.add(reviewDto);
        }

        return resultList;
    }


    /**
     * 리뷰 수정 로직
     */
    @Override
    @Transactional
    public void updateReview(Long memberId, Long reviewId, MultipartFile image, String content) throws IOException {
        // 회원, 리뷰 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new UnAuthenticationException(CustomExceptionStatus.AUTHENTICATION_MEMBER_IS_NULL));

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CommonException(CustomExceptionStatus.REVIEW_NOT_FOUND));

        // 리뷰 수정
        if (image != null) {
            List<ReviewImage> reviewImageList = reviewImageRepository.findByReviewId(review.getId());
            String imageUrl = awsS3Util.s3SaveFile(image);
            reviewImageList.get(0).setImagePath(imageUrl);
        }

        review.setContent(content);
    }

    @Override
    @Transactional
    public void deleteReview(Long memberId, Long reviewId) {
        // 회원, 리뷰, 리뷰이미지 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new UnAuthenticationException(CustomExceptionStatus.AUTHENTICATION_MEMBER_IS_NULL));

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CommonException(CustomExceptionStatus.REVIEW_NOT_FOUND));

        List<ReviewImage> reviewImageList = reviewImageRepository.findByReviewId(review.getId());

        // 작성자와 삭제요청한 아이디가 다를 경우 예외처리
        if (!Objects.equals(member.getId(), review.getMember().getId())) {
            throw new CommonException(CustomExceptionStatus.REVIEW_MEMBER_NOT_MACHED);
        }

        // 리뷰와 이미지 제거
        if (reviewImageList != null) {
            reviewImageRepository.deleteAllInBatch(reviewImageList);
        }
        reviewRepository.delete(review);

    }

    @Override
    @Transactional
    public void saveReviewByMyAttractionId(ReviewDto reviewDto, MultipartFile image) throws IOException {
        String imageUrl = awsS3Util.s3SaveFile(image);

        // db 에 저장할 데이터 조회
        Attraction attraction = attractionRepository.findById(reviewDto.getAttractionId())
                .orElseThrow(() -> new CommonException(CustomExceptionStatus.ATTRACTION_NOT_FOUND));
        Member member = memberRepository.findById(reviewDto.getMemberId())
                .orElseThrow(() -> new CommonException(CustomExceptionStatus.AUTHENTICATION_MEMBER_IS_NULL));

        List<Review> reviewList = reviewRepository.findByMyAttractionIdAndMemberId(reviewDto.getMyAttractionId(), reviewDto.getMemberId());
        if (reviewList.size() > 0) throw new CommonException(CustomExceptionStatus.REVIEW_ALREADY_EXIST);

        // Review 저장
        Review review = new Review();
        review.setAttraction(attraction);
        review.setMember(member);
        review.setContent(reviewDto.getContent());
        EditTime editTime = new EditTime();
        editTime.setCreateTime(LocalDateTime.now());
        review.setEditTime(editTime);
        review.setMyAttractionId(reviewDto.getMyAttractionId());
        reviewRepository.save(review);

        // Review Image 저장
        ReviewImage reviewImage = new ReviewImage();
        reviewImage.setReview(review);
        reviewImage.setImagePath(imageUrl);
        reviewImageRepository.save(reviewImage);
    }

    @Override
    @Transactional
    public ReviewDto getReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CommonException(CustomExceptionStatus.REVIEW_NOT_FOUND));

        return ReviewDto.builder()
                .reviewId(review.getId())
                .memberId(review.getMember().getId())
                .memberName(review.getMember().getNickname())
                .attractionId(review.getAttraction().getId())
                .myAttractionId(review.getMyAttractionId())
                .imagePath(review.getReviewImageList().get(0).getImagePath())
                .content(review.getContent())
                .createTime(review.getEditTime().getCreateTime())
                .build();
    }

}
