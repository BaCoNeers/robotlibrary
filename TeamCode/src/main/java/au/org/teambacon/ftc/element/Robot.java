package au.org.teambacon.ftc.element;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import au.org.teambacon.ftc.element.Gamepad;
import au.org.teambacon.ftc.element.HardwareMap;
import au.org.teambacon.ftc.element.Telemetry;
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

        load();

        waitForStart();
        resetStartTime();

        if (!isStopRequested()) // triple check opmode is running
            run();
    }

    public synchronized final void gamepadUpdate() {
        gamepad1.update();
        gamepad2.update();
    }

    public abstract void load() throws InterruptedException;

    public abstract void run() throws InterruptedException;
}
