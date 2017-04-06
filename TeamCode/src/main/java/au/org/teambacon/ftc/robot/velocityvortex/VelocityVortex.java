package au.org.teambacon.ftc.robot.velocityvortex;

import com.qualcomm.hardware.adafruit.AdafruitBNO055IMU;
import com.qualcomm.hardware.adafruit.BNO055IMU;
import com.qualcomm.hardware.adafruit.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

import au.org.teambacon.ftc.component.AdafruitBNO055IMUComponent;
import au.org.teambacon.ftc.component.CrServoComponent;
import au.org.teambacon.ftc.component.ServoComponent;
import au.org.teambacon.ftc.component.TouchSensorComponent;
import au.org.teambacon.ftc.element.ButtonState;
import au.org.teambacon.ftc.element.Robot;
import au.org.teambacon.ftc.component.MotorComponent;
import au.org.teambacon.ftc.game.Alliance;

public abstract class VelocityVortex extends Robot {
    // debug status (whether to output status of various components to telemetry)
    public static final boolean DEBUG = true;

    // component enabled declarations
    public static final boolean ENABLE_DRIVE = true;
    public static final boolean ENABLE_BEACON = true;
    public static final boolean ENABLE_BUFFER = true;
    public static final boolean ENABLE_HARVESTER = true;
    public static final boolean ENABLE_LAUNCHER = true;
    public static final boolean ENABLE_LIFT = true;
    public static final boolean ENABLE_PARTICLELIFT = true;

    public static final boolean ENABLE_IMU = true;

    // robot constant definitions/declarations
    public static final double DRIVE_WHEEL_DIAMETER = 0.09814;
    public static final double DRIVE_GEAR_REDUCTION = 0.5;

    // robot component declarations
    protected MotorComponent driveLeft;
    protected MotorComponent driveRight;

    protected MotorComponent motorHarvester;
    protected MotorComponent motorLauncher;
    protected MotorComponent motorLift;
    protected MotorComponent motorParticleLift;

    protected CrServoComponent crServoBuffer;
    protected ServoComponent servoBeacon;

    protected TouchSensorComponent touchSensorLauncher;
    protected AdafruitBNO055IMUComponent imu;

    @Override
    public void load() throws InterruptedException {
        if (ENABLE_DRIVE) {
            driveLeft = hardwareMap.getMotor("drive_left", MotorComponent.MotorType.NEVEREST60_ENCODER);
            driveRight = hardwareMap.getMotor("drive_right", MotorComponent.MotorType.NEVEREST60_ENCODER);

            // set encoder distance calculation variables
            driveLeft.setTicksPerMeter(DRIVE_WHEEL_DIAMETER, DRIVE_GEAR_REDUCTION);
            driveRight.setTicksPerMeter(DRIVE_WHEEL_DIAMETER, DRIVE_GEAR_REDUCTION);

            // reverse left drive side to account for backwards/inverse motor mounting
            driveLeft.setDirection(DcMotorSimple.Direction.REVERSE);

            // reset drive motors to a default starting status
            // (reset what isnt restart when the 'robot isnt restarted'
            driveLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            driveRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            driveLeft.setTarget(0);
            driveRight.setTarget(0);
            driveLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            driveRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

        // define components if they are 'enabled'
        if (ENABLE_BEACON) {
            servoBeacon = hardwareMap.getServo("servo_beacon");
        }

        if (ENABLE_BUFFER) {
            crServoBuffer = hardwareMap.getCrServo("crservo_buffer");
        }

        if (ENABLE_HARVESTER) {
            motorHarvester = hardwareMap.getMotor("motor_harvester", MotorComponent.MotorType.GENERIC);
            motorHarvester.setDirection(DcMotorSimple.Direction.REVERSE);
        }

        if (ENABLE_LAUNCHER) {
            motorLauncher = hardwareMap.getMotor("motor_launcher", MotorComponent.MotorType.GENERIC);

            touchSensorLauncher = hardwareMap.getTouchSensor("touchsensor_launcher");
        }

        if (ENABLE_LIFT) {
            motorLift = hardwareMap.getMotor("motor_lift", MotorComponent.MotorType.GENERIC);
            motorLift.setDirection(DcMotorSimple.Direction.REVERSE);
        }

        if (ENABLE_PARTICLELIFT) {
            motorParticleLift = hardwareMap.getMotor("motor_particlelift", MotorComponent.MotorType.GENERIC);
        }

        if (ENABLE_IMU) {
            imu = hardwareMap.getImu("imu_robot");

            AdafruitBNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
            parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
            parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
            parameters.calibrationDataFile = "AdafruitIMUCalibration.json";
            parameters.loggingEnabled = true;
            parameters.loggingTag = "IMU";
            parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
            imu.getImu().initialize(parameters);

            imu.getImu().startAccelerationIntegration(new Position(), new Velocity(), 1000);
        }

        if (DEBUG) {
            new Thread() {
                @Override
                public void run() {
                    while (!isStopRequested()) {
                        if (ENABLE_DRIVE) {
                            telemetry.set("D|DRIVE_L_POWER", "%f", driveLeft.getPower());
                            telemetry.set("D|DRIVE_L_MODE", "%s", driveLeft.getMode().toString());
                            if (driveLeft.getMotorType().hasEncoder()) {
                                telemetry.set("D|DRIVE_L_TARGET", "%d", driveLeft.getTarget());
                                telemetry.set("D|DRIVE_L_POS", "%d", driveLeft.getPosition());
                            }

                            telemetry.set("D|DRIVE_R_POWER", "%f", driveRight.getPower());
                            telemetry.set("D|DRIVE_R_MODE", "%s", driveRight.getMode().toString());
                            if (driveRight.getMotorType().hasEncoder()) {
                                telemetry.set("D|DRIVE_R_TARGET", "%d", driveRight.getTarget());
                                telemetry.set("D|DRIVE_R_POS", "%d", driveRight.getPosition());
                            }
                        }

                        if (ENABLE_IMU) {
                            telemetry.set("D|IMU_HEADING", "%f", imu.getHeading());
                        }
                    }
                }
            }.start();
        }

        // alliance selector
        while (!isStarted()) {
            gamepad1.update(); // update button states
            // gamepad 2's update function not required as we only read input from controller 1
            // in this state - pending start to be pressed on the driver station

            if (gamepad1.X == ButtonState.PRESSED) // blue button on controller (X)
                alliance = Alliance.BLUE;
            else if (gamepad1.B == ButtonState.PRESSED) // red button on controller (B)
                alliance = Alliance.RED;

            // output status back to telemetry
            telemetry.set("ALLIANCE", "%s", alliance != null ? alliance.toString() : "not set");

            sleep(1); // slow processing (prevent battery significantly draining from doing 'nothing'
        }
    }
}
