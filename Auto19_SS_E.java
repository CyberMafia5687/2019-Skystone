package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.util.Locale;


// LAST UPDATED: 1/29/20 \\
// FOR TESTING PURPOSES ONLY \\
@Autonomous
@Disabled
public class Auto19_SS_E extends LinearOpMode {

    // Motors
    DcMotor frontLeftMotor = null;
    DcMotor backLeftMotor = null;
    DcMotor frontRightMotor = null;
    DcMotor backRightMotor = null;

    // Servos
    Servo servo1;
    Servo servo2;
    Servo armServo;
    double servo_pos1 = 1.0;
    double servo_pos2 = 0.0;
    double arm_pos = 0.0;

    // Sensors
    ColorSensor sensorColor;
    DistanceSensor sensorDistance;

    // Variables

    @Override
    public void runOpMode() throws InterruptedException {

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
        armServo = hardwareMap.get(Servo.class, "armServo");
        servo1.setPosition(servo_pos1);
        servo2.setPosition(servo_pos2);
        armServo.setPosition(arm_pos);

        sensorColor = hardwareMap.get(ColorSensor.class, "sensor_color_distance");
        sensorDistance = hardwareMap.get(DistanceSensor.class, "sensor_range");

        float hsvValues[] = {0F, 0F, 0F};
        final double SCALE_FACTOR = 255;


        waitForStart();

        if (opModeIsActive()){

            Color.RGBToHSV((int) (sensorColor.red() * SCALE_FACTOR),
                    (int) (sensorColor.green() * SCALE_FACTOR),
                    (int) (sensorColor.blue() * SCALE_FACTOR),
                    hsvValues);

            /*
            Tester for official programs

            EXAMPLE ONLY; BASED ON BLUE SIDE
            */

            telemetry.addData("Status:", "The Hitbot is running");
            telemetry.update();

            // Initial Strafe
            frontLeftMotor.setPower(-0.45);
            backLeftMotor.setPower(0.45);
            frontRightMotor.setPower(0.53);
            backRightMotor.setPower(-0.45);
            sleep(3050);
            frontLeftMotor.setPower(0.0);
            backLeftMotor.setPower(0.0);
            frontRightMotor.setPower(0.0);
            backRightMotor.setPower(0.0);

            sleep(700);

            // Adjust distance
            while(sensorDistance.getDistance(DistanceUnit.CM) > 15.00){
                frontLeftMotor.setPower(-0.35);
                backLeftMotor.setPower(0.35);
                frontRightMotor.setPower(0.43);
                backRightMotor.setPower(-0.35);

                telemetry.addData("Distance (cm)",
                        String.format(Locale.US, "%.02f", sensorDistance.getDistance(DistanceUnit.CM)));
                telemetry.update();
            }

            telemetry.addData("", "");
            telemetry.update();
            sleep(800);

            long start = System.nanoTime();
            //
            // Skystone Detection
            while(sensorColor.red() > 320){
                frontLeftMotor.setPower(0.25);
                backLeftMotor.setPower(0.25);
                frontRightMotor.setPower(0.25);
                backRightMotor.setPower(0.25);
                sleep(550);
                frontLeftMotor.setPower(0.0);
                backLeftMotor.setPower(0.0);
                frontRightMotor.setPower(0.0);
                backRightMotor.setPower(0.0);
                sleep(500);

                // Telemetry
                telemetry.addData("Red  ", sensorColor.red());
                telemetry.addData("Green", sensorColor.green());
                telemetry.addData("Blue ", sensorColor.blue());
                telemetry.addData("Time", System.nanoTime());
                telemetry.update();
            }

            // If Not Yellow Detected, STOP
            frontLeftMotor.setPower(0.0);
            backLeftMotor.setPower(0.0);
            frontRightMotor.setPower(0.0);
            backRightMotor.setPower(0.0);
            //
            long finish = System.nanoTime();

            sleep(1000);

            long timeElapsed = (finish - start) / 100000000;    // Convert nanoseconds to seconds (0.0)

            // If Else-If Else loop w/ time; purely experimental
            //  b/c if the timeElapsed is consistent, we know where the other stone is
            //  and we can grab 1-2 stones with the altered times

            telemetry.addData("timeElapsed", timeElapsed);
            telemetry.update();

            if(timeElapsed >= 15 && timeElapsed <= 7){
                // Telemetry
                telemetry.addData("timeElapsed", timeElapsed);
                telemetry.addData("Path:", "1");
                telemetry.update();

                // Move forward a tad for the arm
                frontLeftMotor.setPower(-0.20);
                backLeftMotor.setPower(-0.20);
                frontRightMotor.setPower(-0.20);
                backRightMotor.setPower(-0.20);
                sleep(190);
                frontLeftMotor.setPower(0.0);
                backLeftMotor.setPower(0.0);
                frontRightMotor.setPower(0.0);
                backRightMotor.setPower(0.0);

                // Grab Skystone
                arm_pos = 1.0;
                armServo.setPosition(arm_pos);
                sleep(800);

                // Strafe away from Stone Line
                frontLeftMotor.setPower(0.45);
                backLeftMotor.setPower(-0.45);
                frontRightMotor.setPower(-0.56);
                backRightMotor.setPower(0.45);
                sleep(1250);
                frontLeftMotor.setPower(0.0);
                backLeftMotor.setPower(0.0);
                frontRightMotor.setPower(0.0);
                backRightMotor.setPower(0.0);

                // Move across the Midfield Tape to Building Side
                frontLeftMotor.setPower(-0.6);
                backLeftMotor.setPower(-0.6);
                frontRightMotor.setPower(-0.6);
                backRightMotor.setPower(-0.6);
                sleep(3200);
                frontLeftMotor.setPower(0.0);
                backLeftMotor.setPower(0.0);
                frontRightMotor.setPower(0.0);
                backRightMotor.setPower(0.0);

                // Release Skystone
                arm_pos = 0.0;
                armServo.setPosition(arm_pos);
                sleep(300);

                // Back to Midfield Tape
                frontLeftMotor.setPower(0.5);
                backLeftMotor.setPower(0.5);
                frontRightMotor.setPower(0.5);
                backRightMotor.setPower(0.5);
                sleep(1400);
                frontLeftMotor.setPower(0.0);
                backLeftMotor.setPower(0.0);
                frontRightMotor.setPower(0.0);
                backRightMotor.setPower(0.0);
            }
            else if(timeElapsed >= 1.6 && timeElapsed <= 2.5){
                // Telemetry
                telemetry.addData("timeElapsed", timeElapsed);
                telemetry.addData("Path:", "2");
                telemetry.update();

                // Move forward a tad for the arm
                frontLeftMotor.setPower(-0.20);
                backLeftMotor.setPower(-0.20);
                frontRightMotor.setPower(-0.20);
                backRightMotor.setPower(-0.20);
                sleep(190);
                frontLeftMotor.setPower(0.0);
                backLeftMotor.setPower(0.0);
                frontRightMotor.setPower(0.0);
                backRightMotor.setPower(0.0);

                // Grab Skystone
                arm_pos = 1.0;
                armServo.setPosition(arm_pos);
                sleep(800);

                // Strafe away from Stone Line
                frontLeftMotor.setPower(0.45);
                backLeftMotor.setPower(-0.45);
                frontRightMotor.setPower(-0.55);
                backRightMotor.setPower(0.45);
                sleep(1450);
                frontLeftMotor.setPower(0.0);
                backLeftMotor.setPower(0.0);
                frontRightMotor.setPower(0.0);
                backRightMotor.setPower(0.0);

                // Move across the Midfield Tape to Building Side
                frontLeftMotor.setPower(-0.6);
                backLeftMotor.setPower(-0.6);
                frontRightMotor.setPower(-0.6);
                backRightMotor.setPower(-0.6);
                sleep(3800);
                frontLeftMotor.setPower(0.0);
                backLeftMotor.setPower(0.0);
                frontRightMotor.setPower(0.0);
                backRightMotor.setPower(0.0);

                // Release Skystone
                arm_pos = 0.0;
                armServo.setPosition(arm_pos);
                sleep(300);

                // Back to Midfield Tape
                frontLeftMotor.setPower(0.5);
                backLeftMotor.setPower(0.5);
                frontRightMotor.setPower(0.5);
                backRightMotor.setPower(0.5);
                sleep(1400);
                frontLeftMotor.setPower(0.0);
                backLeftMotor.setPower(0.0);
                frontRightMotor.setPower(0.0);
                backRightMotor.setPower(0.0);
            }
            else if(timeElapsed >= 2.5 && timeElapsed <= 3.5) {
                // Telemetry
                telemetry.addData("timeElapsed", timeElapsed);
                telemetry.addData("Path:", "3");
                telemetry.update();

                // Move forward a tad for the arm
                frontLeftMotor.setPower(-0.20);
                backLeftMotor.setPower(-0.20);
                frontRightMotor.setPower(-0.20);
                backRightMotor.setPower(-0.20);
                sleep(190);
                frontLeftMotor.setPower(0.0);
                backLeftMotor.setPower(0.0);
                frontRightMotor.setPower(0.0);
                backRightMotor.setPower(0.0);

                // Grab Skystone
                arm_pos = 1.0;
                armServo.setPosition(arm_pos);
                sleep(800);

                // Strafe away from Stone Line
                frontLeftMotor.setPower(0.45);
                backLeftMotor.setPower(-0.40);
                frontRightMotor.setPower(-0.45);
                backRightMotor.setPower(0.45);
                sleep(1450);
                frontLeftMotor.setPower(0.0);
                backLeftMotor.setPower(0.0);
                frontRightMotor.setPower(0.0);
                backRightMotor.setPower(0.0);

                // Move across the Midfield Tape to Building Side
                frontLeftMotor.setPower(-0.6);
                backLeftMotor.setPower(-0.6);
                frontRightMotor.setPower(-0.6);
                backRightMotor.setPower(-0.6);
                sleep(4000);
                frontLeftMotor.setPower(0.0);
                backLeftMotor.setPower(0.0);
                frontRightMotor.setPower(0.0);
                backRightMotor.setPower(0.0);

                // Release Skystone
                arm_pos = 0.0;
                armServo.setPosition(arm_pos);
                sleep(300);

                // Back to Midfield Tape
                frontLeftMotor.setPower(0.5);
                backLeftMotor.setPower(0.5);
                frontRightMotor.setPower(0.5);
                backRightMotor.setPower(0.5);
                sleep(1400);
                frontLeftMotor.setPower(0.0);
                backLeftMotor.setPower(0.0);
                frontRightMotor.setPower(0.0);
                backRightMotor.setPower(0.0);
            }
            else if(timeElapsed >= 3.5){
                // Telemetry
                telemetry.addData("timeElapsed", timeElapsed);
                telemetry.addData("Path:", "4");
                telemetry.update();

                // Move forward a tad for the arm
                frontLeftMotor.setPower(-0.20);
                backLeftMotor.setPower(-0.20);
                frontRightMotor.setPower(-0.20);
                backRightMotor.setPower(-0.20);
                sleep(190);
                frontLeftMotor.setPower(0.0);
                backLeftMotor.setPower(0.0);
                frontRightMotor.setPower(0.0);
                backRightMotor.setPower(0.0);

                // Grab Skystone
                arm_pos = 1.0;
                armServo.setPosition(arm_pos);
                sleep(800);

                // Strafe away from Stone Line
                frontLeftMotor.setPower(0.45);
                backLeftMotor.setPower(-0.45);
                frontRightMotor.setPower(-0.55);
                backRightMotor.setPower(0.45);
                sleep(1450);
                frontLeftMotor.setPower(0.0);
                backLeftMotor.setPower(0.0);
                frontRightMotor.setPower(0.0);
                backRightMotor.setPower(0.0);

                // Move across the Midfield Tape to Building Side
                frontLeftMotor.setPower(-0.6);
                backLeftMotor.setPower(-0.6);
                frontRightMotor.setPower(-0.6);
                backRightMotor.setPower(-0.6);
                sleep(5000);
                frontLeftMotor.setPower(0.0);
                backLeftMotor.setPower(0.0);
                frontRightMotor.setPower(0.0);
                backRightMotor.setPower(0.0);

                // Release Skystone
                arm_pos = 0.0;
                armServo.setPosition(arm_pos);
                sleep(300);

                // Back to Midfield Tape
                frontLeftMotor.setPower(0.5);
                backLeftMotor.setPower(0.5);
                frontRightMotor.setPower(0.5);
                backRightMotor.setPower(0.5);
                sleep(1400);
                frontLeftMotor.setPower(0.0);
                backLeftMotor.setPower(0.0);
                frontRightMotor.setPower(0.0);
                backRightMotor.setPower(0.0);
            }
        }
     }
}