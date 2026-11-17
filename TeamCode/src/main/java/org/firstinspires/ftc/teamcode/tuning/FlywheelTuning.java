package org.firstinspires.ftc.teamcode.tuning;

import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.subsystems.Shooter;

@TeleOp
public class FlywheelTuning extends OpMode {

   private Shooter shooter;
   private DcMotorEx intakeMotor;
   private DcMotorEx transferMotor;
   private DcMotorEx transferMotorTwo;
   public double highVelocity = 1493;

   public double lowVelocity = 1000;

   double curTargetVelocity = highVelocity;

   double F = 16.3;
   double P = 0;

   double[] stepSizes = {10.0, 1.0, 0.1, 0.01, 0.001, 0.0001};

   int stepIndex = 0;

   static final double TICKS_PER_REV = 28;

   private TelemetryManager telemetryM;



    @Override
    public void init() {
        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
        shooter = new Shooter(hardwareMap, telemetryM);

        transferMotor = hardwareMap.get(DcMotorEx.class, "transferMotor");
        transferMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        transferMotor.setDirection(DcMotorEx.Direction.REVERSE);

        transferMotorTwo = hardwareMap.get(DcMotorEx.class, "transferMotorTwo");
        transferMotorTwo.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        transferMotorTwo.setDirection(DcMotorEx.Direction.REVERSE);

        intakeMotor = hardwareMap.get(DcMotorEx.class, "intakeMotor");
        intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intakeMotor.setDirection(DcMotorEx.Direction.FORWARD);

        shooter.start();
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
            transferMotorTwo.setPower(1);
        }
        else {
            intakeMotor.setPower(0);
            transferMotor.setPower(0);
            transferMotorTwo.setPower(0);
        }

        shooter.setPIDF(P, F);
        shooter.setTargetRPM(curTargetVelocity / TICKS_PER_REV * 60);
        shooter.periodic();

        telemetry.addData("Target Velocity", curTargetVelocity);
        telemetry.addLine("--------------------------------");
        telemetry.addData("Tuning P", "%.4f (D-Pad U/D)", P);
        telemetry.addData("Tuning F", "%.4f (D-Pad L/R)", F);
        telemetry.addData("Step Size", "%.4f (B Button)", stepSizes[stepIndex]);

        telemetryM.update(telemetry);
    }
}
