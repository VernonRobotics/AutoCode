package org.usfirst.frc.team1989.robot;

import java.util.ArrayList;

public interface cmd {

	
	/**
	 * 
	 * These methods need to exist within each concrete class.
	 * Each mode (autonomous, test, and teleop) has an init function that happens at the beginning
	 * of the mode being called from the driver station.
	 * 
	 */
    public void autonomousInit();
    public void autonomousPeriodic() ;
    public void testInit();
    public void testPeriodic();
    public void teleopInit();
    public void teleopPeriodic() ;


	//public ArrayList<autocmd> auto_list = new ArrayList<autocmd>(); // list of auto commands

}
