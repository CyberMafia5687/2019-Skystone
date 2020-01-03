package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;


// LAST UPDATED: 12/1/19 \\
@Disabled
public class SKYSTONE {

    public static final double CenterSkystone_Min = 0.3;
    public static final double CenterSkystone_Max = 0.7;
    public static final int Sel_Skystone_MinArea = 2000;
    public static final int Cand_Skystone_MinArea = Sel_Skystone_MinArea / 2;

    public static final Scalar mask = new Scalar(255);
    final private static Scalar white = new Scalar(255, 255, 255);
    final private static Scalar green = new Scalar(0, 255, 0);
    final private static Scalar red = new Scalar(255, 0, 0);
    public static final Scalar Skystone_HSV_Min = new Scalar(10, 127, 80);
    public static final Scalar Skystone_HSV_Max = new Scalar(30, 255, 255);

    private Rect searchArea;
    private double currentSkystone_Area = 0;
    private double lastKnownSkystone_Area = 0;
    private SkystonePosition currentSkystone_Pos = SkystonePosition.UNKNOWN;
    private SkystonePosition lastKnownSkystone_Pos = SkystonePosition.UNKNOWN;
    private Rect currentSkystone_BoundBox;
    private Rect lastKnownSkystone_BoundBox;
    private List<Rect> candSkystone_BoundBox;

    private Mat annotatedImage;

    private static void center_Label(Mat image, String label, Rect box, int verticalOffset){
        Size labelSize = Imgproc.getTextSize(label, 1, 1.0, 1, null);
        double offset = verticalOffset <= 0 ?
                box.tl().y + verticalOffset :
                box.br().y + labelSize.height + verticalOffset;
        Imgproc.putText(image, label,
                new Point(box.tl().x + (double) box.width / 2 - labelSize.width / 2, offset),
                1, 1.0, white, 1);

    }

    private SkystonePosition derivePosition(Rect stone_BoundBox){
        double stoneLocation =
                (double)(stone_BoundBox.x - searchArea.x + stone_BoundBox.width/2) /
                        (double) searchArea.width;

        if(stoneLocation < CenterSkystone_Min)
            return SkystonePosition.Left;
        else if(stoneLocation > CenterSkystone_Max)
            return SkystonePosition.Right;
        else
            return SkystonePosition.Center;
    }

    public Rect getSearchArea(){
        return searchArea;
    }

    public void setSearchArea(Rect searchArea){
        this.searchArea = searchArea;
    }

    public SkystonePosition getCurrentSkystone_Pos() {
        return currentSkystone_Pos;
    }

    public SkystonePosition getLastKnownSkystone_Pos() {
        return lastKnownSkystone_Pos;
    }

    public Rect getCurrentSkystone_BoundBox(){
        return currentSkystone_BoundBox;
    }

    public Rect getLastKnownSkystone_BoundBox(){
        return lastKnownSkystone_BoundBox;
    }

    public List<Rect> getCandSkystone_BoundBox(){
        return candSkystone_BoundBox;
    }

    public Mat getAnnotatedImage(){
        return annotatedImage;
    }

    private Mat annotateImage(Mat rgbaImage, Mat grayImage, Mat skystoneMask){
        grayImage.copyTo(annotatedImage);
        Imgproc.cvtColor(annotatedImage, annotatedImage, Imgproc.COLOR_GRAY2RGBA, 4);

        rgbaImage.copyTo(annotatedImage, skystoneMask);

        if(searchArea != null){
            Imgproc.rectangle(annotatedImage, searchArea.tl(), searchArea.br(), green, 2);
        }

        if(candSkystone_BoundBox != null){
            for(Rect candGoldMineral_BoundBox : candSkystone_BoundBox){
                Imgproc.rectangle(annotatedImage,
                        candGoldMineral_BoundBox.tl(), candGoldMineral_BoundBox.br(),
                        red, 1);
            }
        }

        if(currentSkystone_BoundBox != null){
            Imgproc.rectangle(annotatedImage,
                    currentSkystone_BoundBox.tl(), currentSkystone_BoundBox.br(),
                    white, 4);
            center_Label(annotatedImage,
                    currentSkystone_Pos.toString(),
                    currentSkystone_BoundBox, -5);

            center_Label(annotatedImage,
                    String.format(Locale.US, "%d, %d",
                            currentSkystone_BoundBox.x + currentSkystone_BoundBox.width / 2,
                            currentSkystone_BoundBox.y + currentSkystone_BoundBox.height / 2),
                    currentSkystone_BoundBox, +5);

            center_Label(annotatedImage,
                    String.format(Locale.US, "%.0f / %.0f",
                            currentSkystone_Area, currentSkystone_BoundBox.area()),
                    currentSkystone_BoundBox, +20);
        }

        return annotatedImage;
    }

