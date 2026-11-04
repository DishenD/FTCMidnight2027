package org.firstinspires.ftc.teamcode.subsystems;

import android.telecom.TelecomManager;

import com.bylazar.configurables.annotations.IgnoreConfigurable;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.SubsystemBase;

public class Hood extends SubsystemBase {

    private Servo hoodServo;
    private double hoodHigh = 0.43;
    private double hoodLow = 0.13;
    private boolean hoodDown = true;

    @IgnoreConfigurable
    private TelemetryManager telemetryM;

    public Hood(HardwareMap hMap, TelemetryManager telemetryM) {
        hoodServo = hMap.get(Servo.class, "hoodServo");

        this.telemetryM = telemetryM;
        hoodServo.setPosition(hoodLow);
    }

    @Override
    public void periodic(){
        telemetryM.addData("Hood Servo Position", hoodServo.getPosition());
    }


    public void high() {
        hoodServo.setPosition(hoodHigh);
        hoodDown = false;
    }

    public void low() {
        hoodServo.setPosition(hoodLow);
        hoodDown = true;
    }

    public void toggle() {
        if (hoodDown) {
            high();
        }
        else {
            low();
        }
    }

}
