package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

// LAST UPDATED: 2/14/20 \\
@Autonomous
//@Disabled
public class Auto19_F_R extends LinearOpMode {

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
            // sleep(2000);

            // Move backwards from wall
            frontLeftMotor.setPower(0.55);
            backLeftMotor.setPower(0.55);
            frontRightMotor.setPower(0.55);
            backRightMotor.setPower(0.55);
            sleep(1100);
            frontLeftMotor.setPower(0.0);
            backLeftMotor.setPower(0.0);
            frontRightMotor.setPower(0.0);
            backRightMotor.setPower(0.0);
            sleep(300);

            /*
            Robot should strafe to Foundation, approach Foundation, latch on, pull Foundation back as far as possible,
            turn slightly to angle Foundation in, turn back to orthogonal and strafe to Midfield

            BLUE SIDE
            */

            // Strafe Left
            frontLeftMotor.setPower(0.55);
            backLeftMotor.setPower(-0.50);
            frontRightMotor.setPower(-0.50);
            backRightMotor.setPower(0.50);
            sleep(1580);
            frontLeftMotor.setPower(0.0);
            backLeftMotor.setPower(0.0);
            frontRightMotor.setPower(0.0);
            backRightMotor.setPower(0.0);
            sleep(300);

            // Backwards to Foundation
            frontLeftMotor.setPower(0.4);
            backLeftMotor.setPower(0.4);
            frontRightMotor.setPower(0.4);
            backRightMotor.setPower(0.4);
            sleep(1420);
            frontLeftMotor.setPower(0.0);
            backLeftMotor.setPower(0.0);
            frontRightMotor.setPower(0.0);
            backRightMotor.setPower(0.0);
            sleep(1000);

            // Latch Foundation Servos
            servo1.setPosition(0.0);
            servo2.setPosition(1.0);
            sleep(800);

            // Reverse towards Build Site
            frontLeftMotor.setPower(-0.75);
            backLeftMotor.setPower(-0.80);
            frontRightMotor.setPower(-0.80);
            backRightMotor.setPower(-0.75);
            sleep(2300);
            frontLeftMotor.setPower(0.0);
            backLeftMotor.setPower(0.0);
            frontRightMotor.setPower(0.0);
            backRightMotor.setPower(0.0);
            sleep(500);

            // Release Foundation Servos
            servo1.setPosition(1.0);
            servo2.setPosition(0.0);
            sleep(300);

            // Strafe to Midfield Tape, Pt. 1
            frontLeftMotor.setPower(-0.65);
            backLeftMotor.setPower(0.60);
            frontRightMotor.setPower(0.60);
            backRightMotor.setPower(-0.60);
            sleep(4900);
            frontLeftMotor.setPower(0.0);
            backLeftMotor.setPower(0.0);
            frontRightMotor.setPower(0.0);
            backRightMotor.setPower(0.0);
            sleep(300);

        }
    }
}