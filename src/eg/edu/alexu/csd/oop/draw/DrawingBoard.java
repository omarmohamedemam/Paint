package eg.edu.alexu.csd.oop.draw;

import eg.edu.alexu.csd.oop.draw.DrawingUtility.Drawing;
import eg.edu.alexu.csd.oop.draw.shapes.FreeHand;
import eg.edu.alexu.csd.oop.draw.mouseUtility.SelectionTool;
import eg.edu.alexu.csd.oop.draw.shapes.Circle;
import eg.edu.alexu.csd.oop.draw.shapes.Ellipse;
import eg.edu.alexu.csd.oop.draw.shapes.Line;
import eg.edu.alexu.csd.oop.draw.shapes.Polygon;
import eg.edu.alexu.csd.oop.draw.shapes.Rectangle;
import eg.edu.alexu.csd.oop.draw.shapes.Shape;
import eg.edu.alexu.csd.oop.draw.shapes.Square;
import eg.edu.alexu.csd.oop.draw.shapes.Triangle;


import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.HashMap;
import java.util.Map;

public class DrawingBoard extends JPanel {
    private boolean moving = false, resizing = false, painting = false, erasing = false, Copying = false, PropertiesSelected = false;
    private Shape currentShape = null;
    private Shape cloningShape;
    private Shape NEWShape;
    private Shape Copied;
    private String currentShapeType = "";
    private Color strokeColor = Color.black;
    private Color fillColor = Color.white;
    private Point pressedPoint;


