package eg.edu.alexu.csd.oop.draw.shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

public class Square extends SuperShape{


	@Override
	public void draw(Graphics canvas) {
		int length = this.getProperties().get("length").intValue();
		Graphics2D g = (Graphics2D) canvas;

	    g.setColor(color);
		g.drawRect(getPosition().x,getPosition().y,length,length); //Math.abs(getPosition().x-getProperties().get("ex").intValue()),Math.abs(getPosition().x-getProperties().get("ex").intValue()));// Math.abs(getPosition().y-getProperties().get("ey").intValue()));
		g.setColor(fillcolor);
		g.fillRect(getPosition().x,getPosition().y,length,length);
	}
	
	@Override
	public Object clone(){
		Shape cSquare = new Square();
		cSquare.setPosition(this.getPosition());
		cSquare.setProperties(this.getProperties());
		cSquare.setColor(this.getColor());
		cSquare.setFillColor(this.getFillColor());
		return cSquare;
	}

}
