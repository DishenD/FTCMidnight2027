package org.firstinspires.ftc.teamcode.testing;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

@TeleOp
public class ShooterTest extends OpMode {

    private DcMotorEx shooterMotor;
    private DcMotorEx intakeMotor;
    private DcMotorEx transferMotor;

    double motorIncrement = 0.1;
    double shooterPower = 0;



    @Override
    public void init() {

        shooterMotor = hardwareMap.get(DcMotorEx.class, "shooterMotor");
        shooterMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        shooterMotor.setDirection(DcMotorEx.Direction.REVERSE);
        shooterMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        transferMotor = hardwareMap.get(DcMotorEx.class, "transferMotor");
        transferMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        transferMotor.setDirection(DcMotorEx.Direction.REVERSE);

        intakeMotor = hardwareMap.get(DcMotorEx.class, "intakeMotor");
        intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intakeMotor.setDirection(DcMotorEx.Direction.FORWARD);


    }


    @Override
    public void loop() {

        if (gamepad1.circleWasPressed()) {
            shooterPower = shooterPower + motorIncrement;
        }

        if (gamepad1.squareWasPressed()) {
            shooterPower = shooterPower - motorIncrement;
        }

        shooterPower = Range.clip(shooterPower, -1, 1);

        if (gamepad1.triangle) {
            intakeMotor.setPower(1);
            transferMotor.setPower(1);
        }
        else {
            transferMotor.setPower(0);
            intakeMotor.setPower(0);
        }

        if (gamepad1.right_trigger > 0.5) {
            shooterMotor.setPower(shooterPower);
        }
        else {
            shooterMotor.setPower(0);
        }
        double velocity = shooterMotor.getVelocity();

        telemetry.addData("Shooter Power", shooterPower);
        telemetry.addData("Current Velocity", velocity);
        telemetry.update();
    }
}
