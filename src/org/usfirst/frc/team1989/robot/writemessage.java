package org.usfirst.frc.team1989.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/*
 * Note it expects to have indexes passed in the proper range
 * 
 *Usage : add to cmdlist in robot init
 *
 *in a class where you use it you can use the findcmd("writemessage") and assign it to a variable in any of the init methods that
 *get called after robot init like teleopinit 
 *andd then use setmessage and and setboolean to write your messages.
 *
 *or you can directly write to Robot.sharedStuff.msg etc
 */
public class writemessage extends a_cmd {
	public Timer t1 = new Timer();

	public writemessage() {
		this.type = "writemessage"; // so we can find it
		for (int i = 0; i < 10; i++) {
			SharedStuff.msg[i] = "";
		
			SharedStuff.lastmsg[i] = "";
			SmartDashboard.putString("DB/String " + i, "");
			if (i < 5) {
				SharedStuff.led[i] = false;
				SharedStuff.lastled[i] = false;
				SmartDashboard.putBoolean("DB/LED " + i, false);
			}
		}
		t1.start();
	}

	public void setmessage(int index, String msg) {
		SharedStuff.msg[index] = msg;
	}

	public void setboolean(int index, Boolean b) {
		SharedStuff.led[index] = b;
	}

	public void updatedash() {
		String curr ;
		String last;
		if (t1.get() > .25) {
			t1.reset();
			t1.start();

			for (int i = 0; i < 10; i++) {
				curr =SharedStuff.msg[i].toString();
				last =SharedStuff.lastmsg[i].toString();
				
				if (!(curr.equals(last.toString()))) {
					SmartDashboard.putString("DB/String " + i, curr);
					SharedStuff.lastmsg[i]= curr;
				}
				if (i < 5) {
					if (SharedStuff.led[i].booleanValue() !=SharedStuff.lastled[i].booleanValue()) {
						SmartDashboard.putBoolean("DB/LED " + i, SharedStuff.led[i].booleanValue());
						SharedStuff.lastled[i] = SharedStuff.led[i].booleanValue();
					}
				}
			}
		}

	}
	public void autionomousInit(){
    	t1.reset();
    	t1.start();

	}
    public void autonomousPeriodic() {
    	updatedash();
    }
    public void DisabledPeriodic(){
    	updatedash();
    }
    public void testInit(){
    	t1.reset();
    	t1.start();
    	updatedash();
    }
    public void teleopInit(){
    	t1.reset();
    	t1.start();
    	updatedash();
    }
    public void teleopPeriodic() {
    	updatedash();
    }
    public void testPeriodic() {
    	updatedash();
    }
    

}
