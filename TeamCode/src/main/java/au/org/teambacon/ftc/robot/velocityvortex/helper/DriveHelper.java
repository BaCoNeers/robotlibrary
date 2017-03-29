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

    public void drive(double power) {
        driveLeft.setPower(power);
        driveRight.setPower(power);
    }

    public void drive(double leftPower, double rightPower) {
        driveLeft.setPower(leftPower);
        driveRight.setPower(rightPower);
    }

    public void setMode(DcMotor.RunMode mode) {
        driveLeft.setMode(mode);
        driveRight.setMode(mode);
    }

    public boolean isBothBusy() {
        return driveLeft.isBusy() && driveRight.isBusy();
    }

    public boolean isEitherBusy() {
        return driveLeft.isBusy() || driveRight.isBusy();
    }

    public void initializeEncoderDistanceDrive(double power, double distance) {
        driveLeft.addToTarget(driveLeft.calculateDistanceTicks(distance));
        driveRight.addToTarget(driveRight.calculateDistanceTicks(distance));

        setMode(DcMotor.RunMode.RUN_TO_POSITION);

        drive(power);
    }

    public void driveBySteer(double steer, double power) {
        if (driveLeft.getTarget() < 0)
            steer = -steer;

        double leftPower = power + steer;
        double rightPower = power - steer;

        double max = Math.max(Math.abs(leftPower), Math.abs(rightPower));

        if (max > 1) {
            leftPower /= max;
            rightPower /= max;
        }

        drive(leftPower, rightPower);
    }

    public static double calculateSteer(double power, double headingError) {
        if (Math.abs(headingError) < 0.7)
            return 0;

        double steer = 0.5 * power * Math.atan((1 / (75 * power)) * headingError);

        power = Math.abs(power);
        return Range.clip(steer, -power, power);
    }
}
