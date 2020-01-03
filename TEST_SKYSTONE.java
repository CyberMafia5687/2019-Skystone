package org.firstinspires.ftc.teamcode;

import android.os.Environment;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;
import org.openftc.easyopencv.OpenCvPipeline;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

import org.firstinspires.ftc.teamcode.SKYSTONE;
import org.firstinspires.ftc.teamcode.SKYSTONE_Pipe;

import java.io.IOException;
import java.util.List;
import java.io.File;
import java.util.Locale;


// LAST UPDATED: 11/18/19 \\
@TeleOp
//@Disabled
public class TEST_SKYSTONE extends LinearOpMode {

    final private File SavedImagePath =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
    final private SimpleDateFormat SavedImageDateFormat =
            new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US);

    private static void saveCapturedImage(File path, String prefix, Mat rgbaImage, String suffix){
        Mat bgrImage = new Mat();
        Imgproc.cvtColor(rgbaImage, bgrImage, Imgproc.COLOR_RGBA2BGR, 3);
        String filename = prefix + "_" + suffix + ".jpg";
        File file = new File(path, filename);
        Imgcodecs.imwrite(file.toString(), bgrImage);
    }

    OpenCvCamera phoneCam;
    //

    @Override
    public void runOpMode(){
        SKYSTONE skystone = new SKYSTONE();
        SKYSTONE_Pipe skyPipe = new SKYSTONE_Pipe(skystone);

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        phoneCam = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
        phoneCam.openCameraDevice();
        phoneCam.setPipeline(skyPipe);
        phoneCam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);


        while(!isStarted() && !isStopRequested()){
            telemetry.addData("Current Position",
                    skystone.getCurrentSkystone_Pos());
            telemetry.addData("Last Known Position",
                    skystone.getLastKnownSkystone_Pos());
            telemetry.update();
        }

        phoneCam.stopStreaming();
        phoneCam.closeCameraDevice();

        String savedImagePrefix = SavedImageDateFormat.format(new Date());

        saveCapturedImage(SavedImagePath, savedImagePrefix,
                skystone.getOriginalImage(),
                skystone.getCurrentSkystone_Pos().
                        toString().toLowerCase());

        saveCapturedImage(SavedImagePath, savedImagePrefix,
                skystone.getAnnotatedImage(),
                "annotated");
    }
}