package com.example.letscareer.stage.repository;

import com.example.letscareer.schedule.domain.Schedule;
import com.example.letscareer.stage.domain.Stage;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface StageRepository extends JpaRepository<Stage, Long> {

    @Query("SELECT st FROM Stage st WHERE st.schedule.user.userId = :userId AND FUNCTION('MONTH', st.date) = :month")
    Page<Stage> findAllByUserIdAndMonth(@Param("userId") Long userId, @Param("month") int month, Pageable pageable);
    List<Stage>findAllByDate(Date date);
}
