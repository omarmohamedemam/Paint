package eg.edu.alexu.csd.oop.draw.DrawingUtility;

import eg.edu.alexu.csd.oop.draw.shapes.*;
import eg.edu.alexu.csd.oop.draw.shapes.Polygon;
import eg.edu.alexu.csd.oop.draw.shapes.Rectangle;
import eg.edu.alexu.csd.oop.draw.shapes.Shape;


import java.awt.*;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;

//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;

public class Drawing implements DrawingEngine {




	private ArrayList<Class<? extends Shape>> clalist = new ArrayList<>() ;

	private ArrayList<Shape> shapes = new ArrayList<>();
	private Stack<ArrayList<Shape>> undoStack = new Stack<>();
	private Stack<ArrayList<Shape>> redoStack = new Stack<>();


	private static Drawing instance = new Drawing();
	private Drawing(){}
	public static Drawing getInstance(){
		return instance;
	}


	@Override
	public void refresh(Graphics canvas) {
		Graphics2D graphSettings = (Graphics2D)canvas;
		graphSettings.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphSettings.setStroke(new BasicStroke(5));

		for (Shape s : shapes)
			s.draw(canvas);

	}

	@Override
	public void addShape(Shape shape) {
		redoStack.removeAllElements();
		shapes.add(shape);

	}

	@Override
	public void removeShape(Shape shape) {
		redoStack.removeAllElements();
		shapes.remove(shape);
		addSnapShoot();

	}

	
	@Override
	public void updateShape(Shape oldShape, Shape newShape) {
		redoStack.removeAllElements();
		shapes.remove(oldShape);
		shapes.add(newShape);

	}

	public void addSnapShoot() {
		ArrayList<Shape> snapshotshapes = new ArrayList<>();
		for(Shape s :shapes){
			try {
				snapshotshapes.add((Shape) s.clone());
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
		if(undoStack.size() == 20) undoStack.remove(0);
		undoStack.push(snapshotshapes);
	}


	@Override
	public Shape[] getShapes() {
		Shape[] s = new Shape[shapes.size()];
		return (Shape[]) shapes.toArray(s);
	}


	@Override
	public List<Class<? extends Shape>> getSupportedShapes() {
		clalist.add(Line.class);
		clalist.add(Circle.class);
		clalist.add(Ellipse.class);
		clalist.add(Rectangle.class);
		clalist.add(Square.class);
		clalist.add(Triangle.class);
		clalist.add(Polygon.class);
		
		return clalist;
	}

	@Override
	public void undo() {
		if(!undoStack.isEmpty()) {
			redoStack.add(undoStack.pop());
			if (!undoStack.isEmpty()) shapes = undoStack.peek();
			else shapes = new ArrayList<>();
		}

	}

	@Override
	public void redo() {
		if(!redoStack.isEmpty()) {
			undoStack.push(redoStack.peek());
			shapes = redoStack.pop();
		}
	}

	@Override
	public void save(String path) {
		if(path.contains("xml")) {
			try {
				FileOutputStream fos = new FileOutputStream(new File(path));
				XMLEncoder encoder = new XMLEncoder(fos);
				encoder.writeObject(Drawing.getInstance().getShapes());

				encoder.close();
				fos.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		else {// saving in Json extend
			for(Shape shape : getShapes()) {


			}
		}
		
	}

	@Override
	public void load(String path) {
		if(path.contains("xml")){
			try {
				FileInputStream fis = new FileInputStream(new File(path));
				XMLDecoder decoder= new XMLDecoder(fis);
				Shape[] shapes = (Shape[]) decoder.readObject();
				decoder.close();

				for(Shape s : shapes)
					if(s != null)
					  Drawing.getInstance().addShape(s);
				fis.close();
			}
			catch(IOException ex) {
				ex.printStackTrace();
			}
		}
		else {//loading a Json File



		}




		
	}

}