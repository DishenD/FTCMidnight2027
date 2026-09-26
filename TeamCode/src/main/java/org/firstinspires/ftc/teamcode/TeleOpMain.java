package org.firstinspires.ftc.teamcode;

import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.RunCommand;

import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;

@TeleOp(name = "TeleOp Main", group = "Drive")
public class TeleOpMain extends CommandOpMode {

    private Drivetrain drivetrain;
    private TelemetryManager telemetryM;

    @Override
    public void initialize() {
        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();

        drivetrain = new Drivetrain(hardwareMap, telemetryM);

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

        // One owner for update(). Subsystems only addData(); this sends the
        // batch to Panels and mirrors it to the Driver Hub once per tick.
        schedule(new RunCommand(() -> telemetryM.update(telemetry)));
    }
}
