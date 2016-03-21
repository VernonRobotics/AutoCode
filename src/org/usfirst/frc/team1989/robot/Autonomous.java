package org.usfirst.frc.team1989.robot;

import edu.wpi.first.wpilibj.Timer;

public class Autonomous extends a_cmd implements cmd{
	
	JsScaled driveStick;
	Timer autoStraight;
	Timer autoTurn; 
	double currentDistance;
	Timer rangeTimer;
	Timer test1;
	double madj;
	
	boolean shutdown = false;
	
	public Autonomous(JsScaled driveStick, Timer autoStraight, Timer autoTurn, Timer rangeTimer, Timer test1){
		this.driveStick = driveStick;
		this.autoStraight = autoStraight;
		this.autoTurn = autoTurn;
		this.rangeTimer = rangeTimer;
		this.test1 = test1;
	}
	

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

	
	
	
	
	
	public void disabledInit() {}
	public void autonomousInit() {}
	public void autonomousPeriodic() {
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
		
	}
	public void DisabledPeriodic() {}
	public void testInit() {}
	public void teleopInit() {}
	public void teleopPeriodic() {
		
	}
	public void testPeriodic() {}
}
