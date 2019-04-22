package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.networktables.*;
import java.util.ArrayList;
import com.kauailabs.navx.frc.*;
import frc.util.snail_vision.*;
import edu.wpi.first.wpilibj.drive.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.networktables.*;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import java.util.*;
import java.io.*;

public class Robot extends TimedRobot {
  WPI_TalonSRX FrontRight;
  WPI_TalonSRX FrontLeft;
  WPI_TalonSRX BackRight;
  WPI_TalonSRX BackLeft;
  SpeedControllerGroup Right;
  SpeedControllerGroup Left;
  DifferentialDrive DriveTrain;
  XboxController Controller;
  double driveSpeed;
  double turnSpeed;
  SnailVision vision;

  @Override
  public void robotInit() {
    FrontLeft = new WPI_TalonSRX(1);
        BackLeft = new WPI_TalonSRX(2);
        BackRight = new WPI_TalonSRX(3);
        FrontRight = new WPI_TalonSRX(4);

        Right = new SpeedControllerGroup(FrontRight, BackRight);
        Left = new SpeedControllerGroup(FrontLeft, BackLeft);

        DriveTrain = new DifferentialDrive(Left, Right);

        Controller = new XboxController(0);

        driveSpeed = 0;
        turnSpeed = 0;

        vision = new SnailVision(true);
  }

  @Override
  public void robotPeriodic() {
    // Basic Teleop Drive Code
    vision.trackTargetPosition();
    driveSpeed = 0;
    turnSpeed = 0;
    if(Controller.getAButton()) {
          double y = Controller.getY(GenericHID.Hand.kLeft);
          double x = Controller.getX(GenericHID.Hand.kLeft);
          driveSpeed = -y;
          turnSpeed = x;
      } 
      else if(Controller.getBumper(GenericHID.Hand.kLeft)) {
          double y = Controller.getY(GenericHID.Hand.kLeft);
          double x = Controller.getX(GenericHID.Hand.kRight);
          driveSpeed = -y;
          turnSpeed = x;
      } 
      else if(Controller.getBumper(GenericHID.Hand.kRight)) {
          double x = Controller.getX(GenericHID.Hand.kLeft);
          double y = Controller.getY(GenericHID.Hand.kRight);
          driveSpeed = -y;
          turnSpeed = x;
      }
      if(Controller.getBButton()){
        vision.findTarget();
      }
      DriveTrain.arcadeDrive(driveSpeed, turnSpeed);
  }

  @Override
  public void autonomousInit() {
 
  }

  @Override
  public void autonomousPeriodic() {

  }

  @Override
  public void teleopPeriodic() {
    
  }

  @Override
  public void testPeriodic() {
  }
}