    public DrawingBoard() {

        setBackground(Color.white);
        setForeground(Color.black);
        SelectionTool selectionTool = new SelectionTool();

        /**
         * moving is true refers that moving action is wanted so change only the position
         * at false then painting or resizing the shape is wanted so change only the properties
         * @param moving
         */

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                pressedPoint = e.getPoint();
                if ((moving || resizing || erasing || Copying || PropertiesSelected)&& currentShape == null) {
                    currentShape = selectionTool.SelectedShape(e.getPoint());
                }
                if(!painting && currentShape != null) {
                    if(PropertiesSelected){
                        Map<String, Double> prop = new HashMap<>();
                        prop.putAll(currentShape.getProperties());

                        JTable table=new JTable(prop.size(),2);
                         int row=0;
                         for(Map.Entry<String,Double> entry : prop.entrySet()){
                              table.setValueAt(entry.getKey(),row,0);
                              table.setValueAt(entry.getValue(),row,1);
                              row++;
                         }
                        JTableHeader header = table.getTableHeader();
                        TableColumnModel columnModel = header.getColumnModel();
                        TableColumn columnProperty = columnModel.getColumn(0);
                        columnProperty.setHeaderValue("Property");
                        TableColumn columnValue = columnModel.getColumn(1);
                        columnValue.setHeaderValue("Value");
                        table.setFillsViewportHeight(true);
                        table.getTableHeader().setFont(new Font("serfi",Font.BOLD,23));
                        table.setFont(new Font("serfi",Font.BOLD,20));
                        table.setRowHeight(30);
                        table.setForeground(Color.BLUE);
                        table.enable(false);
                        JOptionPane.showMessageDialog(null, new JScrollPane(table),"shape Properties",JOptionPane.PLAIN_MESSAGE);
                        PropertiesSelected = false;
                    }
                    else if(Copying) {
                        try {
                            Copied = (Shape) currentShape.clone();
                        } catch (CloneNotSupportedException e1) {
                            e1.printStackTrace();
                        }
                        Copied.setPosition(new Point(Copied.getPosition().x+2,Copied.getPosition().y+2));
                        JOptionPane.showMessageDialog(DrawingBoard.super.getRootPane(), " Shape Copied !", "Copy Shape", JOptionPane.PLAIN_MESSAGE);
                        Copying = false;
                    }
                    else if (moving || resizing) {
                        try {
                            cloningShape = (Shape) currentShape.clone();
                        } catch (CloneNotSupportedException e1) {
                            e1.printStackTrace();
                        }
                    }
                    else if (erasing) { // erasing the shape
                        Drawing.getInstance().removeShape(currentShape);
                        repaint();
                        currentShape = null;
                    }
                }

                else if (painting && currentShapeType.equals("Triangle")) { //painting Triangle shape
                    int count = 1;
                    if (currentShape == null) {
                        currentShape = new Triangle();
                        currentShape.setColor(strokeColor);
                        currentShape.setFillColor(fillColor);
                    }
                    while (count < 4) {
                        if (!currentShape.getProperties().containsKey("v" + count + "x")) {
                            currentShape.getProperties().put("v" + count + "x", Double.valueOf(e.getX()));
                            currentShape.getProperties().put("v" + count + "y", Double.valueOf(e.getY()));
                            break;
                        }
                        count++;
                    }
                    if (count == 3) {
                        count = 1;
                        currentShape.setColor(strokeColor);
                        currentShape.setFillColor(fillColor);
                        Drawing.getInstance().addShape(currentShape);
                        Drawing.getInstance().addSnapShoot();
                        currentShape = null;
                        repaint();

                    }
                }
                else if (painting && currentShape instanceof Polygon) {//painting polygon shape
                    int count = 1;
                    while (count < currentShape.getProperties().get("N=") + 1) {
                        if (!currentShape.getProperties().containsKey("v" + count + "x")){
                            currentShape.getProperties().put("v" + count + "x", Double.valueOf(e.getX()));
                            currentShape.getProperties().put("v" + count + "y", Double.valueOf(e.getY()));
                            break;
                        }
                        count++;
                    }
                    if (count == currentShape.getProperties().get("N=")) {
                        painting = false;
                        currentShape.setColor(strokeColor);
                        currentShape.setFillColor(fillColor);
                        Drawing.getInstance().addShape(currentShape);
                        Drawing.getInstance().addSnapShoot();
                        repaint();

                    }

                }
                else if (painting && currentShapeType.equals("FreeHand")){
                    currentShape = new FreeHand(e.getPoint());
                    currentShape.setColor(strokeColor);
                    Drawing.getInstance().addShape(currentShape);
                }
                else if(painting){//painting
                    switch (currentShapeType) {
                        case "Line":
                            currentShape = new Line();
                            break;
                        case "Square":
                            currentShape = new Square();
                            break;
                        case "Rectangle":
                            currentShape = new Rectangle();
                            break;
                        case "Circle":
                            currentShape = new Circle();
                            break;
                        case "Ellipse":
                            currentShape = new Ellipse();
                            break;
                    }
                    currentShape.setPosition(e.getPoint());
                    currentShape.setColor(strokeColor);
                    currentShape.setFillColor(fillColor);
                    Drawing.getInstance().addShape(currentShape);
                }

            }


