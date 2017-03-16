package au.org.teambacon.ftc.component;

public interface GyroComponent extends Component {
    double getHeading();

    double getHeadingError(double targetHeading);
}
