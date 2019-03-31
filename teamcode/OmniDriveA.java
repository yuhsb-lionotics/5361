package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This file is a superclass for autonomous modes of robots with omniwheels
 * It contains all possible movements of omniwheels via encoders(except for a moveBack function,
 * but I trust that you're smart enough to understand negative numbers)
 * Each function does exactly what its name says
 * Please let me know if there are any errors
 * Important things to note:
 * - This class only works for 4-wheeled robots
 * - This class only provides drive functionality; other motors will need their own encoder functions
 * - This class should only be used for Autonomous. If you use it for anything else, you will anger the Code Gods
 * - Please do not anger the Code Gods
 */
@Disabled
public class OmniDriveA extends LinearOpMode{
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor motorA;
    private DcMotor motorB;
    private DcMotor motorC;
    private DcMotor motorD;

    private static final double COUNTS_PER_MOTOR_REV    = 1440 ;    // eg: TETRIX Motor Encoder
    private static final double DRIVE_GEAR_REDUCTION    = 2.0 ;     // This is < 1.0 if geared UP
    private static final double WHEEL_DIAMETER_INCHES   = 3.92 ;     // For figuring circumference
    private static final double COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double DRIVE_SPEED = 0.6;
    static final double TURN_SPEED = 0.5;



    @Override
    public void runOpMode() {

    }

    void wheelInit(String nameFL, String nameBL, String nameFR, String nameBR) {
        motorA = hardwareMap.get(DcMotor.class, nameFL);
        motorB = hardwareMap.get(DcMotor.class, nameBL);
        motorC = hardwareMap.get(DcMotor.class, nameFR);
        motorD = hardwareMap.get(DcMotor.class, nameBR);

        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        motorA.setDirection(DcMotor.Direction.REVERSE);
        motorB.setDirection(DcMotor.Direction.REVERSE);
        motorC.setDirection(DcMotor.Direction.FORWARD);
        motorD.setDirection(DcMotor.Direction.FORWARD);

        motorA.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorC.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorD.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    void encoderDrive(double speed,
                      double AInches, double BInches, double CInches, double DInches, /*double extInches,*/
                      double timeoutS) {
        /* motorA = Front left motor
        motorB = Back left motor
        motorC = Front right motor
        motorD = Back right motor
         */
        int newATarget;
        int newBTarget;
        int newCTarget;
        int newDTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newATarget = motorA.getCurrentPosition() + (int)(AInches * COUNTS_PER_INCH);
            newBTarget = motorB.getCurrentPosition() + (int)(BInches * COUNTS_PER_INCH);
            newCTarget = motorC.getCurrentPosition() + (int)(CInches * COUNTS_PER_INCH);
            newDTarget = motorD.getCurrentPosition() + (int)(DInches * COUNTS_PER_INCH);


            motorA.setTargetPosition(newATarget);
            motorB.setTargetPosition(newBTarget);
            motorC.setTargetPosition(newCTarget);
            motorD.setTargetPosition(newDTarget);


            // Turn On RUN_TO_POSITION
            motorA.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorB.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorC.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorD.setMode(DcMotor.RunMode.RUN_TO_POSITION);


            // reset the timeout time and start motion.
            runtime.reset();
            motorA.setPower((Math.abs(speed))/ 2*Math.sqrt(2));
            motorB.setPower((Math.abs(speed))/ 2*Math.sqrt(2));
            motorC.setPower((Math.abs(speed))/ 2*Math.sqrt(2));
            motorD.setPower((Math.abs(speed))/ 2*Math.sqrt(2));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (motorA.isBusy() || motorB.isBusy() || motorC.isBusy() || motorD.isBusy() )) {

                /* Telemetry code causes an error. // Display it for the driver.
                telemetry.addData("Path1",  "Running to %7d :%7d :%7d :%7d :%7d :%7d",
                        newATarget,
                        newCTarget
                );
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        motorA.getCurrentPosition(),
                        motorB.getCurrentPosition(),
                        motorC.getCurrentPosition(),
                        motorD.getCurrentPosition()
                );
                telemetry.update(); */

            }

            // Stop all motion;
            motorA.setPower(0);
            motorB.setPower(0);
            motorC.setPower(0);
            motorD.setPower(0);

            // Turn off RUN_TO_POSITION
            motorA.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorC.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorD.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //  sleep(250);   // optional pause after each move
        }
    }

    void move(double inches, float timeout)
    {
        encoderDrive(DRIVE_SPEED,  inches,  inches, inches, inches,timeout);
    }


    void strafeRight(double inches, float timeout)
    {
        encoderDrive(DRIVE_SPEED,  inches,  -inches, -inches, inches,timeout);
    }

    void strafeLeft(double inches, float timeout)
    {
        encoderDrive(DRIVE_SPEED,  -inches,  inches, inches, -inches,timeout);
    }

    void pivotTurnLeftFL(double inches, float timeout) {
        encoderDrive(TURN_SPEED,  0,  -inches, inches, inches, timeout);
    }

    void pivotTurnLeftBL(double inches, float timeout) {
        encoderDrive(TURN_SPEED,  -inches,  0, inches, inches, timeout);
    }

    public void pivotTurnLeftFR(double inches, float timeout) {
        encoderDrive(TURN_SPEED,  -inches,  -inches, 0, inches, timeout);
    }

    public void pivotTurnLeftBR(double inches, float timeout) {
        encoderDrive(TURN_SPEED,  -inches,  -inches, inches, 0, timeout);
    }

    void pivotTurnRightFL(double inches, float timeout) {
        encoderDrive(TURN_SPEED,  0,  inches, -inches, -inches, timeout);
    }

    void pivotTurnRightBL(double inches, float timeout) {
        encoderDrive(TURN_SPEED,  inches,  0, -inches, -inches, timeout);
    }

    void pivotTurnRightFR(double inches, float timeout) {
        encoderDrive(TURN_SPEED,  inches,  inches, 0, -inches, timeout);
    }

    void pivotTurnRightBR(double inches, float timeout) {
        encoderDrive(TURN_SPEED,  inches,  inches, -inches, 0, timeout);
    }

    void turnLeft(double inches, float timeout)
    {
        encoderDrive(TURN_SPEED,  -inches,  -inches, inches, inches, timeout);
    }

    void turnRight(double inches, float timeout)
    {
        encoderDrive(TURN_SPEED,  inches,  inches, -inches, -inches, timeout);
    }
}
