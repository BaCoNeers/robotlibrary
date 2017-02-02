package au.org.teambacon.ftc.component;

import com.qualcomm.robotcore.hardware.Servo;

public class ServoComponent implements Component {
    protected Servo Servo;

    public ServoComponent(Servo servo) {
        Servo = servo;
    }

    public Servo getServo() {
        return Servo;
    }

    public void setPosition(double position) {
        Servo.setPosition(position);
    }

    public double getPosition() {
        return Servo.getPosition();
    }

    public void setDiretion(Servo.Direction diretion) {
        Servo.setDirection(diretion);
    }

    public Servo.Direction getDirection() {
        return Servo.getDirection();
    }
}
