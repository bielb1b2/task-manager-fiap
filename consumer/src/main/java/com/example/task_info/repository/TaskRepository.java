package com.example.task_info.repository;

import com.example.task_info.model.PersonTaskStats;
import com.example.task_info.utils.Action;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TaskRepository {

    private final JdbcTemplate jdbcTemplate;

    public TaskRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<PersonTaskStats> rowMapper = (rs, rowNum) -> new PersonTaskStats(
            rs.getString("person_id"),
            rs.getInt("tasks_created"),
            rs.getInt("tasks_completed"),
            rs.getInt("tasks_deleted"),
            rs.getTimestamp("updated_at").toLocalDateTime()
    );

    public Optional<PersonTaskStats> findByPersonId(String personId) {
        String sql = "SELECT * FROM person_tasks_stats WHERE person_id = ?";
        try {
            PersonTaskStats stats= jdbcTemplate.queryForObject(sql, rowMapper, personId);
            return Optional.ofNullable(stats);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public void incrementCounter(String personId, Action action) {
        String column = switch (action) {
            case CREATE -> "tasks_created";
            case FINISH -> "tasks_completed";
            case DELETE -> "tasks_deleted";
        };

        String sql = String.format(
                """
                INSERT INTO person_tasks_stats (person_id, %s, updated_at)
                VALUES (?, 1, CURRENT_TIMESTAMP)
                ON CONFLICT (person_id)
                DO UPDATE SET %s = person_tasks_stats.%s + 1, updated_at = CURRENT_TIMESTAMP
                """, column, column, column
        );

        jdbcTemplate.update(sql, personId);
    }
}
