package au.org.teambacon.ftc.robot.velocityvortex.opmode;

import android.os.Environment;

import com.qualcomm.robotcore.eventloop.opmode.*;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp()
public class Testing extends LinearOpMode {
    public void runOpMode() {
        waitForStart();

        telemetry.addData("path", Environment.getExternalStorageDirectory().getAbsolutePath());
        telemetry.update();

        sleep(10000);
    }
}
