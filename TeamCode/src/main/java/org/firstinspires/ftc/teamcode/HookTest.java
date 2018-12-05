package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp(name="Hook Test")
@Disabled
public class HookTest extends LinearOpMode
{
    //Declaring Hook Servo
    public Servo Hook;

    public void runOpMode() throws InterruptedException
    {
        //Initializing Hook Servo
        Hook = hardwareMap.servo.get("Hook");

        waitForStart();

        while (opModeIsActive())
        {
            if (gamepad1.a) {

                Hook.setPosition(0);
            }
            
            if (gamepad1.b) {
                
                Hook.setPosition(1);
            }
            
            telemetry.addData("ServoPosition", Hook.getPosition());
            
            idle();
        }
    }
}