package com.example.letscareer.schedule.domain.dto.request;

import com.example.letscareer.schedule.domain.model.Progress;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public record UpdateScheduleProgressRequest(
        @Enumerated(EnumType.STRING)
        Progress progress
) {
}
