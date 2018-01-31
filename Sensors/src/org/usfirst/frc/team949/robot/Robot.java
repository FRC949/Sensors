
package org.usfirst.frc.team949.robot;

import edu.wpi.cscore.AxisCamera;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.security.cert.PKIXRevocationChecker;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team949.robot.commands.JoystickDrive;
import org.usfirst.frc.team949.robot.commands.Rotate;
import org.usfirst.frc.team949.robot.subsystems.DriveTrain;

import org.usfirst.frc.team949.robot.util.LEDFilter;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static final DriveTrain DRIVE = new DriveTrain();
	public static final OI oi = new OI();

	Command autonomousCommand;
	private SendableChooser<Command> chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {

//		AxisCamera AXIS_CAMERA = CameraServer.getInstance().addAxisCamera("10.9.49.104");
//		AXIS_CAMERA.setResolution(640, 480);

		chooser.addDefault("Default Auto", new JoystickDrive());
		// chooser.addObject("My Auto", );

		SmartDashboard.putData("Auto mode", chooser);

		new Thread(() -> {

			CvSink cvSink = CameraServer.getInstance().getVideo();
			CvSource outputStream = CameraServer.getInstance().putVideo("Blur", 640, 480);

			Mat source = new Mat();
			LEDFilter filter = new LEDFilter();

			while (!Thread.interrupted()) {
				cvSink.grabFrame(source);
				if (source.empty())
					continue;

				filter.process(source);
				Mat o = filter.hslThresholdOutput();
				outputStream.putFrame(o);
			}
		});//.start();

	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		autonomousCommand = chooser.getSelected();

		// String autoSelected = SmartDashboard.getString("Auto Selector",
		// "Default");
		// switch (autoSelected) {
		// case "My Auto":
		// autonomousCommand = new MyAutoCommand();
		// break;
		// case "Default Auto":
		// default:
		// autonomousCommand = new ExampleCommand();
		// break;
		// }

		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {

		if (oi.getJoystick().getRawButton(1))
			Robot.DRIVE.resetEncoder();
		Scheduler.getInstance().run();

//		System.out.println((DRIVE.leftEncoder.getRate() + "   ").substring(0, 3) + " "
//				+ (DRIVE.rightEncoder.getRate() + "   ").substring(0, 3) + " "
//				+ (DRIVE.rightEncoder.getRate() - DRIVE.leftEncoder.getRate() + "   ").substring(0, 3));

		// System.out.println("Gyro: " + (int) (10 * drive.gyro.getAngle()) /
		// 10.);
	}

	/**
	 * This function is called periodically dur ing test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
