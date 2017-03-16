package au.org.teambacon.ftc.component;

import com.qualcomm.hardware.adafruit.AdafruitBNO055IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class AdafruitBNO055IMUComponent implements GyroComponent {
    protected AdafruitBNO055IMU imu;

    public AdafruitBNO055IMUComponent(AdafruitBNO055IMU imu) {
        this.imu = imu;
    }

    public AdafruitBNO055IMU getImu() {
        return imu;
    }

    public Orientation getAngles() {
        return imu.getAngularOrientation().toAxesReference(AxesReference.INTRINSIC).toAxesOrder(AxesOrder.ZYX);
    }

    public double getHeading() {
        Orientation angles = getAngles();

        return AngleUnit.DEGREES.normalize(AngleUnit.DEGREES.fromUnit(angles.angleUnit, angles.firstAngle));
    }

    public double getHeadingError(double targetHeading) {
        return targetHeading - getHeading();
    }
}
