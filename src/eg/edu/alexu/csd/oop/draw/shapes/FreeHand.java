package eg.edu.alexu.csd.oop.draw.shapes;

import eg.edu.alexu.csd.oop.draw.shapes.SuperShape;

import java.awt.*;
import java.util.ArrayList;

public class FreeHand extends SuperShape {

     private ArrayList<Point>points = new ArrayList<>();


     public FreeHand(Point e){
         points.add(e);
     }


     @Override
     public void setPosition(Point e){
         points.add(e);
     }

    @Override
     public void draw(Graphics canvas) {
         canvas.setColor(this.getColor());
    	   for (int i = 0; i < points.size()-1; i++)
    	       canvas.drawLine(points.get(i).x, points.get(i).y, points.get(i+1).x, points.get(i+1).y);
     }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Shape cFreeHand = new FreeHand(points.get(0));
        cFreeHand.setColor(this.getColor());
        for(int i = 1; i < points.size(); i++) {
            cFreeHand.setPosition(points.get(i));
        }
        return cFreeHand;
    }


}
     
