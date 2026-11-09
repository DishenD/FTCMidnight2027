package org.firstinspires.ftc.teamcode.testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;




@TeleOp
public class BallIntake extends LinearOpMode {

    private DcMotorEx intakeMotor;
    private DcMotorEx transferMotorTwo;

    @Override
    public void runOpMode() {

         intakeMotor = hardwareMap.get(DcMotorEx.class, "intakeMotor");
         transferMotorTwo = hardwareMap.get(DcMotorEx.class, "transferMotor");

        transferMotorTwo.setDirection(DcMotorSimple.Direction.REVERSE);
        intakeMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        transferMotorTwo.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        transferMotorTwo.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();

        while(opModeIsActive()){
            if(gamepad1.right_trigger_pressed){
                intakeMotor.setPower(1);
                transferMotorTwo.setPower(1);
            }
            else {
                intakeMotor.setPower(0);
                transferMotorTwo.setPower(0);
            }

            telemetry.addData("intakeMotor Power", intakeMotor.getPower());
            telemetry.addData("TransferMotor Power", transferMotorTwo.getPower());
            telemetry.update();
        }

        intakeMotor.setPower(0);
        transferMotorTwo.setPower(0);

    }

}
