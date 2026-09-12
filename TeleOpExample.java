package org.firstinspires.ftc.teamcode.code.Workshop;

// ==================== IMPORTS ====================
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.Range;


// ==================== TELEOP ====================
@TeleOp(name="TeleOpExample", group="Linear Opmode")
public class TeleOpExample extends LinearOpMode {

    // ==================== HARDWARE VARIABLES ====================
    // Declare actuators variables
    private DcMotorEx Intake;
    private DcMotorEx leftMotor;
    private DcMotorEx rightMotor;

    // ==================== STATE VARIABLES ====================
    // Declare state variables
    double speed = 0;

    // ==================== OPMODE ====================
    @Override
    public void runOpMode() {

        // ==================== INITIALIZATION ====================
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // ==================== HARDWARE MAPPING ====================
        // Initialize & mapping hardware variables.
        leftMotor = hardwareMap.get(DcMotorEx.class, "leftMotor");
        rightMotor = hardwareMap.get(DcMotorEx.class, "rightMotor");
        Intake = hardwareMap.get(DcMotorEx.class, "Intake");

        // ==================== MOTOR DIRECTION ====================
        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        leftMotor.setDirection(DcMotor.Direction.REVERSE);
        rightMotor.setDirection(DcMotor.Direction.FORWARD);


        // ==================== WAIT FOR START ====================
        waitForStart();

        // ==================== MAIN ROBOT LOOP ====================
        while (opModeIsActive()) {

            // ==================== BOOSTER ====================
            if (gamepad1.right_bumper) {
                speed = 1.0;
            } else {
                speed = 0.5;
            }

            // ==================== GAMEPAD INPUT ====================
            double drive = gamepad1.left_stick_y * speed;
            double turn = gamepad1.left_stick_x * speed;

            // ==================== ARCADE DRIVE ====================
            double leftPower =
                    Range.clip(drive - turn, -1.0, 1.0);

            double rightPower =
                    Range.clip(drive + turn, -1.0, 1.0);

            // ==================== MOTOR OUTPUT ====================
            leftMotor.setPower(leftPower);
            rightMotor.setPower(rightPower);


            // ==================== INTAKE CONTROL ====================
            // Gamepad Input
            if (gamepad1.a) {
                Intake.setPower(1);
            } else if (gamepad1.b) {
                Intake.setPower(-1);
            } else {
                Intake.setPower(0);
            }

            // ==================== TELEMETRY ====================
            telemetry.addLine("=== DRIVETRAIN ===");
            telemetry.addData("Speed", "%.1f", speed);
            telemetry.addData("Drive", "%.2f", drive);
            telemetry.addData("Turn", "%.2f", turn);
            telemetry.addData("Left Motor", "%.2f", leftPower);
            telemetry.addData("Right Motor", "%.2f", rightPower);
            telemetry.addLine();

            telemetry.update();
        }
    }
}
