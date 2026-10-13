package org.firstinspires.ftc.teamcode.testing;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.constants.DrivetrainConstants;
@TeleOp
public class TestOp extends OpMode {

    private DcMotorEx frontRight;
    private DcMotorEx frontLeft;
    private DcMotorEx rightRear;
    private DcMotorEx leftRear;

    @Override
    public void init() {

        frontRight = hardwareMap.get(DcMotorEx.class, DrivetrainConstants.fRMotorID);
        frontLeft = hardwareMap.get(DcMotorEx.class, DrivetrainConstants.fLMotorID);
        rightRear = hardwareMap.get(DcMotorEx.class, DrivetrainConstants.bRMotorID);
        leftRear = hardwareMap.get(DcMotorEx.class, DrivetrainConstants.bLMotorID);

        frontRight.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        frontLeft.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        rightRear.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        leftRear.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        frontRight.setDirection(DcMotor.Direction.FORWARD);
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        rightRear.setDirection(DcMotorSimple.Direction.FORWARD);
        leftRear.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void loop() {
        if (gamepad1.triangle) {
            frontRight.setPower(0.3);
        }
        else {
            frontRight.setPower(0);
        }
        if (gamepad1.circle) {
            frontLeft.setPower(0.3);
        }
        else {
            frontLeft.setPower(0);
        }
        if (gamepad1.cross) {
            rightRear.setPower(0.3);
        }
        else {
            rightRear.setPower(0);
        }
        if (gamepad1.square) {
            leftRear.setPower(0.3);
        }
        else {
            leftRear.setPower(0);
        }
    }
}
