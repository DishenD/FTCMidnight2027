package org.firstinspires.ftc.teamcode.subsystems;

import com.bylazar.configurables.annotations.IgnoreConfigurable;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;
import com.seattlesolvers.solverslib.command.SubsystemBase;

public class Shooter extends SubsystemBase {
    private DcMotorEx shooterMotor;
    double targetPower = 0.5;
    double increments = 0.1;
    boolean isRunning = false;


    @IgnoreConfigurable
    private TelemetryManager telemetryM;

    public Shooter(HardwareMap hMap, TelemetryManager telemetryM) {
        shooterMotor = hMap.get(DcMotorEx.class, "shooterMotor");
        shooterMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        shooterMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        shooterMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        this.telemetryM = telemetryM;
    }

    @Override
    public void periodic() {
        if(isRunning) {
            shooterMotor.setPower(targetPower);
        }
        else {
            shooterMotor.setPower(0);
        }
        telemetryM.addData("Shooter Target Power", targetPower);
        telemetryM.addData("Shooter Motor Velocity", shooterMotor.getVelocity());
        telemetryM.addData("Shooter Running", isRunning);
    }

    public void start() {
        isRunning = true;
    }

    public void stop() {
        isRunning = false;
    }
    public void toggle() {
        isRunning = !isRunning;
    }
    public void increasePower() {
        targetPower = Range.clip(targetPower + increments, 0, 1);
        targetPower = Math.round(targetPower * 10) / 10.0;
    }
    public void decreasePower() {
        targetPower = Range.clip(targetPower - increments, 0, 1);
        targetPower = Math.round(targetPower * 10) / 10.0;
    }

}
