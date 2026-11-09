package org.firstinspires.ftc.teamcode.autonomous;

import static com.pedropathing.api.Paths.*;
import com.pedropathing.api.Paths;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;

public class BlueLeaveBallsPaths {

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(83.6, 131, 90);
    private final Pose path1 = poseFactory.of(83.6, 112, 90);
    private final Pose point2 = poseFactory.of(110,112,90);

    public Path path1() {
        return Paths.line(start, path1).linear(start, path1);
    }
    public Path path2() {return Paths.line(path1, point2).constant(point2);
    }
}

