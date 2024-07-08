#ifndef MOTORCONTROL_H
#define MOTORCONTROL_H
#include <AFMotor.h>

namespace CarMovement {
  // Variables
  extern int motorSpeed, turnSpeed; 

  // Functions
  void moveForward(AF_DCMotor* rightBack, AF_DCMotor* rightFront, AF_DCMotor* leftFront, AF_DCMotor* leftBack);
  void turnLeft(AF_DCMotor* rightBack, AF_DCMotor* rightFront, AF_DCMotor* leftFront, AF_DCMotor* leftBack);
  void turnRight(AF_DCMotor* rightBack, AF_DCMotor* rightFront, AF_DCMotor* leftFront, AF_DCMotor* leftBack);
  void stop(AF_DCMotor* rightBack, AF_DCMotor* rightFront, AF_DCMotor* leftFront, AF_DCMotor* leftBack);
}

#endif