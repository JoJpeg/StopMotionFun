/**
 * Time Displacement
 * by David Muth 
 * 
 * Keeps a buffer of video frames in memory and displays pixel rows
 * taken from consecutive frames distributed over the y-axis 
 */ 
 
import processing.video.*;

Capture video;
int signal = 0;

//the buffer for storing video frames
ArrayList frames = new ArrayList();

void setup() {
  size(640, 480);
  
  // This the default video input, see the GettingStartedCapture 
  // example if it creates an error
  video = new Capture(this, renderWidth, renderHeight);
  
  // Start capturing the images from the camera
  video.start();  
}

void captureEvent(Capture camera) {
  camera.read();
  
  // Copy the current video frame into an image, so it can be stored in the buffer
  PImage img = createImage(renderWidth, renderHeight, RGB);
  video.loadPixels();
  arrayCopy(video.pixels, img.pixels);
  
  frames.add(img);
  
  // Once there are enough frames, remove the oldest one when adding a new one
  if (frames.size() > renderHeight/4) {
    frames.remove(0);
  }
}

void draw() {
 // Set the image counter to 0
 int currentImage = 0;
 
 loadPixels();
  
  // Begin a loop for displaying pixel rows of 4 pixels renderHeight
  for (int y = 0; y < video.renderHeight; y+=4) {
    // Go through the frame buffer and pick an image, starting with the oldest one
    if (currentImage < frames.size()) {
      PImage img = (PImage)frames.get(currentImage);
      
      if (img != null) {
        img.loadPixels();
        
        // Put 4 rows of pixels on the screen
        for (int x = 0; x < video.renderWidth; x++) {
          pixels[x + y * renderWidth] = img.pixels[x + y * video.renderWidth];
          pixels[x + (y + 1) * renderWidth] = img.pixels[x + (y + 1) * video.renderWidth];
          pixels[x + (y + 2) * renderWidth] = img.pixels[x + (y + 2) * video.renderWidth];
          pixels[x + (y + 3) * renderWidth] = img.pixels[x + (y + 3) * video.renderWidth];
        }  
      }
      
      // Increase the image counter
      currentImage++;
       
    } else {
      break;
    }
  }
  
  updatePixels();
  
  // For recording an image sequence
  //saveFrame("frame-####.jpg"); 
}




