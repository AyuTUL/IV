package com.ayush.workoutlogger.dao.impl;

import com.ayush.workoutlogger.dao.WorkoutSessionDao;
import com.ayush.workoutlogger.model.WorkoutSession;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class WorkoutSessionDaoListImpl implements WorkoutSessionDao {
    private final Map<Integer, WorkoutSession> data = new HashMap<>();
    private final AtomicInteger seq = new AtomicInteger(1);

    @Override
    public WorkoutSession create(WorkoutSession s) {
        int id = seq.getAndIncrement();
        s.setId(id);
        data.put(id, s);
        return s;
    }

    @Override
    public void update(WorkoutSession s) {
        data.put(s.getId(), s);
    }

    @Override
    public void delete(int id) {
        data.remove(id);
    }

    @Override
    public WorkoutSession findById(int id) {
        return data.get(id);
    }

    @Override
    public List<WorkoutSession> findByUserAndDateRange(int userId, LocalDate from, LocalDate to) {
        List<WorkoutSession> out = new ArrayList<>();
        for (WorkoutSession s : data.values()) {
            if (s.getUserId() == userId) {
                LocalDate d = s.getDate();
                if ((from == null || !d.isBefore(from)) && (to == null || !d.isAfter(to))) {
                    out.add(s);
                }
            }
        }
        out.sort(Comparator.comparing(WorkoutSession::getDate));
        return out;
    }
}
