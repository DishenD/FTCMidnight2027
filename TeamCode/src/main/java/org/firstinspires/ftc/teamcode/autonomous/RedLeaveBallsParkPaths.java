package org.firstinspires.ftc.teamcode.autonomous;
import static com.pedropathing.api.Paths.*;
import com.pedropathing.api.Paths;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;

public class RedLeaveBallsParkPaths {

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(59.2, 10.3, 270);
    private final Pose path1 = poseFactory.of(59.2, 30, 270);
    private final Pose point2 = poseFactory.of(34, 30, 270);
    private final Pose point3 = poseFactory.of(15, 47, 180);
    private final Pose point3Control1 = poseFactory.of(38, 49, 0);
    private final Pose point4 = poseFactory.of(13, 100, 180);
    private final Pose point4Control1 = poseFactory.of(44, 60, 0);

    public Path path1() {
        return Paths.line(start, path1).linear(start, path1);
    }

    public Path path2() {
        return Paths.line(path1, point2).constant(point2);
    }

    public Path path3() {
        return Paths.curve(point2, point3Control1, point3).constant(point3);
    }

    public Path path4() {
        return Paths.curve(point3, point4Control1, point4).constant(point4);
    }
}
