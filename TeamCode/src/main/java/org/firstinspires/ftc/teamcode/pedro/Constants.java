package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.PinpointConfig;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {

    public static MecanumConfig drivetrainConfig = new MecanumConfig(c -> {
        c.frontLeftName.set("frontLeft");
        c.frontRightName.set("frontRight");
        c.backLeftName.set("leftRear");
        c.backRightName.set("rightRear");
        c.frontLeftDirection.set(DcMotorSimple.Direction.FORWARD);
        c.frontRightDirection.set(DcMotorSimple.Direction.REVERSE);
        c.backLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.backRightDirection.set(DcMotorSimple.Direction.FORWARD);
    });

    public static PinpointConfig localizerConfig = new PinpointConfig(c -> {
        c.name.set("pinpoint");
        c.podType.set(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        c.xPodOffset.set(5.535298745463214);
        c.yPodOffset.set(3.5888299416369343);
        c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.REVERSED);
        c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.REVERSED);
        c.globalDistanceUnit.set(DistanceUnit.INCH);
        c.offsetUnits.set(DistanceUnit.INCH);
    });

    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.5815195600621188);
                Controller secondaryTranslationalForward = Controller.proportional(0.21485593895413163);
                Controller primaryTranslationalLateral = Controller.proportional(0.938097855148543);
                Controller secondaryTranslationalLateral = Controller.proportional(0.3466020910410419);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.02467805136285429));
                c.brake.set(Controller.proportionalFeedforward(0.02097634365842615));

                c.headingFeedback.set(Controller.proportional(6.800143338767741));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.06709704012419827, 0.005312507407306227));

                c.linearBrakeCoefficients.set(Matrix.diag(0.13093683599448183, 0.07617836882212697));
                c.quadraticBrakeCoefficients.set(Matrix.diag(0.0023739493053203382, 0.0028684473217001727));

                c.maxAchievableForwardVelocity.set(47.77318163565194);
                c.maxAchievableStrafeVelocity.set(39.609213101677696);
                c.naturalForwardDeceleration.set(21.129879750435517);
                c.naturalStrafeDeceleration.set(42.97736904578639);
                c.maxPathSpeed.set(30.0);
                c.holdPointTranslationalScaling.set(0.3);
                c.holdPointHeadingScaling.set(0.25);
            }
    );

    public static Follower create(HardwareMap h) {
        return new Follower(new PinpointLocalizer(h, localizerConfig), new Mecanum(h, drivetrainConfig), new Foresight(foresightConfig));
    }
}
