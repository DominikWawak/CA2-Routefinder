package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import models.CostedPath;
import models.GraphLinkDw;
import models.GraphNodeDw;
import models.Landmark;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;
/**
 * Controller class
 * This controller class is responsible for traversing and finding paths on a graph data structure as well as an image.
 *
 * @author Dominik Wawak
 * @version 1.0.0 (27.Apr.2021)
 */

public class Controller implements Initializable {


    public TextArea avoidBox;
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
    private Pane dijkstraPane;

    @FXML
    private TextArea routeBox, waypointsList,singlePathBox,multiplePathBox;

    @FXML
    private ComboBox<String> startNode, waypoints, avoidedLand;

    @FXML
    private ComboBox<String> endNode, start, end;

    @FXML
    private Pane pathPane;

    @FXML
    private Spinner<Integer> selectPath ;

    @FXML
    private RadioButton histRoute;

    private ArrayList<int[]> mouseCoord= new ArrayList<>();


    @FXML
    private ImageView mainImage, blackImage;

    private List<Landmark> landmarks = new ArrayList<>();
    private List<GraphNodeDw<Landmark>> landmarkNodes = new ArrayList<>();
    private ObservableList<String> names = FXCollections.observableArrayList();
    private ArrayList<Integer> waypointsArray = new ArrayList<>();
    String img = "waterford.jpg";
    Image image = new Image(img);

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        mainImage.setFitHeight(image.getHeight());
        mainImage.setFitWidth(image.getWidth());


        blackImage.setImage(image);
        invertImage(image);


        //===========================================================================

        //        Method used to load the csv file into the program


        //===========================================================================
        String path = "src/landmarks.csv";
        String line = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Landmark l = new Landmark(values[0], Integer.parseInt(values[1]), Integer.parseInt(values[2]), values[3].equals("1"));
                GraphNodeDw<Landmark> node = new GraphNodeDw<>(l);
                landmarkNodes.add(node);
                names.add(values[0]);
                landmarks.add(l);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        startNode.setItems(names);
        endNode.setItems(names);
        waypoints.setItems(names);
        avoidedLand.setItems(names);
        start.setItems(names);
        end.setItems(names);

        int i = 0;
        for (GraphNodeDw<Landmark> l : landmarkNodes) {

            System.out.println(l.data);
            Text t = new Text(l.data.getX() - 3, l.data.getY() + 3, i + "");
            Circle c = new Circle(l.data.getX(), l.data.getY(), 7, Color.PINK);
            anPane.getChildren().addAll(c, t);
            i++;
        }

        connectByIndex(0, 9, landmarkNodes);
        connectByIndex(0, 2, landmarkNodes);
        connectByIndex(0, 3, landmarkNodes);
        connectByIndex(3, 4, landmarkNodes);
        connectByIndex(4, 2, landmarkNodes);
        connectByIndex(2, 5, landmarkNodes);
        connectByIndex(5, 9, landmarkNodes);
        connectByIndex(5, 8, landmarkNodes);
        connectByIndex(8, 9, landmarkNodes);
        connectByIndex(8, 6, landmarkNodes);
        connectByIndex(6, 7, landmarkNodes);
        connectByIndex(1, 9, landmarkNodes);
        connectByIndex(1, 6, landmarkNodes);
        connectByIndex(1,26,landmarkNodes);
        connectByIndex(26,27,landmarkNodes);
        connectByIndex(27,28,landmarkNodes);
        connectByIndex(28,29,landmarkNodes);
        connectByIndex(35,28,landmarkNodes);
        connectByIndex(1,32,landmarkNodes);
        connectByIndex(32,33,landmarkNodes);
        connectByIndex(33,34,landmarkNodes);
        connectByIndex(3,31,landmarkNodes);
        connectByIndex(31,30,landmarkNodes);
        connectByIndex(39,38,landmarkNodes);
        connectByIndex(38,37,landmarkNodes);
        connectByIndex(37,36,landmarkNodes);
        connectByIndex(36,22,landmarkNodes);
        connectByIndex(22,20,landmarkNodes);
        connectByIndex(20,21,landmarkNodes);
        connectByIndex(20,19,landmarkNodes);
        connectByIndex(19,18,landmarkNodes);
        connectByIndex(18,17,landmarkNodes);
        connectByIndex(17,16,landmarkNodes);
        connectByIndex(16,15,landmarkNodes);
        connectByIndex(15,14,landmarkNodes);
        connectByIndex(16,23,landmarkNodes);
        connectByIndex(18,23,landmarkNodes);
        connectByIndex(23,24,landmarkNodes);
        connectByIndex(23,13,landmarkNodes);
        connectByIndex(13,4,landmarkNodes);
        connectByIndex(13,12,landmarkNodes);
        connectByIndex(7,10,landmarkNodes);
        connectByIndex(10,11,landmarkNodes);
        connectByIndex(11,12,landmarkNodes);
        connectByIndex(8,25,landmarkNodes);
        connectByIndex(5,25,landmarkNodes);
        connectByIndex(34,7,landmarkNodes);


//        avoidLandmark("Clock Tower",landmarkNodes);
//        avoidLandmark("Reginalds Tower",landmarkNodes);
        System.out.println("");
        System.out.println("What is Connected");
        System.out.println("");

