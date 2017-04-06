package au.org.teambacon.ftc.component;

import com.qualcomm.robotcore.hardware.Servo;

public class ServoComponent implements Component {
    protected Servo servo;

    public ServoComponent(Servo servo) {
        this.servo = servo;
    }

    public Servo getServo() {
        return servo;
    }

    public void setPosition(double position) {
        servo.setPosition(position);
    }

    public double getPosition() {
        return servo.getPosition();
    }

    public void setDiretion(Servo.Direction diretion) {
        servo.setDirection(diretion);
    }

    public Servo.Direction getDirection() {
        return servo.getDirection();
    }
}
