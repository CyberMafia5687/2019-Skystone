package org.firstinspires.ftc.teamcode;

// Main Imports
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;


// All driving code is from here: https://seamonsters-2605.github.io/archive/mecanum/, go check it out!


// LAST UPDATED: 1/19/20 \\
@TeleOp
//@Disabled
public class MAINrev_19_Arc extends LinearOpMode {

    // Motors
    private DcMotor frontLeftMotor;
    private DcMotor backLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backRightMotor;
    private DcMotor armMotor;

    // Servos
    Servo servo1;
    Servo servo2;
    Servo armServo;
    double servo_pos1 = 0.0;
    double servo_pos2 = 1.0;
    double arm_pos = 1.0;
    CRServo wheel1;
    CRServo wheel2;

    // Other Stuff
    //


    @Override
    public void runOpMode() {

        frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeftMotor");
        backLeftMotor = hardwareMap.get(DcMotor.class, "backLeftMotor");
        frontRightMotor = hardwareMap.get(DcMotor.class, "frontRightMotor");
        backRightMotor = hardwareMap.get(DcMotor.class, "backRightMotor");

        armMotor = hardwareMap.get(DcMotor.class, "armMotor");

        servo1 = hardwareMap.get(Servo.class, "servo1");
        servo2 = hardwareMap.get(Servo.class, "servo2");
        armServo = hardwareMap.get(Servo.class, "armServo");
        servo1.setPosition(servo_pos1);
        servo2.setPosition(servo_pos2);
        armServo.setPosition(arm_pos);

        wheel1 = hardwareMap.get(CRServo.class, "wheel1");
        wheel2 = hardwareMap.get(CRServo.class, "wheel2");



        // Wait for game to start (Driver, press PLAY)
        // waitForStart();
        while (!opModeIsActive() && !isStopRequested()){
            telemetry.addData("Status", "Waiting for  next mission...");
            telemetry.update();
        }


        while (opModeIsActive()){

           double tgtPower1;
           double tgtPower2;

           // Calculations
           float y1 = this.gamepad1.left_stick_y;
           float x1 = this.gamepad1.left_stick_x;
           float x2 = this.gamepad1.right_stick_x;

           double direction = Math.atan2(y1, x1);
           double magnitude = 2 * (Math.sqrt(Math.pow(x1, 2) + Math.pow(y1, 2))); // Makes the amplitude of the graphs formed 2 instead of 1, giving full power
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

           // Mecanum Drive v3.0 - Trigonometry (Strafe + Turn)
           // DO NOT CHANGE UNLESS DRIVE TRAIN CHANGES
           frontLeftMotor.setPower(-(Math.sin(direction - (π / 4)) * magnitude - turn));
           backLeftMotor.setPower(Math.sin(direction + (π / 4)) * magnitude - turn);
           frontRightMotor.setPower(-(Math.sin(direction + (π / 4)) * magnitude + turn));
           backRightMotor.setPower(Math.sin(direction - (π / 4)) * magnitude + turn);

           ////////////////////////////////////////

           // Foundation-Grabbing Servos
           if(this.gamepad2.right_bumper){
               servo_pos1 = 1.0;
               servo_pos2 = 0.0;
           }
           else if(this.gamepad2.left_bumper){
               servo_pos1 = 0.0;
               servo_pos2 = 1.0;
           }
           servo1.setPosition(servo_pos1);
           servo2.setPosition(servo_pos2);


           // Side Arm Servo
           if(this.gamepad2.x){
               arm_pos = 1.0;
           }
           else if(this.gamepad2.y){
               arm_pos = 0.0;
           }
           armServo.setPosition(arm_pos);


           // Lift/Arm Control
           tgtPower1 = this.gamepad2.left_stick_y;
           armMotor.setPower(tgtPower1);

           // Intake Controls
           tgtPower2 = this.gamepad2.right_stick_y;
           wheel1.setPower(tgtPower2);
           wheel2.setPower(-tgtPower2);

          }
       }
   }