#include <OLED_I2C.h>

OLED  myOLED(SDA, SCL);

extern uint8_t SmallFont[];
//extern uint8_t MediumNumbers[];
//extern uint8_t BigNumbers[];

#define SLIDER_PIN  A0
int oldPotValue1 = 0;
int oldPotValue2 = 0;
byte oldVol = 0;
int curPotValue1;
byte vol;

void setup()
{
  Serial.begin(2000000);
  myOLED.begin();
  myOLED.setFont(SmallFont);
  myOLED.print("Vol = ", LEFT, 14);
  myOLED.update();
 // myOLED.setFont(MediumNumbers);
}

void loop()
{
  curPotValue1 = analogRead(SLIDER_PIN);
  vol = map(curPotValue1, 0, 1023, 100, 0);
  // выводим показания в Serial-порт
  if (vol != oldVol && abs(vol - oldVol) > 1) {
    //  if (vol != oldVol) {
    Serial.print("Volume = ");
    Serial.println(vol);
    String myString = String(vol);
    myOLED.print("   ", 30, 14);
    myOLED.print(myString, 30, 14);
//    myOLED.setCursor(67, 3);
//    myOLED.print("    ");
//    myOLED.setCursor(67, 3);
//    myOLED.print(vol);
    myOLED.update();
    oldVol = vol;
  }

  //  for (int i=0; i<=100; i++)
  //  {
  //    myOLED.setFont(MediumNumbers);
  //    myOLED.printNumF(float(i)/3, 2, RIGHT, 0);
  //    myOLED.setFont(BigNumbers);
  //    myOLED.printNumI(i, RIGHT, 40);
  //    myOLED.update();
  //  }
  //
  //  myOLED.setFont(SmallFont);
  //  myOLED.print("|", LEFT, 24);
  //  myOLED.print("|", RIGHT, 24);
  //  myOLED.update();
  //  delay(500);
  //  for (int i=0; i<19; i++)
  //  {
  //    myOLED.print("\\", 7+(i*6), 24);
  //    myOLED.update();
  //    delay(250);
  //  }
  //  myOLED.clrScr();
}
