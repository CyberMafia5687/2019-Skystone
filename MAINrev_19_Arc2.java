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

// LAST UPDATED: 12/28/19 \\
@TeleOp
@Disabled
public class MAINrev_19_Arc2 extends LinearOpMode {

    // Motors
    private DcMotor frontLeftMotor;
    private DcMotor backLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backRightMotor;


    // Other Stuff
    //

    @Override
    public void runOpMode() {

        frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeftMotor");
        backLeftMotor = hardwareMap.get(DcMotor.class, "backLeftMotor");
        frontRightMotor = hardwareMap.get(DcMotor.class, "frontRightMotor");
        backRightMotor = hardwareMap.get(DcMotor.class, "backRightMotor");


        // Wait for game to start (Driver, press PLAY)
        // waitForStart();
        while (!opModeIsActive() && !isStopRequested()){
            telemetry.addData("Status", "Waiting for  next mission...");
            telemetry.update();
        }


        while (opModeIsActive()){

           telemetry.addData("Status", "The HitBot is running");
           telemetry.update();

           // Any variables needed to make the following run
           float y1 = this.gamepad1.left_stick_y;
           float x1 = this.gamepad1.left_stick_x;
           double power;

           double magnitude = (Math.sqrt(Math.pow(x1, 2) + Math.pow(y1, 2)));
           double angle = Math.atan2(y1, x1);
           double direction = (angle * 180) / Math.PI;

           // Telemetry
           telemetry.addData("Status", "The HitBot is running");
           telemetry.addData("Direction", direction);
           telemetry.addData("Magnitude", magnitude);
           telemetry.addData("Power", frontLeftMotor.getPower());
           telemetry.addData("Power", frontRightMotor.getPower());
           telemetry.addData("Power", backLeftMotor.getPower());
           telemetry.addData("Power", backRightMotor.getPower());
           telemetry.update();

           // Long If-Else loop for mecanum driving because we had to

           // Calculating Speed & Power, Left Joystick
           if(magnitude < 0.2){
               power = 0.0;
           }
           else if(magnitude < 0.4){
               power = 0.25;
           }
           else if(magnitude < 0.6){
               power = 0.5;
           }
           else if(magnitude < 0.8){
               power = 0.75;
           }
           else{
               power = 1.0;
           }

           // Calculate Direction
           if(direction >= 23 && direction <= 67){
               // Forward, Diagonal Right
               frontLeftMotor.setPower(power);
               frontRightMotor.setPower(0.0);
               backLeftMotor.setPower(0.0);
               backRightMotor.setPower(power);
           }
           else if(direction >= 68 && direction <= 112){
               // Strafe Right
               frontLeftMotor.setPower(power);
               frontRightMotor.setPower(-power);
               backLeftMotor.setPower(-power);
               backRightMotor.setPower(power);
           }
           else if(direction >= 113 && direction <= 157){
               // Backward, Diagonal Right
               frontLeftMotor.setPower(0.0);
               frontRightMotor.setPower(-power);
               backLeftMotor.setPower(-power);
               backRightMotor.setPower(0.0);
           }
           else if(direction >= 158 && direction <= 202){
               // Backwards
               frontLeftMotor.setPower(-power);
               frontRightMotor.setPower(-power);
               backLeftMotor.setPower(-power);
               backRightMotor.setPower(-power);
           }
           else if(direction >= 203 && direction <= 247){
               // Backward, Diagonal Left
               frontLeftMotor.setPower(-power);
               frontRightMotor.setPower(0.0);
               backLeftMotor.setPower(0.0);
               backRightMotor.setPower(-power);
           }
           else if(direction >= 248 && direction <= 292){
               // Strafe Left
               frontLeftMotor.setPower(-power);
               frontRightMotor.setPower(power);
               backLeftMotor.setPower(power);
               backRightMotor.setPower(-power);
           }
           else if(direction >= 293 && direction <= 337){
               // Forward, Diagonal Left
               frontLeftMotor.setPower(0.0);
               frontRightMotor.setPower(power);
               backLeftMotor.setPower(power);
               backRightMotor.setPower(0.0);
           }
           else{
               // Forward
               frontLeftMotor.setPower(power);
               frontRightMotor.setPower(power);
               backLeftMotor.setPower(power);
               backRightMotor.setPower(power);
           }

           // Calculating Speed & Power, Right Joystick
           double magnitude2 = this.gamepad1.right_stick_x;
           double power2;

           if(magnitude2 < 0.2){
               power2 = 0.0;
           }
           else if(magnitude2 < 0.4){
               power2 = 0.25;
           }
           else if(magnitude2 < 0.6){
               power2 = 0.5;
           }
           else if(magnitude2 < 0.8){
               power2 = 0.75;
           }
           else{
               power2 = 1.0;
           }

           // Spin Controls
           if(this.gamepad1.right_stick_x > 0){
               frontLeftMotor.setPower(power2);
               frontRightMotor.setPower(-power2);
               backLeftMotor.setPower(power2);
               backRightMotor.setPower(-power2);
           }
           else if(this.gamepad1.right_stick_x < 0){
               frontLeftMotor.setPower(-power2);
               frontRightMotor.setPower(power2);
               backLeftMotor.setPower(-power2);
               backRightMotor.setPower(power2);
           }

          }
       }
   }