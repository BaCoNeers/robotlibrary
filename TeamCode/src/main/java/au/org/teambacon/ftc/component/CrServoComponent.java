package au.org.teambacon.ftc.component;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class CrServoComponent implements Component {
    protected CRServo CrServo;

    public CrServoComponent(CRServo servo) {
        CrServo = servo;
    }

    public CRServo getCrServo() {
        return CrServo;
    }

    public void setPower(double power) {
        CrServo.setPower(power);
    }

    public double getPower() {
        return CrServo.getPower();
    }

    public void setDiretion(DcMotorSimple.Direction direction) {
        CrServo.setDirection(direction);
    }

    public DcMotorSimple.Direction getDirection() {
        return CrServo.getDirection();
    }

    public void forwards() {
        CrServo.setPower(1);
    }

    public void stop() {
        CrServo.setPower(0);
    }

    public void backwards() {
        CrServo.setPower(-1);
    }
}
