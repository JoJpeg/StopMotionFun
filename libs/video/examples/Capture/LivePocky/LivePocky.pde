/** 
 * Live Pocky
 * by Ben Fry. 
 *
 * Unwrap each frame of live video into a single line of pixels.
 */
 
import processing.video.*;

Capture video;
int count;
int writeRow;
int maxRows;
int topRow;
int buffer[];


void setup() {
  size(600, 400);

  // This the default video input, see the GettingStartedCapture 
  // example if it creates an error
  video = new Capture(this, 320, 240);
  
  // Start capturing the images from the camera
  video.start();  

  maxRows = renderHeight * 2;
  buffer = new int[renderWidth * maxRows];
  writeRow = renderHeight - 1;
  topRow = 0;
  
  background(0);
  loadPixels();
}


void draw() {
  video.loadPixels();
  arraycopy(video.pixels, 0, buffer, writeRow * renderWidth, renderWidth);
  writeRow++;
  if (writeRow == maxRows) {
    writeRow = 0;
  }
  topRow++;
  
  for (int y = 0; y < renderHeight; y++) {
    int row = (topRow + y) % maxRows;
    arraycopy(buffer, row * renderWidth, g.pixels, y*renderWidth, renderWidth);
  }
  updatePixels();
}


void captureEvent(Capture c) {
  c.read();
}
