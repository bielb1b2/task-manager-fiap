CREATE TABLE IF NOT EXISTS person_tasks_stats (
    person_id VARCHAR(255) PRIMARY KEY,
    tasks_created INTEGER DEFAULT 0,
    tasks_completed INTEGER DEFAULT 0,
    tasks_deleted INTEGER DEFAULT 0,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);