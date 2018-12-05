package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name="Auto") //Just an Autonomous shell, for practice and setup. (Actual autonomous is the vision java).
@Disabled
public class Auto extends LinearOpMode
{
    //Declaring Motors
    public DcMotor LeftTrack;
    public DcMotor RightTrack;

    //Declaring Servo
    public Servo Hook;

    //Declaring Color Sensors
    //public ColorSensor ColorSensor;

    public void runOpMode() throws InterruptedException
    {

        //Initializing Motors
        LeftTrack = hardwareMap.dcMotor.get("LeftTrack");
        RightTrack = hardwareMap.dcMotor.get("RightTrack");

        //Initializing Color Sensors
        //ColorSensor = hardwareMap.colorSensor.get("ColorSensor");

        //Reversing Motors
        LeftTrack.setDirection(DcMotor.Direction.REVERSE);

        //Initializing Servos
        Hook = hardwareMap.servo.get("Hook");

        telemetry.addData("Mode", "waiting");
        telemetry.update();

        waitForStart();

        //TankBackward requires a positive power (already set to negative in methods)

        telemetry.addData("Mode", "running");
        telemetry.update();


        }


    public void TankForward(double power, int target)
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

    public void TankBackward(double power,int target)
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

    public void TankLeft(double power,int time) throws InterruptedException
    {
        LeftTrack.setPower(-power);
        RightTrack.setPower(power);
        Thread.sleep(time);
    }

    public void TankRight(double power,int time) throws InterruptedException
    {
        LeftTrack.setPower(power);
        RightTrack.setPower(-power);
        Thread.sleep(time);
    }

    public void ResetEncoders()
    {
        LeftTrack.setMode((DcMotor.RunMode.STOP_AND_RESET_ENCODER));
        RightTrack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

}
