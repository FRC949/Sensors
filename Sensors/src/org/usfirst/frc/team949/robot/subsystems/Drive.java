package org.usfirst.frc.team949.robot.subsystems;

import org.usfirst.frc.team949.robot.RobotMap;
import org.usfirst.frc.team949.robot.commands.JoystickDrive;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Drive extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	private CANTalon fL = new CANTalon(1);
	private CANTalon fR = new CANTalon(5);
	private CANTalon bL = new CANTalon(3);
	private CANTalon bR = new CANTalon(6);
	
	private RobotDrive drive;
	
	public Encoder leftEncoder;
	public Encoder rightEncoder;

	public Drive() {
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
		drive = new RobotDrive(fL, bL, fR, bR);
		drive.setSafetyEnabled(false);
	}

	public void drive(Joystick x) {
		drive.arcadeDrive(x);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
		setDefaultCommand(new JoystickDrive());
	}
}
