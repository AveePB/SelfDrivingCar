#include "UltrasonicVision.h"
#include <Arduino.h>
#include <NewPing.h>
#include <Servo.h>

namespace CarVision {
  // Variable definition
  int stopDist = 60;

  // Function implementations
  bool checkLeft(Servo* servoLook, NewPing* sonar) {
    // Change position
    servoLook->write(180);
    delay(500);

    // Measure distance
    int dist = sonar->ping_cm();
    servoLook->write(90);

    if (dist == 0) return false;
    return dist <= stopDist;
  }

  bool checkCenter(Servo* servoLook, NewPing* sonar) {
    // Change position
    servoLook->write(90);
    delay(500);

    // Measure distance
    int dist = sonar->ping_cm();

    if (dist == 0) return false;
    return dist <= stopDist;
  }

  bool checkRight(Servo* servoLook, NewPing* sonar) {
    // Change position
    servoLook->write(0);
    delay(500);

    // Measure distance
    int dist = sonar->ping_cm();
    servoLook->write(90);

    if (dist == 0) return false;
    return dist <= stopDist;
  }
}
