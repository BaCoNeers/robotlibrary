package au.org.teambacon.ftc.robot.velocityvortex.opmode;

import com.qualcomm.robotcore.hardware.DcMotor;

import au.org.teambacon.ftc.robot.velocityvortex.VelocityVortex;
import au.org.teambacon.ftc.robot.velocityvortex.helper.DriveHelper;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "Velocity Vortex - Autonomous", group = "Velocity Vortex")
public class Autonomous extends VelocityVortex {
    @Override
    public void run() throws InterruptedException {
        if (!ENABLE_DRIVE || !ENABLE_IMU)
            return;

        imuDrive(1, 1, 0);
    }

    public void imuDrive(double power, double distance, double headingFromInitial) {
        driveHelper.initializeEncoderDistanceDrive(power, distance);

        while (driveHelper.isBothBusy() && !isStopRequested())
            driveHelper.driveBySteer(
                    DriveHelper.calculateSteer(
                            power,
                            imu.getHeadingError(headingFromInitial)
                    ),
                    power
            );
    }

    public void imuTurn(double power, double headingFromInitial) {
        driveHelper.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        while (!isStopRequested()) {
            double steer = DriveHelper.calculateSteer(
                    power,
                    imu.getHeadingError(-headingFromInitial)
            );

            if (steer == 0)
                break;

            driveHelper.drive(steer, -steer);
        }
    }
}
