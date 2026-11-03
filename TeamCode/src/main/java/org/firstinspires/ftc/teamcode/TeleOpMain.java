package org.firstinspires.ftc.teamcode;

import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.RunCommand;
import com.seattlesolvers.solverslib.command.button.Trigger;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Hood;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.Transfer;

@TeleOp(name = "TeleOp Main", group = "Drive")
public class TeleOpMain extends CommandOpMode {

    private Drivetrain drivetrain;
    private Intake intake;
    private Transfer transfer;
    private Shooter shooter;
    private Hood hood;

    private TelemetryManager telemetryM;

    @Override
    public void initialize() {
        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();

        drivetrain = new Drivetrain(hardwareMap, telemetryM);
        intake =  new Intake(hardwareMap, telemetryM);
        transfer = new Transfer(hardwareMap, telemetryM);
        shooter = new Shooter(hardwareMap, telemetryM);
        hood = new Hood(hardwareMap, telemetryM);

        GamepadEx driver = new GamepadEx(gamepad1);
        GamepadEx driver2 = new GamepadEx(gamepad2);

        // Default command: what the drivetrain does whenever no other command
        // has claimed it -- i.e. all of TeleOp. The lambda is a supplier, so the
        // sticks are re-read every scheduler tick. Passing the values directly
        // would capture 0.0 here in initialize() and never change.
        //
        // left_stick_y is negated because pushing the stick FORWARD reports
        // NEGATIVE on the gamepad.
        drivetrain.setDefaultCommand(new RunCommand(
                () -> drivetrain.drive(
                        gamepad1.left_stick_x,     // x  -- strafe
                        -gamepad1.left_stick_y,    // y  -- forward
                        gamepad1.right_stick_x     // rx -- turn
                ),
                drivetrain
        ));

        driver2.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(() -> shooter.increasePower());
        driver2.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenPressed(() -> shooter.decreasePower());
        driver.getGamepadButton(GamepadKeys.Button.TRIANGLE).whenPressed(() -> shooter.toggle());
        driver2.getGamepadButton(GamepadKeys.Button.TRIANGLE).whenPressed(() -> hood.toggle());

        new Trigger(() -> driver.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.5)
                .whenActive(() -> {intake.start(); transfer.start(); })
                        .whenInactive(() -> {intake.stop(); transfer.stop(); });


        // One owner for update(). Subsystems only addData(); this sends the
        // batch to Panels and mirrors it to the Driver Hub once per tick.
        schedule(new RunCommand(() -> telemetryM.update(telemetry)));
    }
}
