package au.org.teambacon.ftc.element;

import java.util.HashMap;
import java.util.Map;

public class Telemetry {
    protected org.firstinspires.ftc.robotcore.external.Telemetry telemetry;

    protected HashMap<String, Object> values = new HashMap<String, Object>();

    public Telemetry(org.firstinspires.ftc.robotcore.external.Telemetry telemetry) {
        this.telemetry = telemetry;
    }

    public org.firstinspires.ftc.robotcore.external.Telemetry getTelemetry() {
        return telemetry;
    }

    public void set(String key, String format, Object ... args) {
        String value = String.format(format, args);

        values.put(key, value);
        update();
    }

    public void clear(String key) {
        values.remove(key);
        update();
    }

    public void clear() {
        values.clear();
        update();
    }

    public void update() {
        telemetry.clear();

        for (Map.Entry<String, Object> entry : values.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            telemetry.addData(key, value);
        }

        telemetry.update();
    }
}
