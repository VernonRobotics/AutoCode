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
	Timer autoStraight = new Timer();
	Timer autoTurn = new Timer();
	double currentDistance;
	Timer rangeTimer = new Timer();
	Timer test1 = new Timer();
	boolean shutdown = false;
	
	

	/*
	 * Main controller for use.  Based on 4 motors and a speed controller.
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
		
		autoStraight.start();
		test1.start();
		shutdown = false;

	}

	@Override
	public void autonomousPeriodic() {
		
		//autoDriveTwist(SmartDashboard.getNumber("DB/Slider 2"), SmartDashboard.getNumber("DB/Slider 3"));
		autoDriveY(.69, 7);
		double madj = 0.0;
		
		if(test1.get() > .2)
		{
			test1.reset();
			
			if (frontRightMotor.getOutputCurrent() > 16 || frontLeftMotor.getOutputCurrent() > 16)
			{
				shutdown = true;
				System.out.println("crash");
			}
		}
		
		if (shutdown)
		{
			return;
		}
		if (driveStick.pY !=0)
		{
			madj = (driveStick.pY > 0 ? - .22 : 0.1);
		}
		
		arcadeDrive(driveStick.pY, driveStick.pTwist + madj);
		// TODO Auto-generated method stub
		

	}

		
	@Override
	public void testInit() {
		// TODO Auto-generated method stub
		test1.start();

	}

	@Override
	public void teleopInit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void teleopPeriodic() {
		// TODO Auto-generated method st
		double madj = 0.0;
		
		if (driveStick.sgetY() !=0)
		{
		 madj = (driveStick.sgetY() < 0 ? - .22 : 0.1);
		}
		arcadeDrive(0 - driveStick.sgetY(), 0-driveStick.sgetTwist()*.75 + madj);
	}

	@Override
	public void testPeriodic() {
		// TODO Auto-generated method stub
		
        double madj;
        
        
		arcadeDrive(0 - driveStick.sgetY(), 0-driveStick.sgetTwist()*.75 -.224);
	
		if (test1.get() > .25)
		{
			test1.stop();
			test1.reset();
			test1.start();
			double ir =  (frontRightMotor.getOutputCurrent() *100);
			double il =  (frontLeftMotor.getOutputCurrent() *100);
			double sy = (driveStick.sgetY() * 100);
			System.out.println("speed " + sy + " left I " + il + " right I " + ir);
	}
		
		
	}
	
	// Autonomous method to drive either forwards or backwards
	public void autoDriveY(double driveSpeedY, double driveTime){
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
		double distance1 = Robot.rf1.getVoltage();
		double distance2;
		rangeTimer.start();
			if (rangeTimer.get() > 0.01){
				distance2 = Robot.rf1.getVoltage();
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


	
