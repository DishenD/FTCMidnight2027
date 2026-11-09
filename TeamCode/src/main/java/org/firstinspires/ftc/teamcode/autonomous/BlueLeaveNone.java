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

@Autonomous(name = "Blue Leave None")
public class BlueLeaveNone extends OpMode {


    private Follower follower;
    private Pose startPose = new Pose(81.2, 10, Math.toRadians(270));
    private BlueLeaveNonePaths paths = new BlueLeaveNonePaths();
    private int pathState = 0;
    private TelemetryManager telemetryM;
    private Intake intake;
    private Shooter shooter;
    private Transfer transfer;
    private Gate gate;
    private Hood hood;
    private ElapsedTime timer = new ElapsedTime();

    public void init() {follower = Constants.create(hardwareMap);
        follower.setPose(startPose);
        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
        shooter = new Shooter(hardwareMap, telemetryM);
        shooter.setTargetRPM(3300);
        intake = new Intake(hardwareMap, telemetryM);
        hood = new Hood(hardwareMap, telemetryM);
        gate = new Gate(hardwareMap, telemetryM);
        transfer = new Transfer(hardwareMap, telemetryM);
        gate.closed();
        hood.high();
        shooter.setPIDF(50, 17.3);
    }

    public void start() {
        timer.reset();
    }


    public void loop() {
        follower.update();
        shooter.periodic();
        switch (pathState) {

            case 0:
                if (timer.seconds() > 15) {
                    shooter.start();
                    timer.reset();
                    pathState = 1;
                }
                break;

            case 1:
                if(shooter.isAtSpeed() || timer.seconds() > 3.5) {
                    gate.open();
                    transfer.start();
                    intake.start();
                    timer.reset();
                    pathState = 2;
                }
                break;

            case 2:
                if (timer.seconds() > 4) {
                    gate.closed();
                    intake.stop();
                    transfer.stop();
                    shooter.stop();
                    pathState = 3;
                }
                break;

            case 3:
                follower.follow(paths.path1());
                pathState = 4;
                break;
        }

        telemetryM.update(telemetry);

    }


}
