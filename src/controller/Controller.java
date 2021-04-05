package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import models.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;


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
    private ImageView mainImage, blackImage;

    private List<Landmark> landmarks = new ArrayList<>();
    private List<GraphNodeAl<Landmark>> landmarkALnodes=new ArrayList<>();
    private List<GraphNodeDw<Landmark>> landmarkNodes = new ArrayList<>();
    private Tooltip tl;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String img = "waterford.jpg";
        Image image = new Image(img);
        mainImage.setFitHeight(image.getHeight());
        mainImage.setFitWidth(image.getWidth());


        blackImage.setImage(image);
        invertImage(image);


        //===========================================================================
        String path = "src/landmarks.csv";
        String line = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Landmark l = new Landmark(values[0], Integer.parseInt(values[1]), Integer.parseInt(values[2]), values[3].equals("1"));
                GraphNodeDw<Landmark> node = new GraphNodeDw<>(l);
                GraphNodeAl<Landmark> node2=new GraphNodeAl<>(l);
                landmarkNodes.add(node);
                landmarkALnodes.add(node2);
                landmarks.add(l);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int i = 0;
        for (GraphNodeDw<Landmark> l : landmarkNodes) {

            System.out.println(l.data);
            Text t = new Text(l.data.getX() - 3, l.data.getY() + 3, i + "");
            Circle c = new Circle(l.data.getX(), l.data.getY(), 7, Color.RED);
            anPane.getChildren().addAll(c, t);
            i++;
        }

        for (GraphNodeAl<Landmark> l : landmarkALnodes) {

            System.out.println(l.data);
            Text t = new Text(l.data.getX() - 3, l.data.getY() + 3, i + "");
            Circle c = new Circle(l.data.getX(), l.data.getY(), 7, Color.RED);
            anPane.getChildren().addAll(c, t);
            i++;
        }
        connectByIndex(0, 9);
        connectByIndex(0, 2);
        connectByIndex(0, 3);
        connectByIndex(3, 4);
        connectByIndex(4, 2);
        connectByIndex(2, 5);
        connectByIndex(5, 9);
        connectByIndex(5, 8);
        connectByIndex(8, 9);
        connectByIndex(8, 6);
        connectByIndex(6, 7);
        connectByIndex(1, 9);
        connectByIndex(1, 6);


        System.out.println("");
        System.out.println("What is Connected");
        System.out.println("");

        for (GraphNodeDw<Landmark> n : landmarkNodes) {
            System.out.println("");
            System.out.println(n.data.getName());
            for (GraphLinkDw s : n.adjList) System.out.println(s.destNode.data + " Cost: " + s.cost);
        }


        CostedPath cpa = findCheapestPathDijkstra(landmarkNodes.get(0), landmarks.get(1));
        System.out.println("");
        System.out.println("=============================================================================");
        System.out.println("Finding Path with Dijkstra");
        for (GraphNodeDw<?> n : cpa.pathList)
            System.out.println(n.data);

        System.out.println("\nThe total path cost is: " + cpa.pathCost);

