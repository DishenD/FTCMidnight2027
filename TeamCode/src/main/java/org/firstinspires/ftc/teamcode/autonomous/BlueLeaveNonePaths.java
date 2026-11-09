package org.firstinspires.ftc.teamcode.autonomous;

import static com.pedropathing.api.Paths.*;
import com.pedropathing.api.Paths;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;

public class BlueLeaveNonePaths {

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(81.2, 10, 270);
    private final Pose path1 = poseFactory.of(81.2, 24.7, 270);

    public Path path1() {
        return Paths.line(start, path1).linear(start, path1);
    }
}