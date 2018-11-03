package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="Auto")
public class Auto extends LinearOpMode
{
    //Declaring Motors
    private DcMotor LeftTrack;
    private DcMotor RightTrack;

    public void runOpMode() throws InterruptedException
    {

        //Initializing Motors
        LeftTrack = hardwareMap.dcMotor.get("LeftTrack");
        RightTrack = hardwareMap.dcMotor.get("RightTrack");

        //Reversing Motors
        RightTrack.setDirection(DcMotor.Direction.REVERSE);

        telemetry.addData("Mode", "waiting");
        telemetry.update();

        waitForStart();

        //TankBackward requires a positive power (already set to negative in methods)

        telemetry.addData("Mode", "running");
        telemetry.update();

        ResetEncoders();
        TankForward(0.5, 1000);
        Thread.sleep(1000);
        TankBackward(0.5, 1000);
        Thread.sleep(1000);
        TankForward(1,1000);
        Thread.sleep(1000);
        TankBackward(1,1000);
        Thread.sleep(1000);
        TankForward(0.5,500);
        Thread.sleep(1000);
        TankBackward(.5,500);
        Thread.sleep(1000);
        TankForward(1,500);
        Thread.sleep(1000);
        TankBackward(1,500);
        }


    private void TankForward(double power, int target)
    {
        ResetEncoders();
        LeftTrack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RightTrack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        LeftTrack.setTargetPosition(target);

        while(LeftTrack.isBusy()) {
            LeftTrack.setPower(power);
            RightTrack.setPower(power);
        }

        LeftTrack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        RightTrack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    private void TankBackward(double power,int target)
    {
        ResetEncoders();
        LeftTrack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RightTrack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        LeftTrack.setTargetPosition(target);

        while(LeftTrack.isBusy()) {
            LeftTrack.setPower(-power);
            RightTrack.setPower(-power);
        }
    }

    private void ResetEncoders()
    {
        LeftTrack.setMode((DcMotor.RunMode.STOP_AND_RESET_ENCODER));
        RightTrack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

}
