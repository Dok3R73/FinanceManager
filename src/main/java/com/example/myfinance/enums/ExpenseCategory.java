package com.example.myfinance.enums;

public enum ExpenseCategory {
    FOOD("Еда"),
    TRANSPORT("Транспорт"),
    UTILITIES("Коммунальные услуги"),
    ENTERTAINMENT("Развлечения"),
    HEALTH("Здоровье"),
    CLOTHING("Одежда"),
    OTHER("Прочее");

    private final String displayName;

    ExpenseCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
