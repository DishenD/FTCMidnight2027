package org.firstinspires.ftc.teamcode.subsystems;

import com.bylazar.configurables.annotations.IgnoreConfigurable;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;
import com.seattlesolvers.solverslib.command.SubsystemBase;

public class Intake extends SubsystemBase {

    private DcMotorEx intakeMotor;
    double intakePower = 1;
    double outtakePower = -1;


    @IgnoreConfigurable
    private TelemetryManager telemetryM;

    public Intake(HardwareMap hMap, TelemetryManager telemetryM) {

        intakeMotor = hMap.get(DcMotorEx.class, "intakeMotor");
        intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intakeMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        this.telemetryM = telemetryM;

    }

    @Override
    public void periodic() {
        telemetryM.addData("Intake Motor Velocity", intakeMotor.getVelocity());
        telemetryM.addData("Intake Motor Power", intakeMotor.getPower());
    }

    public void stop() {
        intakeMotor.setPower(0);
    }

    public void start() {
        intakePower = Range.clip(intakePower, -1, 1);
        intakeMotor.setPower(intakePower);
    }

    public void reverse() {
        outtakePower = Range.clip(outtakePower, -1, 1);
        intakeMotor.setPower(outtakePower);
    }
}
