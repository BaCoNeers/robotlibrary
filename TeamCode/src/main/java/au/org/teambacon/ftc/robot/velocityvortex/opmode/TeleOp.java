package au.org.teambacon.ftc.robot.velocityvortex.opmode;

import au.org.teambacon.ftc.element.ButtonState;
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

                if (gamepad1.A == ButtonState.ACTIVE)
                    power /= 3;

                if (gamepad1.B == ButtonState.ACTIVE)
                    rotation /= 2;

                drive.drive(power + rotation, power - rotation);
            }

            if (ENABLE_BEACON) {
                //servoBeacon
            }

            if (ENABLE_BUFFER)
                if (gamepad2.Y == ButtonState.ACTIVE)
                    if (gamepad2.LEFT_BUMPER == ButtonState.ACTIVE)
                        crServoBuffer.backwards();
                    else
                        crServoBuffer.forwards();
                else
                    crServoBuffer.stop();

            if (ENABLE_HARVESTER)
                if (gamepad2.A == ButtonState.ACTIVE)
                    if (gamepad2.LEFT_BUMPER == ButtonState.ACTIVE)
                        motorHarvester.backwards();
                    else
                        motorHarvester.forwards();
                else
                    motorHarvester.stop();

            if (ENABLE_LAUNCHER)
                if (gamepad2.B == ButtonState.ACTIVE && !gamepad2.getGamepad().start)
                    motorLauncher.forwards();
                else
                    motorLauncher.stop();

            if (ENABLE_LIFT)
                if (gamepad1.LEFT_BUMPER == ButtonState.ACTIVE)
                    motorLift.setPower(gamepad1.RIGHT_STICK_Y);
                else
                    motorLift.stop();

            if (ENABLE_PARTICLELIFT)
                if (gamepad2.X == ButtonState.ACTIVE)
                    if (gamepad2.LEFT_BUMPER == ButtonState.ACTIVE)
                        motorParticleLift.backwards();
                    else
                        motorParticleLift.forwards();
                else
                    motorParticleLift.stop();
        }
    }
}
