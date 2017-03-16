package au.org.teambacon.ftc.element;

public class Gamepad {
    public ButtonState A, B, X, Y; // right side of controller
    public ButtonState DPAD_DOWN, DPAD_RIGHT, DPAD_LEFT, DPAD_UP; // dpad
    public ButtonState LEFT_BUMPER, RIGHT_BUMPER; // bumpers
    public ButtonState LEFT_STICK, RIGHT_STICK; // sticks

    public double LEFT_TRIGGER, RIGHT_TRIGGER;

    public double LEFT_STICK_X, LEFT_STICK_Y;
    public double RIGHT_STICK_X, RIGHT_STICK_Y;

    protected com.qualcomm.robotcore.hardware.Gamepad Gamepad;

    public Gamepad(com.qualcomm.robotcore.hardware.Gamepad gamepad) {
        Gamepad = gamepad;
    }

    public com.qualcomm.robotcore.hardware.Gamepad getGamepad() {
        return Gamepad;
    }

    public ButtonState updateButton(boolean state, ButtonState previous) {
        if (previous == null)
            return ButtonState.INACTIVE;

        if (state == true) {
            if (previous == ButtonState.INACTIVE || previous == ButtonState.RELEASED)
                return ButtonState.PRESSED;
            else if (previous == ButtonState.PRESSED)
                return ButtonState.ACTIVE;
        } else if (state == false) {
            if (previous == ButtonState.ACTIVE || previous == ButtonState.PRESSED)
                return ButtonState.RELEASED;
            else if (previous == ButtonState.RELEASED)
                return ButtonState.INACTIVE;
        }

        return previous;
    }

    public void update() {
        A = updateButton(Gamepad.a, A);
        B = updateButton(Gamepad.b, B);
        X = updateButton(Gamepad.x, X);
        Y = updateButton(Gamepad.y, Y);

        DPAD_DOWN = updateButton(Gamepad.dpad_down, DPAD_DOWN);
        DPAD_RIGHT = updateButton(Gamepad.dpad_right, DPAD_RIGHT);
        DPAD_LEFT = updateButton(Gamepad.dpad_left, DPAD_LEFT);
        DPAD_UP = updateButton(Gamepad.dpad_up, DPAD_UP);

        LEFT_BUMPER = updateButton(Gamepad.left_bumper, LEFT_BUMPER);
        RIGHT_BUMPER = updateButton(Gamepad.right_bumper, RIGHT_BUMPER);

        LEFT_STICK = updateButton(Gamepad.left_stick_button, LEFT_STICK);
        RIGHT_STICK = updateButton(Gamepad.right_stick_button, RIGHT_STICK);

        LEFT_TRIGGER = Gamepad.left_trigger;
        RIGHT_TRIGGER = Gamepad.right_trigger;

        LEFT_STICK_X = Gamepad.left_stick_x;
        LEFT_STICK_Y = Gamepad.left_stick_y;

        RIGHT_STICK_X = Gamepad.right_stick_x;
        RIGHT_STICK_Y = Gamepad.right_stick_y;
    }
}
