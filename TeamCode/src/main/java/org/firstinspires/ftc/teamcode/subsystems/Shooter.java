package org.firstinspires.ftc.teamcode.subsystems;

import com.bylazar.configurables.annotations.IgnoreConfigurable;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

public class Shooter extends SubsystemBase {
    private double TICKS_PER_REV = 28;
    private DcMotorEx shooterMotor;
    double targetRPM = 1500;
    double increments = 100;
    boolean isRunning = false;
    double P = 50;
    double F = 14.5;
    double maxRPM = 4500;


    @IgnoreConfigurable
    private TelemetryManager telemetryM;

    public Shooter(HardwareMap hMap, TelemetryManager telemetryM) {
        shooterMotor = hMap.get(DcMotorEx.class, "shooterMotor");
        shooterMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        shooterMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        shooterMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        PIDFCoefficients pidfCoefficients = new PIDFCoefficients(P, 0, 0, F);
        shooterMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);

        this.telemetryM = telemetryM;
    }

    @Override
    public void periodic() {

        double currentRPM = shooterMotor.getVelocity() / TICKS_PER_REV * 60;
        if(isRunning) {
            shooterMotor.setVelocity(targetRPM * TICKS_PER_REV / 60); //conversion to ticks per second which is what set velocity accepts
        }
        else {
            shooterMotor.setPower(0);
        }
        telemetryM.addData("Shooter Target RPM", targetRPM);
        telemetryM.addData("Shooter Current RPM", Math.round(currentRPM));
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
        targetRPM = Range.clip(targetRPM + increments, 0, maxRPM);

    }
    public void decreasePower() {
        targetRPM = Range.clip(targetRPM - increments, 0, maxRPM);

    }

}
