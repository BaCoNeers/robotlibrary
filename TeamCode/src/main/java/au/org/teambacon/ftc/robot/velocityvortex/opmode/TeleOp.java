package au.org.teambacon.ftc.robot.velocityvortex.opmode;

import au.org.teambacon.ftc.element.ButtonState;
import au.org.teambacon.ftc.robot.velocityvortex.VelocityVortex;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "Velocity Vortex - TeleOp", group = "Velocity Vortex")
public class TeleOp extends VelocityVortex {
    public void run() throws InterruptedException {
        while (!isStopRequested()) {
            gamepad1.update(); // update gamepads (button states)
            gamepad2.update();

            // drive calculations
            if (ENABLE_DRIVE) {
                double rotation = gamepad1.LEFT_STICK_X * 0.5;
                double power = gamepad1.RIGHT_TRIGGER - gamepad1.LEFT_TRIGGER;

                if (gamepad1.A == ButtonState.ACTIVE) // decrease power
                    power /= 3;

                if (gamepad1.B == ButtonState.ACTIVE) // decrease rotation
                    rotation /= 2;

                double leftPower = power + rotation;
                double rightPower = power - rotation;

                // scale/normalize power (increases agility when moving faster - ensures motor
                // power doesnt max (1), therefore increase effect of rotation variable on power)
                // get which variable has higher value of leftPower and rightPower
                double max = Math.max(leftPower, rightPower);

                // if the highest is greater than maximum
                if (max > 1) {
                    // scale both back to maximum evenly
                    leftPower /= max;
                    rightPower /= max;
                }

                driveHelper.drive(leftPower, rightPower); // set motor powers
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