        for (GraphNodeDw<Landmark> n : landmarkNodes) {
            System.out.println("");
            System.out.println(n.data.getName());
            for (GraphLinkDw s : n.adjList) System.out.println(s.destNode.data + " Cost: " + s.cost);
        }


        System.out.println("");


//        int [][] bfs =floodFill(create2DArray(mainImage.getImage()),492,162,5);
//        for (int y = 0; y < mainImage.getImage().getHeight(); y++) {
//                System.out.println("");
//            for (int x = 0; x < mainImage.getImage().getWidth(); x++) {
//                System.out.print(bfs[x][y]);
//
//            }
//        }
//
//        colourInPath(mainImage.getImage(),bfs);


       // colourInPath(mainImage.getImage(),BFS(create2DArray(mainImage.getImage()),147,504,515,147));

    }

    /**
     * invertImage method
     * This method turns my black and white vector map to a black and white more user friendly map
     * It takes in an image as a parameter.
     * @param original
     */

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

    public int[][] create2DArray(Image img){

        PixelReader pixelReader = img.getPixelReader();

        int width = (int)img.getWidth();

        int height = (int) img.getHeight();
        WritableImage wImage = new WritableImage(width, height);
        PixelWriter pixelWriter = wImage.getPixelWriter();

        int[][] imgArr = new int[width][height];

        for (int y = 0; y < height; y++) {

            for (int x = 0; x < width; x++) {
                imgArr[x][y] = (pixelReader.getColor(x,y).equals(Color.BLACK) ? 0:-1);

            }
        }





        return imgArr;


    }

    public ArrayList<int[]> BFS(int [][] image, int x1, int y1, int x2, int y2) {
        int[] directions = new int[]{0, 1, 0, -1, 0};

        int startNode = image[x1][y1];
        int destNode = image[x2][y2];
        ArrayList<int[]> agenda = new ArrayList<>();
//        int[]  ={
//            x1, x2
//        } ;
        agenda.add(new int[]{x1, y1});
        image[x1][y1] = 1;
        while (!agenda.isEmpty()) {
            if (agenda.get(0)[0]==x2 && agenda.get(0)[1] == y2) {
                ArrayList<int[]> pathList = new ArrayList<>();
                int[] currentNode = new int[]{x2, y2};
                int v = image[x2][y2];
                int tD = v;

                while (!(currentNode[0] == x1 && currentNode[1] == y1)) {
                    pathList.add(0, currentNode);

                    for (int i = 0; i < directions.length - 1; i++) {
                        int nextx = currentNode[0] + directions[i];
                        int nexty = currentNode[1] + directions[i + 1];

                        if (image[nextx][nexty] == v - 1) {
                            currentNode = new int[]{nextx, nexty};
                            v = v - 1;
                        }
                    }
                }
                return pathList;

            }
             else {
                 int [] store = agenda.remove(0);
                int v = image[store[0]][store[1]];
                for (int i = 0; i < directions.length - 1; i++) {
                    int nextx = store[0] + directions[i];
                    int nexty = store[1] + directions[i + 1];
                    if(nextx<1000 && nextx > 0 && nexty > 0 && nexty<517) {
                        if (image[nextx][nexty] == 0) {
                            image[nextx][nexty] = v + 1;
                            agenda.add(new int[]{nextx, nexty});
                        }
                    }

                }

            }
           // Collections.sort(agenda,(a,b)-> (int) Math.sqrt(Math.pow(a[0]-x2,2)+Math.pow(a[1]-y2,2)-Math.sqrt(Math.pow(b[0]-x2,2)+Math.pow(b[1]-y2,2))));
        }
        return null;
    }

    public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        int[] directions = new int[]{0, 1, 0, -1, 0};
        int m = image.length;
        int n = image[0].length;
        int originalValue = image[sr][sc];
        image[sr][sc] = newColor;

        boolean[][] visited = new boolean[m][n];

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{sr, sc});
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            visited[curr[0]][curr[1]] = true;
            for (int i = 0; i < directions.length - 1; i++) {
                int nextR = curr[0] + directions[i];
                int nextC = curr[1] + directions[i + 1];
                if (nextR < 0 || nextC < 0 || nextR >= m || nextC >= n || image[nextR][nextC] != originalValue || visited[nextR][nextC]) {
                    continue;
                }
                image[nextR][nextC] = newColor;
                queue.offer(new int[]{nextR, nextC});
            }
        }
        return image;
    }


    public void colourInPath(Image original,ArrayList<int[]> pathList) {


        PixelReader pixelReader = original.getPixelReader();

        int width = (int) original.getWidth();
        int height = (int) original.getHeight();
        WritableImage wImage = new WritableImage(width, height);
        PixelWriter pixelWriter = wImage.getPixelWriter();




        for (int y = 0; y < height; y++) {

            for (int x = 0; x < width; x++) {





                pixelWriter.setColor(x, y, pixelReader.getColor(x,y));




            }
        }
        for(int[] a : pathList){
            System.out.println(a[0]+" "+a[1]);
            pixelWriter.setColor(a[0],a[1],Color.RED);
        }
        mainImage.setImage(wImage);



    }




    /**
     * getDistance Method
     * used to calculate mathematically the distance between two nodes
     * This value will bw the cost of travelling from one node to another
     *
     * @param a is the index of a node
     * @param b is the index of another node
     * @param landList list of landmark nodes
     * @return the integer cost of the route
     */

    public int getDistance(int a, int b, List<GraphNodeDw<Landmark>> landList) {
        int x1 = landList.get(a).data.getX();
        int x2 = landList.get(b).data.getX();
        int y1 = landList.get(a).data.getY();
        int y2 = landList.get(b).data.getY();
        return (int) Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }

    /**
     * connectByIndex method
     * method used to connect nodes undirected
     * It also uses the getDistance method as the cost between the two nodes
     * @param a is the index of a node
     * @param b is the index of another node
     * @param landList list of landmark nodes
     */
    public void connectByIndex(int a, int b, List<GraphNodeDw<Landmark>> landList) {

        //int discount = (landmarkNodes.get(b).data.getCultural())? 25:0;

        landList.get(a).connectToNodeUndirected(landList.get(b), getDistance(a, b, landList));
    }

    /**
     * historicDiscount method
     *
     * this method is used to reduce the cost of travelling to a node that is historic (isCultural)
     * This method is run when the option of a historic route eis selected
     * @param landList  list of landmark nodes
     */
    public void historicDiscount(List<GraphNodeDw<Landmark>> landList) {
        for (GraphNodeDw<Landmark> n : landList) {

            for (GraphLinkDw s : n.adjList) {
                s.cost = (n.data.getCultural()) ?   s.cost - 30: s.cost;

            }
        }
    }

    /**
     * findCheapestPathDijkstra
     * this method was taken from lecture notes and was slightly refactored by me.
     * First the start node value is zero and the other nodes have a MAX value set.
     * This method keeps track of encountered and unencountered nodes, It starts by adding the start node to the unencountered arrayList
     * while the unencountered arrayList is not empty meaning theres still nodes to travese the first unencountered node is taken from the list and added to the encountered arrayList
     * then a check is made to see if it is the destination node
     * if its not a loop will start going through all the nodes connected to the current node and see if they have been encountered if not then the cost of the node is updated with the lower cost.
     * That node will also be added to the unencountered arraylist to be examined in the next iterations
     * The unencountered arrayList will also be sordet based on the node value that is cheapest.
     *
     * My addition to this algorithm is that it removes duplicate values from the unencountered arrayList making the algorithm do unnecesary looping.
     * @param startNode node that the path will start
     * @param lookingfor node that the path will finish
     * @param <T>
     * @return the costed path object that has a path cost object and a path list object
     */

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

            Set<GraphNodeDw<?>> set = new HashSet<>(unencountered);
            unencountered.clear();
            unencountered.addAll(set);

            unencountered.sort(Comparator.comparingInt(n -> n.nodeValue));

        } while (!unencountered.isEmpty());
        return null;

    }

    /**
     * waypointSupport method
     * This method uses Dijkstra's algorithm to get from a start node to an end node,
     * It aslo uses a List of nodes that are on its path and needs to traverse through,
     * it does this by repeatedly calling Dijkstra's algorithm.
     * @param landmarkIndexSrc start node
     * @param landmarkIndexDest end node
     * @param waypoints list of nodes that the path must go through
     * @param landList list of nodes that is passed into the method
     * @param lands list of landmark objects
     * @return
     */
    public List<GraphNodeDw<?>> waypointSupport(int landmarkIndexSrc, int landmarkIndexDest, ArrayList<Integer> waypoints, List<GraphNodeDw<Landmark>> landList, List<Landmark> lands) {

        List<GraphNodeDw<?>> pl = new ArrayList<>();
        int pathCost = 0;
        CostedPath cpa = null;
        int lastWaypoint = -1;
        for (Integer i : waypoints) {
            int landmarkIndexWay = i;
            cpa = findCheapestPathDijkstra(landList.get(landmarkIndexSrc), lands.get(landmarkIndexWay));

            pathCost = pathCost + cpa.pathCost;
            pl.addAll(cpa.pathList);
            landmarkIndexSrc = i;
            lastWaypoint = i;

        }
        if (lastWaypoint != -1) {
            cpa = findCheapestPathDijkstra(landList.get(lastWaypoint), lands.get(landmarkIndexDest));
            pathCost = pathCost + cpa.pathCost;
            pl.addAll(cpa.pathList);

        }

        System.out.println("");
        System.out.println("=============================================================================");
        System.out.println("Finding Path with Waypoints Dijkstra");
        for (GraphNodeDw<?> n : pl)
            System.out.println(n.data);

        System.out.println("\nThe total path cost is: " + pathCost);

        return pl;


    }

    /**
     * avoidLandmark method
     * This method essentially looks up a landmark in the list and gives all its links a high cost of reaching ,
     * so that the path will avoid it.
     *
     * @param landmark String of the landmark looking for
     * @param landList list of all landmark objects
     */
    public void avoidLandmark(String landmark, List<GraphNodeDw<Landmark>> landList) {
        for (GraphNodeDw<Landmark> n : landList) {
            // System.out.println("");
            //System.out.println(n.data.getName());
            for (GraphLinkDw s : n.adjList) {
                GraphNodeDw<Landmark> l = (GraphNodeDw<Landmark>) s.destNode;
                if (n.data.getName().equals(landmark) || l.data.getName().equals(landmark)) s.cost = s.cost + 9999999;
                //System.out.println(s.destNode.data +" Cost: " + s.cost);
            }
        }
    }

    /**
     * findLandmark method
     * used for searching for the index at which a specific landmark is located in the array.
     * @param array
     * @param name
     * @return
     */

    public int findLandmark(ArrayList<Landmark> array, String name) {
        for (Landmark node : array) {
            if (node.getName().equals(name)) {
                return array.indexOf(node);
            }
        }
        return -1;
    }


    /**
     * findPathBreadthFirst 2 methods
     * these method work together using tail recursion finding a path with an agenda list
     *The agenda path list is kept in reverse order
     * The first method sets up the search and passes into the second method were
     * it is performs tail recursion checking the encountered arrayList and adding path lists to the end of the agenda
     * It returns the path when the current node is the node that it was looking for.
     * @param startNode node specified as start
     * @param lookingFor note that is the destination
     * @param <T>
     * @return the shortest path only based on the number of connections.
     */
    public <T> List<GraphNodeDw<?>> findPathBreadthFirst(GraphNodeDw<?> startNode, T lookingFor) {
        List<List<GraphNodeDw<?>>> agenda = new ArrayList<>();
        List<GraphNodeDw<?>> firstAgendaPath = new ArrayList<>(), resultPath;
        firstAgendaPath.add(startNode);
        agenda.add(firstAgendaPath);
        resultPath = findPathBreadthFirst(agenda, null, lookingFor);
        Collections.reverse(resultPath);
        return resultPath;
    }


    public <T> List<GraphNodeDw<?>> findPathBreadthFirst(List<List<GraphNodeDw<?>>> agenda, List<GraphNodeDw<?>> encountered, T lookingFor) {
        if (agenda.isEmpty()) return null;
        List<GraphNodeDw<?>> nextPath = agenda.remove(0);
        GraphNodeDw<?> currentNode = nextPath.get(0);
        if (currentNode.data.equals(lookingFor)) return nextPath;
        if (encountered == null) encountered = new ArrayList<>();
        encountered.add(currentNode);
        for (GraphLinkDw adjNode : currentNode.adjList)
            if (!encountered.contains(adjNode.destNode)) {
                List<GraphNodeDw<?>> newPath = new ArrayList<>(nextPath);

                newPath.add(0, adjNode.destNode);
                agenda.add(newPath);

            }
       // Collections.sort(agenda,(a,b)->Math.sqrt(Math.pow(a.x-dest.x,2)+Math.pow(a.y-dest.y,2)-Math.sqrt(Math.pow(b.x-dest.x,2)+Math.pow(b.y-dest.y,2));
        return findPathBreadthFirst(agenda, encountered, lookingFor);

    }

    /**
     * findAllPathsDepthFirst
     *
     * this method was taken from the lecture notes and was refactored by me
     *
     *
     * @param from
     * @param encountered
     * @param lookingfor
     * @param <T>
     * @return
     */
    public <T> List<List<GraphNodeDw<?>>> findAllPathsDepthFirst(GraphNodeDw<?> from, List<GraphNodeDw<?>> encountered, T lookingfor) {
        List<List<GraphNodeDw<?>>> result = null, temp2;
        if (from.data.equals(lookingfor)) {
            List<GraphNodeDw<?>> temp = new ArrayList<>();
            temp.add(from);
            result = new ArrayList<>();
            result.add(temp);
            return result;

        }

        if (encountered == null) encountered = new ArrayList<>();
        encountered.add(from);

        for (GraphLinkDw adjNode : from.adjList) {
            if (!encountered.contains(adjNode.destNode)) {
                temp2 = findAllPathsDepthFirst(adjNode.destNode, new ArrayList<>(encountered), lookingfor);

                if (temp2 != null) {
                    for (List<GraphNodeDw<?>> a : temp2)
                        a.add(0, from);
                    if (result == null) result = temp2;
                    else result.addAll(temp2);
                }
            }
        }
        return result;
    }

    /**
     * DrawSinglePath method
     * This method takes in a path list and draws lines connecting all the nodes.
     *
     * @param pathList a path list taken from the output of the path finding algorithms
     * @param c colour of the path
     */

    public void drawSinglePath(List<GraphNodeDw<?>> pathList,Color c) {

        for (int i = 0; i < pathList.size(); i++) {
            GraphNodeDw<Landmark> node = (GraphNodeDw<Landmark>) pathList.get(i);

            if (i + 1 < pathList.size()) {
                GraphNodeDw<Landmark> nextNode = (GraphNodeDw<Landmark>) pathList.get(i + 1);
                Line l = new Line(node.data.getX(), node.data.getY() + 50, nextNode.data.getX(), nextNode.data.getY() + 50);
                l.setFill(c);
                l.setStroke(c);
                l.setStrokeWidth(5);
                mainPane.getChildren().add(l);
            }

        }
    }


    //==========================================================================================================================================
    // GUI METHODS

    public void openDijksAlgo(ActionEvent actionEvent) {
        dijkstraPane.setVisible(true);
        pathPane.setVisible(false);
    }

    public void addWaypoint(ActionEvent actionEvent) {
        waypointsArray.add(findLandmark((ArrayList<Landmark>) landmarks, waypoints.getValue()));
        waypointsList.setText(waypointsList.getText() + "\n" + waypoints.getValue());
        System.out.println(waypointsArray);

    }

    public void findPathDij(ActionEvent actionEvent) {
        if (histRoute.isSelected()) {
            historicDiscount(landmarkNodes);
        }
        List<GraphNodeDw<?>> pathList = new ArrayList<>();
        if (!waypointsArray.isEmpty()) {
            pathList = waypointSupport(findLandmark((ArrayList<Landmark>) landmarks, startNode.getValue()), findLandmark((ArrayList<Landmark>) landmarks, endNode.getValue()), waypointsArray, landmarkNodes, landmarks);
        } else {
            CostedPath cp = findCheapestPathDijkstra(landmarkNodes.get(findLandmark((ArrayList<Landmark>) landmarks, startNode.getValue())), landmarks.get(findLandmark((ArrayList<Landmark>) landmarks, endNode.getValue())));

            pathList = cp.pathList;
        }

        //
        // Option of doing a hash set however the hash set doesn't keep the path in order
        //
        //HashSet<GraphNodeDw<?>> hs = new HashSet<>(pathList);
        drawSinglePath(pathList,Color.BLUE);
        for (GraphNodeDw<?> n : pathList) {
            GraphNodeDw<Landmark> l = (GraphNodeDw<Landmark>) n;
            routeBox.setText(routeBox.getText() + "\n" + l.data.getName());
        }
    }



    public void findSinglePathBFS(ActionEvent actionEvent) {
        System.out.println("");
        System.out.println("find a path breadth first");
        System.out.println("===============================================================");
        List<GraphNodeDw<?>> breadthFirstPath = findPathBreadthFirst(landmarkNodes.get(findLandmark((ArrayList<Landmark>) landmarks, start.getValue())), landmarks.get(findLandmark((ArrayList<Landmark>) landmarks, end.getValue())));
        drawSinglePath(breadthFirstPath,Color.DEEPPINK);
        for (GraphNodeDw<?> n : breadthFirstPath){
            System.out.println(n.data);
            singlePathBox.setText(singlePathBox.getText()+"\n"+n.data);
        }
    }

    public void findMultiplePathsDFS(ActionEvent actionEvent) {
        System.out.println("All paths depth first");
        System.out.println("===============================================================");
        List<List<GraphNodeDw<?>>> allPaths = findAllPathsDepthFirst(landmarkNodes.get(findLandmark((ArrayList<Landmark>) landmarks, start.getValue())),null, landmarks.get(findLandmark((ArrayList<Landmark>) landmarks, end.getValue())));

        int pCount = 1;

        for (List<GraphNodeDw<?>> p : allPaths) {
            double R = (Math.random());
            double G =(Math.random());
            double B= (Math.random());
            drawSinglePath(p,Color.color(R,B,B));
            System.out.println("\nPath " + (pCount++) + "\n----------------");
            for (GraphNodeDw<?> n : p) {

                System.out.println(n.data);
            }
        }
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,pCount,0,1);
        selectPath.setValueFactory(valueFactory);
    }

    public void showSelectedPath(ActionEvent actionEvent) {
        List<List<GraphNodeDw<?>>> allPaths = findAllPathsDepthFirst(landmarkNodes.get(findLandmark((ArrayList<Landmark>) landmarks, start.getValue())),null, landmarks.get(findLandmark((ArrayList<Landmark>) landmarks, end.getValue())));
        double R = (Math.random());
        double G =(Math.random());
        double B= (Math.random());

        drawSinglePath(allPaths.get(selectPath.getValue()),Color.color(R,G,B));
        multiplePathBox.clear();
        for (GraphNodeDw<?> n : allPaths.get(selectPath.getValue())) {

            System.out.println(n.data);
            multiplePathBox.setText(multiplePathBox.getText()+"\n"+ n.data);
        }


    }

    public void resetDij(ActionEvent actionEvent) {
        routeBox.setText("");
        waypointsArray.clear();
        waypointsList.clear();
        avoidBox.clear();
        waypointsArray.clear();

    }

    public void addToAvoid(ActionEvent actionEvent) {
        avoidBox.setText(avoidBox.getText() + avoidedLand.getValue());
        avoidLandmark(avoidedLand.getValue(), landmarkNodes);
    }
    public void openPathPane(ActionEvent actionEvent) {
        pathPane.setVisible(true);
        dijkstraPane.setVisible(false);
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
        Circle c = new Circle(x, y, 4, Color.RED);
        anPane.getChildren().addAll(c);

        mouseCoord.add(new int[] {x,y});
        for(int[] a : mouseCoord){
            System.out.println(a[0]+" "+ a[1]);
            System.out.println(mouseCoord.size());
        }
        if(mouseCoord.size()==2) {
            System.out.println(mouseCoord.get(0)[0]+" "+mouseCoord.get(0)[1]+" "+mouseCoord.get(1)[0]+" "+mouseCoord.get(1)[1]);
            colourInPath(mainImage.getImage(),BFS(create2DArray(mainImage.getImage()),mouseCoord.get(0)[0],mouseCoord.get(0)[1],mouseCoord.get(1)[0],mouseCoord.get(1)[1]));
            mouseCoord.clear();
        }

        PixelReader p = mainImage.getImage().getPixelReader();

        System.out.println(x + " " + y + " hue: " + p.getColor(x, y).getHue() + "Sat" + p.getColor(x, y).getSaturation());

        Tooltip tl = new Tooltip("You are here");
        //mainPane.getChildren().

        Tooltip.install(mainImage, tl);




    }
    public void clearPane(ActionEvent actionEvent) {
        anPane.getChildren().clear();
//        invertImage(image);
//        int i = 0;
//
//        for (GraphNodeDw<Landmark> l : landmarkNodes) {
//
//            System.out.println(l.data);
//            Text t = new Text(l.data.getX() - 3, l.data.getY() + 3, i + "");
//            Circle c = new Circle(l.data.getX(), l.data.getY(), 7, Color.RED);
//            anPane.getChildren().addAll(c, t);
//            i++;
//
//        }
    }


    //========================================== MUTATORS ====================================================================================================
    public List<Landmark> getLandmarks() {
        return landmarks;
    }

    public List<GraphNodeDw<Landmark>> getLandmarkNodes() {
        return landmarkNodes;
    }

    public ObservableList<String> getNames() {
        return names;
    }

    public ArrayList<Integer> getWaypointsArray() {
        return waypointsArray;
    }

    public void setLandmarks(List<Landmark> landmarks) {
        this.landmarks = landmarks;
    }

    public void setLandmarkNodes(List<GraphNodeDw<Landmark>> landmarkNodes) {
        this.landmarkNodes = landmarkNodes;
    }

    public void setNames(ObservableList<String> names) {
        this.names = names;
    }

    public void setWaypointsArray(ArrayList<Integer> waypointsArray) {
        this.waypointsArray = waypointsArray;
    }



}


