package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


@Autonomous(name="OmniDrive Auto", group="Autonomous")
public class Auto5361OmniDrive extends OmniDriveA {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor landerRiser;
    private Servo markerDrop;


    @Override
    public void runOpMode() {
        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        wheelInit("motorFL", "motorBL", "motorFR", "motorBR");
        landerRiser = hardwareMap.get(DcMotor.class, "lander riser");
        landerRiser.setDirection(DcMotor.Direction.FORWARD);
        landerRiser.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        markerDrop = hardwareMap.get(Servo.class, "marker");
        markerDrop.setDirection(Servo.Direction.FORWARD);
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        //Landing & unlatching
        landerRiser.setPower(-1);
        telemetry.addData("Status", "Descending");
        telemetry.update();
        sleep(6000);
        landerRiser.setPower(0);
        telemetry.addData("Status","Unhooking");
        telemetry.update();
        //OmniDrive commands don't work now (at least not for some wheels). Not sure of the exact reason. We may have to use the built-in commands without OmniDrive.
        move(-3, 1);
        strafeLeft(4, 2);
        //pivotTurnLeftFL(6, 1.5);
        //turnLeft(6, 1);
        //drive to the depot:
        markerDrop.setPosition(0);
        sleep(1000);
        markerDrop.setPosition(0.5);
        sleep(1000);
        markerDrop.setPosition(0);
        sleep(1000);

    }
}
