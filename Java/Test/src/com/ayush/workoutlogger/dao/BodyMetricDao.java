package com.ayush.workoutlogger.dao;

import com.ayush.workoutlogger.model.BodyMetric;
import java.time.LocalDate;
import java.util.List;

public interface BodyMetricDao {
    BodyMetric create(BodyMetric b);
    void update(BodyMetric b);
    void delete(int id);
    List<BodyMetric> findByUserAndDateRange(int userId, LocalDate from, LocalDate to);
}
