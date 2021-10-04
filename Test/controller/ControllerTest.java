package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import models.CostedPath;
import models.GraphNodeDw;
import models.Landmark;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ControllerTest {
    private List<Landmark> landmarks = new ArrayList<>();
    private List<GraphNodeDw<Landmark>> landmarkNodes = new ArrayList<>();
    private ObservableList<String> names = FXCollections.observableArrayList();
    private ArrayList<Integer> waypointsArray = new ArrayList<>();
    private Controller c = new Controller();
    Image image = new Image("src/waterford.jpg");

        @Before
    public void setUp() throws Exception {


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

            c.connectByIndex(0, 9,landmarkNodes);
            c.connectByIndex(0, 2,landmarkNodes);
            c.connectByIndex(0, 3,landmarkNodes);
            c.connectByIndex(3, 4,landmarkNodes);
            c.connectByIndex(4, 2,landmarkNodes);
            c.connectByIndex(2, 5,landmarkNodes);
            c.connectByIndex(5, 9,landmarkNodes);
            c.connectByIndex(5, 8,landmarkNodes);
            c.connectByIndex(8, 9,landmarkNodes);
            c.connectByIndex(8, 6,landmarkNodes);
            c.connectByIndex(6, 7,landmarkNodes);
            c.connectByIndex(1, 9,landmarkNodes);
            c.connectByIndex(1, 6,landmarkNodes);




        }


    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getDistance() {

        assertEquals(143,c.getDistance(0,1,landmarkNodes));


    }

    @Test
    public void connectByIndex() {
            c.connectByIndex(0,1,landmarkNodes);
            assertTrue(landmarkNodes.get(0).adjList.contains(landmarkNodes.get(1)));
    }

    @Test
    public void historicDiscount() {
            assertEquals(83,landmarkNodes.get(9).adjList.get(0).cost);
            c.historicDiscount(landmarkNodes);
            assertEquals(53,landmarkNodes.get(9).adjList.get(0).cost);

    }

    @Test
    public void waypointSupport() {
            waypointsArray.add(9);
            waypointsArray.add(1);
            List<GraphNodeDw<?> >costPath= c.waypointSupport(0,7,waypointsArray,landmarkNodes,landmarks);
            assertTrue(costPath.contains(landmarkNodes.get(9)));
            assertTrue(costPath.contains(landmarkNodes.get(1)));
    }

    @Test
    public void avoidLandmark() {

        CostedPath cpa = c.findCheapestPathDijkstra(landmarkNodes.get(0),landmarks.get(6));
        assertTrue(cpa.pathList.contains(landmarkNodes.get(5)));
        c.avoidLandmark("City Square",landmarkNodes);
        CostedPath cpa2 = c.findCheapestPathDijkstra(landmarkNodes.get(0),landmarks.get(6));
        assertFalse(cpa2.pathList.contains(landmarkNodes.get(5)));
    }

    @Test
    public void findLandmark() {
            assertEquals(0,c.findLandmark((ArrayList<Landmark>) landmarks,"Reginalds Tower"));
    }


    @Test
    public void BFSPixelTest(){
            assertTrue(c.BFS(c.create2DArray(image),147,504,515,147).contains(new int[]{916, 459}));
    }

    @Test
    public void findCheapestPathDijkstra() {
        CostedPath cpa = c.findCheapestPathDijkstra(landmarkNodes.get(1), landmarks.get(3));

        System.out.println("");
        System.out.println("=============================================================================");
        System.out.println("Finding Path with Dijkstra");
        for (GraphNodeDw<?> n : cpa.pathList)
            System.out.println(n.data);

        System.out.println("\nThe total path cost is: " + cpa.pathCost);

        assertEquals(149,cpa.pathCost);
    }
    @Test
    public void findPathBFS() {
        System.out.println("");
        System.out.println("find a path breadth first");
        System.out.println("===============================================================");
        List<GraphNodeDw<?>> breadthFirstPath = c.findPathBreadthFirst(landmarkNodes.get(4),landmarks.get(1));
        for (GraphNodeDw<?> n : breadthFirstPath){
            System.out.println(n.data);

        }
    }
    @Test
    public void findMultiplePathsDFS() {
        System.out.println("All paths depth first");
        System.out.println("===============================================================");
        List<List<GraphNodeDw<?>>> allPaths = c.findAllPathsDepthFirst(landmarkNodes.get(1),null,landmarks.get(3));

        int pCount = 1;

        for (List<GraphNodeDw<?>> p : allPaths) {

            System.out.println("\nPath " + (pCount++) + "\n----------------");
            for (GraphNodeDw<?> n : p) {

                System.out.println(n.data);
            }
        }
    }


}