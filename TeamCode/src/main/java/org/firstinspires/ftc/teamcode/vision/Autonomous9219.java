package org.firstinspires.ftc.teamcode.vision;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

@Autonomous(name = "Autonomous9219")
public class Autonomous9219 extends LinearOpMode {
    MasterVision vision;
    SampleRandomizedPositions goldPosition;

    //Declaring Motors
    public DcMotor LF;
    public DcMotor RF;
    public DcMotor LB;
    public DcMotor RB;
    public DcMotor Lift;

    //Declaring Servos
    public Servo Hook;

    @Override
    public void runOpMode() throws InterruptedException
    {
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;// recommended camera direction
        parameters.vuforiaLicenseKey = "AXudchL/////AAABmaroK78zg0ZFpS1V9FRQ779Ts9dWY6EpHQGLt6dKWb8ocrUgdPeJ+/Ps1eVUW08uXKxF9Vt5LjTBuGHK1Pr7SN9yoDYZ86up4KUkiIEFpBInbKzipptJvUNXHEVA6trUzg3QDZBSctZPE4dJkK7PV6F4S2idWsWzMoyDP4SDIg6Tgo7JcK3HadqRIofDjjeRB0tM5IWhOE1DhPZ4U+OR5Y82mfnPqAYVLvX3EnhPejOsR8Q3Z9a7BQVJIdZtdZCEeWkaLb1jUYROvngXqYOTnQ4oUYtmPydrVCbLQgBMXaemwx64/A2pEGSIk4IPB86p/mLEISN/+1iDcpsz0NwYGF2knXWZvSD1KQSWPNO4ALv6\n";

        vision = new MasterVision(parameters, hardwareMap, false, MasterVision.TFLiteAlgorithm.INFER_NONE);
        vision.init();// enables the camera overlay. this will take a couple of seconds
        vision.enable();// enables the tracking algorithms. this might also take a little time

        //Initializing Motors
        LF = hardwareMap.dcMotor.get("LeftFront");
        LB = hardwareMap.dcMotor.get("LeftBack");
        RF = hardwareMap.dcMotor.get("RightFront");
        RB = hardwareMap.dcMotor.get("RightBack");
        Lift = hardwareMap.dcMotor.get("Arm");

        //Reversing Motor/Track
        LF.setDirection(DcMotor.Direction.REVERSE);
        LB.setDirection(DcMotor.Direction.REVERSE);
        Lift.setDirection(DcMotor.Direction.REVERSE);

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


        LiftRaise(1, 1000);
        Hook.setPosition(1);
        LiftLower(1, 1000);

        //phone must be horizontal and the camera on the left side.
        switch (goldPosition) { // using for things in the autonomous program
            case LEFT:
                telemetry.addLine("going to the left");
                TankLeft(1, 500);
                TankForward(1, 1500);
                break;

            case CENTER:
                telemetry.addLine("going straight");
                TankForward(1,2500);
                break;

            case RIGHT:
                telemetry.addLine("going to the right");
                TankRight(1,500);
                TankForward(1,1500);
                break;

            case UNKNOWN:
                telemetry.addLine("not moving");
                Thread.sleep(5000);
            break;
        }

        telemetry.update();
        vision.shutdown();
    }

    public void LiftRaise(double power, int target)
    {
        ResetEncoders();
        Lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Lift.setPower(power);
        Lift.setTargetPosition(target);
    }

    public void LiftLower(double power, int target)
    {
        ResetEncoders();
        Lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Lift.setPower(power);
        Lift.setTargetPosition(target);
    }

    public void TankForward(double power, int target)
    {
        ResetEncoders();
        LF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        LB.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RB.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        LF.setTargetPosition(target);


        while (LF.isBusy()) {
            LF.setPower(power);
            LB.setPower(power);
            RF.setPower(power);
            RB.setPower(power);
        }

        LF.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        LB.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        RF.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        RB.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void ResetEncoders()
    {
        LF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void TankBackward(double power,int target)
    {
        ResetEncoders();
        LF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        LB.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RB.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        LF.setTargetPosition(target);

        while(LF.isBusy()) {
            LF.setPower(-power);
            LB.setPower(-power);
            RF.setPower(-power);
            RB.setPower(-power);
        }
    }

    public void TankLeft(double power,int time) throws InterruptedException
    {
        LF.setPower(-power);
        LB.setPower(-power);
        RF.setPower(power);
        RF.setPower(power);
        Thread.sleep(time);
    }

    public void TankRight(double power,int time) throws InterruptedException
    {
        LF.setPower(power);
        LB.setPower(power);
        RF.setPower(-power);
        RB.setPower(-power);
        Thread.sleep(time);
    }
}