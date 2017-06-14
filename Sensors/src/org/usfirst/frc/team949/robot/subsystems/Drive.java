package org.usfirst.frc.team949.robot.subsystems;

import org.usfirst.frc.team949.robot.commands.JoystickDrive;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Drive extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public Encoder leftEncoder;
	public Encoder rightEncoder;
	
	public Drive()
	{
		leftEncoder = new Encoder(0, 1, false, Encoder.EncodingType.k4X);
		rightEncoder = new Encoder(2, 3, false, Encoder.EncodingType.k4X);
		leftEncoder.setMaxPeriod(.1);
		leftEncoder.setMinRate(10);
		leftEncoder.setDistancePerPulse(6 * Math.PI / 360);
		leftEncoder.setReverseDirection(true);
		leftEncoder.setSamplesToAverage(7);
		rightEncoder.setMaxPeriod(.1);
		rightEncoder.setMinRate(10);
		rightEncoder.setDistancePerPulse(6 * Math.PI / 250);
		rightEncoder.setReverseDirection(false);
		rightEncoder.setSamplesToAverage(7);
	}
	
	public void drive(double x)
	{
		
	}
	
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
		setDefaultCommand(new JoystickDrive());
	}
}
