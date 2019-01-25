/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

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

    public void runOpMode() throws InterruptedException
    {

        //Initializing Motors
        BR = hardwareMap.dcMotor.get("BR");
        BL = hardwareMap.dcMotor.get("BL");
        FL = hardwareMap.dcMotor.get("FL");
        FR = hardwareMap.dcMotor.get("FR");
        LA = hardwareMap.dcMotor.get("LA");
        CL = hardwareMap.dcMotor.get("CL");

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

        RaiseAndLower(1, 1120);
        if (detector.getXPosition() > 400 && 200 > detector.getXPosition())// Gold aligned close to center
            {
                FBMove(1, 2000);
            }
            else if (detector.getXPosition() > 410)// Gold too far to the right
            {
                FBMove(1,2000);
            }
            else if (detector.getXPosition() < 210)// Gold too far to the left
            {
                FBMove(1,2000);
            }
            else//Gold can't be seen change position
            {
                FBMove(1,2000);
            }

            detector.disable();
    }

    public void FBMove(double power, int target)
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

    public void RaiseAndLower(double power, int target)
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

    public void AllRunToPos()
    {
        FL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void ResetEncoders()
    {
        FL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void RunWithoutEncoders()
    {
        FL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void RightTurn(double power, int target)
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

    public void LeftTurn(double power, int target)
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
