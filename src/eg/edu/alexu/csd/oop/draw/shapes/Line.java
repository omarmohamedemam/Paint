package eg.edu.alexu.csd.oop.draw.shapes;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;




public class Line extends SuperShape {




	@Override
	public void draw(Graphics canvas) {
		int ex = this.getProperties().get("ex").intValue();
		int ey = this.getProperties().get("ey").intValue();

		Graphics2D g = (Graphics2D) canvas;

		g.setColor(color);
		g.drawLine(getPosition().x,getPosition().y,ex,ey);
	}
	
	@Override
	public Object clone(){

		Shape cLine = new Line();
		cLine.setPosition(this.getPosition());
		cLine.setProperties(this.getProperties());
		cLine.setColor(this.getColor());

		return cLine;
	}

}
