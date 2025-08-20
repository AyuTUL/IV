package com.ayush.workoutlogger.dao.impl;

import com.ayush.workoutlogger.dao.BodyMetricDao;
import com.ayush.workoutlogger.model.BodyMetric;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class BodyMetricDaoListImpl implements BodyMetricDao {
    private final Map<Integer, BodyMetric> data = new HashMap<>();
    private final AtomicInteger seq = new AtomicInteger(1);

    @Override
    public BodyMetric create(BodyMetric b) {
        int id = seq.getAndIncrement();
        b.setId(id);
        data.put(id, b);
        return b;
    }

    @Override
    public void update(BodyMetric b) {
        data.put(b.getId(), b);
    }

    @Override
    public void delete(int id) {
        data.remove(id);
    }

    @Override
    public List<BodyMetric> findByUserAndDateRange(int userId, LocalDate from, LocalDate to) {
        List<BodyMetric> out = new ArrayList<>();
        for (BodyMetric b : data.values()) {
            if (b.getUserId() == userId) {
                LocalDate d = b.getDate();
                if ((from == null || !d.isBefore(from)) && (to == null || !d.isAfter(to))) {
                    out.add(b);
                }
            }
        }
        out.sort(Comparator.comparing(BodyMetric::getDate));
        return out;
    }
}
