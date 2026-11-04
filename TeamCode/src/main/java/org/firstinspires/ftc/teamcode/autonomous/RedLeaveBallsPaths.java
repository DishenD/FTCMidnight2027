package org.firstinspires.ftc.teamcode.autonomous;

import com.pedropathing.api.Paths;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;

public class RedLeaveBallsPaths {

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(59.2, 10.3, 90);
    private final Pose path1 = poseFactory.of(59.2, 30, 90);

    public Path path1() {
        return Paths.line(start, path1).linear(start, path1);
    }
}