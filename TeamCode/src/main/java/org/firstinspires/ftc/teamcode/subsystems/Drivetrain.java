package org.firstinspires.ftc.teamcode.subsystems;

import com.bylazar.configurables.annotations.IgnoreConfigurable;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.seattlesolvers.solverslib.command.SubsystemBase;

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

        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontRight.setDirection(DcMotor.Direction.FORWARD);
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        rightRear.setDirection(DcMotor.Direction.FORWARD);
        leftRear.setDirection(DcMotor.Direction.REVERSE);

        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightRear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftRear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        this.telemetryM = telemetryM;

    }

    public void drive(double x, double y, double rx) {
            double denominator = Math.max(Math.abs(x) + Math.abs(y) + Math.abs(rx), 1);
        frontLeft.setPower((y + x + rx) / denominator);
        frontRight.setPower((y - x - rx) / denominator);
        rightRear.setPower((y + x - rx) / denominator);
        leftRear.setPower((y - x + rx) / denominator);
    }

    public void stop() {
        frontLeft.setPower(0);
        frontRight.setPower(0);
        leftRear.setPower(0);
        rightRear.setPower(0);
    }

    @Override
    public void periodic() {
        telemetryM.addData("Front Right Motor Power", frontRight.getPower());
        telemetryM.addData("Right Rear Motor Power", rightRear.getPower());
        telemetryM.addData("Left Rear Motor Power", leftRear.getPower());
        telemetryM.addData("Front Left Motor Power", frontLeft.getPower());
    }




}
