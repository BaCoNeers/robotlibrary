package au.org.teambacon.ftc.component;

import com.qualcomm.robotcore.hardware.TouchSensor;

public class TouchSensorComponent implements Component {
    protected TouchSensor touchSensor;

    public TouchSensorComponent(TouchSensor touchSensor) {
        this.touchSensor = touchSensor;
    }

    public TouchSensor getTouchSensor() {
        return touchSensor;
    }

    public boolean isPressed() {
        return touchSensor.isPressed();
    }
}
