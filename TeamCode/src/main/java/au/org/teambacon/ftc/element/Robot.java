package au.org.teambacon.ftc.element;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import au.org.teambacon.ftc.game.Alliance;

public abstract class Robot extends LinearOpMode {
    protected Alliance alliance;

    protected Gamepad gamepad1;
    protected Gamepad gamepad2;

    protected Telemetry telemetry;
    protected HardwareMap hardwareMap;

    public final void runOpMode() throws InterruptedException {
        gamepad1 = new Gamepad(super.gamepad1);
        gamepad2 = new Gamepad(super.gamepad2);

        telemetry = new Telemetry(super.telemetry);
        hardwareMap = new HardwareMap(super.hardwareMap);

        load(); // map hardware (motors, servos, sensors, etc)

        waitForStart(); // wait for start button to be pressed
        resetStartTime(); // reset total running time (start of opmode)

        if (!isStopRequested()) // double check opmode is running (waitForStart and !isStopRequested)
            run(); // start opmode (child function)
    }

    public abstract void load() throws InterruptedException;

    public abstract void run() throws InterruptedException;
}
