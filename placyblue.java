package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "park in warehouse after placing block for blue")
public class placyblue extends LinearOpMode {
    DcMotor motor;
    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;
    DcMotor clawmotor;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException{
        motor = hardwareMap.dcMotor.get("motor");
        clawmotor = hardwareMap.dcMotor.get("claw");
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        backRight = hardwareMap.dcMotor.get("backRight");

        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();
        claw(-1);
        chassis(-100,100,-100,100, 2, 0.5);
        chassis(1500,1500, -1500, -1500, 2, 0.5);
        lift(1, 3);
        chassis(-800,800,-800,800,2, 0.5);
        claw(1);
        claw(-1);
        chassis(200,-200,200,-200,2, 0.5);
        chassis(1000,1000,1000,1000, 2, 0.5);
        chassis(-3500,3500,-3500,3500, 2, 1);
    }



    public void lift(int upordown, int liftpoints) {
        double speed2 = 0.25*upordown;
        motor.setPower(speed2);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 1.5*liftpoints)) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }
        motor.setPower(0);
    }
    public void chassis(double fl, double fr, double bl, double br, int timeoutS, double speed) {
        int ntfl;
        int ntfr;
        int ntbl;
        int ntbr;
        double speed2 = speed;
        if (opModeIsActive()) {
            ntfl = frontLeft.getCurrentPosition() + (int) fl;
            ntfr = frontRight.getCurrentPosition() + (int) fr;
            ntbl = backLeft.getCurrentPosition() + (int) bl;
            ntbr = backRight.getCurrentPosition() + (int) br;
            frontLeft.setTargetPosition(ntfl);
            frontRight.setTargetPosition(ntfr);
            backLeft.setTargetPosition(ntbl);
            backRight.setTargetPosition(ntbr);
            frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontLeft.setPower(speed2);
            frontRight.setPower(speed2);
            backLeft.setPower(speed2);
            backRight.setPower(speed2);

            runtime.reset();
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (frontLeft.isBusy() && frontRight.isBusy() &&
                    backLeft.isBusy() && backRight.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Goal",  "Running to %7d :%7d :%7d :%7d", ntfl,  ntfr, ntbl, ntbr);
                telemetry.addData("Actual distance moved",  "Running at %7d :%7d :%7d :%7d",
                        frontLeft.getCurrentPosition(),
                        frontRight.getCurrentPosition(), backLeft.getCurrentPosition(), backRight.getCurrentPosition());
                telemetry.update();
            }


            frontLeft.setPower(0);
            frontRight.setPower(0);
            backLeft.setPower(0);
            backRight.setPower(0);
            frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }
    public void claw(int openorclose){
        clawmotor.setPower((openorclose)*0.25);
        runtime.reset();
        while(runtime.seconds() < 1){
            telemetry.addData("Open or close", openorclose);
            telemetry.update();
        }
        clawmotor.setPower(0);
    }
}
