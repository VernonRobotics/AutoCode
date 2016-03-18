package org.usfirst.frc.team1989.robot;



import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;


public class ArmsCmd extends a_cmd{
	
	
	JsScaled driveStick;
	
	
	public ArmsCmd(JsScaled driveStick){
			
			this.driveStick = driveStick;
	}
	
	public void armMotorOperation(){
		if (driveStick.getRawButton(6) == true){
			armMotor1.set(-0.5);
			armMotor2.set(-0.5);
		}
		
		else if (driveStick.getRawButton(4) == true){
			armMotor1.set(0.5);
			armMotor2.set(0.5);
		}
		else{
			armMotor1.set(-0.05);
			armMotor2.set(-0.05);
		}
	}



	public void disabledInit() {}
	public void autonomousInit() {}
	public void autonomousPeriodic() {}
	public void DisabledPeriodic() {}
	public void testInit() {}
	public void teleopInit() {}
	public void teleopPeriodic() {
		armMotorOperation();
	}
	public void testPeriodic() {}
	
	
}
