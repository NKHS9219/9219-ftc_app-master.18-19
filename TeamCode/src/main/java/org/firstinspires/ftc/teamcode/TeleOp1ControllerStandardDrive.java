package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp(name="TeleOp1ControllerStandardDrive")
public class TeleOp1ControllerStandardDrive extends LinearOpMode
{

    //Declaring Motors
    public DcMotor LF;
    public DcMotor RF;
    public DcMotor LB;
    public DcMotor RB;


    public void runOpMode() throws InterruptedException
    {

        //Initializing Motors
        LF = hardwareMap.dcMotor.get("LeftFront");
        RF = hardwareMap.dcMotor.get("RightFront");
        LB = hardwareMap.dcMotor.get("LeftBack");
        RB = hardwareMap.dcMotor.get("Rightback");

        //Reversing Motors
        RF.setDirection(DcMotor.Direction.REVERSE);
        RB.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        while (opModeIsActive())
        {
            LF.setPower(gamepad1.left_stick_y-gamepad1.right_stick_x);
            LB.setPower(gamepad1.left_stick_y-gamepad1.right_stick_x);
            RF.setPower(gamepad1.left_stick_y+gamepad1.right_stick_x);
            RB.setPower(gamepad1.left_stick_y+gamepad1.right_stick_x);

            idle();
        }
    }
}

