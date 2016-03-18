package org.usfirst.frc.team1989.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;

public abstract class a_cmd extends IterativeRobot implements cmd {
	
	
	public String type = ""; // holds class type
	public static double Kp = 0.03; // const for multiplying gyro angle 
	
	// Instantiating TalonSRX Motors
	CANTalon frontLeftMotor = new CANTalon(3);
	CANTalon frontRightMotor = new CANTalon(9);
	CANTalon rearLeftMotor = new CANTalon(6);
	CANTalon rearRightMotor = new CANTalon(7);
	CANTalon shootMotor1 = new CANTalon(2);
	CANTalon shootMotor2 = new CANTalon(8);
	CANTalon armMotor1 = new CANTalon(1);
	CANTalon armMotor2 = new CANTalon(4);
	CANTalon elevator = new CANTalon(5);
	
	// Instantiate Gyro
	ADXRS450_Gyro gyro;
	
	Accelerometer b_acc = new BuiltInAccelerometer();
	
	
	
	// Needs to be updated when hooked up.
//	public RangeFinderCmd rangeFinder = new RangeFinderCmd(0);
	
	/**
	 * For the disabledInit and disabledPeriodic, be sure to disable all motors.
	 * Whenever a motor is added, it should be done in this class.
	 * Once a motor is initiated, it should also be added to the disabled method below.
	 */
	public void disabledInit(){
	}
	
    public void disabledPeriodic(){
    	// Disable all motors
    	frontLeftMotor.set(0);
    	frontRightMotor.set(0);
    	rearLeftMotor.set(0);
    	rearRightMotor.set(0);
    	shootMotor1.set(0);
    	shootMotor2.set(0);
    	armMotor1.set(0);
    	armMotor2.set(0);
    	elevator.set(0);
    }

  //public ArrayList<autocmd> auto_list = new ArrayList<autocmd>(); // list of auto commands

  	/*public void addauto(String Command)
  	{
  		this.auto_list.add(new autocmd(Command));
  	}*/

	
}
