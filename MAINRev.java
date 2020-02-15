package org.firstinspires.ftc.teamcode;

// Main Imports
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;


// LAST UPDATED: 1/30/20 \\
// BACK-UP PROGRAM (just in case)
@TeleOp
//@Disabled
public class MAINRev extends LinearOpMode {

    // Motors
    private DcMotor frontLeftMotor;
    private DcMotor backLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backRightMotor;
    private DcMotor armMotor;
    private DcMotor armMotor2;
    private DcMotor tapeMotor;

    // Servos
    Servo servo1;
    Servo servo2;
    Servo armServo;
    Servo bucket;
    double servo_pos1 = 1.0;
    double servo_pos2 = 0.0;
    double arm_pos = 0.0;
    double bucket_pos = 0.0;

    CRServo wheel1;
    CRServo wheel2;

    @Override
    public void runOpMode() {

        frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeftMotor");
        backLeftMotor = hardwareMap.get(DcMotor.class, "backLeftMotor");
        frontRightMotor = hardwareMap.get(DcMotor.class, "frontRightMotor");
        backRightMotor = hardwareMap.get(DcMotor.class, "backRightMotor");

        armMotor = hardwareMap.get(DcMotor.class, "armMotor");
        armMotor2 = hardwareMap.get(DcMotor.class, "armMotor2");
        tapeMotor = hardwareMap.get(DcMotor.class, "tapeMotor");

        // Servos
        servo1 = hardwareMap.get(Servo.class, "servo1");
        servo2 = hardwareMap.get(Servo.class, "servo2");
        armServo = hardwareMap.get(Servo.class, "armServo");
        bucket = hardwareMap.get(Servo.class, "bucket");
        servo1.setPosition(servo_pos1);
        servo2.setPosition(servo_pos2);
        armServo.setPosition(arm_pos);
        bucket.setPosition(bucket_pos);

        wheel1 = hardwareMap.get(CRServo.class, "wheel1");
        wheel2 = hardwareMap.get(CRServo.class, "wheel2");



        // Wait for game to start (Driver, press PLAY)
        // waitForStart();
        while (!opModeIsActive() && !isStopRequested()){
            telemetry.addData("Status", "Waiting for  next mission...");
            telemetry.update();
        }

        // Run until end of match (End of match, press STOP)
        double tgtPower1;
        double tgtPower2;
        double tgtPower3;
        double tgtPower4;
        double tgtPower5;
        double tgtPower6;

        while (opModeIsActive()){

           telemetry.addData("Status", "The HitBot is Running");


           // Tank Drive
           tgtPower1 = this.gamepad1.left_stick_y;
           frontLeftMotor.setPower(-tgtPower1);
           backLeftMotor.setPower(tgtPower1);

           tgtPower2 = this.gamepad1.right_stick_y;
           frontRightMotor.setPower(-tgtPower2);
           backRightMotor.setPower(tgtPower2);

           // Mechanum Drive v1.0 - Traditional Stick-Drive
           tgtPower3 = this.gamepad1.left_stick_x;
           frontLeftMotor.setPower(tgtPower3);
           backLeftMotor.setPower(tgtPower3);

           tgtPower4 = -this.gamepad1.right_stick_x;
           frontRightMotor.setPower(tgtPower4);
           backRightMotor.setPower(tgtPower4);


           // Tank Drive, Controlled
           if(this.gamepad1.right_bumper){
               frontLeftMotor.setPower(tgtPower1 / 8);
               backLeftMotor.setPower(tgtPower1 / 8);
               frontRightMotor.setPower(tgtPower2 / 8);
               backRightMotor.setPower(tgtPower2 / 8);
           }


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


            // Side Arm Servo
            if(this.gamepad2.x){
                arm_pos = 1.0;
            }
            else if(this.gamepad2.y){
                arm_pos = 0.0;
            }
            armServo.setPosition(arm_pos);


            // Lift/Arm Control - VERTICAL
            tgtPower5= this.gamepad2.left_stick_y;
            armMotor.setPower(tgtPower5);

            // Lift/Arm Control - HORIZONTAL
            if(this.gamepad2.dpad_up){
                armMotor2.setPower(-0.5);
            }
            else if(this.gamepad2.dpad_down){
                armMotor2.setPower(0.5);
            }
            else{
                armMotor2.setPower(0.0);
            }

            // Intake Controls
            tgtPower6 = this.gamepad2.right_stick_y;
            wheel1.setPower(tgtPower6);
            wheel2.setPower(-tgtPower6);

            // Capstone Bucket
            if(this.gamepad1.x){
                bucket_pos = 1.0;
            }
            else if(this.gamepad1.y){
                bucket_pos = 0.0;
            }
            bucket.setPosition(bucket_pos);

            // Tape Measure Controls
            if(this.gamepad2.right_bumper){
                tapeMotor.setPower(1.0);
            }
            else if(this.gamepad2.left_bumper){
                tapeMotor.setPower(-1.0);
            }
            else{
                tapeMotor.setPower(0.0);
            }

        }
    }
}