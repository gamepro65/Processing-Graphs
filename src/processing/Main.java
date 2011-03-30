
package processing;

import processing.core.*;
import java.awt.Color;
/*
 * Created By Christopher Drury
 * 3/29/2011
 * Processing Graphs
 */


public class Main extends PApplet {

    //Our Array Counters
    public int valuesCount, labelsCount;


    //Our Arrays that will hold our values, labels and color data
    public Float values[];
    public String labels[];
    public Color colors[];

    @Override
    public void setup(){

        //The arrays length needs to be the number of values we have - 1
        //because our data starts at arg 1 therefore we need to take that
        //into consideration as well as dividing it by 2 because
        //we have data and labels.
        values = new Float[((args.length-1)/2)];
        labels = new String[((args.length-1)/2)];
        colors = new Color[((args.length-1)/2)];
        //Init counters to 0;
        valuesCount = 0;
        labelsCount = 0;
        //Loop through graph args to grab out the label and data
        //We want data after arg 0,1,2 as that is our width, height, and name
        for (int x = 3; x < args.length; x++)
        {
            //Random numbers to make colors
            double random1, random2, random3;
            random1 = Math.random();
            random2 = Math.random();
            random3 = Math.random();
            //Assign our color index our new random color
            Color newColor =
                    new Color((float)random1, (float)random2, (float)random3);            
            colors[valuesCount] = newColor;
            //If it is an even number we know that this is our graph label
            if ((x % 2) == 0)
            {
                //Increase the label counter while assigning in 1 line.
                labels[labelsCount++] = args[x];
            }
            //If it is an odd number we know its our graph data
            else
            {
                //Increase the value counter while assigning in 1 line
                //Parse the string into a Float
                values[valuesCount++] = Float.parseFloat(args[x]);
            }
        }

        //Set the size of our screen to the arg at element 1 and 2
        size(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
        //Set background color
        background(204);


    }


    @Override
    public void draw()
    {
        //Draw a fresh canvas.
      background(204);
      //Gather our screen width
      float width = Float.parseFloat(args[1]);
      //take the screen width, subtract 5 * the number of data values we have
      //plus one that way we have the correct ammount of blank space
      //divide whats left over from the initial width by the number of values
      //this will give us the width of each individual rectangle
      float rectWidth = (width-(5*(valuesCount+1)))/valuesCount;
      //Will hold our tallest rectangle
      float tallestRect = 0;
      //Loop through the values to find the biggest one
      //This will be used in determining our ratio for all other rectangles
      for (int x = 0; x < valuesCount; x++)
      {
          if (values[x] > tallestRect)
              tallestRect = values[x];
      }

      //Begin drawing rectangles
      for (int x = 0; x < valuesCount; x++)
      {
          //set the color of the rectangle to our random color generated in
          //the setup at our same index
        fill(colors[x].getRed(), colors[x].getGreen(), colors[x].getBlue());
        //Grab our current rectangle ratio compared to max
        float ratio = values[x]/tallestRect;

        //Our starting point is our total height - (the total height - 20) * our
        //ratio based on our tallest rect in order to get the starting draw
        //point for our rectangle
        float rectStartingPointHeight = Float.parseFloat(args[2]) - 
                ((Float.parseFloat(args[2])-20) * ratio);

        //draw our rectangle, this looks at what value count we are at
        //then multiplies the number of rects before it and the number of 5 pixel
        //blank spaces required adds them together and says thats our starting point.
        //then uses our rectWidth and our screen height - rectStartingPointHeight
        //to tell the rect how far down it has to go to reach the botom of the screen
        rect((5*(x+1) + (rectWidth*x)), rectStartingPointHeight,
                rectWidth, Float.parseFloat(args[2])-rectStartingPointHeight);

        //Reset the color to white as its easily readable on most colors
        fill(255, 255, 255, 255);

        //set text alignment to center
        textAlign(CENTER);

        //draw the label at the middle of the rectangle of the current rectangle
        //The formula takes the blank space up to this point and the number of rectWidths
        //to this point and then adds my current width/2 because that is the
        //middle of my current rect, then draws it 10 pixels above the bottom of the page.
        text(labels[x], (5*(x+1) + (rectWidth*x))+((rectWidth/2)),
                Float.parseFloat(args[2])-10);

        //This checks to see if our mouseX and mouseY locations are bound
        //inside of the current rectangle. If it is then it draws the exact
        //data that it knows for that rectangle. Doing it here makes sence
        //because this is the rect we also happen to be drawing. However if you
        //get close to the right side of the rect then the next rectangle will
        //be drawn over your text, To fix we could just do another loop
        //outside of this one through the data again in order to
        //draw the text after it has drawn the
        //rectangles and put it ontop. However that just will waste more cycles

        if ((mouseX < (5*(x+1) + (rectWidth*x)) + rectWidth)
                && (mouseX > (5*(x+1) + (rectWidth*x))))
        {
            if ((mouseY <
                    rectStartingPointHeight +
                    Float.parseFloat(args[2])-rectStartingPointHeight)
                    && (mouseY > rectStartingPointHeight))
            {
                text(values[x], mouseX, mouseY-15);
            }
        }
      }
    }


    public static void main(String[] args) {
        //Create an array which will be sent to the PApplet's main function
        //This array should include the arguments from the comand line and
        //the arguments that processing requires. The first two will be used by
        //the Processing API and the rest will be used by our program.
        String tempArgs[] = new String[args.length + 2];

        //Set the parameters which will be used by Processing's API
        tempArgs[0] = "--bgcolor=#FFFFFF";
        tempArgs[1] = "processing.Main";

        //Append the arguments from the command line to the end of the tempArray
        for(int i = 2; i < tempArgs.length; i++){
            tempArgs[i] = args[i-2];
        }

        //Send the String array to the PApplet's main function.
        PApplet.main(tempArgs);
    }
}
