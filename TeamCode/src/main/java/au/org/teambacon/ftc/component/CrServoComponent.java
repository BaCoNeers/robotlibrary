package au.org.teambacon.ftc.component;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class CrServoComponent implements Component {
    protected CRServo crServo;

    public CrServoComponent(CRServo crServo) {
        crServo = servo;
    }

    public CRServo getCrServo() {
        return crServo;
    }

    public void setPower(double power) {
        crServo.setPower(power);
    }

    public double getPower() {
        return crServo.getPower();
    }

    public void setDiretion(DcMotorSimple.Direction direction) {
        crServo.setDirection(direction);
    }

    public DcMotorSimple.Direction getDirection() {
        return crServo.getDirection();
    }

    public void forwards() {
        crServo.setPower(1);
    }

    public void stop() {
        crServo.setPower(0);
    }

    public void backwards() {
        crServo.setPower(-1);
    }
}
