
//  [0,55:0,22;0]
//  [0,11:0,66;0]
//
//
//
//
//


int xDir = 2;
int xStep =3;
int yDir = 4;
int yStep = 5;
int electromagnet = 7;
int resetController = 8;


int charOffset = 48;
int startChar = '[';
int endChar = ']';
int deliminator = ':';
int knightDeliminator = ';';
int itemDeliminator = ',';

int squareSteps = 42;
int xCurrent = 0;
int yCurrent = 0;



void setup() {
  pinMode(xDir, OUTPUT);
  pinMode(xStep, OUTPUT);
  pinMode(yDir, OUTPUT);
  pinMode(yStep, OUTPUT);
  
  pinMode(electromagnet, OUTPUT); 
  pinMode(resetController,OUTPUT);


  Serial.begin(9600);
  digitalWrite(resetController, HIGH);
}

// the loop routine runs over and over again forever:
void loop() {
  int startPoint[3]; 
  int endPoint[3];
  int isKnight = 0;

  waitForChar();
  if(Serial.read() == startChar){
    waitForChar();
    startPoint[0] = readChar();

    waitForChar();    
    if(Serial.read() == itemDeliminator){
     
      startPoint[1] = readChar();
     
      startPoint[2] = readChar();

      waitForChar();
      if(Serial.read() == deliminator){
        
        endPoint[0] = readChar();

        waitForChar();    
        if(Serial.read() == itemDeliminator){
          
          endPoint[1] = readChar();
          
          endPoint[2] = readChar();

          waitForChar();
          if(Serial.read() == knightDeliminator){
            
            isKnight = readChar();            
            Serial.println("Command OK");
            runMove(startPoint, endPoint, isKnight);
            serialNL();
            
            Serial.println("COMPLETE");
            
            
            Serial.println("Reseting controller");
            digitalWrite(resetController, LOW);
            delay(2000);
            digitalWrite(resetController, HIGH);
            
            delay(5000);
            Serial.println("Reset Complete");
          }
        }
      }
    }

  }
}

int readChar(){
  waitForChar();
  int character = Serial.read();
  character = character - charOffset; 
  serialPrint("Reading: ", character);
  return character;
}
void waitForChar(){
  while(!Serial.available()){
  }
}



void runMove(int startPoint[], int endPoint[], int isKnight){
  
  if(isKnight == 0){
    //Go to the start point
    //Since this is not a knight we don't care about the start and end directions.
    //First we work out how to get there in number of steps
    //So we work out the square differance between the current position and the position we need to get to
    serialNL();
    Serial.println("Moving to start point");
    
    int xChange = startPoint[1] - xCurrent;
    int yChange = startPoint[2] - yCurrent;

    //Now that we know how many squares to move we run the move command
    serialPrint("xChange: ", xChange);
    serialPrint("yChange: ", yChange);    

    moveSquaresNormal(xChange,yChange);    
    Serial.println("At start point");
    serialNL();
    Serial.println("Turning on electromagnet");
    digitalWrite(electromagnet, HIGH);
    //Now that we are at the start location, we turn on the electromagnet and move
    //To the end location
    serialNL();
    Serial.println("Moving to end point");
    
   
    xChange = endPoint[1] - xCurrent;
    yChange = endPoint[2] - yCurrent;
  
    //Now that we know how many squares to move we run the move command
    serialPrint("xChange: ", xChange);
    serialPrint("yChange: ", yChange);   
    moveSquaresNormal(xChange,yChange);
    
    Serial.println("At end point");
    serialNL();
    Serial.println("Turning off electromagnet");
    digitalWrite(electromagnet, LOW);    
    
  }
}



void moveSquaresNormal(int x, int y){
  //First convert the square change to steps
  int xSteps = x * squareSteps;
  int ySteps = y * squareSteps;
  boolean xDir = xSteps > 0 ? true : false;
  boolean yDir = ySteps > 0 ? true : false;
  
  xSteps = abs(xSteps);
  ySteps = abs(ySteps);
  while(xSteps > 0 && ySteps > 0){
    if(xSteps > 0){
      stepX(xDir);
      xSteps--;     
    }
    if(ySteps > 0){
      stepY(yDir);
      ySteps--;     
    }
  }
  
  //Update the current position
  xCurrent += x;
  yCurrent += y;
}

void serialPrint(char text[], int value){
   Serial.print(text);
   Serial.println(value);
}

void serialNL(){
  Serial.println("");
}

void stepX(boolean dirCW) {
  if(dirCW){
    digitalWrite(xDir, HIGH); 
  }
  else{
    digitalWrite(xDir, LOW); 
  }
  digitalWrite(xStep, LOW);    
  digitalWrite(xStep, HIGH);
  delay(10);
  digitalWrite(xStep, LOW);  
  delay(3);
}




void stepY(boolean dirCW) {
  if(dirCW){
    digitalWrite(yDir, HIGH); 
  }
  else{
    digitalWrite(yDir, LOW); 
  }
  digitalWrite(yStep, LOW);    
  digitalWrite(yStep, HIGH);
  delay(10);
  digitalWrite(yStep, LOW);
  delay(3);
}






