package org.firstinspires.ftc.teamcode.testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;




@TeleOp
public class BallIntake extends LinearOpMode {

    private DcMotorEx intakeMotor;
    private DcMotorEx transferMotor;

    @Override
    public void runOpMode() {

         intakeMotor = hardwareMap.get(DcMotorEx.class, "intakeMotor");
         transferMotor = hardwareMap.get(DcMotorEx.class, "transferMotor");

        transferMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        intakeMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        transferMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        transferMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();

        while(opModeIsActive()){
            if(gamepad1.right_trigger_pressed){
                intakeMotor.setPower(0.3);
                transferMotor.setPower(0.3);
            }
            else {
                intakeMotor.setPower(0);
                transferMotor.setPower(0);
            }

            telemetry.addData("intakeMotor Power", intakeMotor.getPower());
            telemetry.addData("TransferMotor Power", transferMotor.getPower());
            telemetry.update();
        }

        intakeMotor.setPower(0);
        transferMotor.setPower(0);

    }

}
