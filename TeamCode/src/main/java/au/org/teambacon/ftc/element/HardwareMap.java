package au.org.teambacon.ftc.element;

import com.qualcomm.hardware.adafruit.AdafruitBNO055IMU;

import au.org.teambacon.ftc.component.CrServoComponent;
import au.org.teambacon.ftc.component.AdafruitBNO055IMUComponent;
import au.org.teambacon.ftc.component.MotorComponent;
import au.org.teambacon.ftc.component.ServoComponent;
import au.org.teambacon.ftc.component.TouchSensorComponent;

public class HardwareMap {
    protected com.qualcomm.robotcore.hardware.HardwareMap hardwareMap;

    public HardwareMap(com.qualcomm.robotcore.hardware.HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
    }

    public com.qualcomm.robotcore.hardware.HardwareMap getHardwareMap() {
        return hardwareMap;
    }

    public MotorComponent getMotor(String name, MotorComponent.MotorType motorType) {
        return new MotorComponent(hardwareMap.dcMotor.get(name), motorType);
    }

    public ServoComponent getServo(String name) {
        return new ServoComponent(hardwareMap.servo.get(name));
    }

    public CrServoComponent getCrServo(String name) {
        return new CrServoComponent(hardwareMap.crservo.get(name));
    }

    public AdafruitBNO055IMUComponent getImu(String name) {
        return new AdafruitBNO055IMUComponent(hardwareMap.get(AdafruitBNO055IMU.class, name));
    }

    public TouchSensorComponent getTouchSensor(String name) {
        return new TouchSensorComponent(hardwareMap.touchSensor.get(name));
    }
}
