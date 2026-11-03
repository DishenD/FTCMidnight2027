package org.firstinspires.ftc.teamcode.autonomous;


import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedro.Constants;

@Autonomous
public class RedFlower extends OpMode {

    private Follower follower;
    private Pose startPose = new Pose(56, 8.5, Math.toRadians(90));
    private RedFlowerPaths paths = new RedFlowerPaths();
    private int pathState = 0;

    @Override
    public void init() {
        follower = Constants.create(hardwareMap);
        follower.setPose(startPose);


    }


    @Override
    public void loop() {
        follower.update();
        switch(pathState) {
            case 0:
                follower.follow(paths.path1());
                pathState = 1;
                break;

            case 1:
                if(!follower.isBusy()) {
                    follower.follow(paths.path2());
                    pathState = 2;
                }
                break;

            case 2:
                if(!follower.isBusy()) {
                    follower.follow(paths.path3());
                    pathState = 3;
                }
                break;

            case 3:
                if(!follower.isBusy()) {
                    pathState = 4;
                }
                break;
        }

    }
}
