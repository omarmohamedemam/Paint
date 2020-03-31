package eg.edu.alexu.csd.oop.draw.shapes;

import com.sun.javafx.collections.MappingChange;

import java.awt.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class SuperShape implements Shape {

    protected Point position;
    protected Map<String,Double> properties = new HashMap<>();
    protected Color color;
    protected Color fillcolor;

    public void setPosition(java.awt.Point position) {
        this.position = position;
    }

    public java.awt.Point getPosition() {
        return this.position;
    }

    public void setProperties(java.util.Map<String, Double> properties) {
        this.properties = properties;
    }

    public java.util.Map<String, Double> getProperties() {
        return this.properties;
    }

    public void setColor(java.awt.Color color){
        this.color = color;
    }
    public java.awt.Color getColor(){
        return this.color;
    }

    public void setFillColor(java.awt.Color color) {
        this.fillcolor = color;
    }

    public java.awt.Color getFillColor(){
        return this.fillcolor;
    }

    public abstract void draw(java.awt.Graphics canvas);

    public abstract Object clone() throws CloneNotSupportedException;



}
