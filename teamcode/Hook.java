package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Hook", group="Autonomous")
public class Hook extends LinearOpMode {
    private DcMotor landerRiser;
    public void runOpMode() {
        landerRiser = hardwareMap.get(DcMotor.class, "lander riser");
        landerRiser.setDirection(DcMotor.Direction.FORWARD);
        landerRiser.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        //Landing & unlatching
        landerRiser.setPower(0.6);
        telemetry.addData("Status", "Rising");
        telemetry.update();
        sleep(10500);
    }
}
