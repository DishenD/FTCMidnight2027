package org.firstinspires.ftc.teamcode.subsystems;

import com.bylazar.configurables.annotations.IgnoreConfigurable;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.SubsystemBase;

public class FlowerServo extends SubsystemBase {

    private Servo flowerServo;
    private double flowerServoUp = 0.46;
    private double flowerServoDown = 0.08;
    private boolean isFlowerServoDown = false;

    @IgnoreConfigurable
    private TelemetryManager telemetryM;

    public FlowerServo(HardwareMap hMap, TelemetryManager telemetryM) {

        flowerServo = hMap.get(Servo.class, "flowerServo");

        this.telemetryM = telemetryM;
        flowerServo.setPosition(flowerServoUp);
    }

    public void periodic() {
        telemetryM.addData("Flower Servo Position", flowerServo.getPosition());
    }


    public void up() {
        flowerServo.setPosition(flowerServoUp);
        isFlowerServoDown = false;
    }

    public void down() {
        flowerServo.setPosition(flowerServoDown);
        isFlowerServoDown = true;
    }

    public void toggle() {
        if (isFlowerServoDown) {
            up();
        }
        else {
            down();
        }
    }

}
