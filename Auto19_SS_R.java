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

// LAST UPDATED: 12/27/19 \\
// CONCEPTUAL; PLEASE ADJUST VALUES BEFORE MAKING IT OFFICIAL \\
@Autonomous
@Disabled
public class Auto19_SS_R extends LinearOpMode {

    // Motors
    DcMotor frontLeftMotor = null;
    DcMotor backLeftMotor = null;
    DcMotor frontRightMotor = null;
    DcMotor backRightMotor = null;

    // Servos
    Servo servo1;
    Servo servo2;
    Servo arm;
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

        servo1 = hardwareMap.get(Servo.class, "servo1");
        servo2 = hardwareMap.get(Servo.class, "servo2");
        arm = hardwareMap.get(Servo.class, "arm");
        servo1.setPosition(servo_pos1);
        servo2.setPosition(servo_pos2);
        arm.setPosition(arm_pos);

        sensorColor = hardwareMap.get(ColorSensor.class, "sensor_color_distance");
        sensorDistance = hardwareMap.get(DistanceSensor.class, "sensor_color_distance");
        float hsvValues[] = {0F, 0F, 0F};
        final float values[] = hsvValues;
        final double SCALE_FACTOR = 255;

        waitForStart();

        while (opModeIsActive()){

            Color.RGBToHSV((int) (sensorColor.red() * SCALE_FACTOR),
                    (int) (sensorColor.green() * SCALE_FACTOR),
                    (int) (sensorColor.blue() * SCALE_FACTOR),
                    hsvValues);

            // Telemetry
            telemetry.addData("Distance (cm)",
                    String.format(Locale.US, "%.02f", sensorDistance.getDistance(DistanceUnit.CM)));
            telemetry.addData("Alpha", sensorColor.alpha());
            telemetry.addData("Red  ", sensorColor.red());
            telemetry.addData("Green", sensorColor.green());
            telemetry.addData("Blue ", sensorColor.blue());
            telemetry.addData("Hue", hsvValues[0]);
            telemetry.update();


            /*
            Robot should strafe to the Stone Line, detect the Skystone, retrieve & deliver the Skystone to the Build Side, grab Foundation,
            turn into the Build Site, release the Foundation, return to Midfield Tape

            RED SIDE
            */


            // Strafe to the blocks
            frontLeftMotor.setPower(-0.5);
            backLeftMotor.setPower(0.5);
            frontRightMotor.setPower(0.5);
            backRightMotor.setPower(-0.5);
            sleep(2000);
            frontLeftMotor.setPower(0.0);
            backLeftMotor.setPower(0.0);
            frontRightMotor.setPower(0.0);
            backRightMotor.setPower(0.0);

            // Adjust distance, using Distance Sensor
            if(sensorDistance.getDistance(DistanceUnit.CM) > 5.45){
                frontLeftMotor.setPower(-0.3);
                backLeftMotor.setPower(0.3);
                frontRightMotor.setPower(0.3);
                backRightMotor.setPower(-0.3);
            }
            else if(sensorDistance.getDistance(DistanceUnit.CM) <= 5.45){
                frontLeftMotor.setPower(0.0);
                backLeftMotor.setPower(0.0);
                frontRightMotor.setPower(0.0);
                backRightMotor.setPower(0.0);
            }
            sleep(300);

            // Skystone Detection
            if(sensorColor.red() > 86) {
                // If Yellow Detected, move
                frontLeftMotor.setPower(0.3);
                backLeftMotor.setPower(0.3);
                frontRightMotor.setPower(0.3);
                backRightMotor.setPower(0.3);
                //
            }
            else if(sensorColor.red() <= 86){
                // If Not Yellow Detected, STOP
                frontLeftMotor.setPower(0.0);
                backLeftMotor.setPower(0.0);
                frontRightMotor.setPower(0.0);
                backRightMotor.setPower(0.0);
                sleep(300);

                // Grab Skystone
                arm_pos = 1.0;
                arm.setPosition(arm_pos);

                // Strafe away from Stone Line
                frontLeftMotor.setPower(0.4);
                backLeftMotor.setPower(-0.4);
                frontRightMotor.setPower(-0.4);
                backRightMotor.setPower(0.4);
                sleep(300);
                frontLeftMotor.setPower(0.0);
                backLeftMotor.setPower(0.0);
                frontRightMotor.setPower(0.0);
                backRightMotor.setPower(0.0);

                // Move across the Midfield Tape to Building Side
                frontLeftMotor.setPower(-0.5);
                backLeftMotor.setPower(-0.5);
                frontRightMotor.setPower(-0.5);
                backRightMotor.setPower(-0.5);
                sleep(5000);
                frontLeftMotor.setPower(0.0);
                backLeftMotor.setPower(0.0);
                frontRightMotor.setPower(0.0);
                backRightMotor.setPower(0.0);

                // Release Skystone
                arm_pos = 0.0;
                arm.setPosition(arm_pos);

                // Approach Foundation
                frontLeftMotor.setPower(0.4);
                backLeftMotor.setPower(-0.4);
                frontRightMotor.setPower(-0.4);
                backRightMotor.setPower(0.4);
                sleep(300);
                frontLeftMotor.setPower(0.0);
                backLeftMotor.setPower(0.0);
                frontRightMotor.setPower(0.0);
                backRightMotor.setPower(0.0);

                // Grab Foundation
                servo1.setPosition(0.0);
                servo2.setPosition(1.0);
                sleep(300);

                // Spin into Build Site
                frontLeftMotor.setPower(-0.5);
                backLeftMotor.setPower(-0.5);
                frontRightMotor.setPower(0.5);
                backRightMotor.setPower(0.5);
                sleep(1000);
                frontLeftMotor.setPower(0.0);
                backLeftMotor.setPower(0.0);
                frontRightMotor.setPower(0.0);
                backRightMotor.setPower(0.0);

                // Release Foundation
                servo1.setPosition(1.0);
                servo2.setPosition(0.0);
                sleep(300);

                // Undo Spin
                frontLeftMotor.setPower(0.5);
                backLeftMotor.setPower(0.5);
                frontRightMotor.setPower(-0.5);
                backRightMotor.setPower(-0.5);
                sleep(500);
                frontLeftMotor.setPower(0.0);
                backLeftMotor.setPower(0.0);
                frontRightMotor.setPower(0.0);
                backRightMotor.setPower(0.0);

                // Back to Midfield Tape
                frontLeftMotor.setPower(0.5);
                backLeftMotor.setPower(0.5);
                frontRightMotor.setPower(0.5);
                backRightMotor.setPower(0.5);
                sleep(3500);
                frontLeftMotor.setPower(0.0);
                backLeftMotor.setPower(0.0);
                frontRightMotor.setPower(0.0);
                backRightMotor.setPower(0.0);
            }
        }
    }
}