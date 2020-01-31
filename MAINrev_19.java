package org.firstinspires.ftc.teamcode;

// Main Imports
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

// Extra Imports
//

// LAST UPDATED: 1/5/20 \\
// In case Arcade Style stops working for whatever reason \\
@TeleOp
@Disabled
public class MAINrev_19 extends LinearOpMode {

    // Motors
    private DcMotor frontLeftMotor;
    private DcMotor backLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backRightMotor;

    // Servos
    Servo servo1;
    Servo servo2;
    double servo_pos1 = 0.0;
    double servo_pos2 = 1.0;


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
        servo1.setPosition(servo_pos1);
        servo2.setPosition(servo_pos2);


        // Wait for game to start (Driver, press PLAY)
        // waitForStart();
        while (!opModeIsActive() && !isStopRequested()){
            telemetry.addData("Status", "Waiting for  next mission...");
            telemetry.update();
        }

        double tgtPower1;
        double tgtPower2;
        double tgtPower3;
        double tgtPower4;


        while (opModeIsActive()){

           telemetry.addData("Status", "The HitBot is running");
           telemetry.update();

           // Tank Drive
           tgtPower1 = -this.gamepad1.left_stick_y;
           frontLeftMotor.setPower(tgtPower1);
           backLeftMotor.setPower(tgtPower1);

           tgtPower2 = this.gamepad1.right_stick_y;
           frontRightMotor.setPower(tgtPower2);
           backRightMotor.setPower(tgtPower2);

           // Mechanum Drive v1.0 - Traditional Stick-Drive
           tgtPower3 = this.gamepad1.left_stick_x;
           frontLeftMotor.setPower(tgtPower3);
           backLeftMotor.setPower(-tgtPower3);

           tgtPower4 = -this.gamepad1.right_stick_x;
           frontRightMotor.setPower(-tgtPower4);
           backRightMotor.setPower(tgtPower4);


           // Foundation-Grabbing Servos
           if(this.gamepad2.b){
               servo_pos1 = 1.0;
               servo_pos2 = 0.0;
           }
           else if(this.gamepad2.a){
               servo_pos1 = 0.0;
               servo_pos2 = 1.0;
           }
           servo1.setPosition(servo_pos1);
           servo2.setPosition(servo_pos2);


          }
       }
   }