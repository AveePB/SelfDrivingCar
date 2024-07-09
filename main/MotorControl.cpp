#include "MotorControl.h"
#include <Arduino.h>
#include <AFMotor.h>

namespace CarMovement {
  // Variable definitions
  int motorSpeed = 80, turnSpeed = 100, offset = 20;

  // Function implementations
  void moveForward(AF_DCMotor* rightBack, AF_DCMotor* rightFront, AF_DCMotor* leftFront, AF_DCMotor* leftBack) {
    // Set motor speeds
    rightBack->setSpeed(motorSpeed);
    rightFront->setSpeed(motorSpeed);
    leftFront->setSpeed(motorSpeed+offset);
    leftBack->setSpeed(motorSpeed+offset);

    // Set motor states
    rightBack->run(FORWARD);
    rightFront->run(FORWARD);
    leftFront->run(FORWARD);
    leftBack->run(FORWARD);
  }

  void turnLeft(AF_DCMotor* rightBack, AF_DCMotor* rightFront, AF_DCMotor* leftFront, AF_DCMotor* leftBack) {
    // Set motor turn speeds
    rightBack->setSpeed(turnSpeed);
    rightFront->setSpeed(turnSpeed);
    leftFront->setSpeed(turnSpeed+offset);
    leftBack->setSpeed(turnSpeed+offset);

    // Set motor states
    rightBack->run(FORWARD);
    rightFront->run(FORWARD);
    leftFront->run(BACKWARD);
    leftBack->run(BACKWARD);

    // Keep turning (0.475s)
    delay(475);

    // Stop movement
    CarMovement::stop(rightBack, rightFront, leftFront, leftBack);
    delay(1000);
  }

  void turnRight(AF_DCMotor* rightBack, AF_DCMotor* rightFront, AF_DCMotor* leftFront, AF_DCMotor* leftBack) {
    // Set motor turn speeds
    rightBack->setSpeed(turnSpeed);
    rightFront->setSpeed(turnSpeed);
    leftFront->setSpeed(turnSpeed+offset);
    leftBack->setSpeed(turnSpeed+offset);

    // Set motor states
    rightBack->run(BACKWARD);
    rightFront->run(BACKWARD);
    leftFront->run(FORWARD);
    leftBack->run(FORWARD);

    // Keep turning (0.555s)
    delay(465);

    // Stop movement
    CarMovement::stop(rightBack, rightFront, leftFront, leftBack);
    delay(1000);
  }

  void stop(AF_DCMotor* rightBack, AF_DCMotor* rightFront, AF_DCMotor* leftFront, AF_DCMotor* leftBack) {
    // Set motor turn speeds
    rightBack->setSpeed(0);
    rightFront->setSpeed(0);
    leftFront->setSpeed(0);
    leftBack->setSpeed(0);

    // Set motor states
    rightBack->run(RELEASE);
    rightFront->run(RELEASE);
    leftFront->run(RELEASE);
    leftBack->run(RELEASE);
  }
}
