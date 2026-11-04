package org.firstinspires.ftc.teamcode.subsystems;

import com.bylazar.configurables.annotations.IgnoreConfigurable;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.SubsystemBase;


public class Gate extends SubsystemBase {

    private Servo gateServo;
    private double gateServoClosed = 0.45;
    private double gateServoOpen = 0.33;

    @IgnoreConfigurable
    private TelemetryManager telemetryM;

    public Gate(HardwareMap hMap, TelemetryManager telemetryM) {

        gateServo = hMap.get(Servo.class, "gateServo");

        this.telemetryM = telemetryM;
        gateServo.setPosition(gateServoClosed);
    }

    @Override
    public void periodic() {
        telemetryM.addData("Gate Servo Position", gateServo.getPosition());
    }

    public void open() {
        gateServo.setPosition(gateServoOpen);
    }

    public void closed() {
        gateServo.setPosition(gateServoClosed);
    }

}
