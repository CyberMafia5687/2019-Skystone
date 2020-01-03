package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.util.Locale;

// Check this website for color value help: http://colorizer.org/

// LAST UPDATED: 12/28/19 \\
// FOR TESTING PURPOSES ONLY \\
@Autonomous
//@Disabled
public class Auto19_SS_E extends LinearOpMode {

    // Motors
    DcMotor frontLeftMotor = null;
    DcMotor backLeftMotor = null;
    DcMotor frontRightMotor = null;
    DcMotor backRightMotor = null;

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
            Robot should strafe to the Stone Line, detect the Skystone, retrieve the Skystone

            EXAMPLE ONLY
            */


            // Strafe to the blocks
            frontLeftMotor.setPower(-0.5);
            backLeftMotor.setPower(0.5);
            frontRightMotor.setPower(0.5);
            backRightMotor.setPower(-0.5);
            sleep(400);
            frontLeftMotor.setPower(0.0);
            backLeftMotor.setPower(0.0);
            frontRightMotor.setPower(0.0);
            backRightMotor.setPower(0.0);

            // Adjust distance, using Distance Sensor
            if(sensorDistance.getDistance(DistanceUnit.CM) > 5.0){
                frontLeftMotor.setPower(-0.3);
                backLeftMotor.setPower(0.3);
                frontRightMotor.setPower(0.3);
                backRightMotor.setPower(-0.3);
            }
            else if(sensorDistance.getDistance(DistanceUnit.CM) <= 5.0){
                frontLeftMotor.setPower(0.0);
                backLeftMotor.setPower(0.0);
                frontRightMotor.setPower(0.0);
                backRightMotor.setPower(0.0);
            }
            sleep(300);

            // Skystone Detection
            if(sensorColor.red() > 86) {
                // If Yellow Detected, move
                frontLeftMotor.setPower(-0.3);
                backLeftMotor.setPower(0.3);
                frontRightMotor.setPower(-0.3);
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

                sleep(300);

            }
        }
    }
}