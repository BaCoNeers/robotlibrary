package au.org.teambacon.ftc.element;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Telemetry {
    protected org.firstinspires.ftc.robotcore.external.Telemetry telemetry;

    protected ConcurrentHashMap<String, Object> values = new ConcurrentHashMap<String, Object>();

    public Telemetry(org.firstinspires.ftc.robotcore.external.Telemetry telemetry) {
        this.telemetry = telemetry;
    }

    public org.firstinspires.ftc.robotcore.external.Telemetry getTelemetry() {
        return telemetry;
    }

    public synchronized void set(String key, String format, Object ... args) {
        String value = String.format(format, args);

        values.put(key, value);
        update();
    }

    public synchronized void clear(String key) {
        values.remove(key);
        update();
    }

    public synchronized void clear() {
        values.clear();
        update();
    }

    public synchronized void update() {
        telemetry.clear();

        for (Map.Entry<String, Object> entry : values.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            telemetry.addData(key, value);
        }

        telemetry.update();
    }
}
