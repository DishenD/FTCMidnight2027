package org.firstinspires.ftc.teamcode.subsystems;

import com.bylazar.configurables.annotations.IgnoreConfigurable;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.opencv.core.Mat;

import java.util.Timer;

public class Shooter extends SubsystemBase {
    private double TICKS_PER_REV = 28;
    private DcMotorEx shooterMotor;
    double targetRPM = 3100;
    double increments = 100;
    boolean isRunning = false;
    double maxRPM = 4500;
    double rampRate = 4000; //80 rpm per loop 0.02 seconds
    double bangGap = 150;
    double readyWindow = 50;
    double readyTime = 0.2;
    double P = 58;
    double F = 17.3;
    double profiledRPM = 0;
    private ElapsedTime loopTimer = new ElapsedTime(); //time between loops
    private ElapsedTime readyTimer = new ElapsedTime(); //time spent in 50 window
    boolean wheelReady = false;

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
        double dt = Math.min(loopTimer.seconds(), 0.1);
        loopTimer.reset();
        double currentRPM = shooterMotor.getVelocity() / TICKS_PER_REV * 60;
        double power = 0;
        if(isRunning) {
            double maxStep = rampRate * dt;
            if (profiledRPM < targetRPM) {
                profiledRPM = Math.min(profiledRPM + maxStep, targetRPM);
            }
            else {
                profiledRPM = Math.max(profiledRPM - maxStep, targetRPM);
            }

            double error = profiledRPM - currentRPM; //negative equals over and positive is under

            if (error > bangGap) {
                power = 1;
            }
            else {
                power = (F * ((profiledRPM * TICKS_PER_REV)/60) + P * ((error * TICKS_PER_REV)/60)) / 32767;
            }
            power = Range.clip(power, 0, 1);
            shooterMotor.setPower(power);

        }
        else {
            shooterMotor.setPower(0);
        }

        boolean inWindow = isRunning && profiledRPM == targetRPM && Math.abs(targetRPM - currentRPM) < readyWindow;
        if (inWindow && !wheelReady) {
            readyTimer.reset();
        }
        wheelReady = inWindow;

        telemetryM.addData("Shooter Target RPM", targetRPM);
        telemetryM.addData("Shooter Current RPM", Math.round(currentRPM));
        telemetryM.addData("Shooter Motor Velocity", shooterMotor.getVelocity());
        telemetryM.addData("Shooter Running", isRunning);
        telemetryM.addData("Shooter Power", power);
        telemetryM.addData("Shooter Profiled RPM", Math.round(profiledRPM));
    }

    public void start() {
        if (!isRunning) {
            profiledRPM = shooterMotor.getVelocity() / TICKS_PER_REV * 60;
        }
        isRunning = true;
    }

    public void stop() {
        isRunning = false;
    }
    public void toggle() {
        if (isRunning) {
            stop();
        }
        else {
            start();
        }
    }
    public void increasePower() {
        targetRPM = Range.clip(targetRPM + increments, 0, maxRPM);

    }
    public void decreasePower() {
        targetRPM = Range.clip(targetRPM - increments, 0, maxRPM);

    }

    public void setTargetRPM(double rpm) {
        targetRPM = Range.clip(rpm, 0 , maxRPM);
    }

    public void setPIDF(double P, double F) {
        this.P = P;
        this.F = F;
    }

    public boolean isAtSpeed() {
        return wheelReady && readyTimer.seconds() >= readyTime;
    }

}
