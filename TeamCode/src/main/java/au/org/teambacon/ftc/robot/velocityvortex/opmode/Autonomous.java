package au.org.teambacon.ftc.robot.velocityvortex.opmode;

import au.org.teambacon.ftc.robot.velocityvortex.VelocityVortex;
import au.org.teambacon.ftc.robot.velocityvortex.helper.DriveHelper;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "Velocity Vortex - Autonomous", group = "Velocity Vortex")
public class Autonomous extends VelocityVortex {
    @Override
    public void run() throws InterruptedException {
        if (!DRIVE_ENABLED)
            return;

        DriveHelper drive = new DriveHelper(driveLeft, driveRight);

        drive.initializeEncoderDistanceDrive(0.5, 2);
        while (drive.isBothBusy())
            drive.headingCorrection(imu.getHeadingError(0), 0.5);
    }
}
