
//Martin's Code

package org.usfirst.frc.team1989.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Timer;

/**
 * The VM is configured to automatically run this class, and to call the
 * 
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends a_cmd {

	int state = 0;

	double driveramp = 6.0;
	int driveTime;

	// Instantiating Timer
	Timer t1 = new Timer();
	Timer autoStraight = new Timer();
	Timer autoTurn = new Timer(); 
	Timer rangeTimer = new Timer();
	Timer test1 = new Timer();

	// Instantiating Servo
	Servo s1 = new Servo(0);

	// Instantiating Joysticks

	JsScaled driveStick = new JsScaled(0);
	// JsScaled uStick = new JsScaled(1);//The uStick will stand for the utility
	// joystick responsible for shooting and arm movement

	// Instantiate RangeFinder
	static AnalogInput rf1 = new AnalogInput(3);

	// Instantiating writmessage
	writemessage wmsg = new writemessage();

	// ArcadeDriveCMD Constructor - 4 motors
	ArcadeDriveCmd aDrive = new ArcadeDriveCmd(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor,
			driveStick);

	Autonomous auto = new Autonomous(driveStick, autoStraight, autoTurn, rangeTimer, test1);
	
	ShooterCmd shooter = new ShooterCmd(driveStick, s1);
	ArmsCmd arms = new ArmsCmd(driveStick);

	ShooterCmd shooter2 = new ShooterCmd(driveStick, s1);
	ArmsCmd arms2 = new ArmsCmd(driveStick);

	public void robotInit() {

		// Initialize Microsoft camera
		CameraServer server = CameraServer.getInstance();
		server.setQuality(50);
		server.startAutomaticCapture("cam1");

		System.out.println("i'm Alive");

		// Construct CMD List
		SharedStuff.cmdlist.add(aDrive);
		SharedStuff.cmdlist.add(shooter);
		SharedStuff.cmdlist.add(arms);
		SharedStuff.cmdlist.add(shooter2);
		SharedStuff.cmdlist.add(arms2);
		SharedStuff.cmdlist.add(wmsg);
		SharedStuff.cmdlist.add(auto);
		// can update first

		// Limit Switches- In for now, will be changed to CAN network
		frontLeftMotor.enableLimitSwitch(false, false);
		frontRightMotor.enableLimitSwitch(false, false);
		rearLeftMotor.enableLimitSwitch(false, false);
		rearRightMotor.enableLimitSwitch(false, false);
		shootMotor1.enableLimitSwitch(false, false);
		shootMotor2.enableLimitSwitch(false, false);

		// Voltage Ramps - none for now
		// frontLeftMotor.setVoltageRampRate(driveramp);
		// frontRightMotor.setVoltageRampRate(driveramp);
		// rearLeftMotor.setVoltageRampRate(driveramp);
		// rearRightMotor.setVoltageRampRate(driveramp);

		// add ref to list

	}

	//
	public void autonomousInit() {
		// Output RangeFinder Distance
		// rangeFinder.setDistance();
		t1.stop();
		t1.reset();
		t1.start();
		for (int i = 0; i < SharedStuff.cmdlist.size(); i++) {
			SharedStuff.cmdlist.get(i).autonomousInit();
		}

	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		for (int i = 0; i < SharedStuff.cmdlist.size(); i++) {
			SharedStuff.cmdlist.get(i).autonomousPeriodic();
		}

		// for now methods must be controlled with hardcoded values,
		// later will be changed to either SmartDashboard input or a text file

	}

	public void teleopInit() {
		t1.start();
		state = 0;
	}

	public void autoGuillotine() {
		if (driveStick.getRawButton(7) && state == 0) {
			System.out.println("State to 1");
			state = 1;
		}
		if (state > 0) {
			if (state == 1) {
				System.out.println("State to 2");
				state = 2;
				armMotor1.set(-.8);
				armMotor2.set(-.8);
				t1.stop();
				t1.start();
				driveStick.pY = 0;
				aDrive.autonomousPeriodic();
			} else if (state == 2) {

//				System.out.println("I made it to state 2");
				armMotor1.set(-.5);
				armMotor2.set(-.5);
				aDrive.autonomousPeriodic();
				if (t1.get() > .5) {
					driveStick.pY = .65;
					System.out.println("State to 3");
					state = 3;
				}
			} else if (state == 3) {
				driveStick.pY = .65;
				armMotor1.set(-.5);
				armMotor2.set(-.5);
				aDrive.autonomousPeriodic();
				if (t1.get() > 3) {
					t1.reset();
					t1.stop();
					driveStick.pY = 0;
					armMotor1.set(0);
					armMotor2.set(0);
					state = 0;

				}

			}
		}
	}

	/**
	 * This function is called periodically during operator control
	 */

	public void teleopPeriodic() {

		double distance = rf1.getVoltage() * 102.4;
		SharedStuff.msg[1] = "RF: " + new Integer((int) distance).toString();
		if (t1.get() > .25) {
			// Double angle = gyro.getAngle();
			Double xVal = b_acc.getX();
			Double yVal = b_acc.getY();
			Double zVal = b_acc.getZ();
			// Integer ia = new Integer(angle.intValue()* 100);
			/*
			 * SharedStuff.msg[7] = " x  " + xVal.toString();
			 * //SharedStuff.msg[6] = " angle  " + angle.toString();
			 * SharedStuff.msg[8] = " y  " + yVal.toString(); SharedStuff.msg[9]
			 * = " z  " + zVal.toString();
			 * 
			 * t1.reset(); //System.out.print(" angle  " + angle.toString());
			 * System.out.print(" x  " + xVal.toString()); System.out.print(
			 * " y  " + yVal.toString()); System.out.println(" z  " +
			 * zVal.toString()) ;
			 * 
			 * }
			 */
			// Output RangeFinder Distance
			// rangeFinder.setDistance();

			if (driveStick.getRawButton(7) || state > 0) {
				autoGuillotine();
			} 
			if (state == 0) {
				for (int i = 0; i < SharedStuff.cmdlist.size(); i++) {
					SharedStuff.cmdlist.get(i).teleopPeriodic();
				}
			}

		}
	}

	public void testInit() {
		t1.start();
		state = 0;
		for (int i = 0; i < SharedStuff.cmdlist.size(); i++) {
			SharedStuff.cmdlist.get(i).testInit();
		}
	}

	/**
	 * This function is called periodically during test mode
	 * 
	 */
	public void testPeriodic() {
		if(driveStick.getRawButton(1) == true){
			auto.rangeFinderDrive(10);
		
		}
		else
		{
			for (int i = 0; i < SharedStuff.cmdlist.size(); i++) {
				SharedStuff.cmdlist.get(i).testPeriodic();
			}
		
		// Debug Output
		SharedStuff.msg[0] = " Left I " + frontLeftMotor.getOutputCurrent();
		SharedStuff.msg[0] = "RangeFinder Output: " + rf1.getVoltage();
		SharedStuff.msg[5] = "right I " + frontRightMotor.getOutputCurrent();
		SharedStuff.msg[5] = "Average Distance Calculation" + auto.averageDistance();
		SharedStuff.msg[1] = " Left O " + frontLeftMotor.getOutputVoltage();
		SharedStuff.msg[6] = "right O " + frontRightMotor.getOutputVoltage();
		SharedStuff.msg[2] = " Left V " + frontLeftMotor.getBusVoltage();
		SharedStuff.msg[7] = "right V " + frontRightMotor.getBusVoltage();
		SharedStuff.msg[3] = " Enc pos " + elevator.getEncPosition();
		SharedStuff.msg[8] = "getpos" + elevator.getPosition();
		SharedStuff.msg[4] = " sh1 I " + shootMotor1.getOutputCurrent();
		SharedStuff.msg[9] = "right S " + shootMotor2.getOutputCurrent();
			
	}
	}

}