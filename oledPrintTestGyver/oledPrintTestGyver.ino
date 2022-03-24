#include <GyverOLED.h>
//GyverOLED<SSD1306_128x32, OLED_BUFFER> oled;
//GyverOLED<SSD1306_128x32, OLED_NO_BUFFER> oled;
//GyverOLED<SSD1306_128x64, OLED_BUFFER> oled;
//GyverOLED<SSD1306_128x64, OLED_NO_BUFFER> oled;
//GyverOLED<SSD1306_128x64, OLED_BUFFER, OLED_SPI, 8, 7, 6> oled;
GyverOLED<SSD1306_128x64> oled;
#define SLIDER_PIN  A0
int oldPotValue1 = 0;
int oldPotValue2 = 0;
byte oldVol = 0;
int curPotValue1;
byte vol;
void setup() {
  Serial.begin(9600);
  oled.init();              // инициализация
  oled.setScale(2);
  oled.setCursor(0, 3);
  oled.print("Vol = ");
  oled.update();
}

void loop() {
  curPotValue1 = analogRead(SLIDER_PIN);
  vol = map(curPotValue1, 0, 1023, 100, 0);
  // выводим показания в Serial-порт
  if (vol != oldVol && abs(vol - oldVol) > 1) {
    //  if (vol != oldVol) {
    Serial.print("Volume = ");
    Serial.println(vol);
    oled.setCursor(67, 3);
    oled.print("    ");
    oled.setCursor(67, 3);
    oled.print(vol);
    oled.update();

    oldVol = vol;
  }
}

void printScale(byte x) {
  //  oled.clear();
  //  oled.setScale(x);
  //  for (byte i = 0; i < 8; i += x) {
  //    oled.setCursor(0, i);
  //    oled.print("Hello!");
  //  }
  //  oled.update();
  //  delay(1000);
}
