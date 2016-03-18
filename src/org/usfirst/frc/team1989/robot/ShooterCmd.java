package org.usfirst.frc.team1989.robot;

// All Imports - Will remove unecessary later
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Servo;

public class ShooterCmd extends a_cmd {
	
	JsScaled driveStick;
	Servo s1;
	Timer t1 = new Timer();
	int lastaction = 0; //0 off, 1 pickup 2 spinup
	double lasti =0.000; //last current	
	double nexttimer = 0.000;
	
	public ShooterCmd (JsScaled driveStick, Servo s1){
		this.driveStick = driveStick;
		this.s1 = s1;
		
	}
	
	public void elevatorOperation(){
		if(driveStick.getPOV(0) == 180){
			elevator.set(.4);
		}else if(driveStick.getPOV(0) == 0){
			elevator.set(-.4);
		}else{
			elevator.set(-.04);
		}
		
	}
	
	public void shootMotorOperation(){
		//so the shooter needs a timer and a state and some logic depending on state
//		SharedStuff.msg[0] =" Left s I " + shootMotor1.getOutputCurrent();
//		SharedStuff.msg[5] = "lasti " + lasti;
		if(driveStick.getRawButton(5) == true){
			SharedStuff.led[1] = false;
			this.lastaction = 1;
			shootMotor1.set(-.35);
			shootMotor2.set(.35);
			elevator.set(.5);
			SharedStuff.led[0] = false;
			lastaction = 0;
			if (shootMotor1.isFwdLimitSwitchClosed())
			{
				SharedStuff.led[1] = true;
			}
			}
		else if (driveStick.getRawButton(3)){
			SharedStuff.led[1] = false;
			if(lastaction != 2){
				t1.stop();
				t1.reset();
				t1.start();
				nexttimer = t1.get() + 1.0;
				lastaction = 2;
			}
			else if(t1.get() > nexttimer && lastaction ==2){
				nexttimer = t1.get() + .3;
				if(Math.abs(lasti- shootMotor1.getOutputCurrent()) < 0.5){
					SharedStuff.led[0] = true;
					lastaction = 1;
				}
				lasti = shootMotor1.getOutputCurrent();
			}
			shootMotor1.set(1);
			shootMotor2.set(-1);
		}
		else
		{
			lastaction = 0;
			SharedStuff.led[0] = false;
			shootMotor1.set(0);
			shootMotor2.set(0);
			this.lastaction = 0;
			SharedStuff.led[1] = false;
		}
		
	}
	
	public void servoOperation(){
		
		if(driveStick.getRawButton(1) == true){
			s1.set(1);
			t1.start();
			
			
		}
	}
	/* Teleop Init and Teleop Periodic */
	public void teleopInit(){
		// Once we have a limit switch then make the elevator start at the bottom.
		s1.set(0);
	}
    public void teleopPeriodic(){
    	if(t1.get() > .2 ){
    		s1.set(0);
    		t1.stop();
    		t1.reset();
    	}
		
    	servoOperation();
    	elevatorOperation();
    	shootMotorOperation();
    }

    
    public void autonomousPeriodic(){}
    public void DisabledPeriodic(){}
    public void testInit(){}
    public void testPeriodic(){}
    public void disabledInit(){}
    public void autonomousInit(){}
}