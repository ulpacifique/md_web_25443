package com.auca.library.model;

public enum MembershipType {
    GOLD(5, 50.0),
    SILVER(3, 30.0),
    STRIVER(2, 10.0);

    private final int maxBooks;
    private final double dailyFee;

    MembershipType(int maxBooks, double dailyFee) {
        this.maxBooks = maxBooks;
        this.dailyFee = dailyFee;
    }

    public int getMaxBooks() {
        return maxBooks;
    }

    public double getDailyFee() {
        return dailyFee;
    }
}