package org.usfirst.frc.team949.robot.commands;

import org.usfirst.frc.team949.robot.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;

public class Forward extends Command {
	private PIDController controller;
	private double output = 0;
	private final double dis;

	public Forward() {
		this(10);
	}

	public Forward(double dis) {
		controller = new PIDController(0.01, 0.0005, 0, (Robot.drive.leftEncoder), output -> this.output = output);
		controller.setOutputRange(-1, 1);
		controller.enable();
		this.dis = dis;
	}

	protected void initialize() {
		// System.out.println("Something");

		controller.setSetpoint(Robot.drive.leftEncoder.getDistance() + dis);
		System.out.println("Init:" + (int) Robot.drive.leftEncoder.getDistance());
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.drive.drive(output, 0);
	}

	private boolean previous = false;

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return previous & (previous = Math.abs(controller.getError()) < 1);
		// return false;
	}

	protected void end() {

		// Timer.delay(1);
		System.out.println(controller.getError());
	}

}