    public Mat getOriginalImage(){
        return originalImage;
    }

    private Mat originalImage;
    private Mat HSVImage;
    private Mat searchedImage;
    private Mat skystoneMask;
    public boolean locate(Mat rgbaImage, Mat grayImage){
        if(originalImage == null)
            originalImage = new Mat();
        if(HSVImage == null)
            HSVImage = new Mat();
        if(searchedImage == null)
            searchedImage = new Mat();
        if(skystoneMask == null)
            skystoneMask = new Mat();
        if(annotatedImage == null)
            annotatedImage = new Mat();

        rgbaImage.copyTo(originalImage);

        Imgproc.cvtColor(rgbaImage, HSVImage, Imgproc.COLOR_RGBA2RGB);
        Imgproc.cvtColor(HSVImage, HSVImage, Imgproc.COLOR_RGB2HSV);

        if(searchArea != null){
            Mat searchAreaMask = Mat.zeros(rgbaImage.size(), CvType.CV_8U);
            Imgproc.rectangle(searchAreaMask, searchArea.tl(), searchArea.br(), mask, -1);
            HSVImage.copyTo(searchedImage);
        } else{
            HSVImage.copyTo(searchedImage);
        }

        Core.inRange(searchedImage, Skystone_HSV_Min, Skystone_HSV_Max, skystoneMask);

        List<MatOfPoint> skystoneContours = new ArrayList<>();
        Imgproc.findContours(skystoneMask, skystoneContours, new Mat(),
                Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

        currentSkystone_BoundBox = null;
        currentSkystone_Pos = SkystonePosition.UNKNOWN;

        if(!skystoneContours.isEmpty()){
            MatOfPoint selectedContour = Collections.max(skystoneContours, new Comparator<MatOfPoint>(){
                @Override
                public int compare(MatOfPoint o1, MatOfPoint o2){
                    return Double.compare(
                            Imgproc.contourArea(o1),
                            Imgproc.contourArea(o2));
                }
            });

            Rect selectedContourBoundingBox = Imgproc.boundingRect(selectedContour);
            double selectedContourArea = Imgproc.contourArea(selectedContour);
            if(selectedContourArea >= Sel_Skystone_MinArea){
                currentSkystone_Area = selectedContourArea;
                currentSkystone_BoundBox = selectedContourBoundingBox;
                currentSkystone_Pos = derivePosition(currentSkystone_BoundBox);
            }

            candSkystone_BoundBox = new ArrayList<>();
            for(MatOfPoint candidateMineralContour : skystoneContours){
                Rect bb = Imgproc.boundingRect(candidateMineralContour);
                if(bb.area() > Cand_Skystone_MinArea &&
                        !selectedContourBoundingBox.contains(bb.tl()) &&
                        !selectedContourBoundingBox.contains(bb.br()) &&
                        !selectedContourBoundingBox.contains(new Point(bb.tl().x, bb.br().y)) &&
                        !selectedContourBoundingBox.contains(new Point(bb.br().x, bb.tl().y)))
                    candSkystone_BoundBox.add(bb);
            }
        }

        if(currentSkystone_Pos != SkystonePosition.UNKNOWN){
            lastKnownSkystone_Area = currentSkystone_Area;
            lastKnownSkystone_Pos = currentSkystone_Pos;
            lastKnownSkystone_BoundBox = currentSkystone_BoundBox;
        }

        annotatedImage = annotateImage(originalImage, grayImage, skystoneMask);

        return currentSkystone_Pos != SkystonePosition.UNKNOWN;
    }

    public enum SkystonePosition {
        UNKNOWN,
        Left,
        Center,
        Right,
    }

}