/*
        List<GraphNodeAl<?>> bfsPath=findPathBFS(//dnt know what to put in here);
                for(GraphNodeAl<?>n:bfsPath)System.out.println(n.data);


       CostedPathAl cpal=searchGraphDepthFirstCheapestPath(landmarkALnodes.get(0),null,0,"Tower Hotel");
       for(GraphNodeAl2<?>n:cpal.pathList)
           System.out.println(n.data);
       System.out.println("PathCost= "+cpal.pathCost);*/
    }


    public void invertImage(Image original) {


        PixelReader pixelReader = original.getPixelReader();

        int width = (int) original.getWidth();
        int height = (int) original.getHeight();
        WritableImage wImage = new WritableImage(width, height);
        PixelWriter pixelWriter = wImage.getPixelWriter();


        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {


                double total = pixelReader.getColor(x, y).getRed() + pixelReader.getColor(x, y).getBlue() + pixelReader.getColor(x, y).getGreen();
                Color borW = (total < 1.5) ? Color.WHITE : Color.BLACK;

                pixelWriter.setColor(x, y, borW);

                mainImage.setImage(wImage);
                // System.out.println(pixel);

            }
        }


    }

    public int getDistance(int a, int b) {
        int x1 = landmarkNodes.get(a).data.getX();
        int x2 = landmarkNodes.get(b).data.getX();
        int y1 = landmarkNodes.get(a).data.getY();
        int y2 = landmarkNodes.get(b).data.getY();
        return (int) Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }

    public void connectByIndex(int a, int b) {

        int discount = (landmarkNodes.get(b).data.getCultural()) ? 25 : 0;

        landmarkNodes.get(a).connectToNodeUndirected(landmarkNodes.get(b), getDistance(a, b) - discount);
    }

    //public void historicDiscount()


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

        System.out.println(x + " " + y + " hue: " + p.getColor(x, y).getHue() + "Sat" + p.getColor(x, y).getSaturation());

        tl = new Tooltip("You are here");


        Tooltip.install(mainImage, tl);

    }

    public <T> CostedPath findCheapestPathDijkstra(GraphNodeDw<?> startNode, T lookingfor) {
        CostedPath cp = new CostedPath();
        List<GraphNodeDw<?>> encountered = new ArrayList<>(), unencountered = new ArrayList<>();
        startNode.nodeValue = 0;
        unencountered.add(startNode);
        GraphNodeDw<?> currentNode;

        do {
            currentNode = unencountered.remove(0);
            encountered.add(currentNode);

            if (currentNode.data.equals(lookingfor)) {
                cp.pathList.add(currentNode);
                cp.pathCost = currentNode.nodeValue;

                while (currentNode != startNode) {
                    boolean foundPrevPathNode = false;
                    for (GraphNodeDw<?> n : encountered) {
                        for (GraphLinkDw e : n.adjList)
                            if (e.destNode == currentNode && currentNode.nodeValue - e.cost == n.nodeValue) {
                                cp.pathList.add(0, n);
                                currentNode = n;
                                foundPrevPathNode = true;
                                break;
                            }
                        if (foundPrevPathNode) break;
                    }
                }

                for (GraphNodeDw<?> n : encountered) n.nodeValue = Integer.MAX_VALUE;
                for (GraphNodeDw<?> n : encountered) n.nodeValue = Integer.MAX_VALUE;

                return cp;
            }
            for (GraphLinkDw e : currentNode.adjList)
                if (!encountered.contains(e.destNode)) {
                    e.destNode.nodeValue = Integer.min(e.destNode.nodeValue, currentNode.nodeValue + e.cost);

                    unencountered.add(e.destNode);
                }

            unencountered.sort(Comparator.comparingInt(n -> n.nodeValue));

        } while (!unencountered.isEmpty());
        return null;

    }

    public static <T> List<GraphNodeAl<?>> findPathBFS(GraphNodeAl<?> startNode, T lookingFor) {
        List<List<GraphNodeAl<?>>> agenda = new ArrayList<>();
        List<GraphNodeAl<?>> firstAgendaPath = new ArrayList<>(), resultPath;
        firstAgendaPath.add(startNode);
        agenda.add(firstAgendaPath);
        resultPath = findPathBFS(agenda, null, lookingFor);
        Collections.reverse(resultPath);
        return resultPath;
    }

    public static <T> List<GraphNodeAl<?>> findPathBFS(List<List<GraphNodeAl<?>>> agenda, List<GraphNodeAl<?>> encountered, T lookingFor) {
        if (agenda.isEmpty()) return null;
        List<GraphNodeAl<?>> nextPath = agenda.remove(0);
        GraphNodeAl<?> currentNode = nextPath.get(0);
        if (currentNode.data.equals(lookingFor)) return nextPath;
        if (encountered == null) encountered = new ArrayList<>();

        encountered.add(currentNode);
                for (GraphNodeAl<?> adjNode : currentNode.adjlist) {                        //Not compatible??
                if (!encountered.contains(adjNode)) {
                    List<GraphNodeAl<?>> newPath = new ArrayList<>(nextPath);
                    newPath.add(0, adjNode);
                    agenda.add(newPath);
                }
            }

        return findPathBFS(agenda, encountered, lookingFor);
    }


    public void traverseGraphDepthFirst(GraphNodeAl2<?> from , List<GraphNodeAl2<?>> encountered){
        System.out.println(from.data);
        if(encountered==null) encountered=new ArrayList<>();
        encountered.add(from);
        for(GraphLinkAl adjLink:from.adjList){
            if (!encountered.contains(adjLink.destNode)) traverseGraphDepthFirst(adjLink.destNode,encountered);
        }
    }

    public void traverseGraphFirstShowingTotalCost(GraphNodeAl2<?> from,List<GraphNodeAl2<?>> encountered,int totalCost){
        System.out.println(from.data+"(Total cost): "+totalCost+")");
        if(encountered==null) encountered=new ArrayList<>();
        encountered.add(from);

        Collections.sort(from.adjList,(a,b)->a.cost-b.cost);
        for(GraphLinkAl adjLink:from.adjList){
            if(!encountered.contains(adjLink.destNode)){
                traverseGraphFirstShowingTotalCost(adjLink.destNode,encountered,totalCost+adjLink.cost);
            }
        }
    }

    public <T> CostedPathAl searchGraphDepthFirstCheapestPath(GraphNodeAl2<?> from,List<GraphNodeAl2<?>> encountered,int totalCost,T lookingFor){


        if(from.data.equals(lookingFor)){
            CostedPathAl cp=new CostedPathAl();
            cp.pathList.add(from);
            cp.pathCost=totalCost;
            return cp;
        }

        if(encountered==null) encountered=new ArrayList<>();
        encountered.add(from);
        List<CostedPathAl> allPaths=new ArrayList<>();

        for(GraphLinkAl adjLink:from.adjList){
            if(!encountered.contains(adjLink.destNode)){
                CostedPathAl temp=searchGraphDepthFirstCheapestPath(adjLink.destNode,new ArrayList<>(encountered),totalCost+adjLink.cost,lookingFor);

                if(temp==null) continue;
                temp.pathList.add(0,from);
                allPaths.add(temp);

            }
        }return allPaths.isEmpty()?null:Collections.min(allPaths,(p1,p2)->p1.pathCost-p2.pathCost);
    }


}


