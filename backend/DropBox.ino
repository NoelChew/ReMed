/* 
   lab2.ino

   AT&T DevLab pushbutton demo
*/
#include <aJSON.h>
#include "SPI.h"
#include "WiFi.h"

#include "M2XStreamClient.h"

char ssid[] = "L3wifi"; //  your network SSID (name)
char pass[] = "abcde12345"; // your network password (use for WPA, or use as key for WEP)
int keyIndex = 0;            // your network key Index number (needed only for WEP)

int status = WL_IDLE_STATUS;

char deviceId[] = "e339458b9bbc9b844d1ce34146219c59"; // Feed you want to post to
char m2xKey[] = "7ad5dc35a82080247d1f443a8d6210af"; // Your M2X access key
char streamName1[] = "Temperature"; // Stream you want to post to
char streamName2[] = "UV"; // Stream you want to post to
char streamName3[] = "Moisture"; // Stream you want to post to
char streamName4[] = "Latitude"; // Stream you want to post to
char streamName5[] = "Longitude"; // Stream you want to post to

WiFiClient client;
M2XStreamClient m2xClient(&client, m2xKey);

int btn1 = 1;  // The button state
boolean pushed = false; // True when the button is pushed
int lastSubmit = 0;  // The time in milliseconds from when the button is pushed.

void setup() {

    Serial.begin(9600);
    pinMode(PUSH1, INPUT_PULLUP);
    
    // attempt to connect to Wifi network:
    Serial.print("Attempting to connect to Network named: ");
    // print the network name (SSID);
    Serial.println(ssid); 
    // Connect to WPA/WPA2 network. Change this line if using open or WEP network:
    WiFi.begin(ssid, pass);
    while ( WiFi.status() != WL_CONNECTED) {
      // print dots while we wait to connect
      Serial.print(".");
      delay(300);
    }
  
    Serial.println("\nYou're connected to the network");
    Serial.println("Waiting for an ip address");
  
    while (WiFi.localIP() == INADDR_NONE) {
      // print dots while we wait for an ip addresss
      Serial.print(".");
      delay(300);
    }

    Serial.println("\nIP Address obtained");
  
    // you're connected now, so print out the status  
    printWifiStatus();

    lastSubmit = millis() - 2000;
}

void loop() {
  // Read the pushbutton state. 1 for not pushed, 0 for pushed.

    btn1 = digitalRead(PUSH1);
    if (btn1 == 1 && pushed) {
          // Reset pushed
          pushed = false;
    }
         
    while( (lastSubmit + 2000) > millis() )
    {
      
        btn1 = digitalRead(PUSH1);
      
        if (btn1 == 0 && !pushed) {
          // If pushed, then store the time
          pushed = true;
          
        }
         
    
        delay(250);
    }
    
    // Released. Calculate the time elapsed
    double tDeviate = random(10);
    Serial.print("temperature: ");
    Serial.println(tDeviate + 30);

    if (pushed)
    {
      Serial.print("temperature spiked!! ");
      tDeviate += 15;
      Serial.println(tDeviate + 30);
    }
    
    // Send to M2X
    int response = m2xClient.updateStreamValue(deviceId, streamName1, tDeviate + 30);
    Serial.print("M2X client response code: ");
    Serial.println(response);
    if (response == -1)
      while (1)
        ;

    tDeviate = random(2);
    
    response = m2xClient.updateStreamValue(deviceId, streamName2, 10 + tDeviate);
    Serial.print("M2X client response code: ");
    Serial.println(response);
    if (response == -1)
      while (1)
        ;
    

    response = m2xClient.updateStreamValue(deviceId, streamName3, 5);
    Serial.print("M2X client response code: ");
    Serial.println(response);
    if (response == -1)
      while (1)
        ;
        
    response = m2xClient.updateStreamValue(deviceId, streamName4, 3.152205);
    Serial.print("M2X client response code: ");
    Serial.println(response);
    if (response == -1)
      while (1)
        ;

    response = m2xClient.updateStreamValue(deviceId, streamName5, 101.723112);
    Serial.print("M2X client response code: ");
    Serial.println(response);
    

    // If the response is an error, then send into infinite loop to stop loop
    if (response == -1)
      while (1)
        ;


    lastSubmit = millis();

}

void printWifiStatus() {
  // print the SSID of the network you're attached to:
  Serial.print("SSID: ");
  Serial.println(WiFi.SSID());

  // print your WiFi shield's IP address:
  IPAddress ip = WiFi.localIP();
  Serial.print("IP Address: ");
  Serial.println(ip);
}

