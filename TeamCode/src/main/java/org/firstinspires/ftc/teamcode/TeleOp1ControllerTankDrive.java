package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp(name="TeleOp1ControllerTankDrive")
public class TeleOp1ControllerTankDrive extends LinearOpMode
{

    //Declaring Motors
    public DcMotor LeftTrack;
    public DcMotor RightTrack;

    public void runOpMode() throws InterruptedException
    {

        //Initializing Motors
        LeftTrack = hardwareMap.dcMotor.get("LeftTrack");
        RightTrack = hardwareMap.dcMotor.get("RightTrack");

        //Reversing Motors
        RightTrack.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        while (opModeIsActive())
        {
            LeftTrack.setPower(gamepad1.left_stick_y);
            RightTrack.setPower(gamepad1.right_stick_y);

            idle();
        }
    }
}

