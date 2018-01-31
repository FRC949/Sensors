package org.usfirst.frc.team949.robot.commands;

import org.usfirst.frc.team949.robot.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Rotate extends Command {
	private PIDController controller;
	private double output = 0;
	private final double angle;

	public Rotate() {
		this(90);
	}

	public Rotate(double angle) {
//		controller = new PIDController(0.01, 0.0005, 0, Robot.DRIVE.gyro, output -> this.output = output);
		controller.setInputRange(0, 360);
		controller.setContinuous(true);
		controller.setOutputRange(-1, 1);
		controller.enable();
		this.angle = angle;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		// System.out.println("Something");

//		controller.setSetpoint(Math.floorMod((int) (Robot.DRIVE.gyro.getAngle() + angle), 360));
//		System.out.println("Init:" + Math.floorMod((int) (Robot.DRIVE.gyro.getAngle() + angle), 360));
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.DRIVE.drive(0, -output);
	}

	private boolean previous = false;

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return previous & (previous = Math.abs(controller.getError()) < 1);
		// return false;
	}

	// Called once after isFinished returns true
	protected void end() {

		// Timer.delay(1);
		System.out.println(controller.getError());
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
