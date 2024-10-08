package com.example.letscareer.self_intro.service;

import com.example.letscareer.common.exception.model.NotFoundException;
import com.example.letscareer.schedule.domain.model.Schedule;
import com.example.letscareer.schedule.domain.repository.ScheduleRepository;
import com.example.letscareer.self_intro.domain.model.SelfIntro;
import com.example.letscareer.self_intro.domain.dto.SelfIntroDTO;
import com.example.letscareer.self_intro.domain.dto.request.SaveSelfIntroRequest;
import com.example.letscareer.self_intro.domain.repository.SelfIntroRepository;
import com.example.letscareer.stage.domain.model.Stage;
import com.example.letscareer.stage.domain.repository.StageRepository;
import com.example.letscareer.user.domain.User;
import com.example.letscareer.user.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static com.example.letscareer.common.exception.enums.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class SelfIntroService {

    @Autowired
    private final SelfIntroRepository selfIntroRepository;
    private final ScheduleRepository scheduleRepository;
    private final StageRepository stageRepository;
    private final UserRepository userRepository;

    @Transactional
    public void saveSelfIntro(Long userId, Long scheduleId, Long stageId, SaveSelfIntroRequest request) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new NotFoundException(SCHEDULE_NOT_FOUND_EXCEPTION));
        Stage stage = stageRepository.findByStageIdAndSchedule(stageId, schedule)
                .orElseThrow(() -> new NotFoundException(STAGE_NOT_FOUND_EXCEPTION));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_EXCEPTION));

        // 현재 stage에 있는 모든 SelfIntro를 삭제한다.
        selfIntroRepository.deleteByStage(stage);

        // 새로 들어온 자기소개서 항목을 저장한다.
        for (SelfIntroDTO selfIntroDTO : request.selfIntros()) {
            SelfIntro selfIntro = SelfIntro.of(selfIntroDTO, stage);
            selfIntroRepository.save(selfIntro);
        }
    }
}
