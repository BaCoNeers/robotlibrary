package au.org.teambacon.ftc.robot.velocityvortex.helper;

import com.qualcomm.robotcore.hardware.DcMotor;

import au.org.teambacon.ftc.component.MotorComponent;
import au.org.teambacon.ftc.utility.Range;

public class DriveHelper {
    protected MotorComponent driveLeft;
    protected MotorComponent driveRight;

    public DriveHelper(MotorComponent driveLeft, MotorComponent driveRight) {
        // save motor variable references - for access within this classes functions
        this.driveLeft = driveLeft;
        this.driveRight = driveRight;
    }

    // set drive motor powers equally
    public void drive(double power) {
        driveLeft.setPower(power);
        driveRight.setPower(power);
    }

    // prescribe drive motor powers
    public void drive(double leftPower, double rightPower) {
        driveLeft.setPower(leftPower);
        driveRight.setPower(rightPower);
    }

    // set the mode of both drive motors
    public void setMode(DcMotor.RunMode mode) {
        driveLeft.setMode(mode);
        driveRight.setMode(mode);
    }

    // test if both motors are currently advancing under encoder/controller control
    // (if one stops advancing, this function returns false)
    public boolean isBothBusy() {
        return driveLeft.isBusy() && driveRight.isBusy();
    }

    // test if either motor is currently advancing under encoder/controller control
    // (if either is advancing, this function returns true)
    public boolean isEitherBusy() {
        return driveLeft.isBusy() || driveRight.isBusy();
    }

    // set encoders up (initialize) for encoder/controller drive/control
    public void initializeEncoderDistanceDrive(double power, double distance) {
        // add new distance tick calculation to existing target
        // added to target instead of current position because encoders may not have reached the
        // target they previously had set
        driveLeft.addToTarget(driveLeft.calculateDistanceTicks(distance));
        driveRight.addToTarget(driveRight.calculateDistanceTicks(distance));

        // set drive motors to assume control/encoder-calculations of motor controller
        setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // set power to prescribed power
        drive(power);
    }

    public void driveBySteer(double steer, double power) {
        //
        if (driveLeft.getTarget() < 0)
            steer = -steer;

        // calculate individual motor powers (from steer/rotation value)
        double leftPower = power + steer;
        double rightPower = power - steer;

        // normalize motor powers
        // (scale powers if they exceed maximum prescribable power)
        double max = Math.max(Math.abs(leftPower), Math.abs(rightPower));

        // scale
        if (max > 1) {
            leftPower /= max;
            rightPower /= max;
        }

        // set powers
        drive(leftPower, rightPower);
    }

    // calculate steer/rotation value from heading error (from gyroscopic sensor)
    public static double calculateSteer(double power, double headingError) {
        // test if heading error is within allowable tolerance
        if (Math.abs(headingError) < 0.7)
            return 0; // return steer value of 0 - no calculations required

        // use inverse tangent function for steer value calculation
        // depending on power as coeff.
        double steer = 0.5 * power * Math.atan((1 / (75 * power)) * headingError);

        // absolute power value for ...
        power = Math.abs(power);

        // ensure steer value is within range of power values supplied
        return Range.clip(steer, -power, power);
    }
}
