package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp(name="Color Hue Test")
@Disabled
public abstract class ColorSensorSetup extends LinearOpMode
{
    //Declaring the color sensor
    private ColorSensor ColorSensor;

    public void runOpMode() throws InterruptedException
    {
        //Initializing ColorSensor
        ColorSensor = hardwareMap.colorSensor.get("ColorSensor");


        ColorSensor.argb();

        waitForStart();
        while (opModeIsActive());
        {
            //Telemetry of color sensor
            telemetry.addData("Color Sensor Value", ColorSensor.getI2cAddress());
            telemetry.update();
        }
    }
}
