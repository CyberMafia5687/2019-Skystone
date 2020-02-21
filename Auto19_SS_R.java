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


// Check this website for color value help: http://colorizer.org/

// LAST UPDATED: 2/1/20 \\
// PRELIMINARY PROGRAM; ADDITIONAL MATERIAL NEEDED \\
@Autonomous
//@Disabled
public class Auto19_SS_R extends LinearOpMode {

    // Motors
    DcMotor frontLeftMotor = null;
    DcMotor backLeftMotor = null;
    DcMotor frontRightMotor = null;
    DcMotor backRightMotor = null;

    // Servos
    Servo servo1;
    Servo servo2;
    Servo armServo2;
    double servo_pos1 = 1.0;
    double servo_pos2 = 0.0;
    double arm_pos2 = 1.0;

    // Sensors
    ColorSensor sensorColor2;
    DistanceSensor sensorDistance2;

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
        armServo2 = hardwareMap.get(Servo.class, "armServo2");
        servo1.setPosition(servo_pos1);
        servo2.setPosition(servo_pos2);
        armServo2.setPosition(arm_pos2);

        sensorColor2 = hardwareMap.get(ColorSensor.class, "sensor_color_distance_2");
        sensorDistance2 = hardwareMap.get(DistanceSensor.class, "sensor_range_2");

        float hsvValues[] = {0F, 0F, 0F};
        final double SCALE_FACTOR = 255;

        waitForStart();

        if (opModeIsActive()){

            Color.RGBToHSV((int) (sensorColor2.red() * SCALE_FACTOR),
                    (int) (sensorColor2.green() * SCALE_FACTOR),
                    (int) (sensorColor2.blue() * SCALE_FACTOR),
                    hsvValues);

            /*
            Robot should strafe to the Stone Line, detect the Skystone, retrieve & deliver the Skystone to the Build Side, grab Foundation,
            turn into the Build Site, release the Foundation, return to Midfield Tape, and stop

            RED SIDE
            */


            // Initial Strafe
            frontLeftMotor.setPower(0.55);
            backLeftMotor.setPower(-0.50);
            frontRightMotor.setPower(-0.55);
            backRightMotor.setPower(0.55);
            sleep(3050);
            frontLeftMotor.setPower(0.0);
            backLeftMotor.setPower(0.0);
            frontRightMotor.setPower(0.0);
            backRightMotor.setPower(0.0);

            sleep(700);

            // Adjust distance
            while(sensorDistance2.getDistance(DistanceUnit.CM) >= 4.10){
                frontLeftMotor.setPower(0.35);
                backLeftMotor.setPower(-0.30);
                frontRightMotor.setPower(-0.35);
                backRightMotor.setPower(0.35);

                telemetry.addData("Distance (cm)",
                        String.format(Locale.US, "%.02f", sensorDistance2.getDistance(DistanceUnit.CM)));
                telemetry.update();
            }

            sleep(500);

            // Skystone Detection
            while(sensorColor2.red() > 400){
                frontLeftMotor.setPower(0.25);
                backLeftMotor.setPower(0.25);
                frontRightMotor.setPower(0.25);
                backRightMotor.setPower(0.25);

                // Telemetry
                telemetry.addData("Red  ", sensorColor2.red());
                telemetry.addData("Green", sensorColor2.green());
                telemetry.addData("Blue ", sensorColor2.blue());
                telemetry.addData("Hue", hsvValues[0]);
                telemetry.update();
            }

            // If Not Yellow Detected, STOP
            frontLeftMotor.setPower(0.0);
            backLeftMotor.setPower(0.0);
            frontRightMotor.setPower(0.0);
            backRightMotor.setPower(0.0);

            sleep(500);

            // Move forward a tad for the arm
            frontLeftMotor.setPower(-0.35);
            backLeftMotor.setPower(-0.35);
            frontRightMotor.setPower(-0.35);
            backRightMotor.setPower(-0.35);
            sleep(200);
            frontLeftMotor.setPower(0.0);
            backLeftMotor.setPower(0.0);
            frontRightMotor.setPower(0.0);
            backRightMotor.setPower(0.0);

            sleep(500);

            // Grab Skystone
            arm_pos2 = 0.0;
            armServo2.setPosition(arm_pos2);
            sleep(800);

            // Strafe away from Stone Line
            frontLeftMotor.setPower(-0.45);
            backLeftMotor.setPower(0.40);
            frontRightMotor.setPower(0.45);
            backRightMotor.setPower(-0.45);
            sleep(1480);
            frontLeftMotor.setPower(0.0);
            backLeftMotor.setPower(0.0);
            frontRightMotor.setPower(0.0);
            backRightMotor.setPower(0.0);

            // Move across the Midfield Tape to Building Side
            frontLeftMotor.setPower(-0.6);
            backLeftMotor.setPower(-0.6);
            frontRightMotor.setPower(-0.6);
            backRightMotor.setPower(-0.6);
            sleep(3350);
            frontLeftMotor.setPower(0.0);
            backLeftMotor.setPower(0.0);
            frontRightMotor.setPower(0.0);
            backRightMotor.setPower(0.0);

            // Release Skystone
            arm_pos2 = 1.0;
            armServo2.setPosition(arm_pos2);
            sleep(300);

            // The foundation-grabbing maneuver has been removed for 1/11/20 competition
            // The missing code can be found in Auto19_SS_E

            // Back to Midfield Tape
            frontLeftMotor.setPower(0.5);
            backLeftMotor.setPower(0.5);
            frontRightMotor.setPower(0.5);
            backRightMotor.setPower(0.5);
            sleep(1550);
            frontLeftMotor.setPower(0.0);
            backLeftMotor.setPower(0.0);
            frontRightMotor.setPower(0.0);
            backRightMotor.setPower(0.0);
        }
    }
}