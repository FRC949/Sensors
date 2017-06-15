package org.usfirst.frc.team949.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team949.robot.Robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team949.robot.Robot;

/**
 *
 */
public class JoystickDrive extends Command {
	
	private final double kNerf = 0.3;
	
	public JoystickDrive() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.drive);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		Joystick x = Robot.oi.getJoystick();
		Robot.drive.drive(x);
		if (x.getRawButton(1))
		{
			Robot.drive.resetEncoder();
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
