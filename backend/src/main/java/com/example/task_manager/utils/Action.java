package com.example.task_manager.utils;

public enum Action {
    CREATE("CREATE"),
    DELETE("DELETE"),
    UPDATE("UPDATE"),
    FINISH("FINISH");

    private final String key;

    Action(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public static Action fromKey(String key) {
        for (Action action : Action.values()) {
            if (action.getKey().equalsIgnoreCase(key)) {
                return action;
            }
        }
        throw new IllegalArgumentException("Action inválida: " + key);
    }
}
