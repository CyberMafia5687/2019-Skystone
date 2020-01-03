package org.firstinspires.ftc.teamcode;

// Main Imports
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;


// Extra Imports
//

// All code is from here: https://seamonsters-2605.github.io/archive/mecanum/


// LAST UPDATED: 12/28/19 \\
@TeleOp
//@Disabled
public class MAINrev_19_Arc extends LinearOpMode {

    // Motors
    private DcMotor frontLeftMotor;
    private DcMotor backLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backRightMotor;

    // Servos
    Servo servo1;
    Servo servo2;
    Servo armServo;
    double servo_pos1 = 0.0;
    double servo_pos2 = 1.0;
    double arm_pos = 0.0;

    // Other Stuff
    //


    @Override
    public void runOpMode() {

        frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeftMotor");
        backLeftMotor = hardwareMap.get(DcMotor.class, "backLeftMotor");
        frontRightMotor = hardwareMap.get(DcMotor.class, "frontRightMotor");
        backRightMotor = hardwareMap.get(DcMotor.class, "backRightMotor");

        servo1 = hardwareMap.get(Servo.class, "servo1");
        servo2 = hardwareMap.get(Servo.class, "servo2");
        armServo = hardwareMap.get(Servo.class, "armServo");
        servo1.setPosition(servo_pos1);
        servo2.setPosition(servo_pos2);
        armServo.setPosition(arm_pos);



        // Wait for game to start (Driver, press PLAY)
        // waitForStart();
        while (!opModeIsActive() && !isStopRequested()){
            telemetry.addData("Status", "Waiting for  next mission...");
            telemetry.update();
        }


        while (opModeIsActive()){

           // v1 Drive data
           float y1 = this.gamepad1.left_stick_y;
           float x1 = this.gamepad1.left_stick_x;
           float x2 = this.gamepad1.right_stick_x;

           double direction = Math.atan2(y1, x1);
           double magnitude = 2 * (Math.sqrt(Math.pow(x1, 2) + Math.pow(y1, 2))); // Makes the amplitude of the graph formed 2 instead of 1, giving full power
           double turn = x2;
           double π = Math.PI;

           // Telemetry
           telemetry.addData("Status", "The HitBot is running");
           telemetry.addData("Direction", direction);
           telemetry.addData("Magnitude", magnitude);
           telemetry.addData("Front Left Power", frontLeftMotor.getPower());
           telemetry.addData("Front Right Power", frontRightMotor.getPower());
           telemetry.addData("Back Left Power", backLeftMotor.getPower());
           telemetry.addData("Back Right Power", backRightMotor.getPower());
           telemetry.update();

           // Strafe + Turn
           // DO NOT CHANGE UNLESS DRIVE TRAIN CHANGES
           frontLeftMotor.setPower(-(Math.sin(direction - (π / 4)) * magnitude - turn));
           backLeftMotor.setPower(Math.sin(direction + (π / 4)) * magnitude - turn);
           frontRightMotor.setPower(-(Math.sin(direction + (π / 4)) * magnitude + turn));
           backRightMotor.setPower(Math.sin(direction - (π / 4)) * magnitude + turn);

           ////////////////////////////////////////

           // Foundation-Grabbing Servos
           if(this.gamepad1.b){
               servo_pos1 = 1.0;
               servo_pos2 = 0.0;
           }
           else if(this.gamepad1.a){
               servo_pos1 = 0.0;
               servo_pos2 = 1.0;
           }
           servo1.setPosition(servo_pos1);
           servo2.setPosition(servo_pos2);


          }
       }
   }