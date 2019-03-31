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

//Start this off with the robot already descended.
@Autonomous(name="Debug", group="Autonomous")
public class DebugA extends OmniDriveA {
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor landerRiser;
    private Servo markerDrop;

    @Override
    public void runOpMode() {
        wheelInit("motorFL", "motorBL", "motorFR", "motorBR");
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();
        runtime.reset();
        telemetry.addData("Status","Started");
        telemetry.update();
        move(-3, 1);
        telemetry.addData("Status", "Unhooked");
        telemetry.update();
        strafeLeft(4, 2);
        telemetry.addData("Status","");

    }
}
