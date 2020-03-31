package eg.edu.alexu.csd.oop.draw.shapes;

import sun.security.provider.SHA;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

public class Rectangle extends SuperShape {



	@Override
	public void draw(Graphics canvas) {
	    int width = this.getProperties().get("width").intValue();
	    int height = this.getProperties().get("height").intValue();
		Graphics2D g = (Graphics2D) canvas;

		g.setColor(this.getColor());
		g.drawRect(this.getPosition().x,this.getPosition().y,width,height);
		g.setColor(this.getFillColor());
		g.fillRect(this.getPosition().x,this.getPosition().y,width,height);
	}
	
	@Override
	public Object clone(){
		Shape cRectangle = new Rectangle();
		cRectangle.setPosition(this.getPosition());
		cRectangle.setProperties(this.getProperties());
		cRectangle.setColor(this.getColor());
		cRectangle.setFillColor(this.getFillColor());

		return cRectangle;
	}

	
}
