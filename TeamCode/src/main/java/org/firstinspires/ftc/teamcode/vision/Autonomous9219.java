package org.firstinspires.ftc.teamcode.vision;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

@Autonomous(name = "Autonomous9219")
@Disabled
public class Autonomous9219 extends LinearOpMode {
    MasterVision vision;
    SampleRandomizedPositions goldPosition;

    //Declaring Motors
    public DcMotor LeftTrack;
    public DcMotor RightTrack;

    //Declaring Servos
    public Servo Hook;

    @Override
    public void runOpMode() throws InterruptedException
    {
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;// recommended camera direction
        parameters.vuforiaLicenseKey = "AXudchL/////AAABmaroK78zg0ZFpS1V9FRQ779Ts9dWY6EpHQGLt6dKWb8ocrUgdPeJ+/Ps1eVUW08uXKxF9Vt5LjTBuGHK1Pr7SN9yoDYZ86up4KUkiIEFpBInbKzipptJvUNXHEVA6trUzg3QDZBSctZPE4dJkK7PV6F4S2idWsWzMoyDP4SDIg6Tgo7JcK3HadqRIofDjjeRB0tM5IWhOE1DhPZ4U+OR5Y82mfnPqAYVLvX3EnhPejOsR8Q3Z9a7BQVJIdZtdZCEeWkaLb1jUYROvngXqYOTnQ4oUYtmPydrVCbLQgBMXaemwx64/A2pEGSIk4IPB86p/mLEISN/+1iDcpsz0NwYGF2knXWZvSD1KQSWPNO4ALv6\n";

        vision = new MasterVision(parameters, hardwareMap, true, MasterVision.TFLiteAlgorithm.INFER_NONE);
        vision.init();// enables the camera overlay. this will take a couple of seconds
        vision.enable();// enables the tracking algorithms. this might also take a little time

        //Initializing Motors
        LeftTrack = hardwareMap.dcMotor.get("LeftTrack");
        RightTrack = hardwareMap.dcMotor.get("RightTrack");

        //Reversing Motor/Track
        LeftTrack.setDirection(DcMotor.Direction.REVERSE);

        //Initializing Servos
        Hook = hardwareMap.servo.get("Hook");

        telemetry.addData("Mode", "waiting");
        telemetry.update();

        waitForStart();

        telemetry.addData("Mode", "running");
        telemetry.update();

        vision.disable();// disables tracking algorithms. this will free up your phone's processing power for other jobs.

        goldPosition = vision.getTfLite().getLastKnownSampleOrder();

        telemetry.addData("goldPosition was", goldPosition);// giving feedback

        switch (goldPosition) { // using for things in the autonomous program
            case LEFT:
                telemetry.addLine("going to the left");
                TankRight(1,150);
                break;

            case CENTER:
                telemetry.addLine("going straight");
                TankForward(1,100);
                break;

            case RIGHT:
                telemetry.addLine("going to the right");
                TankLeft(1,150);
                break;

            default:
                telemetry.addLine("staying put");
        }
        telemetry.update();
        vision.shutdown();
    }

    public void TankForward(double power, int target) {
        ResetEncoders();
        LeftTrack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RightTrack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        LeftTrack.setTargetPosition(target);

        while (LeftTrack.isBusy()) {
            LeftTrack.setPower(power);
            RightTrack.setPower(power);
        }

        LeftTrack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        RightTrack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void ResetEncoders()
    {
        LeftTrack.setMode((DcMotor.RunMode.STOP_AND_RESET_ENCODER));
        RightTrack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
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
}
