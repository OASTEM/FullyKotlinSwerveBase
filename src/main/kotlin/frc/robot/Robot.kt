// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot

import edu.wpi.first.hal.FRCNetComm.tInstances
import edu.wpi.first.hal.FRCNetComm.tResourceType
import edu.wpi.first.hal.HAL
import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.CommandScheduler

class Robot : TimedRobot() {
  private var autonomousCommand: Command? = null
  private var robotContainer: RobotContainer? = null

  init {
    HAL.report(tResourceType.kResourceType_Language, tInstances.kLanguage_Kotlin)
  }

  override fun robotInit() {
    robotContainer = RobotContainer()
  }

  override fun robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run()
  }

  override fun autonomousInit() {
    // Retrieve the autonomous command from the RobotContainer
    autonomousCommand = robotContainer!!.getAutonomousCommand()

    // Schedule the autonomous command if it is not null
    if (autonomousCommand != null) {
      autonomousCommand!!.schedule()
    }
  }

  override fun teleopInit() {
    // Cancel the autonomous command if it is running
    if (autonomousCommand != null) {
      autonomousCommand!!.cancel()
    }
  }

  override fun testInit() {
    CommandScheduler.getInstance().cancelAll()
  }
}
