package frc.robot.commands.Auto;

// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.



import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.Arm.presets.ArmToPreset;
import frc.robot.commands.Arm.sequences.ArmToHomeState;
import frc.robot.commands.Arm.sequences.HighPostCenterState;
import frc.robot.commands.Arm.sequences.HighPostCenterState;
import frc.robot.commands.Drive.DriveDistance;
import frc.robot.commands.Hand.ExpelCone;
import frc.robot.commands.Hand.ExpelConeTimed;
import frc.robot.commands.Hand.ExpelCubeTimed;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Drive;
import frc.robot.testingdashboard.TestingDashboard;

public class ScoreCubeAndDriveBack extends CommandBase {
  enum State {
    INIT,
    SCHEDULE_EXTEND_ARM,
    EXTEND_ARM,
    SCHEDULE_SCORE,
    SCORE,
    SCHEDULE_RETRACT_ARM,
    RETRACT_ARM,
    SCHEDULE_DRIVE_BACK,
    DRIVE_BACK,
    SCHEDULE_WAIT,
    WAIT,
    SCHEDULE_DRIVE_BACK_2,
    DRIVE_BACK_2,
    DONE
  }

  HighPostCenterState m_highPostCenter;
  ExpelCubeTimed m_expelCubeTimed;
  ArmToHomeState m_armToHome;
  DriveDistance m_driveBack;
  Wait m_wait;
  DriveDistance m_driveBack2;


  private boolean m_isFinished;
  private State m_state;
  /** Creates a new ReachForNextBarStatefully. */
  public ScoreCubeAndDriveBack(double driveBackDistance, double secondBackDistance, double power) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_state = State.INIT;
    m_isFinished = false;

    m_highPostCenter = new HighPostCenterState();
    m_expelCubeTimed = new ExpelCubeTimed(); 
    m_armToHome = new ArmToHomeState();
    m_driveBack = new DriveDistance(driveBackDistance, power, power, 0, true);
    m_wait = new Wait(2.5, true);
    m_driveBack2 = new DriveDistance(secondBackDistance, power, power, 0, true);
  }

  //Register with TestingDashboard
  public static void registerWithTestingDashboard() {
    Arm climber = Arm.getInstance();
    ScoreCubeAndDriveBack cmd = new ScoreCubeAndDriveBack(0,0,0);
    TestingDashboard.getInstance().registerCommand(climber, "TestCommands", cmd);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_state = State.INIT;
    m_isFinished = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    switch (m_state) {
      case INIT:
        m_state = State.SCHEDULE_EXTEND_ARM;
        break;

      case SCHEDULE_EXTEND_ARM:
        m_highPostCenter.schedule();
        m_state = State.EXTEND_ARM;
        break;
      case EXTEND_ARM:
        if (m_highPostCenter.isFinished())
          m_state = State.SCHEDULE_SCORE;
        break;

      case SCHEDULE_SCORE:
        m_expelCubeTimed.schedule();
        m_state = State.SCORE;
        break;
      case SCORE:
        if (m_expelCubeTimed.isFinished())
          m_state = State.SCHEDULE_RETRACT_ARM;
        break;
      
      case SCHEDULE_RETRACT_ARM:
        m_armToHome.schedule();
        m_state = State.RETRACT_ARM;
        break;
      case RETRACT_ARM:
        if (m_armToHome.isFinished())
          m_state = State.SCHEDULE_DRIVE_BACK;
        break;

      case SCHEDULE_DRIVE_BACK:
        m_driveBack.schedule();
        m_state = State.DRIVE_BACK;
        break;
        case DRIVE_BACK:
        if (m_driveBack.isFinished()) {
          m_state = State.SCHEDULE_WAIT;
        }
        break;
      case SCHEDULE_WAIT:
        m_wait.schedule();
        m_state = State.WAIT;
        break;
      case WAIT:
        if (m_wait.isFinished()) 
          m_state = State.SCHEDULE_DRIVE_BACK_2;
        break;
      case SCHEDULE_DRIVE_BACK_2:
        m_driveBack2.schedule();
        m_state = State.DRIVE_BACK_2;
      case DRIVE_BACK_2:
        if (m_driveBack2.isFinished())
          m_state = State.DONE;
        break;

      case DONE:
        m_isFinished = true;
        break;
      default:
        break;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_isFinished;
  }
}

