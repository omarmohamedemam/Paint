package eg.edu.alexu.csd.oop.draw.shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Map;

public class Polygon extends SuperShape{



	@Override
	public Point getPosition() {
		return this.position;
	}


	@Override
	public void draw(Graphics canvas) {
		int count = 1,sumx = 0,sumy = 0;
		int[] x = new int[this.getProperties().get("N=").intValue()];
		int[] y = new int[x.length];
		for(int i = 0 ; i < x.length ; i++){
			x[i] = this.getProperties().get("v"+count+"x").intValue();
			sumx += x[i];
			y[i] = this.getProperties().get("v"+count+"y").intValue();
			sumy += y[i];
			count++;
		}
		this.setPosition(new Point(sumx/count,sumy/count));
		canvas.setColor(this.getColor());
		canvas.drawPolygon(x,y,x.length);
		canvas.setColor(this.getFillColor());
		canvas.fillPolygon(x,y,x.length);
	}
	
	@Override
	public Object clone(){
		Shape cPolygon = new Polygon();
		cPolygon.setPosition(this.getPosition());
		cPolygon.setProperties(this.getProperties());
		cPolygon.setColor(this.getColor());
		cPolygon.setFillColor(this.getFillColor());
		return cPolygon;
	}

}



