package org.firstinspires.ftc.teamcode.autonomous;

import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.subsystems.FlowerServo;
import org.firstinspires.ftc.teamcode.subsystems.Gate;
import org.firstinspires.ftc.teamcode.subsystems.Hood;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.Transfer;


@Autonomous(name = "Red Leave Balls Flower")
public class RedLeaveBallsFlower extends OpMode {

    private Follower follower;
    private Pose startPose = new Pose(59.2, 10.3, Math.toRadians(270));
    private RedLeaveBallsFlowerPaths paths = new RedLeaveBallsFlowerPaths();
    private int pathState = 0;
    private TelemetryManager telemetryM;
    private Shooter shooter;
    private Intake intake;
    private Hood hood;
    private Gate gate;
    private FlowerServo flowerServo;
    private Transfer transfer;
    private ElapsedTime timer = new ElapsedTime();

    @Override
    public void init() {
        follower = Constants.create(hardwareMap);
        follower.setPose(startPose);
        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
        shooter = new Shooter(hardwareMap, telemetryM);
        shooter.setTargetRPM(3300);
        intake = new Intake(hardwareMap, telemetryM);
        hood = new Hood(hardwareMap, telemetryM);
        gate = new Gate(hardwareMap, telemetryM);
        transfer = new Transfer(hardwareMap, telemetryM);
        flowerServo = new FlowerServo(hardwareMap, telemetryM);
        gate.closed();
        hood.high();
        flowerServo.up();
        shooter.setPIDF(50, 17.3);
    }

    @Override
    public void loop() {
        follower.update();
        shooter.periodic();
        switch (pathState) {
            case 0:
                shooter.start();
                timer.reset();
                pathState = 1;
                break;

            case 1:
                if (shooter.isAtSpeed() || timer.seconds() > 3.5) {
                    intake.start();
                    transfer.start();
                    gate.open();
                    timer.reset();
                    pathState = 2;
                }
                break;

            case 2:
                if (timer.seconds() > 4) {
                    gate.closed();
                    transfer.stop();
                    intake.stop();
                    timer.reset();
                    shooter.stop();
                    pathState = 3;
                }
                break;

            case 3:
                follower.follow(paths.path1());
                pathState = 4;
                break;

            case 4:
                if (!follower.isBusy()) {
                    follower.follow(paths.path2());
                    pathState = 5;
                }
                break;

            case 5:
                if (!follower.isBusy()) {
                    follower.follow(paths.path3());
                    flowerServo.down();
                    intake.start();
                    transfer.start();
                    pathState = 6;
                }
                break;

            case 6:
                if (!follower.isBusy()) {
                    timer.reset();
                    pathState = 7;
                }
                break;


            case 7:
                if (timer.seconds() > 1.5) {
                    intake.stop();
                    transfer.stop();
                    flowerServo.up();
                    follower.follow(paths.path4());
                    shooter.start();
                    timer.reset();
                    pathState = 8;
                }
                break;


            case 8:
                if (!follower.isBusy() && (shooter.isAtSpeed() || timer.seconds() > 5)) {
                    intake.start();
                    transfer.start();
                    gate.open();
                    pathState = 9;
                    timer.reset();
                }
                break;

            case 9:
                if (timer.seconds() > 4) {
                    intake.stop();
                    transfer.stop();
                    shooter.stop();
                    gate.closed();
                    follower.follow(paths.path5());
                    pathState = 10;
                }
                break;

        }

        telemetryM.update(telemetry);
    }
}
