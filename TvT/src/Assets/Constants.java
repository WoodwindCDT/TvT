package Assets;

public final class Constants {

    // default dis-allowing instantiation
    private Constants() {};

    // Constant values
    public static final double GRAVITY = -9.81;
    public static final double POS_GRAVITY = 9.81;

    // Functions which should not change, likely used with calculations
    // Help with Projectile Motion NO HEIGHT
    public static final double calcVel_X(double v, double a) {
        return v * (Math.cos(a));
    };
    
    public static final double calcVel_Y(double v, double a) {
        return v * (Math.sin(a));
    };

    public static final double distance() {
        return toPos(2 * GRAVITY);
    };

    public static final double calc_vyS(double vy) {
        return Math.pow(vy, 2);
    }

    public static final double calcfPos_X_NOHEIGHT(double vx, double vy) {
        return toPos((2 * (vx) * (vy))/GRAVITY);
    };

    public static final double calcTOF_NOHEIGHT(double vy) {
        return toPos((2 * vy)/(GRAVITY));
    };

    public static final double calcYmax_NOHEIGHT(double vyS) {
        return toPos(vyS/distance());
    };

    // Help with Projectile Motion WITH HEIGHT > 0
    public static final double calcfPos_X_WITHHEIGHT(double vyS, double h, double vx, double vy) {
        return toPos((vx) * ((vy) + Math.sqrt(vyS + 2 * POS_GRAVITY * h))/ POS_GRAVITY);
    };

    public static final double calcYmax_WITHHEIGHT(double vyS, double h) {
        return toPos(h + vyS/distance());
    };

    public static final double calcTOF_WITHHEIGHT(double vy, double vyS, double h) {
        return toPos((((vy) + (Math.sqrt(vyS + 2 * POS_GRAVITY * h))) / POS_GRAVITY));
    };

    // Helper methods
    public static final double toPos(double num) {
        return Math.abs(num);
    };

    public static final double toRad(double angle) {
        return Math.toRadians(angle);
    };
};