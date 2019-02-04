package org.firstinspires.ftc.teamcode;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name="Autonomous9219Depot")
public class GoldAlignExample extends LinearOpMode
{
    // Detector object
    private GoldAlignDetector detector;

    //Declaring Motors
    public DcMotor BR;
    public DcMotor BL;
    public DcMotor FL;
    public DcMotor FR;
    public DcMotor LA;
    public DcMotor CL;
    public DcMotor CF;

    public Servo HK;

    private double goldXPos;


    public void runOpMode() throws InterruptedException {

        //Initializing Motors
        BR = hardwareMap.dcMotor.get("BR");
        BL = hardwareMap.dcMotor.get("BL");
        FL = hardwareMap.dcMotor.get("FL");
        FR = hardwareMap.dcMotor.get("FR");
        LA = hardwareMap.dcMotor.get("LA");
        CL = hardwareMap.dcMotor.get("CL");
        CF = hardwareMap.dcMotor.get("CF");

        HK = hardwareMap.servo.get("HK");

        FL.setDirection(DcMotor.Direction.REVERSE);
        BL.setDirection(DcMotor.Direction.REVERSE);
        LA.setDirection(DcMotor.Direction.REVERSE);

        telemetry.addData("Status", "DogeCV 2018.0 - Gold Align Example");

        // Set up detector
        detector = new GoldAlignDetector(); // Create detector
        detector.init(hardwareMap.appContext, CameraViewDisplay.getInstance()); // Initialize it with the app context and camera
        detector.useDefaults(); // Set detector to use default settings

        // Optional tuning
        detector.alignSize = 100; // How wide (in pixels) is the range in which the gold object will be aligned. (Represented by green bars in the preview)
        detector.alignPosOffset = 0; // How far from center frame to offset this alignment zone.
        detector.downscale = 0.4; // How much to downscale the input frames

        detector.areaScoringMethod = DogeCV.AreaScoringMethod.MAX_AREA; // Can also be PERFECT_AREA
        //detector.perfectAreaScorer.perfectArea = 10000; // if using PERFECT_AREA scoring
        detector.maxAreaScorer.weight = 0.005; //

        detector.ratioScorer.weight = 5; //
        detector.ratioScorer.perfectRatio = 1.0; // Ratio adjustment

        detector.enable(); // Start the detector!
        telemetry.addData("IsAligned", detector.getAligned());//Seeing if the gold is aligned with the robot.
        telemetry.addData("X Pos", detector.getXPosition());//Getting X position of the gold. (Help with the alignment)

        waitForStart();
        //THOMAS ONLY WORK FROM HERE BELOW TO THE "detector.disable", no changes needed above.
        LA.setPower(0.5);//Linear Actuator Raising.
        Thread.sleep(2500);//If you want to go farther down increase this by a tiny bit.
        HK.setPosition(0.7);//Hook opening.
        Thread.sleep(2250);//If you want the hook to open more increase this by A LITTLE BIT.
        RightTurn(0.5, 10);
        if (detector.isFound())// Gold aligned either center or right.
        {
            if(detector.getXPosition() > 600)//Finding the x position if the block is found, if the x position. You may have to mess around with the 600 if it's not noticing it on the right.
            {
                RightTurn(0.5,500);//Turning a bit more to the right to make sure it's aligned.
                FBMove(1,800);//Moving forwards to HOPEFULLY hit the block.
            }
            else//If the block is in the center it will move to this if the x position found on the phone is less then 600.
            {
                LeftTurn(0.3,350);//Turning to the left
                FBMove(1,800);//Moving forward to hit the center block.
            }
        }
        else// If the gold is not aligned on the center or right then it must be on the left.
        {
            LeftTurn(0.5,100);//Aligning to the left.
            if(detector.isFound())//Checking to see if the gold block is there, if needed add an else for motion either farther left or to the right to fix alignment.
            {
            FBMove(0.5,500);//forward motion to push the gold block if it is on the left.
            }
            //If needed add the else here.
        }

        detector.disable();
    }

    public void FBMove(double power, int target)//Forward and backwards motion, target is encoder values.
    {
        ResetEncoders();
        AllRunToPos();
        FL.setTargetPosition(target);
        while (FL.isBusy()) {
            FL.setPower(power);
            BL.setPower(power);
            FR.setPower(power);
            BR.setPower(power);
        }
        RunWithoutEncoders();
    }

    public void RaiseAndLower(double power, int target)//Linear Actuator Motion
    {
        ResetEncoders();
        LA.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        LA.setTargetPosition(target);
        while(LA.isBusy())
        {
            LA.setPower(power);
        }
        LA.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void AllRunToPos()//Setting motors to the mode RUN_TO_POSITION (encoder position/ value)
    {
        FL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void ResetEncoders()//Resets all the encoder's values, make sure to set the mode of the motor back to RUN_TO_POSITION
    {
        FL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LA.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        CL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        CF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void RunWithoutEncoders()//Makes it so the motor can run to position, doesn't require you to add this if you are making a motor run for power for time.
    {
        FL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void RightTurn(double power, int target)//Made for the robot to turn right, if the robot turns left when using RightTurn, make the FR and BR positive and the FL and BL negative.
    {
        ResetEncoders();
        AllRunToPos();
        FL.setTargetPosition(target);
        while (FL.isBusy()) {
            FL.setPower(power);
            BL.setPower(power);
            FR.setPower(-power);
            BR.setPower(-power);
        }
        RunWithoutEncoders();
    }

    public void LeftTurn(double power, int target)//Made for the robot to turn left, if the robot turns right when using LeftTurn, make the FL and BL positive and FR and BR negative.
    {
        ResetEncoders();
        AllRunToPos();
        FL.setTargetPosition(target);
        while (FL.isBusy()) {
            FL.setPower(-power);
            BL.setPower(-power);
            FR.setPower(power);
            BR.setPower(power);
        }
        RunWithoutEncoders();
    }
}
