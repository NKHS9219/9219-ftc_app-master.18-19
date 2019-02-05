package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name ="AutonomousCrater")
public class AutonomousCrater extends LinearOpMode {


    //Declaring Motors
    public DcMotor BR;
    public DcMotor BL;
    public DcMotor FL;
    public DcMotor FR;
    public DcMotor LA;
    public DcMotor CG;
    public DcMotor CL;
    public DcMotor CR;

    //Declaring Servos
    public Servo HK;

    public void runOpMode()
    {
        //Initializing Motors
        BR = hardwareMap.dcMotor.get("BR");
        BL = hardwareMap.dcMotor.get("BL");
        FL = hardwareMap.dcMotor.get("FL");
        FR = hardwareMap.dcMotor.get("FR");
        LA = hardwareMap.dcMotor.get("LA");
        CG = hardwareMap.dcMotor.get("CG");
        CL = hardwareMap.dcMotor.get("CL");
        CR = hardwareMap.dcMotor.get("CR");

        //Initializing Servos
        HK = hardwareMap.servo.get("HK");

        //Reversing Motors
        BL.setDirection(DcMotor.Direction.REVERSE);
        FL.setDirection(DcMotor.Direction.REVERSE);
        CR.setDirection(DcMotor.Direction.REVERSE);

        telemetry.addData("Back Right Encoder Value", BR.getCurrentPosition());
        telemetry.addData("Back Left Encoder Value", BL.getCurrentPosition());
        telemetry.addData("Front Right Encoder Value", FR.getCurrentPosition());
        telemetry.addData("Front Left Encoder Value", FR.getCurrentPosition());
        telemetry.update();
        waitForStart();


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