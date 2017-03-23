package au.org.teambacon.ftc.robot.velocityvortex.opmode;

import au.org.teambacon.ftc.robot.velocityvortex.VelocityVortex;
import au.org.teambacon.ftc.robot.velocityvortex.helper.DriveHelper;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "Velocity Vortex - TeleOp", group = "Velocity Vortex")
public class TeleOp extends VelocityVortex {
    private DriveHelper drive;

    public void run() throws InterruptedException {
        if (ENABLE_DRIVE)
            drive = new DriveHelper(driveLeft, driveRight);

        while (!isStopRequested()) {
            gamepad1.update();
            gamepad2.update();

            if (ENABLE_DRIVE) {
                double rotation = gamepad1.LEFT_STICK_X * 0.5;
                double power = gamepad1.RIGHT_TRIGGER - gamepad1.LEFT_TRIGGER;

                drive.drive(power + rotation, power - rotation);
            }

            if (ENABLE_BEACON) {
                //servoBeacon
            }

            if (ENABLE_BUFFER) {
                //crServoBuffer
            }

            if (ENABLE_HARVESTER) {
                //motorHarvester
            }

            if (ENABLE_LAUNCHER) {
                //motorLauncher
            }

            if (ENABLE_LIFT) {
                //motorLift
            }

            if (ENABLE_PARTICLELIFT) {
                //motorParticleLift
            }
        }
    }
}
