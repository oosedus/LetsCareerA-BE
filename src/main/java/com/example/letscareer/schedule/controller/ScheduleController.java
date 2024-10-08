package com.example.letscareer.schedule.controller;

import com.example.letscareer.common.dto.ApiResponse;
import com.example.letscareer.common.dto.ErrorResponse;
import com.example.letscareer.common.dto.SuccessNonDataResponse;
import com.example.letscareer.common.dto.SuccessResponse;
import com.example.letscareer.common.exception.enums.SuccessCode;
import com.example.letscareer.common.exception.model.BadRequestException;
import com.example.letscareer.common.exception.model.NotFoundException;
import com.example.letscareer.schedule.domain.dto.request.SchedulePostRequest;
import com.example.letscareer.schedule.domain.dto.request.UpdateScheduleProgressRequest;
import com.example.letscareer.schedule.domain.dto.response.*;
import com.example.letscareer.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @GetMapping("/coming")
    public ApiResponse getSchedulesComing(
            @RequestHeader("userId") Long userId,
            @RequestParam("month") int month,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {
            try {
                ScheduleResponse scheduleResponse = scheduleService.getSchedulesComing(userId, month, page, size);
                return SuccessResponse.success(SuccessCode.SCHEDULE_SUCCESS, scheduleResponse);
            }catch (NotFoundException | BadRequestException e) {
                return ErrorResponse.error(e.getErrorCode());
            }
    }

    @GetMapping("/calendar")
    public ApiResponse getSchedulesCalendar(
            @RequestHeader("userId") Long userId,
            @RequestParam("month") int month) {
        try {
            CalendarResponse calendarResponse= scheduleService.getSchedulesCalendar(userId, month);
            return SuccessResponse.success(SuccessCode.SCHEDULE_SUCCESS, calendarResponse);
        }catch (NotFoundException | BadRequestException e) {
            return ErrorResponse.error(e.getErrorCode());
        }
    }

    @GetMapping("/date")
    public ApiResponse getDateSchedules(
            @RequestHeader("userId") Long userId,
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date
    ){
        try{
            DateClickScheduleResponse response = scheduleService.getDateSchedules(userId, date);
            return SuccessResponse.success(SuccessCode.SCHEDULE_SUCCESS, response);
        }catch (NotFoundException | BadRequestException e) {
            return ErrorResponse.error(e.getErrorCode());
        }
    }
    @GetMapping("/always")
    public ApiResponse getSchedules(
            @RequestHeader("userId") Long userId,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {

        try {
            AlwaysResponse alwaysResponse = scheduleService.getAlwaysList(userId, page, size);
            return SuccessResponse.success(SuccessCode.SCHEDULE_SUCCESS, alwaysResponse);
        }catch (NotFoundException | BadRequestException e) {
            return ErrorResponse.error(e.getErrorCode());
        }
    }
    @GetMapping("/reviews/fast")
    public ApiResponse getFastReviews(
            @RequestHeader("userId") Long userId,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {

        try {
            FastReviewListResponse fastReviewListResponse = scheduleService.getFastReviews(userId, page, size);
            return SuccessResponse.success(SuccessCode.FAST_REVIEW_LIST_SUCEESS, fastReviewListResponse);
        }catch (NotFoundException | BadRequestException e) {
            return ErrorResponse.error(e.getErrorCode());
        }
    }
    @GetMapping("/reviews/company")
    public ApiResponse getCompanyReviews(
            @RequestHeader("userId") Long userId,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {

        try {
            CompanyReviewListResponse companyReviewListResponse = scheduleService.getCompanyReviewList(userId, page, size);
            return SuccessResponse.success(SuccessCode.REVIEW_LIST_SUCCESS, companyReviewListResponse);
        }catch (NotFoundException | BadRequestException e) {
            return ErrorResponse.error(e.getErrorCode());
        }
    }

    @PostMapping
    public ApiResponse postNewSchedule(
            @RequestHeader("userId") Long userId,
            @RequestBody SchedulePostRequest request
            ){
        try{
            scheduleService.postSchedule(userId, request);
            return SuccessNonDataResponse.success(SuccessCode.POST_SCHEDULE_SUCCESS);
        }catch (NotFoundException | BadRequestException e) {
            return ErrorResponse.error(e.getErrorCode());
        }
    }

    @PutMapping("/{scheduleId}/progress")
    public ApiResponse updateScheduleProgress(
            @RequestHeader("userId") Long userId,
            @PathVariable Long scheduleId,
            @RequestBody UpdateScheduleProgressRequest request
    ){
        try{
            scheduleService.updateScheduleProgress(userId, scheduleId, request);
            return SuccessNonDataResponse.success(SuccessCode.UPDATE_SCHEDULE_PROGRESS_SUCCESS);
        }catch (NotFoundException | BadRequestException e) {
            return ErrorResponse.error(e.getErrorCode());
        }
    }
}
