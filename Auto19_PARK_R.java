package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

// LAST UPDATED: 2/14/20 \\
// In the event a team wants us to only park, use this program
@Autonomous
//@Disabled
public class Auto19_PARK_R extends LinearOpMode {

    // Motors
    private DcMotor frontLeftMotor = null;
    private DcMotor backLeftMotor = null;
    private DcMotor frontRightMotor = null;
    private DcMotor backRightMotor = null;

    // Servos
    Servo servo1;
    Servo servo2;
    double servo_pos1 = 1.0;
    double servo_pos2 = 0.0;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Waiting for next mission");
        telemetry.update();

        frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeftMotor");
        backLeftMotor = hardwareMap.get(DcMotor.class, "backLeftMotor");
        frontRightMotor = hardwareMap.get(DcMotor.class, "frontRightMotor");
        backRightMotor = hardwareMap.get(DcMotor.class, "backRightMotor");

        frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        frontRightMotor.setDirection(DcMotor.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        backRightMotor.setDirection(DcMotor.Direction.FORWARD);

        servo1 = hardwareMap.get(Servo.class, "servo1");
        servo2 = hardwareMap.get(Servo.class, "servo2");
        servo1.setPosition(servo_pos1);
        servo2.setPosition(servo_pos2);

        waitForStart();

        if (opModeIsActive()) {
            telemetry.addData("Status", "The Hitbot is running");
            telemetry.update();

            // Wait before moving; can be changed according to alliance's preferences
            sleep(3500);

            // Strafe from wall
            frontLeftMotor.setPower(0.50);
            backLeftMotor.setPower(-0.45);
            frontRightMotor.setPower(-0.45);
            backRightMotor.setPower(0.45);
            sleep(500);
            frontLeftMotor.setPower(0.0);
            backLeftMotor.setPower(0.0);
            frontRightMotor.setPower(0.0);
            backRightMotor.setPower(0.0);
            sleep(300);

            // Forward to Midfield Tape
            frontLeftMotor.setPower(-0.5);
            backLeftMotor.setPower(-0.5);
            frontRightMotor.setPower(-0.5);
            backRightMotor.setPower(-0.5);
            sleep(1800);
            frontLeftMotor.setPower(0.0);
            backLeftMotor.setPower(0.0);
            frontRightMotor.setPower(0.0);
            backRightMotor.setPower(0.0);
            sleep(300);

            /*

            RED SIDE

            */
        }
    }
}