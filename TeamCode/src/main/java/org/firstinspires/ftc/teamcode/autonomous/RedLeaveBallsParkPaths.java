package org.firstinspires.ftc.teamcode.autonomous;
import static com.pedropathing.api.Paths.*;
import com.pedropathing.api.Paths;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import org.firstinspires.ftc.teamcode.pedro.Constants;

public class RedLeaveBallsParkPaths {

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(59.2, 10.3, 270);
    private final Pose path1 = poseFactory.of(59.2, 30, 270);
    private final Pose point2 = poseFactory.of(29, 30, 270);
    private final Pose point3 = poseFactory.of(29, 46.5, 180);
    private final Pose point3Flower = poseFactory.of(12, 46.5, 180);
    private final Pose point4 = poseFactory.of(29, 46.5, 180);
    private final Pose point5 = poseFactory.of(11, 97, 180);

    public Path path1() {
        return Paths.line(start, path1).linear(start, path1);
    }

    public Path path2() {
        return Paths.line(path1, point2).constant(point2);
    }

    public Path path3() {
        return Paths.line(point2, point3).constant(point3);
    }

    public Path path3Flower() {
        return Paths.line(point3, point3Flower).constant(point3Flower).with(Constants.foresightConfig.maxPathSpeed.at(15.0));
    }

    public Path path4() {
        return Paths.line(point3Flower, point4).constant(point4);
    }

    public Path path5() {
        return Paths.line(point4, point5).constant(point5);
    }
}
