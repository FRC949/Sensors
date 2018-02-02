package org.usfirst.frc.team949.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team949.robot.Robot;
import org.usfirst.frc.team949.robot.subsystems.DriveTrain;

/**
 *
 */
public class JoystickDrive extends Command {

	private final double kNerf = 1;
	private final double kThreshold = 0;

	public JoystickDrive() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.DRIVE);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		Joystick x = Robot.oi.getJoystick();
//		double y = -x.getY(), z = -x.getZ();
//		y = Math.abs(y) < kThreshold ? 0 : y;
//		z = Math.abs(z) < kThreshold ? 0 : z;
//		z=0;
//		Robot.DRIVE.drive(kNerf * (y - z), kNerf * (y + z));
		Robot.DRIVE.drive(-x.getY(), -x.getY());
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
