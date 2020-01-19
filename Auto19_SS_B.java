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

import static java.lang.Double.isNaN;


// Check this website for color value help: http://colorizer.org/

// LAST UPDATED: 1/19/20 \\
// PRELIMINARY PROGRAM; ADDITIONAL MATERIAL NEEDED \\
@Autonomous
//@Disabled
public class Auto19_SS_B extends LinearOpMode {

    // Motors
    DcMotor frontLeftMotor = null;
    DcMotor backLeftMotor = null;
    DcMotor frontRightMotor = null;
    DcMotor backRightMotor = null;

    // Servos
    Servo servo1;
    Servo servo2;
    Servo armServo;
    double servo_pos1 = 0.0;
    double servo_pos2 = 1.0;
    double arm_pos = 1.0;

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
            Robot should strafe to the Stone Line, detect the Skystone, retrieve & deliver the Skystone to the Build Side, grab Foundation,
            turn into the Build Site, release the Foundation, return to Midfield Tape, and stop

            BLUE SIDE
            */


            // Initial Strafe
            frontLeftMotor.setPower(-0.55);
            backLeftMotor.setPower(0.55);
            frontRightMotor.setPower(0.55);
            backRightMotor.setPower(-0.55);
            sleep(2850);
            frontLeftMotor.setPower(0.0);
            backLeftMotor.setPower(0.0);
            frontRightMotor.setPower(0.0);
            backRightMotor.setPower(0.0);

            sleep(700);

            // Adjust distance
            while(sensorDistance.getDistance(DistanceUnit.CM) > 12.8){
                frontLeftMotor.setPower(-0.3);
                backLeftMotor.setPower(0.3);
                frontRightMotor.setPower(0.3);
                backRightMotor.setPower(-0.3);

                telemetry.addData("Distance (cm)",
                        String.format(Locale.US, "%.02f", sensorDistance.getDistance(DistanceUnit.CM)));
                telemetry.update();
            }

            sleep(500);

            // Skystone Detection
            while(sensorColor.red() > 50){
                frontLeftMotor.setPower(0.3);
                backLeftMotor.setPower(0.3);
                frontRightMotor.setPower(0.3);
                backRightMotor.setPower(0.3);

                // Telemetry
                telemetry.addData("Red  ", sensorColor.red());
                telemetry.addData("Green", sensorColor.green());
                telemetry.addData("Blue ", sensorColor.blue());
                telemetry.addData("Hue", hsvValues[0]);
                telemetry.update();
            }

            // If Not Yellow Detected, STOP
            frontLeftMotor.setPower(0.0);
            backLeftMotor.setPower(0.0);
            frontRightMotor.setPower(0.0);
            backRightMotor.setPower(0.0);

            sleep(1000);

            // Grab Skystone
            arm_pos = 0.0;
            armServo.setPosition(arm_pos);
            sleep(800);

            // Strafe away from Stone Line
            frontLeftMotor.setPower(0.40);
            backLeftMotor.setPower(-0.45);
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
            sleep(3350);
            frontLeftMotor.setPower(0.0);
            backLeftMotor.setPower(0.0);
            frontRightMotor.setPower(0.0);
            backRightMotor.setPower(0.0);

            // Release Skystone
            arm_pos = 1.0;
            armServo.setPosition(arm_pos);
            sleep(300);

            // The foundation-grabbing maneuver has been removed for 1/11/20 competition
            // The missing code can be found in Auto19_SS_E

            // Back to Midfield Tape
            frontLeftMotor.setPower(0.5);
            backLeftMotor.setPower(0.5);
            frontRightMotor.setPower(0.5);
            backRightMotor.setPower(0.5);
            sleep(1280);
            frontLeftMotor.setPower(0.0);
            backLeftMotor.setPower(0.0);
            frontRightMotor.setPower(0.0);
            backRightMotor.setPower(0.0);
        }
    }
}