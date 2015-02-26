#include <Stepper.h>

const int stepsPerRevolution = 200;  // change this to fit the number of steps per revolution
                                     // for your motor

//Stepper motor outputs
Stepper x(stepsPerRevolution, 5,6,7,8);  
Stepper y(stepsPerRevolution, 9,10,11,12); 

//Control signal pins
int xDir = 1;
int xStep = 2;
int yDir = 3;
int yStep = 4;
int led = 13;

int xStepCurrent = 0;
int yStepCurrent = 0;
int xStepPrevious = 0;
int yStepPrevious = 0;
int xDirection = 0;
int yDirection = 0;

void setup() {

  x.setSpeed(150);
  y.setSpeed(150);
  
  pinMode(xDir, INPUT);
  pinMode(xStep, INPUT);
  pinMode(yDir, INPUT);
  pinMode(yStep, INPUT);
  pinMode(led, OUTPUT);
}

void loop() {
  xDirection = digitalRead(xDir);
  yDirection = digitalRead(yDir);
  xStepCurrent = digitalRead(xStep);
  yStepCurrent = digitalRead(yStep);
  
  
  
  if(xStepCurrent != xStepPrevious) {
    if(xStepCurrent == HIGH) {
      digitalWrite(led, HIGH);
      if(xDirection == HIGH){
        x.step(-1);       
      }else{
        x.step(1);
      }
    }
  }
  xStepPrevious = xStepCurrent;
  digitalWrite(led, LOW);
  
  
  if(yStepCurrent != yStepPrevious) {
    if(yStepCurrent == HIGH) {
      digitalWrite(led, HIGH);
      if(yDirection == HIGH){
        y.step(-1);
      }else{
        y.step(1);
      }
      
    }
  }
  yStepPrevious = yStepCurrent;
  digitalWrite(led, LOW);
}
  

