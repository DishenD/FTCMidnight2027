package org.firstinspires.ftc.teamcode.autonomous;


import com.bylazar.panels.Panels;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.subsystems.Gate;
import org.firstinspires.ftc.teamcode.subsystems.Hood;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.Transfer;


@Autonomous(name = "Red Leave Balls")
public class RedLeaveBalls extends OpMode {

    private Follower follower;
    private Pose startPose = new Pose(59.2, 10.3, Math.toRadians(90));
    private RedLeaveBallsPaths paths = new RedLeaveBallsPaths();
    private int pathState = 0;
    private TelemetryManager telemetryM;
    private Shooter shooter;
    private Intake intake;
    private Hood hood;
    private Gate gate;
    private Transfer transfer;
    private ElapsedTime timer = new ElapsedTime();

    @Override
    public void init(){
        follower = Constants.create(hardwareMap);
        follower.setPose(startPose);
        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
        shooter = new Shooter(hardwareMap, telemetryM);
        intake = new Intake(hardwareMap, telemetryM);
        hood = new Hood(hardwareMap, telemetryM);
        gate = new Gate(hardwareMap, telemetryM);
        transfer = new Transfer(hardwareMap, telemetryM);
        gate.closed();
        hood.high();
    }

    @Override
    public void loop() {
        follower.update();
        shooter.periodic();
        switch (pathState) {
            case 0:
                follower.follow(paths.path1());
                shooter.start();
                pathState = 1;
                break;

            case 1:
                if (!follower.isBusy()) {
                    gate.open();
                    transfer.start();
                    intake.start();
                    timer.reset();
                    pathState = 2;
                }
                break;

            case 2:
                if (timer.seconds() > 1.5) {
                    gate.closed();
                    intake.stop();
                    transfer.stop();
                    shooter.stop();
                    pathState = 3;
                }
                break;
        }

        telemetryM.update(telemetry);
    }

}
