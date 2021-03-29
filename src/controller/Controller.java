package controller;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.GraphLinkDw;
import models.GraphNodeDw;
import models.Landmark;


import javax.imageio.ImageIO;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class Controller implements Initializable {



    @FXML
    private Pane mainPane;

    @FXML
    private MenuItem newTabBtn;

    @FXML
    private MenuItem openFile;

    @FXML
    private MenuItem saveBTN;

    @FXML
    private MenuItem closeBTN;

    @FXML
    private MenuItem rgbChannels;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab tab1;
    @FXML
    private AnchorPane anPane;


    @FXML
    private ImageView mainImage,blackImage;

    private List<Landmark> landmarks = new ArrayList<>();
    private List<GraphNodeDw<Landmark>> landmarkNodes = new ArrayList<>();
    private Tooltip tl;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String img = "waterford.jpg";
        Image image = new Image(img);
        mainImage.setFitHeight(image.getHeight());
        mainImage.setFitWidth(image.getWidth());



        blackImage.setImage(image);
       invertImage(image) ;


        //===========================================================================
        String path = "src/landmarks.csv";
        String line="";
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));

            while((line=br.readLine())!= null ){
                String [] values= line.split(",");
                Landmark l = new Landmark(values[0],Integer.parseInt(values[1]),Integer.parseInt(values[2]));
                GraphNodeDw<Landmark> node = new GraphNodeDw<>(l);
                landmarkNodes.add(node);
            //    landmarks.add(l);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int i = 0;
        for(GraphNodeDw<Landmark> l : landmarkNodes) {

            System.out.println(l.data);
            Text t= new Text(l.data.getX()-3,l.data.getY()+3,i+"");
            Circle c = new Circle(l.data.getX(),l.data.getY(),7,Color.RED);
            anPane.getChildren().addAll(c,t);
            i++;
        }

        connectByIndex(0,1);
        System.out.println(landmarkNodes.get(0).adjList.get(0).cost);








    }


    public void invertImage(Image original) {


        PixelReader pixelReader = original.getPixelReader();

        int width = (int) original.getWidth();
        int height = (int) original.getHeight();
        WritableImage wImage = new WritableImage(width, height);
        PixelWriter pixelWriter = wImage.getPixelWriter();


        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {


                 double total = pixelReader.getColor(x,y).getRed() + pixelReader.getColor(x,y).getBlue()+pixelReader.getColor(x,y).getGreen();
                Color borW = (total<1.5)? Color.WHITE : Color.BLACK;

               pixelWriter.setColor(x, y, borW);

                mainImage.setImage(wImage);
                // System.out.println(pixel);

            }
        }



    }

    public int getDistance(int a , int b){
        int x1 =landmarkNodes.get(a).data.getX();
        int x2 =landmarkNodes.get(b).data.getX();
        int y1=landmarkNodes.get(a).data.getY();
        int y2 =landmarkNodes.get(b).data.getY();
       return (int) Math. sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1));
    }

    public void connectByIndex(int a, int b){
        landmarkNodes.get(a).connectToNodeUndirected(landmarkNodes.get(b),getDistance(a,b));
}




    public void closeApp(ActionEvent actionEvent) {
        System.exit(0);
    }


    public void openNewTab(ActionEvent actionEvent) {



    }

    public void browse(ActionEvent actionEvent) {
    }

    public void mouseClick(MouseEvent mouseEvent) {
        int x = new Double(mouseEvent.getX()).intValue();
        int y = new Double(mouseEvent.getY()).intValue();
        PixelReader p = mainImage.getImage().getPixelReader();

        System.out.println(x + " " + y + " hue: "  + p.getColor(x,y).getHue()+ "Sat"+ p.getColor(x,y).getSaturation());

        tl = new Tooltip("You are here");


        Tooltip.install(mainImage, tl);

    }
}


