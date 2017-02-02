package au.org.teambacon.ftc.component;

public interface GyroComponent {
    double getHeading();

    double getHeadingError(double targetHeading);
}
