// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Arm.presets;

import frc.robot.subsystems.Arm;
import frc.robot.testingdashboard.TestingDashboard;


public class ArmToPreset3 extends ArmToPreset {

  /** Creates a new ArmToPreset. */
  public ArmToPreset3(double turretAngle, double shoulderAngle, double elbowAngle, double wristAngle) {
    super(turretAngle, shoulderAngle, elbowAngle, wristAngle);
  }

  public static void registerWithTestingDashboard() {
    Arm arm = Arm.getInstance();
    ArmToPreset3 cmd = new ArmToPreset3(0, 30, 80, 45);
    TestingDashboard.getInstance().registerCommand(arm, "Automatic", cmd);
  }

}
