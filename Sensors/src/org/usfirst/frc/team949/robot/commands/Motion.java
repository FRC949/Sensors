package org.usfirst.frc.team949.robot.commands;

import org.usfirst.frc.team949.robot.Robot;
import org.usfirst.frc.team949.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

/**
 *
 */
public class Motion extends Command {
	EncoderFollower leftFollower;
	EncoderFollower rightFollower;
	double kp = 0.8;
	double ki = 0;
	double kd = 0;
	double kv = 1. / 3;
	double ka = 0;

	public Motion() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.DRIVE);

		// Create the Trajectory Configuration
		//
		// Arguments:
		// Fit Method: HERMITE_CUBIC or HERMITE_QUINTIC
		// Sample Count: SAMPLES_HIGH (100 000)
		// SAMPLES_LOW (10 000)
		// SAMPLES_FAST (1 000)
		// Time Step: 0.05 Seconds
		// Max Velocity: 1.7 m/s
		// Max Acceleration: 2.0 m/s/s
		// Max Jerk: 60.0 m/s/s/s
		Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC,
				Trajectory.Config.SAMPLES_HIGH, 0.05, 2, 1.5, 60.0);

		// Create Waypoints
		Waypoint[] points = new Waypoint[] { new Waypoint(-4, -1, Pathfinder.d2r(-45)),
				new Waypoint(-2, -2, 0), new Waypoint(0, 0, 0) };

		// Generate trajectory with given waypoints and configuration
		Trajectory trajectory = Pathfinder.generate(points, config);

		// Wheelbase Width = 0.5969m
		TankModifier modifier = new TankModifier(trajectory).modify(0.6096);

		// Do something with the new Trajectories...
		Trajectory left = modifier.getLeftTrajectory();
		Trajectory right = modifier.getRightTrajectory();

		leftFollower = new EncoderFollower(left);
		rightFollower = new EncoderFollower(right);
		leftFollower.configurePIDVA(kp, ki, kd, kv, ka);
		rightFollower.configurePIDVA(kp, ki, kd, kv, ka);
		leftFollower.configureEncoder(0, 360, 0.1524);
		rightFollower.configureEncoder(0, 250, 0.1524);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.DRIVE.leftEncoder.reset();
		Robot.DRIVE.rightEncoder.reset();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.DRIVE.drive(leftFollower.calculate(Robot.DRIVE.leftEncoder.get()),
				rightFollower.calculate(Robot.DRIVE.rightEncoder.get()));
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
