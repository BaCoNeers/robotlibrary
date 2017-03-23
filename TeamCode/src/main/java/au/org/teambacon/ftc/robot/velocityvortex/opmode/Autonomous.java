package au.org.teambacon.ftc.robot.velocityvortex.opmode;

import com.qualcomm.robotcore.hardware.DcMotor;

import au.org.teambacon.ftc.robot.velocityvortex.VelocityVortex;
import au.org.teambacon.ftc.robot.velocityvortex.helper.DriveHelper;
import au.org.teambacon.ftc.utility.Range;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "Velocity Vortex - Autonomous", group = "Velocity Vortex")
public class Autonomous extends VelocityVortex {
    private DriveHelper drive;

    @Override
    public void run() throws InterruptedException {
        if (!ENABLE_DRIVE || !ENABLE_IMU)
            return;

        drive = new DriveHelper(driveLeft, driveRight);

        imuDrive(1, 1, 0);
    }

    public void imuDrive(double power, double distance, double headingFromInitial) {
        drive.initializeEncoderDistanceDrive(power, distance);

        while (drive.isBothBusy() && !isStopRequested())
            drive.driveBySteer(
                    drive.calculateSteer(
                            power,
                            imu.getHeadingError(headingFromInitial)
                    ),
                    power
            );
    }

    public void imuTurn(double power, double headingFromInitial) {
        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        while (!isStopRequested()) {
            double steer = drive.calculateSteer(
                    power,
                    imu.getHeadingError(-headingFromInitial)
            );

            if (steer == 0)
                break;

            steer = Range.clip(steer, -power, power);

            drive.drive(steer, -steer);
        }
    }
}
