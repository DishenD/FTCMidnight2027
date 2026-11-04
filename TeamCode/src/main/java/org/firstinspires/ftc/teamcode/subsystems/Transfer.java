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

    @IgnoreConfigurable
    private TelemetryManager telemetryM;

    public Transfer(HardwareMap hMap, TelemetryManager telemetryM) {
        transferMotor = hMap.get(DcMotorEx.class, "transferMotor");
        transferMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        transferMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        transferMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        this.telemetryM = telemetryM;
    }

    @Override
    public void periodic() {
        telemetryM.addData("Transfer Motor Velocity", transferMotor.getVelocity());
        telemetryM.addData("Transfer Motor Power", transferMotor.getPower());
    }

    public void stop() {
        transferMotor.setPower(0);
    }

    public void start() {
        transferMotor.setPower(1);
    }

    public void reverse() {transferMotor.setPower(-1);}

}
