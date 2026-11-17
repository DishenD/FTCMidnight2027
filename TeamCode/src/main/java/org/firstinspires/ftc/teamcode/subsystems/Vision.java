package org.firstinspires.ftc.teamcode.subsystems;

import com.bylazar.configurables.annotations.IgnoreConfigurable;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.constants.GlobalConstants;

public class Vision extends SubsystemBase {

    private Limelight3A limelight;
    private double targetTx = 0;
    private boolean targetFound = false;
    private boolean aiming = false;
    private GlobalConstants.AllianceColor alliance;
    public static double AIM_KP = 0.02;
    public static double TARGET_TX = 0;
    public static double AIM_DEADBAND = 1.0;
    public static double AIM_MAX_TURN = 0.5;


    @IgnoreConfigurable
    private TelemetryManager telemetryM;

    public Vision(HardwareMap hMap, TelemetryManager telemetryM, GlobalConstants.AllianceColor alliance) {

        limelight = hMap.get(Limelight3A.class, "limelight");
        this.telemetryM = telemetryM;
        this.alliance = alliance;
        limelight.pipelineSwitch(0);
        limelight.start();
    }

    public boolean isTagForAlliance(int id) {
        switch (alliance) {
            case RED:
                return id == 37 || id == 33;
            case BLUE:
                return id == 45 || id == 41;
        }
        return false;
    }


    public boolean hasTarget() {
        return targetFound;
    }

    public double getTx() {
        return targetTx;
    }

    public double getAimTurn() {
        if (!targetFound) {
            return 0;
        }
        double error = targetTx - TARGET_TX;
        if (Math.abs(error) < AIM_DEADBAND) {
            return 0;
        }
        double rawTurn = error * AIM_KP;
        return Range.clip(rawTurn, -AIM_MAX_TURN, AIM_MAX_TURN);
    }

    public void toggleAiming() {
        aiming = !aiming;
    }

    public boolean isAiming() {
        return aiming;
    }


    @Override
    public void periodic() {
        targetFound = false;

        LLResult result = limelight.getLatestResult();

        if (result != null && result.isValid()) {
            for (LLResultTypes.FiducialResult fr : result.getFiducialResults()) {
                int id = fr.getFiducialId();
                double tx = fr.getTargetXDegrees();

                if (isTagForAlliance(id) && ((!targetFound) || Math.abs(tx) < Math.abs(targetTx))) {
                    targetTx = tx;
                    targetFound = true;
                }
            }
        }

        telemetryM.addData("Is Target Found", targetFound);
        telemetryM.addData("X Values", targetTx);
        telemetryM.addData("Is Aiming On?", aiming);
    }

}
