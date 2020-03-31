package eg.edu.alexu.csd.oop.draw.shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

public class Circle extends SuperShape{


		@Override
		public void draw(Graphics canvas) {
			int raduis = properties.get("raduis").intValue();

			Graphics2D g = (Graphics2D) canvas;
			g.setColor(this.color);
			g.drawOval(position.x, position.y, raduis , raduis);
			g.setColor(this.fillcolor);
			g.fillOval(position.x, position.y, raduis , raduis);

		}


	 public Object clone() throws CloneNotSupportedException {
		  Shape ccircle = new Circle();
		  ccircle.setPosition(this.getPosition());
		  ccircle.setProperties(this.getProperties());
		  ccircle.setColor(this.getColor());
		  ccircle.setFillColor(this.getFillColor());
		  return ccircle;
	}


}


