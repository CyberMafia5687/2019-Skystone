package org.firstinspires.ftc.teamcode;

// Main Imports
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;


// LAST UPDATED: 12/27/19 \\
@TeleOp
//@Disabled
public class MAINRev extends LinearOpMode {

    // Motors
    private DcMotor frontLeftMotor;
    private DcMotor backLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backRightMotor;
    //private DcMotor armMotor;

    // Servos
    //

    @Override
    public void runOpMode() {

        frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeftMotor");
        backLeftMotor = hardwareMap.get(DcMotor.class, "backLeftMotor");
        frontRightMotor = hardwareMap.get(DcMotor.class, "frontRightMotor");
        backRightMotor = hardwareMap.get(DcMotor.class, "backRightMotor");

        //armMotor = hardwareMap.get(DcMotor.class, "armMotor");



        // Wait for game to start (Driver, press PLAY)
        // waitForStart();
        while (!opModeIsActive() && !isStopRequested()){
            telemetry.addData("Status", "Waiting for  next mission...");
            telemetry.update();
        }

        // Run until end of match (End of match, press STOP)
        double tgtPower1 = 0;
        double tgtPower2 = 0;
        double tgtPower3 = 0;
        double tgtPower4 = 0;

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


        }
    }
}