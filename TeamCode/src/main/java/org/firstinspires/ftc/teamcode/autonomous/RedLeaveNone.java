package org.firstinspires.ftc.teamcode.autonomous;


import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.subsystems.Gate;
import org.firstinspires.ftc.teamcode.subsystems.Hood;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.Transfer;

@Autonomous
public class RedLeaveNone extends OpMode {

    private Follower follower;
    private Pose startPose = new Pose(60.1, 131, Math.toRadians(270));
    private RedLeaveNonePaths paths = new RedLeaveNonePaths();
    private int pathState = 0;
    private TelemetryManager telemetryM;
    private Intake intake;
    private Shooter shooter;
    private Transfer transfer;
    private Gate gate;
    private Hood hood;
    private ElapsedTime timer = new ElapsedTime();



    @Override
    public void init() {
        follower = Constants.create(hardwareMap);
        follower.setPose(startPose);
        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
        shooter = new Shooter(hardwareMap, telemetryM);
        intake = new Intake(hardwareMap, telemetryM);
        transfer = new Transfer(hardwareMap, telemetryM);
        gate = new Gate(hardwareMap, telemetryM);
        hood = new Hood(hardwareMap, telemetryM);
    }


    @Override
    public void loop() {

    }
}
