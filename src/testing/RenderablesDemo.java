package testing;

import java.awt.Color;
import java.awt.Font;
import dataStructures.IntPoint;
import primitiveRenderables.RenderableOval;
import primitiveRenderables.RenderablePoint;
import primitiveRenderables.RenderablePolyline;
import primitiveRenderables.RenderableString;
import easyGui.EasyGui;

public class RenderablesDemo
{
	public static void main(String[] args)
	{
		// Create the GUI with given width and height.
		int width = 800;
		int height = 600;
		EasyGui gui = new EasyGui(width, height);
		
		// Create some Renderables.
		RenderableOval anOval = createOval();
		RenderablePoint aPoint = createPoint();
		RenderablePolyline aLine = createPolyline();
		RenderableString aString = createString();
		
		// Now draw the objects.
		gui.draw(anOval);
		gui.draw(aPoint);
		gui.draw(aLine);
		gui.draw(aString);
		
		// If you want to delete something you can use the "unDraw" method.
		gui.unDraw(aPoint);
		
		// To add it back just call draw again.
		gui.draw(aPoint);
		
		// To remove everything call
		//gui.clearGraphicsPanel();
		
		// After you call the "draw" method you need to update the GUI for the changes
		// to take effect.
		gui.update();
		
		// Show the GUI on the screen. You can also draw and update when the GUI is already
		// shown on the screen.
		gui.show();
	}
	
	public static RenderableOval createOval()
	{
		// The centre point of the oval.
		IntPoint centrePoint = new IntPoint(100, 150);
		
		// Its width and height.
		int width = 100;
		int height = 80;
		
		// Creating the oval.
		RenderableOval oval = new RenderableOval(centrePoint, width, height);
		
		// Set the colour, pen width (line thickness) and the "isFilled" flag.
		// If isFilled == true then the oval will be solid. Otherwise only the
		// outline is drawn.
		oval.setProperties(Color.BLACK, 1.0f, true);
		
		return oval;
	}
	
	public static RenderablePoint createPoint()
	{
		// Create a point at the given (x, y) position.
		RenderablePoint point = new RenderablePoint(100, 150);
		
		// Set colour, pen width and whether or not the point should be round.
		// If "isRounded" is false, a square point is drawn.
		point.setProperties(Color.RED, 10.0f, false);
		
		// The default layer of a new Renderable is zero. To ensure that something is drawn
		// on top of another it needs to have a higher layer number. Renderables with the same
		// layer may be drawn in any order but will typically be in the order they were added
		// to the GUI using the "draw" method.
		point.setLayer(2);
		
		return point;
	}
	
	public static RenderablePolyline createPolyline()
	{
		// Create a RenderablePolyline with no initial points.
		RenderablePolyline line = new RenderablePolyline();
		
		// Now add points. A line will be drawn from each point to the next.
		line.addPoint(50, 300);
		line.addPoint(200, 300);
		line.addPoint(200, 50);
		
		// Set line colour and thickness.
		line.setProperties(Color.BLUE, 8.0f);
		
		return line;
	}
	
	public static RenderableString createString()
	{
		// Create a RenderableString at the given (x, y) point.
		RenderableString string = new RenderableString(100, 400, "Hello World!");
		
		// Create a font using its name, style and size.
		// See http://docs.oracle.com/javase/1.4.2/docs/api/java/awt/Font.html
		Font myFont = new Font("Courier New", Font.ITALIC, 64);
		
		// Set text colour and font.
		string.setProperties(Color.ORANGE, myFont);
		
		return string;
	}
	
	/*
	 * Other Renderables can be found in the "primitiveRenderables" package of the AiToolBox.
	 * They all work in a similar way as shown above.
	 */
}
