//This is the Tank Drive example code meant to show you exatly how you could code your robot to use a Tank Drive
// This entire document is for your use and can be copy and pasted directly into android studios

package decode.teamcode;
// This is sating a package of coding tools and functions that we will be importing information from.
// this package is proved by first inspires FTC to simplify the coding process for FTC Robotics.
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
//These are loops, hardware, and robotcore functions we will be importing to use.
// All of these tools are nearly always used in FTC coding.

@TeleOp(name="Summer Camp Example Code (TankDrive)", group="Linear OpMode")
// This line is simply stating the name of the code and what group of code it falls under

public class SummerCampExample extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor frontleftDrive = null;
    private DcMotor frontrightDrive = null;
    private DcMotor backleftDrive = null;
    private DcMotor backrightDrive = null;
    // In this section you must insert the device names you will be using for the rest of the code for each motor
    // these statements are simply stating the mechanisms you are using in your code, and what their names on the code are
    // They also track how many times they are used and can be left clicked on to find the usages in the code.

    @Override
    public void runOpMode() {
        // This function says when the TelOp is running whatever is in the {} is happening.
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        // Telemetry sends information to the driver station to be viewed, in this situation the telemetry is showing on the driver station that the TeleOP has been initiated.

        frontleftDrive = hardwareMap.get(DcMotor.class, "insert_name");
        frontrightDrive = hardwareMap.get(DcMotor.class, "insert_name");
        backleftDrive = hardwareMap.get(DcMotor.class, "insert_name");
        backrightDrive = hardwareMap.get(DcMotor.class, "insert_name");
        // These statements are defining the names of your motors in the code, and what motor they refer to on the robot.
        // You will have to change the device names to match whatever the device is named on the drive station.
        // for example this code is saying that rightDrive in the code refers to right_drive as defined on the driver station.

        frontleftDrive.setDirection(DcMotor.Direction.REVERSE);
        frontrightDrive.setDirection(DcMotor.Direction.FORWARD);
        backleftDrive.setDirection(DcMotor.Direction.REVERSE);
        backrightDrive.setDirection(DcMotor.Direction.FORWARD);
        // This statement defines that the motors on the left side go in reverse, and that the right drive motors goes forward.
        // This is because the motors facing in opposite directions (right and left) would both consider forward to be a different direction.
        // Hence, motors on one side must have it's direction reversed so both sides of the robot drive in the same direction.

        waitForStart();
        // This statement simply defines that none of the bellow functions will activate until the start button is pressed.

        runtime.reset();
        // This resets the onscreen runtime counter, making it start from 0 every time you start the robot.
        while (opModeIsActive()) {
            // This function acts as a loop that states, as long as TeleOp is active the enclosed statements are active.
            // you should also notice that this loop goes down as far as line 79, as shown by the straight grey line down to the } that ends the loop function.

            double leftPower;
            double rightPower;
            // These lines float statements that will be used for later

            double driveLeft = gamepad1.left_stick_y;
            double driveRight = gamepad1.right_stick_y;
            // These lines define that the statements driveLeft and driveRight correspond with the y values of the two joysticks

            leftPower = Range.clip(driveLeft, -1.0, 1.0);
            rightPower = Range.clip(driveRight, -1.0, 1.0);
            //  These lines define that left and right power equals the driveLeft and driveRight values with a max and minimum range.

            frontleftDrive.setPower(leftPower);
            frontrightDrive.setPower(rightPower);
            backleftDrive.setPower(leftPower);
            backrightDrive.setPower(rightPower);
            // This sets the power of the motors to correspond to how much you move the stick
            // The further forward you move either stick, the faster the motors will run

            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
            telemetry.update();
            // This code makes the robot send telemetry data about the current runtime and motor power to the driver station.
        }
    }
}
// These last few lines are all the long standing loop endings, You are able to track which loop these ending }s belong to by following the grey lines that connect to them.