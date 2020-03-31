package eg.edu.alexu.csd.oop.draw.mouseUtility;
 import eg.edu.alexu.csd.oop.draw.DrawingUtility.Drawing;
 import eg.edu.alexu.csd.oop.draw.shapes.*;


 import java.awt.Point;

public class SelectionTool {
 

    private double Triangle_Area(Point a,Point b,Point c) {
        return Math.abs(((a.x * (b.y - c.y) + b.x * (c.y - a.y) + c.x * (a.y - b.y))) / 2);
    }
   
    public Shape SelectedShape(Point p) {
        Shape[] shapes = Drawing.getInstance().getShapes();

        for ( Shape shape : shapes) {
            if(shape instanceof Triangle) {
                Point v1 = new Point(shape.getProperties().get("v1x").intValue(),shape.getProperties().get("v1y").intValue());
                Point v2 = new Point(shape.getProperties().get("v2x").intValue(),shape.getProperties().get("v2y").intValue());
                Point v3 = new Point(shape.getProperties().get("v3x").intValue(),shape.getProperties().get("v3y").intValue());
                if(Triangle_Area(v1, v2, p)+Triangle_Area(v1, v3, p)+Triangle_Area(v2, v3, p) - Triangle_Area(v1, v2, v3) <= 0) {
                    return shape;
                }  
            }

            else if(shape instanceof Square || shape instanceof Rectangle || shape instanceof Ellipse){
                int flag1,flag2;
                if(shape.getPosition().getX() < shape.getProperties().get("ex")) flag1 = -1;
                else flag1 = 1;

                if(shape.getPosition().getY() < shape.getProperties().get("ey")) flag2 = -1;
                else flag2 = 1;

                if(shape instanceof Square) {
                    if (flag1*p.getX() < flag1*(shape.getProperties().get("ex") + flag1 * shape.getProperties().get("length")) && flag1*p.getX() > flag1*shape.getProperties().get("ex")) {
                        if (flag2*p.getX() < flag2*(shape.getProperties().get("ey") + flag2 * shape.getProperties().get("length")) && flag2*p.getY() > flag2*shape.getProperties().get("ey")){
                            return shape;}
                    }
                }
                else {
                    if (flag1*p.getX() < flag1*(shape.getProperties().get("ex") + flag1 * shape.getProperties().get("width")) && flag1*p.getX() > flag1*shape.getProperties().get("ex")) {
                        if (flag2*p.getX() < flag2*(shape.getProperties().get("ey") + flag2 * shape.getProperties().get("height")) && flag2*p.getY() > flag2*shape.getProperties().get("ey"))
                            return shape;
                    }
                }
            }

            else if(shape instanceof Circle) {
                 Double raduis = shape.getProperties().get("raduis");
                 if((p.x-shape.getPosition().getX())*(p.x-shape.getPosition().getX()) + (p.y-shape.getPosition().getY())*(p.y-shape.getPosition().getY()) < raduis*raduis) {
                    return shape;
                 }
            }

            else if(shape instanceof Line){
                double slope = Math.abs((shape.getPosition().getY()-shape.getProperties().get("ey")) / (shape.getPosition().getX()-shape.getProperties().get("ex")));
                if(Math.abs(slope - Math.abs((p.getY()-shape.getPosition().getY()) / (p.getX()-shape.getPosition().getX()))) < .1)
                    return shape;
            }

            else if(shape instanceof Polygon) {
                Object[] properties = shape.getProperties().keySet().toArray();
                Point center = shape.getPosition();
                for(int j = 0 ; j < properties.length ; j+=2){
                    if(!properties[j].equals("N=") && !properties[j].toString().contains("y") && p.distance(new Point(shape.getProperties().get(properties[j]).intValue(),shape.getProperties().get(properties[j].toString().replace("x","y")).intValue())) < p.distance(center)/2){
                        return shape;}
                }
            }

        }

        return null;
    }
   
   
   
   
   
   
   
   
   
   
   
}