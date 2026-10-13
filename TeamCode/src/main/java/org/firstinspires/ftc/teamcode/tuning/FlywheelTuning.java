package org.firstinspires.ftc.teamcode.tuning;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

@TeleOp
public class FlywheelTuning extends OpMode {

   public DcMotorEx flywheelMotor;
   private DcMotorEx intakeMotor;
   private DcMotorEx transferMotor;

   public double highVelocity = 1850;

   public double lowVelocity = 1000;

   double curTargetVelocity = highVelocity;

   double F = 14.5;
   double P = 50;

   double[] stepSizes = {10.0, 1.0, 0.1, 0.01, 0.001, 0.0001};

   int stepIndex = 0;

   static final double TICKS_PER_REV = 28;



    @Override
    public void init() {
        flywheelMotor = hardwareMap.get(DcMotorEx.class, "shooterMotor");
        flywheelMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        flywheelMotor.setDirection(DcMotorEx.Direction.REVERSE);

        transferMotor = hardwareMap.get(DcMotorEx.class, "transferMotor");
        transferMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        transferMotor.setDirection(DcMotorEx.Direction.REVERSE);

        intakeMotor = hardwareMap.get(DcMotorEx.class, "intakeMotor");
        intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intakeMotor.setDirection(DcMotorEx.Direction.FORWARD);

        PIDFCoefficients pidfCoefficients = new PIDFCoefficients(P, 0, 0, F);
        flywheelMotor.setPIDFCoefficients(DcMotorEx.RunMode.RUN_USING_ENCODER, pidfCoefficients);

    }


    @Override
    public void loop() {

        if (gamepad1.triangleWasPressed()) {
            if (curTargetVelocity == highVelocity) {
                curTargetVelocity = lowVelocity;
            } else {
                curTargetVelocity = highVelocity;
            }
        }

        if (gamepad1.circleWasPressed()) {
            stepIndex = (stepIndex + 1) % stepSizes.length;
        }

        if (gamepad1.dpadLeftWasPressed()) {
            F -= stepSizes[stepIndex];
        }

        if (gamepad1.dpadRightWasPressed()) {
            F += stepSizes[stepIndex];
        }

        if (gamepad1.dpadUpWasPressed()) {
            P += stepSizes[stepIndex];
        }

        if (gamepad1.dpadDownWasPressed()) {
            P -= stepSizes[stepIndex];
        }

        if (gamepad1.right_trigger > 0.5) {
            intakeMotor.setPower(1);
            transferMotor.setPower(1);
        }
        else {
            intakeMotor.setPower(0);
            transferMotor.setPower(0);
        }

        PIDFCoefficients pidfCoefficients = new PIDFCoefficients(P, 0, 0, F);
        flywheelMotor.setPIDFCoefficients(DcMotorEx.RunMode.RUN_USING_ENCODER, pidfCoefficients);

        flywheelMotor.setVelocity(curTargetVelocity);

        double curVelocity = flywheelMotor.getVelocity();
        double rpm = curVelocity / TICKS_PER_REV * 60;
        double error = curTargetVelocity - curVelocity;

        telemetry.addData("Target Velocity", curTargetVelocity);
        telemetry.addData("Current Velocity", "%.2f", curVelocity);
        telemetry.addData("Error", "%.2f", error);
        telemetry.addData("Current RPM", "%.0f",  rpm);
        telemetry.addData("Target RPM", "%.0f", curTargetVelocity / TICKS_PER_REV * 60);
        telemetry.addLine("--------------------------------");
        telemetry.addData("Tuning P", "%.4f (D-Pad U/D)", P);
        telemetry.addData("Tuning F", "%.4f (D-Pad L/R)", F);
        telemetry.addData("Step Size", "%.4f (B Button)", stepSizes[stepIndex]);

        
    }
}
