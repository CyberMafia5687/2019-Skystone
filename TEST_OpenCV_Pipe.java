package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.List;


// LAST UPDATED: 11/11/19 \\
@TeleOp
@Disabled
public class TEST_OpenCV_Pipe extends LinearOpMode {

    OpenCvCamera phoneCam;
    StageSwitchingPipeline stageSwitchingPipeline;

    @Override
    public void runOpMode(){
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        phoneCam = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
        phoneCam.openCameraDevice();
        stageSwitchingPipeline = new StageSwitchingPipeline();
        phoneCam.setPipeline(stageSwitchingPipeline);
        phoneCam.startStreaming(640, 480, OpenCvCameraRotation.UPRIGHT);

        waitForStart();

        while (opModeIsActive()){
            telemetry.addData("Num contours found", stageSwitchingPipeline.getNumContoursFound());
            telemetry.addData("Contour type", stageSwitchingPipeline.stageToRenderToViewport);
            telemetry.update();
            sleep(100);
        }
    }

    static class StageSwitchingPipeline extends OpenCvPipeline
    {
        Mat yCbCrChan2Mat = new Mat();
        Mat thresholdMat = new Mat();
        Mat contoursOnFrameMat = new Mat();
        List<MatOfPoint> contoursList = new ArrayList<>();
        int numContoursFound;

        enum Stage
        {
            YCbCr_CHAN2,
            CONTOURS_OVERLAYED_ON_FRAME,
            RAW_IMAGE,
        }

        private Stage stageToRenderToViewport = Stage.YCbCr_CHAN2;
        private Stage[] stages = Stage.values();

        @Override
        public void onViewportTapped(){
            int currentStageNum = stageToRenderToViewport.ordinal();
            int nextStageNum = currentStageNum + 1;
            if(nextStageNum >= stages.length){
                nextStageNum = 0;
            }

            stageToRenderToViewport = stages[nextStageNum];
        }

        @Override
        public Mat processFrame(Mat input){
            contoursList.clear();

            Imgproc.cvtColor(input, yCbCrChan2Mat, Imgproc.COLOR_RGB2YCrCb);
            Core.extractChannel(yCbCrChan2Mat, yCbCrChan2Mat, 2);

            Imgproc.threshold(yCbCrChan2Mat, thresholdMat, 100, 255, Imgproc.THRESH_BINARY_INV);

            Imgproc.findContours(thresholdMat, contoursList, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
            numContoursFound = contoursList.size();

            input.copyTo(contoursOnFrameMat);
            Imgproc.drawContours(contoursOnFrameMat, contoursList, -2, new Scalar(0, 0, 255), 3, 8);

            switch(stageToRenderToViewport){
                case YCbCr_CHAN2:{
                    return yCbCrChan2Mat;
                }
                case CONTOURS_OVERLAYED_ON_FRAME:{
                    return contoursOnFrameMat;
                }
                case RAW_IMAGE:{
                    return input;
                }
                default:{
                    return input;
                }
            }
        }

        public int getNumContoursFound(){
            return numContoursFound;
        }
    }
}