package org.firstinspires.ftc.teamcode.testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class MotorTest extends LinearOpMode {

    @Override
    public void runOpMode() {
        DcMotor frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        DcMotor frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        DcMotor rightRear = hardwareMap.get(DcMotor.class, "rightRear");
        DcMotor leftRear = hardwareMap.get(DcMotor.class, "leftRear");

        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightRear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftRear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();

        while(opModeIsActive()) {
            if (gamepad1.x) {
                frontRight.setPower(1);
            }
            else {
                frontRight.setPower(0);
            }
            if (gamepad1.y) {
                frontLeft.setPower(1);
            }
            else {
                frontLeft.setPower(0);
            }
            if (gamepad1.a) {
                rightRear.setPower(0.3);

            }
            else {
                rightRear.setPower(0);
            }
            if (gamepad1.b) {
                leftRear.setPower(0.3);
            }
            else {
                leftRear.setPower(0);
            }

            telemetry.addData("frontRight power", frontRight.getPower());
            telemetry.addData("frontRight ticks", frontRight.getCurrentPosition());
            telemetry.addData("frontLeft power", frontLeft.getPower());
            telemetry.addData("frontLeft ticks", frontLeft.getCurrentPosition());
            telemetry.addData("rightRear power", rightRear.getPower());
            telemetry.addData("rightRear ticks", rightRear.getCurrentPosition());
            telemetry.addData("leftRear power", leftRear.getPower());
            telemetry.addData("leftRear ticks", leftRear.getCurrentPosition());
            telemetry.update();
        }

        frontRight.setPower(0);
        frontLeft.setPower(0);
        rightRear.setPower(0);
        leftRear.setPower(0);
    }
}