            @Override
            public void mouseReleased(MouseEvent e) {
                if(currentShape != null) {
                    if (moving) {
                        moveshape(e.getPoint());
                        NEWShape = currentShape;
                        currentShape = cloningShape;
                        Drawing.getInstance().updateShape(currentShape, NEWShape);
                        Drawing.getInstance().addSnapShoot();
                        currentShape = null;
                        repaint();
                    } else if (resizing) {
                        resizeshape(e.getPoint());
                        NEWShape = currentShape;
                        currentShape = cloningShape;
                        Drawing.getInstance().updateShape(currentShape, NEWShape);
                        Drawing.getInstance().addSnapShoot();
                        currentShape = null;
                        repaint();
                    } else if (painting && !(currentShape instanceof Triangle) && !(currentShape instanceof Polygon)) {
                        paintshape(e.getPoint());
                        Drawing.getInstance().addSnapShoot();
                        currentShape = null;
                        repaint();
                    }

                }

            }


        });


        this.addMouseMotionListener(new MouseMotionAdapter() {

            @Override
            public void mouseDragged(MouseEvent e) {
                if (moving) moveshape(e.getPoint());
                else if (resizing) resizeshape(e.getPoint());
                else if (painting && !(currentShape instanceof Triangle) && !(currentShape instanceof Polygon)) paintshape(e.getPoint());
                repaint();
            }

        });


    }




    public void setMoving(boolean b){
        moving = b;
    }
    public void setResizing(boolean b){
        resizing = b;
    }
    public void setPainting(boolean b){
        painting = b;
    }
    public void setErasing(boolean b){
        erasing = b;
    }
    public void setCopying(boolean b){Copying = b;}
    public void setPropertiesSelected(boolean b){PropertiesSelected = b;}

    public void setCurrentShape(Shape s){
        currentShape = s;
    }
    public void setStrokeColor(Color c){
        this.strokeColor = c;
    }
    public void setFillColor(Color c){
        this.fillColor = c;
    }
    public void setCurrentShapeType(String s){
        this.currentShapeType = s;
    }


    private void paintshape(Point e){
        if(currentShapeType.equals("FreeHand")) currentShape.setPosition(e.getLocation());
        else if(currentShapeType.equals("Line")){
            currentShape.getProperties().put("ex",Double.valueOf(e.getX()));
            currentShape.getProperties().put("ey",Double.valueOf(e.getY()));
        }
        else{
            int minX = (int) Math.round(Math.min(pressedPoint.getX(), e.getX()));
            int minY = (int) Math.round(Math.min(pressedPoint.getY(), e.getY()));
            int maxX = (int) Math.round(Math.max(pressedPoint.getX(), e.getX()));
            int maxY = (int) Math.round(Math.max(pressedPoint.getY(), e.getY()));
            if (currentShapeType.equals("Square") || currentShapeType.equals("Circle")) {
                int length = Math.abs(maxX-minX);
                currentShape.setPosition(new Point(minX,e.getY() < pressedPoint.getY()?maxY-length:minY));
                if (currentShapeType.equals("Square")){
                    currentShape.getProperties().put("length",Double.valueOf(length));
                    currentShape.getProperties().put("ex",e.getX());
                    currentShape.getProperties().put("ey",e.getY());
                }
                else currentShape.getProperties().put("raduis",Double.valueOf(length));
            }
            else if (currentShapeType.equals("Rectangle") || currentShapeType.equals("Ellipse")) {
                currentShape.setPosition(new Point(minX,minY));
                int width = Math.abs(maxX-minX);
                int height = Math.abs(maxY-minY);
                currentShape.getProperties().put("width",Double.valueOf(width));
                currentShape.getProperties().put("height",Double.valueOf(height));
                currentShape.getProperties().put("ex",e.getX());
                currentShape.getProperties().put("ey",e.getY());
            }
        }

    }
    private void moveshape(Point e){
        int disx = e.x - pressedPoint.x;
        int disy = e.y - pressedPoint.y;
        pressedPoint = e;

        if(currentShape instanceof Triangle || currentShape instanceof Polygon){
            int count = 1, N = 3;
            if (currentShape instanceof Polygon) N = currentShape.getProperties().get("N=").intValue();
            for (int i = 0; i < N; i++) {
                currentShape.getProperties().replace("v" + count + "x", currentShape.getProperties().get("v" + count + "x") + Double.valueOf(disx));
                currentShape.getProperties().replace("v" + count + "y", currentShape.getProperties().get("v" + count + "y") + Double.valueOf(disy));
                count++;
            }
        }

        else if(currentShape instanceof Line) {
            currentShape.getProperties().replace("ex", currentShape.getProperties().get("ex") + disx);
            currentShape.getProperties().replace("ey", currentShape.getProperties().get("ey") + disy);
        }

        else if(currentShape instanceof Square || currentShape instanceof Rectangle || currentShape instanceof Ellipse){
            currentShape.getProperties().put("ex",currentShape.getProperties().get("ex")+disx);
            currentShape.getProperties().put("ey",currentShape.getProperties().get("ey")+disy);
        }

        if(currentShape != null && !(currentShape instanceof Polygon) && !(currentShape instanceof Triangle))
            currentShape.setPosition(new Point(currentShape.getPosition().x+disx,currentShape.getPosition().y+disy));



    }



    private void resizeshape(Point e){
        Double dis = e.distance(pressedPoint);
        int disx = e.x - pressedPoint.x;
        int disy = e.y - pressedPoint.y;
        pressedPoint = e;

        if (currentShape instanceof Line){
            currentShape.getProperties().put("ex",currentShape.getProperties().get("ex")+disx);
            currentShape.getProperties().put("ey",currentShape.getProperties().get("ey")+disy);
        }
        else if(currentShape instanceof Rectangle || currentShape instanceof Ellipse){
            currentShape.getProperties().put("width",currentShape.getProperties().get("width")+disx);
            currentShape.getProperties().put("height",currentShape.getProperties().get("height")+disy);
            currentShape.getProperties().put("ex",currentShape.getProperties().get("ex")+disx);
            currentShape.getProperties().put("ey",currentShape.getProperties().get("ey")+disy);
        }
        else if(currentShape instanceof Square) {
            currentShape.getProperties().put("length",currentShape.getProperties().get("length")+(disx+disy)/2);
            currentShape.getProperties().put("ex",currentShape.getProperties().get("ex")+(disx+disy)/2);
            currentShape.getProperties().put("ey",currentShape.getProperties().get("ey")+(disx+disy)/2);
        }
        else if(currentShape instanceof Circle) currentShape.getProperties().replace("raduis",currentShape.getProperties().get("raduis")+(disx+disy)/2);

        else if(currentShape instanceof Triangle){
            /**
             * in resizing the triangle shape
             * find nearest two vertices to the pressed point in x
             * add the disx to the two vertices
             * and similarly with the y values
             */
            if(Math.abs(currentShape.getProperties().get("v1x")-pressedPoint.getX()) < Math.abs(currentShape.getProperties().get("v2x")-pressedPoint.getX())) //then v1 one of the two nearest
                currentShape.getProperties().put("v1x",currentShape.getProperties().get("v1x")+disx);
            else currentShape.getProperties().put("v2x",currentShape.getProperties().get("v2x")+disx); // then v2 one of the two nearest
            if(Math.abs(currentShape.getProperties().get("v3x")-pressedPoint.getX()) < Math.abs(currentShape.getProperties().get("v2x")-pressedPoint.getX()) || Math.abs(currentShape.getProperties().get("v3x")-pressedPoint.getX()) < Math.abs(currentShape.getProperties().get("v1x")-pressedPoint.getX())) // then v3 is the second one of the nearest.
                currentShape.getProperties().put("v3x",currentShape.getProperties().get("v3x")+disx);

            if(Math.abs(currentShape.getProperties().get("v1y")-pressedPoint.getY()) < Math.abs(currentShape.getProperties().get("v2y")-pressedPoint.getY()))
                currentShape.getProperties().put("v1y",currentShape.getProperties().get("v1y")+disy);
            else currentShape.getProperties().put("v2y",currentShape.getProperties().get("v2y")+disy);
            if(Math.abs(currentShape.getProperties().get("v3y")-pressedPoint.getY()) < Math.abs(currentShape.getProperties().get("v2y")-pressedPoint.getY()) || Math.abs(currentShape.getProperties().get("v3y")-pressedPoint.getY()) < Math.abs(currentShape.getProperties().get("v1y")-pressedPoint.getY()))
                currentShape.getProperties().put("v3y",currentShape.getProperties().get("v3y")+disy);





        }
    }



    public void paste() {
        if (Copied != null) {
            Drawing.getInstance().addShape(Copied);
            Drawing.getInstance().addSnapShoot();
            repaint();
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Drawing.getInstance().refresh(g);

    }


}
