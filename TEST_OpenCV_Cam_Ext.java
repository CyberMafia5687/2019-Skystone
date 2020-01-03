package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;
import org.openftc.easyopencv.OpenCvPipeline;


// LAST UPDATED: 11/2/19 \\
@TeleOp
@Disabled
public class TEST_OpenCV_Cam_Ext extends LinearOpMode {

    OpenCvCamera phoneCam;

    @Override
    public void runOpMode(){
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        phoneCam = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);

        phoneCam.openCameraDevice();

        phoneCam.setPipeline(new SamplePipeline());

        phoneCam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);


        waitForStart();

        while (opModeIsActive()){
            telemetry.addData("Frame Count", phoneCam.getFrameCount());
            telemetry.addData("FPS", String.format("%.2f", phoneCam.getFps()));
            telemetry.addData("Total Frame Time ms", phoneCam.getTotalFrameTimeMs());
            telemetry.addData("Pipeline Time ms", phoneCam.getPipelineTimeMs());
            telemetry.addData("Overhead Time ms", phoneCam.getOverheadTimeMs());
            telemetry.addData("Theoretical Max FPS", phoneCam.getCurrentPipelineMaxFps());
            telemetry.update();

            if(gamepad1.a){
                phoneCam.stopStreaming();
            }
            else if(gamepad1.x){
                phoneCam.pauseViewport();
            }
            else if(gamepad1.y){
                phoneCam.resumeViewport();
            }

            sleep(100);
        }
    }

    class SamplePipeline extends OpenCvPipeline
    {
        @Override
        public Mat processFrame(Mat input){
            Imgproc.rectangle(
                    input,
                    new Point(
                            input.cols()/3,
                            input.rows()/4),
                    new Point(
                            input.cols()*(3f/4f),
                            input.rows()*(3f/4f)),
                    new Scalar(0, 255, 0), 4);
            return input;
        }
    }
}
/*
REALLY OLD CODE! DO NOT TOUCH!! DO NOT PUT IN; THE WORLD WILL EXPLODE IF YOU DO!!!
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

    @Override
    public void runOpMode(){
        GoldMineral goldMineral = new GoldMineral();
        GoldPipeline goldPipeline = new GoldPipeline(goldMineral);
        goldPipeline.init(hardwareMap.appContext,
                CameraViewDisplay.getInstance());
        goldPipeline.enable();

        while(!isStarted() && !isStopRequested()){
            telemetry.addData("Current Position",
                    goldMineral.getCurrentGoldMineral_Pos());
            telemetry.addData("Last Known Position",
                    goldMineral.getLastKnownMineral_Pos());
            telemetry.update();
        }

        goldPipeline.disable();

        String savedImagePrefix = SavedImageDateFormat.format(new Date());

        saveCapturedImage(SavedImagePath, savedImagePrefix,
                goldMineral.getOriginalImage(),
                goldMineral.getCurrentGoldMineral_Pos().
                        toString().toLowerCase());

        saveCapturedImage(SavedImagePath, savedImagePrefix,
                goldMineral.getAnnotatedImage(),
                "annotated");
    }
 */