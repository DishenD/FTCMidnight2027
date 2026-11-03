package org.firstinspires.ftc.teamcode.testing;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
import com.seattlesolvers.solverslib.hardware.servos.ServoEx;


@TeleOp
public class ServoTest extends OpMode {

    private Servo servo;

    double servoIncrement = 0.01;
    double servoPower = 0.5;

    @Override
    public void init() {

        servo = hardwareMap.get(Servo.class, "servo");
    }



    public void loop() {
        if (gamepad1.circleWasPressed()) {
            servoPower = servoPower + servoIncrement;
        }

        if (gamepad1.squareWasPressed()) {
            servoPower = servoPower - servoIncrement;
        }

        servoPower = Range.clip(servoPower, 0, 1);

        if (gamepad1.triangle) {
            servo.setPosition(servoPower);
        }

        telemetry.addData("servo Position", servo.getPosition());
        telemetry.addData("servo Power", servoPower);
        telemetry.update();
    }
}
