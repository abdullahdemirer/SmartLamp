#include <SoftwareSerial.h>
SoftwareSerial port1(10,11)

int veri;
int ledPin = 8;
void setup() {
  Serial.begin(9600);
  port1.begin(9600);
  pinMode(ledPin,OUTPUT);
}

void loop() {
  if(port1.available()){

      veri = port1.read();
  }
  if(veri == '1'){
    digitalWrite(ledPin,HIGH);
  }else if(veri == '2'){
    digitalWrite(ledPin,LOW);
  }
}
