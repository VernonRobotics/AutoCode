package org.usfirst.frc.team1989.robot;

import edu.wpi.first.wpilibj.Joystick;

public class JsScaled extends Joystick {
    public double low = .9; // set to 1 /full drive if above
    public double deadzone = .15; //set to 0 if below
    public double pY = 0; 
    public double pX = 0;
    public double pTwist = 0;
    public boolean[] buttons;
    
    public JsScaled(int port, double low, double deadzone) {
	this(port);
	this.low = low;
	this.deadzone = deadzone;
	// TODO Auto-generated constructor stub
    }

    public JsScaled(int port) {
	super(port);
	// TODO Auto-generated constructor stub
    }

    public JsScaled(int port, int numAxisTypes, int numButtonTypes, double low,
	    double deadzone) {
	this(port, numAxisTypes, numButtonTypes);
	this.low = low;
	this.deadzone = deadzone;
    }

    public JsScaled(int port, int numAxisTypes, int numButtonTypes) {
	super(port, numAxisTypes, numButtonTypes);
	// TODO Auto-generated constructor stub
    }

    public double sgetX() {
	return scale(super.getX());
    }

    public double sgetY() {
	return scale(super.getY());
    }

    public double sgetTwist() {
	return scale(scale(super.getTwist()));
    }

    public double getThrottle() {
	return scale(super.getThrottle());
    }

    // Scales Analog Joystick value in exponentially
    public double scale(double v) {
	if (v > 0 - deadzone && v < deadzone) {
	    v = 0;
	}
	else if (v>low)
	{v=1;}
	else if (v<0-low)
	{v=-1;}
	else if (v >= deadzone) {
	    v *= (v * low);
	    v += deadzone -(deadzone*deadzone);
	} else if (v <= 0 - deadzone) {
	    v *= (-v * low);
	    v -= deadzone +(deadzone*deadzone);
	}

	return v;
    }

}
