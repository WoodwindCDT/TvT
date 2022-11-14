package Assets;

public final class Constants {

    // default dis-allowing instantiation
    private Constants() {};

    // Constant values
    public static final double PI = 3.14159;
    public static final double GRAVITY = 9.81;

    // Functions which should not change, likely used with calculations
    // Help with Projectile Motion NO HEIGHT
    public static final double calcVel_X() {
        return 0;
    };
    
    public static final double calcVel_Y() {
        return 0;
    };

    public static final double calcfPos_X_NOHEIGHT() {
        return 0;
    };

    public static final double calcTOF_NOHEIGHT() {
        return 0;
    };

    public static final double calcYmax_NOHEIGHT() {
        return 0;
    };

    // Help with Projectile Motion WITH HEIGHT > 0
    public static final double calcfPos_X_WITHHEIGHT() {
        return 0;
    };

    public static final double calcYmax_WITHHEIGHT() {
        return 0;
    };

    public static final double calcTOF_WITHHEIGHT() {
        return 0;
    };

    // Helper methods
    private static final double toPos(double num) {
        return Math.abs(num);
    }

    private static final double toRad(double angle) {
        return Math.toRadians(angle);
    }
};