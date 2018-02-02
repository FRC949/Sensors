package org.usfirst.frc.team949.robot.subsystems;

import org.usfirst.frc.team949.robot.util.*;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import org.usfirst.frc.team949.robot.commands.JoystickDrive;

import com.ctre.CANTalon;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 */
public class DriveTrain extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	private WPI_TalonSRX frontLeft = new WPI_TalonSRX(5);
	private WPI_TalonSRX frontRight = new WPI_TalonSRX(1);
	private WPI_TalonSRX rearLeft = new WPI_TalonSRX(6);
	private WPI_TalonSRX rearRight = new WPI_TalonSRX(3);

	private DifferentialDrive drive;

	// public final ADXRS450_Gyro gyro;
	public final Encoder leftEncoder;
	public final Encoder rightEncoder;

	public DriveTrain() {
		// super(0.1, 0.001, 0);
		// gyro = new ADXRS450_Gyro();
		leftEncoder = new Encoder(0, 1, false, Encoder.EncodingType.k4X);
		rightEncoder = new Encoder(2, 3, false, Encoder.EncodingType.k4X);
		leftEncoder.setMaxPeriod(.1);
		leftEncoder.setMinRate(10);
		leftEncoder.setDistancePerPulse(0.0254 * 6 * Math.PI / 360);
		leftEncoder.setReverseDirection(true);
		leftEncoder.setSamplesToAverage(7);
		leftEncoder.setPIDSourceType(PIDSourceType.kRate);
		rightEncoder.setPIDSourceType(PIDSourceType.kRate);
		rightEncoder.setMaxPeriod(.1);
		rightEncoder.setMinRate(10);
		rightEncoder.setDistancePerPulse(0.0254 * 6 * Math.PI / 250);
		rightEncoder.setReverseDirection(false);
		rightEncoder.setSamplesToAverage(7);

		drive = new DifferentialDrive(new SpeedControllerGroup(frontLeft, rearLeft),
				new SpeedControllerGroup(frontRight, rearRight));
		drive.setSafetyEnabled(false);

	}

	private double maxV = 100;// in/s
	private double iGain = 0;
	private double kp = 0.02;
	private double ki = 0.3 / 20;// 20Hz loop
	private double kd = 0.01;
	private double max = 0;// max speed
	private double perr = 0;

	private double vm = 0, am = 0, jm = 0;
	private double vp = 0, ap = 0, jp = 0;

	public void resetI() {
		iGain = 0;

	}

	private double[] vs = new double[50], ts = new double[50];
	int c = 0;

	public void drive(double l, double r) {
		
		/*double v, a, j;
		v = leftEncoder.getRate();
		a = 20 * (v - vp);
		j = 20 * (a - ap);
		vm = Math.max(v, vm);
		am = Math.max(a, am);
		jm = Math.max(j, jm);
		System.out.println("vm:" + vm);
		System.out.println("am:" + am);
		System.out.println("jm:" + jm);
		System.out.println(v);
		vp = v;
		ap = a;
		jp = j;
		*/
		drive.tankDrive(l, r);
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
		// new Thread(gyro::calibrate).start();
	}

	public void resetGyro() {
		// gyro.reset();
	}

	// @Override
	// protected double returnPIDInput() {
	// // TODO Auto-generated method stub
	// return gyro.getAngle();
	// }
	//
	// @Override
	// protected void usePIDOutput(double output) {
	// drive.drive(0, output);
	//
	// }

}
