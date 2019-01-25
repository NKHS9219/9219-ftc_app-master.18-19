package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="TeleOp1ControllerStandardDrive")
public class TeleOp1ControllerStandardDrive extends LinearOpMode
{

    //Declaring Motors
    public DcMotor BR;
    public DcMotor BL;
    public DcMotor FL;
    public DcMotor FR;
    public DcMotor LA;
    public DcMotor CG;
    public DcMotor CL;

    //Declaring Servos
    public Servo HK;
    public Servo MD;

    public void runOpMode() throws InterruptedException
    {

        //Initializing Motors
        BR = hardwareMap.dcMotor.get("BR");
        BL = hardwareMap.dcMotor.get("BL");
        FL = hardwareMap.dcMotor.get("FL");
        FR = hardwareMap.dcMotor.get("FR");
        LA = hardwareMap.dcMotor.get("LA");
        CG = hardwareMap.dcMotor.get("CG");
        CL = hardwareMap.dcMotor.get("CL");

        //Initializing Servos
        HK = hardwareMap.servo.get("HK");
        MD = hardwareMap.servo.get("MD");

        //Reversing Motors
        BR.setDirection(DcMotor.Direction.REVERSE);
        FR.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        while (opModeIsActive())
        {
            FL.setPower(gamepad1.left_stick_y+gamepad1.right_stick_x);
            BL.setPower(gamepad1.left_stick_y+gamepad1.right_stick_x);
            BR.setPower(gamepad1.left_stick_y-gamepad1.right_stick_x);
            FR.setPower(gamepad1.left_stick_y-gamepad1.right_stick_x);
            if(gamepad1.x)
            {
                MD.setPosition(.1);
            }
            else
            {
                MD.setPosition(0);
            }
            if (gamepad1.y)
            {
                HK.setPosition(0.3);
            }
            else
            {
                HK.setPosition(0);
            }
            if(gamepad1.dpad_left)
            {
                MD.setDirection(Servo.Direction.REVERSE);
            }
            else
            {
                MD.setDirection(Servo.Direction.FORWARD);
            }
            if(gamepad1.dpad_right)
            {
                HK.setDirection(Servo.Direction.REVERSE);
            }
            else
            {
                HK.setDirection(Servo.Direction.FORWARD);
            }
            if(gamepad1.dpad_up)
            {
                CL.setPower(1);
            }
            else
            {
                CL.setPower(0);
            }
            if(gamepad1.dpad_down)
            {
                CL.setPower(-1);
            }
            else
            {
                CL.setPower(0);
            }
            if(gamepad1.left_bumper)
            {
                CG.setPower(1);
            }
            else
                {
                    CG.setPower(0);
                }
                if (gamepad1.right_bumper)
                {
                    CG.setPower(-1);
                }
                else
                {
                    CG.setPower(0);
                }
            if(gamepad1.a)
            {
                LA.setPower(1);
            }
            else
                {
                    LA.setPower(0);
                }
            if(gamepad1.b)
            {
                LA.setPower(-1);
            }
            else
                {
                    LA.setPower(0);
                }
            idle();
        }
    }
}

