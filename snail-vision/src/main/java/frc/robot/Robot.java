/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.util.snail_vision.*;
import edu.wpi.first.wpilibj.drive.*;
import edu.wpi.first.wpilibj.*;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Robot extends TimedRobot {

  WPI_TalonSRX FrontRight;
  WPI_TalonSRX FrontLeft;
  WPI_TalonSRX BackRight;
  WPI_TalonSRX BackLeft;
  SpeedControllerGroup Right;
  SpeedControllerGroup Left;
  DifferentialDrive DriveTrain;
  XboxController Controller;

  SnailVision vision = new SnailVision(true);
  double driveSpeed;
  double turnSpeed;
  double colliding;

  @Override
  public void robotInit() {
    
    colliding = 0;

    FrontLeft = new WPI_TalonSRX(2);
    BackLeft = new WPI_TalonSRX(3);
    BackRight = new WPI_TalonSRX(4);
    FrontRight = new WPI_TalonSRX(5);

    Right = new SpeedControllerGroup(FrontRight, BackRight);
    Left = new SpeedControllerGroup(FrontLeft, BackLeft);

    DriveTrain = new DifferentialDrive(Left, Right);

    Controller = new XboxController(0);
  }

  @Override
  public void robotPeriodic() {

  }

  @Override
  public void autonomousInit() {
 
  }

  @Override
  public void autonomousPeriodic() {

  }

  @Override
  public void teleopPeriodic() {

    vision.calculateJerk();
    SmartDashboard.putNumber("jerk", vision.instantaneousJerk);
    SmartDashboard.putNumber("velocity", vision.navx.getVelocityX());
    SmartDashboard.putNumber("acceleration", vision.getAcceleration());
    if(vision.instantaneousJerk > 10){
      colliding = 1;
    }
    else{
      colliding = 0;
    }
    SmartDashboard.putNumber("colliding", colliding);

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

    DriveTrain.arcadeDrive(driveSpeed, turnSpeed);
  }

  @Override
  public void testPeriodic() {
  }
}
