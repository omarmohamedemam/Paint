package eg.edu.alexu.csd.oop.draw;


import eg.edu.alexu.csd.oop.draw.DrawingUtility.Drawing;
import eg.edu.alexu.csd.oop.draw.shapes.Polygon;
import eg.edu.alexu.csd.oop.draw.shapes.Shape;


import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class view extends JFrame {

    private Color strokeColor = Color.BLACK;
    private Color fillColor = Color.white;

    private DrawingBoard drawingBoard = new DrawingBoard();
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        new view();
    }

    /**
     * Create the application.
     */
    public view() {

        initialize();

    }

    /**
     * Initialize the contents of the frame.
     */


    private void initialize() {
        super.setIconImage(Toolkit.getDefaultToolkit().getImage("./icons/palette.png"));
        super.setResizable(false);
        super.setTitle("Paint");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        super.setSize(screenSize.width, screenSize.height);

        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        //menu bar
        JMenuBar menuBar = new JMenuBar();
        menuBar.setMargin(new Insets(0, 5, 0, 0));
		menuBar.setBackground(Color.WHITE);
		super.setJMenuBar(menuBar);

        JMenu FileMenu = new JMenu("File");
        FileMenu.setForeground(Color.BLACK);
		FileMenu.setFont(new Font("Segoe UI", Font.PLAIN, 20));

		JMenu SaveMenu = new JMenu("Save");

		SaveMenu.add(makeMenuItem(SaveMenu,"XML"));
        SaveMenu.add(makeMenuItem(SaveMenu,"Json"));

        FileMenu.add(SaveMenu);
        FileMenu.add(makeMenuItem(FileMenu,"Load"));
        FileMenu.add(makeMenuItem(FileMenu,"Exit"));

        JMenu EditMenu = new JMenu("Edit");
        EditMenu.setForeground(Color.BLACK);
        EditMenu.setFont(new Font("Segoe UI", Font.PLAIN, 20));

        EditMenu.add(makeMenuItem(EditMenu,"Copy"));
        EditMenu.add(makeMenuItem(EditMenu,"Paste"));


        JMenu PropertiesMenu = new JMenu("Properties");
        PropertiesMenu.setForeground(Color.BLACK);
        PropertiesMenu.setFont(new Font("Segoe UI", Font.PLAIN, 20));

        PropertiesMenu.add(makeMenuItem(PropertiesMenu,"Show Properties"));

        JMenu HelpMenu = new JMenu("Help");
        HelpMenu.setForeground(Color.BLACK);
        HelpMenu.setFont(new Font("Segoe UI", Font.PLAIN, 20));

        HelpMenu.add(makeMenuItem(HelpMenu,"User Guide"));

        menuBar.add(FileMenu);
        menuBar.add(EditMenu);
        menuBar.add(PropertiesMenu);
        menuBar.add(HelpMenu);

        //painting panel

        Box theupBox = Box.createHorizontalBox();
        Box thedownBox = Box.createHorizontalBox();

        thedownBox.setName("btnpanel");
        theupBox.setName("btnpanel");
        //buttons panel
        JPanel btnuppanel = new JPanel();
        JPanel btndownpanel = new JPanel();
        btnuppanel.setSize(theupBox.getSize());
        btndownpanel.setSize(thedownBox.getSize());



        makeButton("./icons/erase.png", theupBox, "erase"); //erase button

        makeButton("./icons/undo.png", theupBox, "undo"); //undo button
        makeButton("./icons/redo.png", theupBox, "redo"); //redo button

        makeButton("./icons/move.png", theupBox, "move"); //move button
        makeButton("./icons/Resize.png", theupBox, "resize"); //resize button

        makeButton("./icons/color.png", theupBox, "stroke"); //color button
        makeButton("./icons/fill.png", theupBox, "fill"); //fill button

        makeButton("./icons/pencil.png", thedownBox, "pencil"); //pencil button
        makeButton("./icons/line.png", thedownBox, "line"); //line button
        makeButton("./icons/square.png", thedownBox, "square"); //square button
        makeButton("./icons/rec.png", thedownBox, "rectangle"); //rectangle button

        makeButton("./icons/circle.png", thedownBox, "circle"); //circle button
        makeButton("./icons/ellipse.png", thedownBox, "ellipse"); //ellipse button

        makeButton("./icons/triangle.png", thedownBox, "triangle"); //triangle button


        makeButton("./icons/polygon.png", thedownBox, "polygon"); //polygon button





        Component[] compnents = thedownBox.getComponents();
        for(Component c : compnents){
            c.setBackground(Color.white);
            c.setForeground(Color.black);
        }

        compnents = theupBox.getComponents();
        for(Component c : compnents){
            c.setBackground(Color.white);
            c.setForeground(Color.black);
        }


        btndownpanel.setBackground(Color.cyan);
        btnuppanel.setBackground(Color.CYAN);

        thedownBox.setForeground(Color.black);
        thedownBox.setVisible(true);

        btndownpanel.add(thedownBox);
        btnuppanel.add(theupBox,BorderLayout.CENTER);

        btndownpanel.setVisible(true);
        btnuppanel.setVisible(true);

        super.add(btndownpanel,BorderLayout.SOUTH,0);
        super.add(btnuppanel,BorderLayout.NORTH,1);
        super.add(drawingBoard,BorderLayout.CENTER,2);
        super.setVisible(true);

    }


    private void resetBackgroundButtonsColor(){

        JPanel panel = (JPanel) super.getContentPane().getComponent(0);
        Box theBox = (Box) panel.getComponent(0);
        Component[] buttons = theBox.getComponents();
        for(Component c : buttons) {
            c.setBackground(Color.white);
        }
        panel = (JPanel) super.getContentPane().getComponent(1);
        theBox = (Box) panel.getComponent(0);
        buttons = theBox.getComponents();
        for (Component c : buttons)
            c.setBackground(Color.white);

    }

    /**
     *
     * @param menu which is the parent for the MenuItem
     * @param Action informs what the action the MenuItem Does
     * @return refrence to the MenuItem
     */

    private JMenuItem makeMenuItem(JMenu menu, String Action){
        JMenuItem menuItem = new JMenuItem(Action);
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (Action){
                    case "XML" :
                        JFileChooser fileChooser = new JFileChooser();
                        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                        int option = fileChooser.showSaveDialog(null);
                        if (option == JFileChooser.APPROVE_OPTION)
                        {
                            Drawing.getInstance().save(fileChooser.getSelectedFile().getAbsolutePath()+"./saved.xml");
                            JOptionPane.showMessageDialog(drawingBoard, "File Saved in : "+fileChooser.getSelectedFile().getAbsolutePath()+"/saved.xml", "Save File", JOptionPane.PLAIN_MESSAGE);
                        }
                        break;

                    case "Json" :
                        fileChooser = new JFileChooser();
                        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                        option = fileChooser.showSaveDialog(null);
                        if (option == JFileChooser.APPROVE_OPTION)
                        {
                            Drawing.getInstance().save(fileChooser.getSelectedFile().getAbsolutePath()+"./saved.json");
                            JOptionPane.showMessageDialog(drawingBoard, "File Saved in : "+fileChooser.getSelectedFile().getAbsolutePath()+"/saved.json", "Save File", JOptionPane.PLAIN_MESSAGE);
                        }
                        break;

                    case "Load" :
                        fileChooser = new JFileChooser();
                        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                        fileChooser.setFileFilter(new FileFilter() {

                            public String getDescription() {
                                return "XML or Json files";
                            }

                            public boolean accept(File f) {
                                if (f.isDirectory()) {
                                    return true;
                                } else {
                                    String filename = f.getName().toLowerCase();
                                    return filename.endsWith(".xml") || filename.endsWith(".json") ;
                                }
                            }
                        });
                        option = fileChooser.showOpenDialog(null);
                        if (option == JFileChooser.APPROVE_OPTION) {
                            Drawing.getInstance().load(fileChooser.getSelectedFile().getAbsolutePath());
                            JOptionPane.showMessageDialog(drawingBoard, "File Loaded Sucessfuly!", "Load File", JOptionPane.PLAIN_MESSAGE);
                            drawingBoard.repaint();
                        }
                        break;
                    case "Exit" :
                        view.super.dispose();
                        break;

                    case "Copy" :
                        showMessageDialog("./icons/copy.png","please, Select the shape to copy\n **Selecting shapes by clicking inside them","CopyShape");
                        setDrawingInformation(null,"",false,false,false,false,true);
                        break;
                    case "Paste" :
                        drawingBoard.paste();
                        break;
                    case "Show Properties" :
                        showMessageDialog("./icons/prop.png","please, Select the shape to show its Propertise\n **Selecting shapes by clicking inside them","Select Shape");
                        resetBackgroundButtonsColor();
                        setDrawingInformation(null,"",false,false,false,false,false);
                        drawingBoard.setPropertiesSelected(true);
                        break;
                    case "User Guide" :
                        showMessageDialog("./icons/user.png","To Use this program \n 1.click buttons to start order \n 2.use mouse click and drag to apply it \n 3.edit trough menu"
                                + " bar \n    >>>HOPE YOU ENJOY <<< ","User Guide");
                        break;
                }
            }
        });
        return menuItem;
    }

    private void showMessageDialog(String path, String message, String title){
        ImageIcon imageIcon =new ImageIcon(path);
        Image image = imageIcon.getImage();
        Image imageScaled = image.getScaledInstance(60, 60,  Image.SCALE_SMOOTH);
        ImageIcon imageIconScalled = new ImageIcon(imageScaled);
        JOptionPane.showMessageDialog(drawingBoard, message,title,JOptionPane.PLAIN_MESSAGE,imageIconScalled);
    }


    private void setDrawingInformation(Shape currentShape, String currentShapeType, boolean painting, boolean moving, boolean resizing, boolean erasing, boolean Copying){
        drawingBoard.setCurrentShape(currentShape);
        drawingBoard.setCurrentShapeType(currentShapeType);
        drawingBoard.setPainting(painting);
        drawingBoard.setMoving(moving);
        drawingBoard.setResizing(resizing);
        drawingBoard.setErasing(erasing);
        drawingBoard.setCopying(Copying);
    }


    /**
     *
     * @param path of the image icon
     * @param theBox which is the container of the Button
     * @param Action informs what the action of the Button
     * @return refrence for the Button
     *
     */

    private JButton makeButton(String path, Box theBox, String Action){
        ImageIcon btnimg = new ImageIcon(path);
        Image img = ((ImageIcon) btnimg).getImage();
        Image scaledimg = img.getScaledInstance(60, 60,  Image.SCALE_SMOOTH);
        ImageIcon scaledbtnimg = new ImageIcon(scaledimg);
        JButton theButton = new JButton();
        theButton.setIcon(scaledbtnimg);
        theBox.add(theButton);

        theButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                resetBackgroundButtonsColor();
                if(!Action.equals("undo") && !Action.equals("redo") && !Action.equals("fill") && !Action.equals("stroke") && !Action.equals("triangle") && !Action.equals("polygon"))
                    theButton.setBackground(Color.lightGray);

                switch (Action) {
                    case "stroke" :{
                        strokeColor = JColorChooser.showDialog(null , "Pick a Stroke", Color.BLACK);
                        drawingBoard.setStrokeColor(strokeColor);
                        break;
                    }
                    case "fill" :{
                        fillColor = JColorChooser.showDialog(null, "Pick a Fill", Color.white);
                        drawingBoard.setFillColor(fillColor);
                        break;
                    }
                    case "undo" :
                        Drawing.getInstance().undo();
                        drawingBoard.repaint();
                        break;
                    case "redo" :
                        Drawing.getInstance().redo();
                        drawingBoard.repaint();
                        break;
                    case "pencil" :{
                        setDrawingInformation(null,"FreeHand",true,false,false,false,false);
                        break;
                    }
                    case  "line" :
                        setDrawingInformation(null,"Line",true,false,false,false,false);
                        break;

                    case  "square" :
                        setDrawingInformation(null,"Square",true,false,false,false,false);
                        break;
                    case "rectangle" :
                        setDrawingInformation(null,"Rectangle",true,false,false,false,false);
                        break;
                    case "circle" :
                        setDrawingInformation(null,"Circle",true,false,false,false,false);
                        break;
                    case "ellipse" :
                        setDrawingInformation(null,"Ellipse",true,false,false,false,false);
                        break;
                    case "triangle" :
                        setDrawingInformation(null,"Triangle",true,false,false,false,false);
                        JOptionPane.showMessageDialog(drawingBoard ,  "select position of the three points to draw triangle","How to draw a Triangle !",JOptionPane.PLAIN_MESSAGE,scaledbtnimg);
                        break;
                    case "polygon" :{
                        ImageIcon imageIcon =new ImageIcon("./icons/demo.png");
                        Image image = imageIcon.getImage();
                        Image imageScaled = image.getScaledInstance(60, 60,  Image.SCALE_SMOOTH);
                        ImageIcon imageIconScaled = new ImageIcon(imageScaled);
                        int pol_sides=0;
				         while (pol_sides<=1) {
                             JOptionPane.showMessageDialog(drawingBoard, "This Shape Is Still A Demo , So you Need to Know\n"
                                     + "- Points will be connected in the Order you Draw it.\n"
                                     + "- you won't be able to move , color , fill or resize it.\n"
                                     + "- After drawing it , you will be able to re draw it with same number of sides as much as you need.\n"
                                     + "- To redraw it with different number of side Click on Polygon Button Agian.\n"
                                     + "- This Button Can bring some errors to the whole programe , so be Careful.\n"
                                     + "                  Thanks For Understanding                                    \n"
                                     + "                                                                                                                         ~the Authors", "DEMO Explenation !", JOptionPane.WARNING_MESSAGE, imageIconScaled);
                             String n = JOptionPane.showInputDialog(drawingBoard, "Enter the number of Sides you will need to draw Polygon", "Polygon Sides Number", JOptionPane.QUESTION_MESSAGE);
                             try {
                                 pol_sides = Integer.parseInt(n);
                             } catch (NumberFormatException o) {
                                 pol_sides = 0;
                                 JOptionPane.showMessageDialog(drawingBoard, "Enter Your Input in correct Way. \n Number of sides must be bigger than 1", "Invalid Input !", JOptionPane.ERROR_MESSAGE);
                                 continue;
                             }

                             if (pol_sides <= 1) {
                                 JOptionPane.showMessageDialog(drawingBoard, "Enter Your Input in correct Way. \n Number of sides must be bigger than 1", "Invalid Input !", JOptionPane.ERROR_MESSAGE);
                             } else {
                                 JOptionPane.showMessageDialog(drawingBoard, "select position of the " + n + "  points to draw Polygon", "How to draw a Polygon !", JOptionPane.PLAIN_MESSAGE, imageIconScaled);
                                 break;
                             }

                         }
				         Shape shape = new Polygon();
				         shape.getProperties().put("N=",Double.valueOf(pol_sides));
				         shape.setColor(strokeColor);
				         shape.setColor(fillColor);
                         setDrawingInformation(shape,"",true,false,false,false,false);
                         break;
                    }
                    case "erase" :
                        setDrawingInformation(null,"",false,false,false,true,false);
                        break;
                    case "move" :
                        setDrawingInformation(null,"",false,true,false,false,false);
                        break;
                    case "resize" :
                        setDrawingInformation(null,"",false,false,true,false,false);
                        break;

                }
            }
        });

        return theButton;
    }




}
