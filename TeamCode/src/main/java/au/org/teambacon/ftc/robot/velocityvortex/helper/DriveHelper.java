package au.org.teambacon.ftc.robot.velocityvortex.helper;

import com.qualcomm.robotcore.hardware.DcMotor;

import au.org.teambacon.ftc.component.MotorComponent;
import au.org.teambacon.ftc.utility.Range;

public class DriveHelper {
    protected MotorComponent driveLeft;
    protected MotorComponent driveRight;

    public DriveHelper(MotorComponent driveLeft, MotorComponent driveRight) {
        this.driveLeft = driveLeft;
        this.driveRight = driveRight;
    }

    public void initializeEncoderDistanceDrive(double power, double distance) {
        driveLeft.addToTarget(driveLeft.calculateDistanceTicks(distance));
        driveRight.addToTarget(driveRight.calculateDistanceTicks(distance));

        driveLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        driveLeft.setPower(power);
        driveRight.setPower(power);
    }

    public void headingCorrection(double headingError, double power) {
        double steer = calculateSteer(headingError, power);

        if (driveLeft.getTarget() < 0)
            steer = -steer;

        double leftPower = power + steer;
        double rightPower = power - steer;

        double max = Math.max(Math.abs(leftPower), Math.abs(rightPower));

        if (max > 1) {
            leftPower /= max;
            rightPower /= max;
        }

        driveLeft.setPower(leftPower);
        driveRight.setPower(rightPower);
    }

    public boolean isBothBusy() {
        return driveLeft.isBusy() && driveRight.isBusy();
    }

    public boolean isEitherBusy() {
        return driveLeft.isBusy() || driveRight.isBusy();
    }

    public static double calculateSteer(double power, double error) {
        if (Math.abs(error) < 0.7)
            return 0;

        double steer = power * Math.atan((1 / (10 * power)) * error);

        power = Math.abs(power);
        return Range.clip(steer, -power, power);

        //return range(steer, -1, 1);
    }
}
