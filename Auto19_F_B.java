package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

// LAST UPDATED: 12/4/19 \\
@Autonomous
//@Disabled
public class Auto19_F_B extends LinearOpMode {

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
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        servo1 = hardwareMap.get(Servo.class, "servo1");
        servo2 = hardwareMap.get(Servo.class, "servo2");
        servo1.setPosition(servo_pos1);
        servo2.setPosition(servo_pos2);

        waitForStart();

        if (opModeIsActive()) {
            telemetry.addData("Status", "The Hitbot is running");

            // Wait before moving; can be changed according to alliance's preferences
            sleep(4000);

            // Move forward from wall
            frontLeftMotor.setPower(0.4);
            backLeftMotor.setPower(0.4);
            frontRightMotor.setPower(0.4);
            backRightMotor.setPower(0.4);
            sleep(400);
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
            frontLeftMotor.setPower(-0.5);
            backLeftMotor.setPower(0.5);
            frontRightMotor.setPower(0.5);
            backRightMotor.setPower(-0.5);
            sleep(800);
            frontLeftMotor.setPower(0.0);
            backLeftMotor.setPower(0.0);
            frontRightMotor.setPower(0.0);
            backRightMotor.setPower(0.0);
            sleep(300);

            // Forward to Foundation
            frontLeftMotor.setPower(0.4);
            backLeftMotor.setPower(0.4);
            frontRightMotor.setPower(0.4);
            backRightMotor.setPower(0.4);
            sleep(1370);
            frontLeftMotor.setPower(0.0);
            backLeftMotor.setPower(0.0);
            frontRightMotor.setPower(0.0);
            backRightMotor.setPower(0.0);
            sleep(300);

            // Latch Foundation Servos
            servo1.setPosition(0.0);
            servo2.setPosition(1.0);
            sleep(300);

            // Reverse towards Build Site
            frontLeftMotor.setPower(-0.6);
            backLeftMotor.setPower(-0.6);
            frontRightMotor.setPower(-0.6);
            backRightMotor.setPower(-0.6);
            sleep(1640);
            frontLeftMotor.setPower(0.0);
            backLeftMotor.setPower(0.0);
            frontRightMotor.setPower(0.0);
            backRightMotor.setPower(0.0);
            sleep(300);

            // Spin Left to place Foundation in Build Site
            frontLeftMotor.setPower(0.4);
            backLeftMotor.setPower(0.4);
            frontRightMotor.setPower(-0.4);
            backRightMotor.setPower(-0.4);
            sleep(670);
            frontLeftMotor.setPower(0.0);
            backLeftMotor.setPower(0.0);
            frontRightMotor.setPower(0.0);
            backRightMotor.setPower(0.0);
            sleep(300);

            // Release Foundation Servos
            servo1.setPosition(1.0);
            servo2.setPosition(0.0);
            sleep(300);

            // Move back a bit
            frontLeftMotor.setPower(0.4);
            backLeftMotor.setPower(0.4);
            frontRightMotor.setPower(0.4);
            backRightMotor.setPower(0.4);
            sleep(400);
            frontLeftMotor.setPower(0.0);
            backLeftMotor.setPower(0.0);
            frontRightMotor.setPower(0.0);
            backRightMotor.setPower(0.0);
            sleep(300);

            // Reverse Right Turn to realign with wall
            frontLeftMotor.setPower(0.0);
            backLeftMotor.setPower(0.0);
            frontRightMotor.setPower(0.4);
            backRightMotor.setPower(0.4);
            sleep(610);
            frontLeftMotor.setPower(0.0);
            backLeftMotor.setPower(0.0);
            frontRightMotor.setPower(0.0);
            backRightMotor.setPower(0.0);
            sleep(300);

            // Strafe to Midfield Tape, Pt. 1
            frontLeftMotor.setPower(0.5);
            backLeftMotor.setPower(-0.5);
            frontRightMotor.setPower(-0.5);
            backRightMotor.setPower(0.5);
            sleep(500);
            frontLeftMotor.setPower(0.0);
            backLeftMotor.setPower(0.0);
            frontRightMotor.setPower(0.0);
            backRightMotor.setPower(0.0);
            sleep(300);

            // Strafe to Midfield Tape, Pt. 2
            frontLeftMotor.setPower(0.5);
            backLeftMotor.setPower(-0.5);
            frontRightMotor.setPower(-0.5);
            backRightMotor.setPower(0.5);
            sleep(2380);
            frontLeftMotor.setPower(0.0);
            backLeftMotor.setPower(0.0);
            frontRightMotor.setPower(0.0);
            backRightMotor.setPower(0.0);
            sleep(300);

        }
    }
}