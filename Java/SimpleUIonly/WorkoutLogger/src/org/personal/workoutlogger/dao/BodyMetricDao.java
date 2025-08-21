package org.personal.workoutlogger.dao;
import java.util.List;
import org.personal.workoutlogger.model.BodyMetric;
public interface BodyMetricDao {
  void add(BodyMetric bm);
  void update(BodyMetric bm);
  void delete(int id);
  java.util.List<BodyMetric> findByUser(int userId);
  // convenience alias
  default java.util.List<BodyMetric> getByUser(int userId){ return findByUser(userId); }
}
