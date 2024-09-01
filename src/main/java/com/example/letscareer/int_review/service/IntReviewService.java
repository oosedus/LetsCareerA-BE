package com.example.letscareer.int_review.service;

import com.example.letscareer.common.exception.model.NotFoundException;
import com.example.letscareer.int_review.domain.IntReview;
import com.example.letscareer.int_review.dto.request.PostIntReviewRequest;
import com.example.letscareer.int_review.repository.IntReviewRepository;
import com.example.letscareer.mid_review.domain.MidReview;
import com.example.letscareer.schedule.domain.Schedule;
import com.example.letscareer.schedule.repository.ScheduleRepository;
import com.example.letscareer.stage.domain.Stage;
import com.example.letscareer.stage.repository.StageRepository;
import com.example.letscareer.user.domain.User;
import com.example.letscareer.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.letscareer.common.exception.enums.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class IntReviewService {

    @Autowired
    private final IntReviewRepository intReviewRepository;
    private final ScheduleRepository scheduleRepository;
    private final StageRepository stageRepository;
    private final UserRepository userRepository;

    @Transactional
    public void postIntReview(Long userId, Long scheduleId, Long stageId, PostIntReviewRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_EXCEPTION));
        Schedule schedule = scheduleRepository.findByUserAndScheduleId(user, scheduleId)
                .orElseThrow(() -> new NotFoundException(SCHEDULE_NOT_FOUND_EXCEPTION));
        Stage stage = stageRepository.findByStageIdAndSchedule(stageId, schedule)
                .orElseThrow(() -> new NotFoundException(STAGE_NOT_FOUND_EXCEPTION));

        IntReview intReview = IntReview.builder()
                        .stage(stage)
                        .method(request.details())
                        .questions(request.qa())
                        .feelings(request.feel())
                        .user(user)
                        .build();

        intReviewRepository.save(intReview);
    }
}
