package by.jwd.cafe.model.entity;

/**
 * The {@code PaymentType} class
 * is enum that represents order payment type.
 */

public enum PaymentType {
    ACCOUNT(15),
    CASH(10),
    LOYALTY_POINTS(0);

    private static final char UNDERSCORE = '_';
    private static final char SPACE = ' ';

    /**
     * Percent of loyalty points that user
     * can get for the order, depends on payment type.
     */
    private int percentLoyaltyPoints;

    PaymentType(int percent) {
        percentLoyaltyPoints = percent;
    }

    public int getPercentLoyaltyPoints() {
        return percentLoyaltyPoints;
    }

    @Override
    public String toString() {
        return this.name().replace(UNDERSCORE, SPACE).toLowerCase();
    }
}
