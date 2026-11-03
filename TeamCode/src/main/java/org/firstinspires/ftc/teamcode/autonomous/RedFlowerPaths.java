package org.firstinspires.ftc.teamcode.autonomous;

import static com.pedropathing.api.Paths.*;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.interpolator.Interpolator;

public class RedFlowerPaths {

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(56, 8.5, 90);
    private final Pose path1 = poseFactory.of(56.3, 32.2, 90);
    private final Pose point2 = poseFactory.of(14.5, 44.3, 160);
    private final Pose point2Segment1Start = poseFactory.of(14.5, 44.3, 90);
    private final Pose point2Segment1End = poseFactory.of(14.5, 44.3, 160);
    private final Pose point2Segment2Heading = poseFactory.of(14.5, 44.3, 160);
    private final Pose point3 = poseFactory.of(56.3, 32.2, 90);

    public Path path1() {
        return line(start, path1).constant(path1);
    }

    public Path path2() {
        return line(path1, point2).heading(Interpolator.piecewise().until(0.3003, Interpolator.linear(point2Segment1Start, point2Segment1End)).until(1, Interpolator.constant(point2Segment2Heading)));
    }

    public Path path3() {
        return line(point2, point3).linear(point2, point3);
    }
}
