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
        c.podType.set(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_SWINGARM_POD);
        c.xPodOffset.set(-7.247);
        c.yPodOffset.set(-4.986);
        c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.REVERSED);
        c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.REVERSED);
        c.globalDistanceUnit.set(DistanceUnit.INCH);
        c.offsetUnits.set(DistanceUnit.INCH);
    });

    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.45613172393241036);
                Controller secondaryTranslationalForward = Controller.proportional(0.1685284839288914);
                Controller primaryTranslationalLateral = Controller.proportional(0.6491348333148451);
                Controller secondaryTranslationalLateral = Controller.proportional(0.23983797570763796);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.013946250871510668));
                c.brake.set(Controller.proportionalFeedforward(0.011854313240784068));

                c.headingFeedback.set(Controller.proportional(6.696369875781155));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.07083744561529191, 0.004634755560512017));

                c.linearBrakeCoefficients.set(Matrix.diag(0.14455711142524671, 0.10534322764899048));
                c.quadraticBrakeCoefficients.set(Matrix.diag(8.44095155824085E-4, 0.0011522514157036611));

                c.maxAchievableForwardVelocity.set(74.64282609860831);
                c.maxAchievableStrafeVelocity.set(59.22447050927069);
                c.naturalForwardDeceleration.set(32.303266290221046);
                c.naturalStrafeDeceleration.set(61.95187301515776);
                c.naturalStrafeDeceleration.set(61.95187301515776);
                c.maxPathSpeed.set(30.0);
                c.holdPointTranslationalScaling.set(0.3);
                c.holdPointHeadingScaling.set(0.25);
            }
    );

    public static Follower create(HardwareMap h) {
        return new Follower(new PinpointLocalizer(h, localizerConfig), new Mecanum(h, drivetrainConfig), new Foresight(foresightConfig));
    }
}
