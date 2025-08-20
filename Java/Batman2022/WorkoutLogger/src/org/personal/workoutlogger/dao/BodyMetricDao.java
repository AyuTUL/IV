package org.personal.workoutlogger.dao;
import java.util.List;
import org.personal.workoutlogger.model.BodyMetric;
public interface BodyMetricDao {
  void add(BodyMetric bm);
  java.util.List<BodyMetric> findByUser(int userId);
}
