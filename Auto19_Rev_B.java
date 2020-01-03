package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

// LAST UPDATED: 12/10/19 \\
@Autonomous
@Disabled
public class Auto19_Rev_B extends LinearOpMode {

    // Motors
    private DcMotor frontLeftMotor = null;
    private DcMotor backLeftMotor = null;
    private DcMotor frontRightMotor = null;
    private DcMotor backRightMotor = null;


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


        waitForStart();

        if (opModeIsActive()) {
            telemetry.addData("Status", "The Hitbot is running");

            // Wait before moving; can be changed according to alliance's preferences
            sleep(5000);

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
            BLUE SIDE
            */

            // Spin reverse left (align with wall)
            frontLeftMotor.setPower(-0.4);
            backLeftMotor.setPower(-0.4);
            frontRightMotor.setPower(0.4);
            backRightMotor.setPower(0.4);
            sleep(800);
            frontLeftMotor.setPower(0.0);
            backLeftMotor.setPower(0.0);
            frontRightMotor.setPower(0.0);
            backRightMotor.setPower(0.0);
            sleep(300);

            // Backwards to Midfield Tape
            frontLeftMotor.setPower(0.4);
            backLeftMotor.setPower(0.4);
            frontRightMotor.setPower(0.4);
            backRightMotor.setPower(0.4);
            sleep(2550);
            frontLeftMotor.setPower(0.0);
            backLeftMotor.setPower(0.0);
            frontRightMotor.setPower(0.0);
            backRightMotor.setPower(0.0);
            sleep(300);

        }
    }
}