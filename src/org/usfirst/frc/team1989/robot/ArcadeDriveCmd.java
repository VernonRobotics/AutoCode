package org.usfirst.frc.team1989.robot;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ArcadeDriveCmd extends a_cmd {

	public String type = "arcadeDriveCmd";
	public ArrayList<cmd> list;
	JsScaled driveStick;
	RobotDrive rd;
	Timer autoStraight;
	Timer autoTurn;
	double currentDistance;
	Timer rangeTimer;
	AnalogInput rangefinder;
	
	

	/*
	 * Main controller for use.  Basasd on 4 motors anda  speed controller.
	 */
	public ArcadeDriveCmd(int frontLeftMotor, int rearLeftMotor, int frontRightMotor, int rearRightMotor, JsScaled driveStick) {
		rd = new RobotDrive(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);
		this.driveStick = driveStick;

	}
		
	// Included for completeness, but unused
	public ArcadeDriveCmd(SpeedController leftMotor, SpeedController rightMotor, JsScaled driveStick) {
		rd = new RobotDrive(leftMotor, rightMotor);
		this.driveStick = driveStick;
	}
	public ArcadeDriveCmd(int leftMotorChannel, int rightMotorChannel, JsScaled driveStick) {
		rd = new RobotDrive(leftMotorChannel, rightMotorChannel);
		this.driveStick = driveStick;
	}
	
	
	public ArcadeDriveCmd(SpeedController frontLeftMotor, SpeedController rearLeftMotor, SpeedController frontRightMotor, SpeedController rearRightMotor, JsScaled driveStick) {
		rd = new RobotDrive(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);
		this.driveStick = driveStick;
	}
	
	// Autonomous Function - Arcade Drive dependency
	public void arcadeDrive(double magnitude, double stwist){
		
		rd.arcadeDrive(magnitude, stwist);
	}

	@Override
	public void autonomousInit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void autonomousPeriodic() {
		
		autoDriveY(SmartDashboard.getNumber("DB/Slider 0"), SmartDashboard.getNumber("DB/Slider 1"));
		autoDriveTwist(SmartDashboard.getNumber("DB/Slider 2"), SmartDashboard.getNumber("DB/Slider 3"));
		
		arcadeDrive(driveStick.pY, driveStick.pTwist);
		// TODO Auto-generated method stub
		

	}

		
	@Override
	public void testInit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void teleopInit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void teleopPeriodic() {
		// TODO Auto-generated method stub
		arcadeDrive(0 - driveStick.sgetY(), 0-driveStick.sgetTwist()*.75);
	}

	@Override
	public void testPeriodic() {
		// TODO Auto-generated method stub
		arcadeDrive(0 - driveStick.sgetY(), 0-driveStick.sgetTwist()*.75);
		
	}
	
	// Autonomous method to drive either forwards or backwards
	public void autoDriveY(double driveSpeedY, double driveTime){
		autoStraight.start();
		if(autoStraight.get() < driveTime){
		driveStick.pY = driveSpeedY;
		}
		else{
			driveStick.pY = 0.0;
		}
	}

	// Autonomous method to drive either left or right
	public void autoDriveTwist(double driveSpeedTwist, double driveTime){
		autoTurn.start();
		if(autoTurn.get() < driveTime){
		driveStick.pX = driveSpeedTwist; //will be controlling Twist function based on the way the Arcade Drive is coded
		}
		else{
			driveStick.pY = 0.0;
		}
	}
	
	// Go backwards if too close
	public void rangeFinderDrive(double limit){
		
		if (averageDistance() < limit){
			frontLeftMotor.set(-.3);
	    	frontRightMotor.set(-.3);
	    	rearLeftMotor.set(-.3);
	    	rearRightMotor.set(-.3);
		} else if (averageDistance() > limit){
			frontLeftMotor.set(.3);
	    	frontRightMotor.set(.3);
	    	rearLeftMotor.set(.3);
	    	rearRightMotor.set(.3);
		} else{
			frontLeftMotor.set(0);
	    	frontRightMotor.set(0);
	    	rearLeftMotor.set(0);
	    	rearRightMotor.set(0);
		}
	}
	
	
	
	// Returns average distance if values are close.
	// Returns last distance if values are not close.
	public double averageDistance(){
		double distance1 = rangefinder.getVoltage();
		double distance2;
		rangeTimer.start();
			if (rangeTimer.get() > 0.01){
				distance2 = rangefinder.getVoltage();
				rangeTimer.reset();
				if (Math.abs(distance1 - distance2) < 5){
					currentDistance = (distance1 + distance2)/2; 	
				}
				else{
					currentDistance = distance2;
				}
			}
		return currentDistance;
	}

}


	
