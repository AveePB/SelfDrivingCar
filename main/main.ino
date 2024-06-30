#include "MotorControl.h"
#include <AFMotor.h>
#include <Servo.h>

// DC Motor pins
#define RB_PIN 1
#define RF_PIN 2
#define LF_PIN 3
#define LB_PIN 4

#define BROADCAST_PORT 9600
#define SERVO_PIN 10

// Controllable components
AF_DCMotor rb_motor(RB_PIN), rf_motor(RF_PIN), lf_motor(LF_PIN), lb_motor(LB_PIN);
Servo servoLook;

void setup() {
  // Establish communication
  Serial.begin(BROADCAST_PORT);

  // Set up servo
  servoLook.attach(SERVO_PIN);

  // Set up motor speeds
  rb_motor.setSpeed(CarMovement::motorSpeed);
  rf_motor.setSpeed(CarMovement::motorSpeed);
  lf_motor.setSpeed(CarMovement::motorSpeed);
  lb_motor.setSpeed(CarMovement::motorSpeed);

  // Set up motor modes
  rb_motor.run(RELEASE);
  rf_motor.run(RELEASE);
  lf_motor.run(RELEASE);
  lb_motor.run(RELEASE);  
}

void loop() {
  // Simple test to go forward!!
  rb_motor.run(FORWARD);
  rf_motor.run(FORWARD);
  lf_motor.run(FORWARD);
  lb_motor.run(FORWARD);
}
