package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.internal.system.Deadline;

@TeleOp
//@Disabled
public class REV_Blinkin extends LinearOpMode {

    RevBlinkinLedDriver blinkin;

    /*
    NOTES:
    http://www.revrobotics.com/content/docs/REV-11-1105-UM.pdf

    You can type in the first few letters of the pattern you want to find it
    */


    @Override
    public void runOpMode(){

        blinkin = hardwareMap.get(RevBlinkinLedDriver.class, "blinkin");


        waitForStart();

        while(opModeIsActive()){

            // BUTTONS
            if(gamepad1.a){
                blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.GREEN);
            }
            else if(gamepad1.b){
                blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.RED);
            }
            else if(gamepad1.x){
                blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE);
            }
            else if(gamepad1.y){
                blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLACK);
            }
            // BUMPERS
            else if(gamepad1.left_bumper){
                blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.RED_ORANGE);
            }
            else if(gamepad1.right_bumper){
                blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.YELLOW);
            }
            // D-PAD
            else if(gamepad1.dpad_right || gamepad1.dpad_left){
                blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.BREATH_GRAY);
            }
            else if(gamepad1.dpad_down){
                blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.STROBE_RED);
            }
            else if(gamepad1.dpad_up){
                blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.RAINBOW_RAINBOW_PALETTE);
            }

        }
    }
}