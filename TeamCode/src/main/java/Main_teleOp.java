/* Copyright (c) 2021 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name="Basic: Omni Linear OpMode", group="Linear OpMode")
public class Main_teleOp extends LinearOpMode {

    // Declare OpMode members for each of the 4 motors.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor frontLeftDrive = null;
    private DcMotor backLeftDrive = null;
    private DcMotor frontRightDrive = null;
    private DcMotor backRightDrive = null;
    private DcMotorEx shootingmotorleft = null;
    private DcMotorEx shootingmotorright = null;
    private Servo shootingservo = null;
//    private CRServo shootingservo = null;

    @Override
    public void runOpMode() {

        // Initialize the hardware variables. Note that the strings used here must correspond
        // to the names assigned during the robot configuration step on the DS or RC devices.
        frontLeftDrive = hardwareMap.get(DcMotor.class, "frontleft_drive");
        frontRightDrive = hardwareMap.get(DcMotor.class, "frontright_drive");
        backLeftDrive = hardwareMap.get(DcMotor.class, "backleft_drive");
        backRightDrive= hardwareMap.get(DcMotor.class, "backright_drive");
        shootingmotorleft= hardwareMap.get(DcMotorEx.class, "shooting_motor_left");
        shootingmotorright= hardwareMap.get(DcMotorEx.class, "shooting_motor_right");
        shootingservo= hardwareMap.get(Servo.class, "shooting_servo");

        shootingmotorleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        shootingmotorright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        shootingmotorleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        shootingmotorright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        frontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        backLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        frontRightDrive.setDirection(DcMotor.Direction.FORWARD);
        backRightDrive.setDirection(DcMotor.Direction.FORWARD);

        shootingmotorleft.setDirection(DcMotor.Direction.REVERSE);
        shootingmotorright.setDirection(DcMotor.Direction.FORWARD);

        frontLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior. BRAKE);
        backLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior. BRAKE);

        shootingmotorleft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        shootingmotorright.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior. BRAKE);

        // Wait for the game to start (driver presses START)
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            double max;


            double axial = -gamepad1.left_stick_y / 2;  // Note: pushing stick forward gives negative value
            double lateral = gamepad1.left_stick_x / 2;
            double yaw = gamepad1.right_stick_x / 2;


            double frontLeftPower = axial + lateral + yaw;
            double frontRightPower = axial - lateral - yaw;
            double backLeftPower = axial - lateral + yaw;
            double backRightPower = axial + lateral - yaw;


            max = Math.max(Math.abs(frontLeftPower), Math.abs(frontRightPower));
            max = Math.max(max, Math.abs(backLeftPower));
            max = Math.max(max, Math.abs(backRightPower));

            if (max > 1.0) {
                frontLeftPower /= max;
                frontRightPower /= max;
                backLeftPower /= max;
                backRightPower /= max;
            }

            // Send calculated power to wheels
            frontLeftDrive.setPower(frontLeftPower);
            frontRightDrive.setPower(frontRightPower);
            backLeftDrive.setPower(backLeftPower);
            backRightDrive.setPower(backRightPower);

            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Front left/Right", "%4.2f, %4.2f", frontLeftPower, frontRightPower);
            telemetry.addData("Back  left/Right", "%4.2f, %4.2f", backLeftPower, backRightPower);
            telemetry.addData("Encoder: FL", frontLeftDrive.getCurrentPosition());
            telemetry.addData("Encoder: FR", frontRightDrive.getCurrentPosition());
            telemetry.addData("Encoder: BL", backLeftDrive.getCurrentPosition());
            telemetry.addData("Encoder: BL", backRightDrive.getCurrentPosition());

            telemetry.update();

//            if (gamepad2.a) {
//                shooting_servo.setPower(.5);
//            } else if (gamepad2.y) {
//                shooting_servo.setPower(-.5);
//            } else shooting_servo.setPower(0);

            if (gamepad2.left_stick_y > 0.1) {
                shootingmotorleft.setVelocity(1700);
                shootingmotorright.setVelocity(1700);
            } else if (gamepad2.left_stick_y< -0.1) {
                shootingmotorleft.setVelocity(1600);
                shootingmotorright.setVelocity(1600);
            } else {shootingmotorright.setVelocity(0); shootingmotorleft.setVelocity(0);}

            if (gamepad2.a){
                shootingservo.setPosition(0);
            }else shootingservo.setPosition(.4);

        }
    }}
