package org.firstinspires.ftc.teamcode.subsystems;

import com.bylazar.configurables.annotations.IgnoreConfigurable;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.constants.DrivetrainConstants;

public class Drivetrain extends SubsystemBase {

    private DcMotor frontRight;

    private DcMotor frontLeft;

    private DcMotor rightRear;

    private DcMotor leftRear;

    private Follower follower;

    private IMU imu;

    @IgnoreConfigurable
    private TelemetryManager telemetryM;
    public Drivetrain (HardwareMap hMap, TelemetryManager telemetryM) {

       frontRight = hMap.get(DcMotor.class, DrivetrainConstants.fRMotorID);
        frontLeft = hMap.get(DcMotor.class, DrivetrainConstants.fLMotorID);
        rightRear = hMap.get(DcMotor.class, DrivetrainConstants.bRMotorID);
        leftRear = hMap.get(DcMotor.class, DrivetrainConstants.bLMotorID);

        imu = hMap.get(IMU.class, "imu");
        imu.initialize(new IMU.Parameters(new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.UP, RevHubOrientationOnRobot.UsbFacingDirection.LEFT)));


        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontRight.setDirection(DcMotor.Direction.REVERSE);
        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        rightRear.setDirection(DcMotor.Direction.FORWARD);
        leftRear.setDirection(DcMotor.Direction.REVERSE);

        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightRear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftRear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        this.telemetryM = telemetryM;

    }

    public void drive(double x, double y, double rx) {
        double heading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
        double rotX = x * Math.cos(-heading) - y * Math.sin(-heading);
        rotX = rotX * 1.1;
        double rotY = x * Math.sin(-heading) + y * Math.cos(-heading);
        double denominator = Math.max(Math.abs(rotX) + Math.abs(rotY) + Math.abs(rx), 1);
        frontLeft.setPower((rotY + rotX + rx) / denominator);
        frontRight.setPower((rotY - rotX - rx) / denominator);
        rightRear.setPower((rotY + rotX - rx) / denominator);
        leftRear.setPower((rotY - rotX + rx) / denominator);
    }

    public void stop() {
        frontLeft.setPower(0);
        frontRight.setPower(0);
        leftRear.setPower(0);
        rightRear.setPower(0);
    }

    public void resetHeading() {
        imu.resetYaw();
    }

    @Override
    public void periodic() {
        telemetryM.addData("Front Right Motor Power", frontRight.getPower());
        telemetryM.addData("Right Rear Motor Power", rightRear.getPower());
        telemetryM.addData("Left Rear Motor Power", leftRear.getPower());
        telemetryM.addData("Front Left Motor Power", frontLeft.getPower());
        telemetryM.addData("Heading", imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
    }




}
