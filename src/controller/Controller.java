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
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
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
    private ImageView mainImage,blackImage;

    private List<Landmark> landmarks = new ArrayList<>();
    private Tooltip tl;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String img = "waterford.jpg";
        Image image = new Image(img);
        mainImage.setFitHeight(image.getHeight());
        mainImage.setFitWidth(image.getWidth());

        blackImage.setImage(image);
        new Thread(()->{  makeWhite(image); }).start();


        //===========================================================================
        String path = "src/landmarks.csv";
        String line="";
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));

            while((line=br.readLine())!= null ){
                String [] values= line.split(",");
                Landmark l = new Landmark(values[0],Integer.parseInt(values[1]),Integer.parseInt(values[2]));
                landmarks.add(l);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        for(Landmark l : landmarks)
            System.out.println(l.toString());


    }


    public void makeWhite(Image original) {


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


        System.out.println(x + " " + y);

        tl = new Tooltip("You are here");


        Tooltip.install(mainImage, tl);

    }
}


