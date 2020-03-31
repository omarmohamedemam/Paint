package eg.edu.alexu.csd.oop.draw.shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

public class Ellipse extends SuperShape{




	@Override
	public void draw(Graphics canvas) {
		int width = getProperties().get("width").intValue();
		int height = getProperties().get("height").intValue();
		Graphics2D g = (Graphics2D) canvas;
		g.setColor(color);
		g.drawOval(getPosition().x, getPosition().y, width, height);
		g.setColor(fillcolor);
		g.fillOval(getPosition().x, getPosition().y, width, height);
	}

	 public Object clone() {
		 Shape cEllipse = new Ellipse();
		 cEllipse.setPosition(this.getPosition());
		 cEllipse.setProperties(this.getProperties());
		 cEllipse.setColor(this.getColor());
		 cEllipse.setFillColor(this.getFillColor());
		 return cEllipse;
	 }


}


