package au.org.teambacon.ftc.element;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import au.org.teambacon.ftc.element.Gamepad;
import au.org.teambacon.ftc.element.HardwareMap;
import au.org.teambacon.ftc.element.Telemetry;

public abstract class Robot extends LinearOpMode {
    protected Gamepad gamepad1;
    protected Gamepad gamepad2;

    protected Telemetry telemetry;
    protected HardwareMap hardwareMap;

    public final void runOpMode() throws InterruptedException {
        this.gamepad1 = new Gamepad(super.gamepad1);
        this.gamepad2 = new Gamepad(super.gamepad2);

        this.telemetry = new Telemetry(super.telemetry);
        this.hardwareMap = new HardwareMap(super.hardwareMap);

        Thread update = new Thread() {
            @Override
            public void run() {
                while (opModeIsActive()) {
                    gamepad1.update();
                    gamepad2.update();

                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                    }
                }
            }
        };
        update.start();

        load();

        waitForStart();

        if (opModeIsActive())
            run();

        update.interrupt();
    }

    public abstract void load() throws InterruptedException;

    public abstract void run() throws InterruptedException;
}
