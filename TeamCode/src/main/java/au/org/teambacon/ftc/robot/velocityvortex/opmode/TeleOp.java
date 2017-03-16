package au.org.teambacon.ftc.robot.velocityvortex.opmode;

import au.org.teambacon.ftc.robot.velocityvortex.VelocityVortex;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "Velocity Vortex - TeleOp", group = "Velocity Vortex")
public class TeleOp extends VelocityVortex {
    public void run() throws InterruptedException {
        while (!isStopRequested()) {
            gamepadUpdate();

            if (DRIVE_ENABLED) {
                double rotation = -gamepad1.LEFT_STICK_X * 0.5;
                double power = gamepad1.RIGHT_TRIGGER - gamepad1.LEFT_TRIGGER;

                driveLeft.setPower(power + rotation);
                driveRight.setPower(power - rotation);
            }
        }
    }
}
