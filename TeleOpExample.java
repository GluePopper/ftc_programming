package org.firstinspires.ftc.teamcode.code.Workshop;

//Imports
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.Range;


@TeleOp(name="TeleOpExample", group="Linear Opmode")
public class TeleOpExample extends LinearOpMode {

    //Declare actuators variables
    private DcMotorEx Intake;
    private DcMotorEx leftMotor;
    private DcMotorEx rightMotor;

    //Declare state variables
    double speed = 0;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize & mapping hardware variables.
        leftMotor = hardwareMap.get(DcMotorEx.class, "leftMotor");
        rightMotor = hardwareMap.get(DcMotorEx.class, "rightMotor");
        Intake = hardwareMap.get(DcMotorEx.class, "Intake");

        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        leftMotor.setDirection(DcMotor.Direction.REVERSE);
        rightMotor.setDirection(DcMotor.Direction.FORWARD);


        waitForStart();

        while (opModeIsActive()) {
            // Booster
            if (gamepad1.right_bumper) {
                speed = 1.0;
            } else {
                speed = 0.5;
            }

            // Gamepad Input
            double drive = gamepad1.left_stick_y * speed;
            double turn = gamepad1.left_stick_x * speed;

            // Arcade Drive
            double leftPower =
                    Range.clip(drive - turn, -1.0, 1.0);

            double rightPower =
                    Range.clip(drive + turn, -1.0, 1.0);

            // Motor Output
            leftMotor.setPower(leftPower);
            rightMotor.setPower(rightPower);
            
            
            //Gamepad Input
            if (gamepad1.a) {
                Intake.setPower(1);
            } else if (gamepad1.b) {
                Intake.setPower(-1);
            } else {
                Intake.setPower(0);
            }

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
