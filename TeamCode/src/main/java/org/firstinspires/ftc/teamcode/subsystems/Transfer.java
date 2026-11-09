package org.firstinspires.ftc.teamcode.subsystems;

import com.bylazar.configurables.annotations.IgnoreConfigurable;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Transfer extends SubsystemBase {

    private DcMotorEx transferMotor;
    private DcMotorEx transferMotorTwo;

    @IgnoreConfigurable
    private TelemetryManager telemetryM;

    public Transfer(HardwareMap hMap, TelemetryManager telemetryM) {
        transferMotor = hMap.get(DcMotorEx.class, "transferMotor");
        transferMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        transferMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        transferMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        transferMotorTwo = hMap.get(DcMotorEx.class, "transferMotorTwo");
        transferMotorTwo.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        transferMotorTwo.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        transferMotorTwo.setDirection(DcMotorSimple.Direction.REVERSE);


        this.telemetryM = telemetryM;
    }

    @Override
    public void periodic() {
        telemetryM.addData("Transfer Motor Velocity", transferMotor.getVelocity());
        telemetryM.addData("Transfer Motor Power", transferMotor.getPower());
        telemetryM.addData("Transfer Motor Two Velocity", transferMotorTwo.getVelocity());
        telemetryM.addData("Transfer Motor Two Power", transferMotorTwo.getPower());

    }

    public void stop() {
        transferMotor.setPower(0);
        transferMotorTwo.setPower(0);
    }

    public void start() {
        transferMotor.setPower(1);
        transferMotorTwo.setPower(1);
    }

    public void reverse() {
        transferMotorTwo.setPower(-1);
        transferMotor.setPower(-1);
    }

}
