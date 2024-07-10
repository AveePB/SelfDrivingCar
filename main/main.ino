#include "UltrasonicVision.h"
#include "MotorControl.h"
#include <NewPing.h>
#include <AFMotor.h>
#include <Servo.h>

// DC Motor pins
#define RB_PIN 1
#define RF_PIN 2
#define LF_PIN 3
#define LB_PIN 4

// Ultrasonic Sensor pins
#define ECHO_PIN A2
#define TRIG_PIN A3

// Other Consts
#define BROADCAST_PORT 9600
#define MAX_DIST_CM 200
#define SERVO_PIN 10

// Controllable components
AF_DCMotor rb_motor(RB_PIN, MOTOR12_64KHZ), rf_motor(RF_PIN, MOTOR12_64KHZ), lf_motor(LF_PIN, MOTOR12_64KHZ), lb_motor(LB_PIN, MOTOR12_64KHZ);
NewPing sonar(TRIG_PIN, ECHO_PIN, MAX_DIST_CM);
Servo servoLook;

void setup() {
  // Establish communication
  Serial.begin(BROADCAST_PORT);

  // Set up servo
  servoLook.attach(SERVO_PIN);
  servoLook.write(90);

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
  CarMovement::moveForward(&rb_motor, &rf_motor, &lf_motor, &lb_motor);
  bool isObstacle = CarVision::checkCenter(&servoLook, &sonar);

  if (isObstacle) {
    CarMovement::stop(&rb_motor, &rf_motor, &lf_motor, &lb_motor);

    bool isLeftFree = !CarVision::checkLeft(&servoLook, &sonar);
    bool isRightFree = !CarVision::checkRight(&servoLook, &sonar);
    
    // Turn back
    if (!isLeftFree && !isRightFree) {
      CarMovement::turnRight(&rb_motor, &rf_motor, &lf_motor, &lb_motor);
      CarMovement::turnRight(&rb_motor, &rf_motor, &lf_motor, &lb_motor);
    }
    // Turn left
    else if (isLeftFree) {
      CarMovement::turnLeft(&rb_motor, &rf_motor, &lf_motor, &lb_motor);
    }
    // Turn right
    else {
      CarMovement::turnRight(&rb_motor, &rf_motor, &lf_motor, &lb_motor);
    }
  }

}
