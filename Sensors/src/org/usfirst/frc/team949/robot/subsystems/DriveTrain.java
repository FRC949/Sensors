package org.usfirst.frc.team949.robot.subsystems;

import org.usfirst.frc.team949.robot.util.*;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc.team949.robot.commands.JoystickDrive;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
/**
 *
 */
public class DriveTrain extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	private CANTalon frontLeft = new CANTalon(5);
	private CANTalon frontRight = new CANTalon(1);
	private CANTalon rearLeft = new CANTalon(6);
	private CANTalon rearRight = new CANTalon(3);

	private RobotDrive drive;

	public ADXRS450_Gyro gyro;
	public Encoder leftEncoder;
	public Encoder rightEncoder;

	public DriveTrain() {
//		super(0.1, 0.001, 0);
		gyro = new ADXRS450_Gyro();
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
		
		drive = new RobotDrive(frontLeft, rearLeft, frontRight, rearRight);
		drive.setSafetyEnabled(false);
	}



	

	public void drive(double y, double z) {

		drive.arcadeDrive(y, z);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
		setDefaultCommand(new JoystickDrive());
	}

	public void resetEncoder() {
		// TODO Auto-generated method stub
		leftEncoder.reset();
		rightEncoder.reset();
	}

	public void calibrateGyro() {
		new Thread(gyro::calibrate).start();
	}

	public void resetGyro() {
		gyro.reset();
	}

//	@Override
//	protected double returnPIDInput() {
//		// TODO Auto-generated method stub
//		return gyro.getAngle();
//	}
//
//	@Override
//	protected void usePIDOutput(double output) {
//		drive.drive(0, output);
//
//	}

}
