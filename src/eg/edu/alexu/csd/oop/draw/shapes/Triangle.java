package eg.edu.alexu.csd.oop.draw.shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

public class Triangle extends SuperShape{


	@Override
	public void draw(Graphics canvas) {

		int[] x = new int[3];//= {v[0].x,v[1].x,v[2].x};
		int[] y = new int[3];//= {v[0].y,v[1].y,v[2].y};
		if(!properties.isEmpty()) {
			x[0] = properties.get("v1x").intValue();
			x[1] = properties.get("v2x").intValue();
			x[2] = properties.get("v3x").intValue();
			y[0] = properties.get("v1y").intValue();
			y[1] = properties.get("v2y").intValue();
			y[2] = properties.get("v3y").intValue();
		}

		Graphics2D g = (Graphics2D) canvas;

		g.setColor(color);
		g.drawPolygon(x, y, 3);
		g.setColor(fillcolor);
		g.fillPolygon(x, y, 3);

	}
	
	@Override
	public Object clone(){
		Triangle cTriangle = new Triangle();
		cTriangle.setColor(this.getColor());
		cTriangle.setFillColor(this.getFillColor());
		cTriangle.setPosition(this.getPosition());
		cTriangle.setProperties(this.getProperties());

		return cTriangle;
	}

}
