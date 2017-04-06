package au.org.teambacon.ftc.component;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class MotorComponent implements Component {
    protected DcMotor motor;
    protected MotorType motorType;

    protected int ticksPerMeter;

    protected double _lastPowerSet = 0;

    public MotorComponent(DcMotor motor, MotorType motorType) {
        this.motor = motor;
        this.motorType = motorType;

        motor.setPower(0); // stop motors on start

        if (motorType.hasEncoder())
            motor.setMaxSpeed(motorType.getCPS());
    }

    public DcMotor getMotor() {
        return motor;
    }

    public MotorType getMotorType() {
        return motorType;
    }

    public void setPower(double power) {
        if (getPower() != power) {
            motor.setPower(power);

            _lastPowerSet = power;
        }
    }

    public void rawSetPower(double power) {
        motor.setPower(power);

        _lastPowerSet = power;
    }

    public double getPower() {
        return _lastPowerSet;
        //return motor.getPower();
    }

    public void setTarget(int target) {
        motor.setTargetPosition(target);
    }

    public void addToTarget(int additionToTarget) {
        motor.setTargetPosition(getTarget() + additionToTarget);
    }

    public int getTarget() {
        return motor.getTargetPosition();
    }

    public int getPosition() {
        return motor.getCurrentPosition();
    }

    public void setMode(DcMotor.RunMode mode) {
        motor.setMode(mode);
    }

    public DcMotor.RunMode getMode() {
        return motor.getMode();
    }

    public void setDirection(DcMotorSimple.Direction direction) {
        motor.setDirection(direction);
    }

    public DcMotorSimple.Direction getDirection() {
        return motor.getDirection();
    }

    public boolean isBusy() {
        return motor.isBusy();
    }

    public void forwards() {
        motor.setPower(1);
    }

    public void stop() {
        motor.setPower(0);
    }

    public void backwards() {
        motor.setPower(-1);
    }

    public void setTicksPerMeter(int ticksPerMeter) {
        this.ticksPerMeter = ticksPerMeter;
    }

    public int getTicksPerMeter() {
        return ticksPerMeter;
    }

    public void setTicksPerMeter(double wheelDiameter, double gearReduction) {
        ticksPerMeter = (int)((motorType.getCPR() * gearReduction) / (wheelDiameter * Math.PI));
    }

    public int calculateDistanceTicks(double distance) {
        return (int)(ticksPerMeter * distance);
    }

    public enum MotorType {
        GENERIC(0, 0),
        NEVEREST20_ENCODER(747, 0),
        NEVEREST40_ENCODER(1120, 3000),
        NEVEREST60_ENCODER(1680, 3600);

        private int CPR; // counts per revolution
        private int CPS; // counts per second (at full speed)

        MotorType(int cpr, int cps) {
            CPR = cpr;
            CPS = cps;
        }

        public int getCPR() {
            return CPR;
        }

        public int getCPS() {
            return CPS;
        }

        public boolean hasEncoder() {
            return CPR > 0;
        }
    }
}
