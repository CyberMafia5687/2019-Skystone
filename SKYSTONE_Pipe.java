package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.android.CameraBridgeViewBase;

import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;
import org.openftc.easyopencv.OpenCvPipeline;

import org.firstinspires.ftc.teamcode.SKYSTONE;


// LAST UPDATED: 11/10/19 \\
public class SKYSTONE_Pipe extends OpenCvPipeline {

    private SKYSTONE mSkystone;

    public SKYSTONE_Pipe (SKYSTONE skystone){
        mSkystone = skystone;
    }

    public Mat processFrame(Mat inputFrame){
        Mat rgb = inputFrame;
        Mat gray = new Mat();
        Imgproc.cvtColor(rgb, gray, Imgproc.COLOR_RGB2GRAY);

        if(rgb == null)
            return null;

        if(mSkystone.getSearchArea() == null){
            mSkystone.setSearchArea(new Rect(
                    new Point((rgb.width() - 1) * 0.0,
                            (rgb.height() - 1) * 0.1),
                    new Point((rgb.width() - 1) * 1.0,
                            (rgb.height() - 1) * 0.4)));
        }

        mSkystone.locate(rgb, gray);

        return mSkystone.getAnnotatedImage();
    }
}