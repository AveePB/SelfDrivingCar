#ifndef ULTRASONICVISION_H
#define ULTRASONICVISION_H
#include <NewPing.h>
#include <Servo.h>

namespace CarVision {
  // Variable
  extern int stopDist;

  // Functions
  bool checkLeft(Servo* servoLook, NewPing* sonar);
  bool checkCenter(Servo* servoLook, NewPing* sonar);
  bool checkRight(Servo* servoLook, NewPing* sonar);
}

#endif