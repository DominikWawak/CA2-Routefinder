package models;

import java.util.ArrayList;
import java.util.List;

public class GraphNodeDw<T> {

    public T data;
    public int nodeValue=Integer.MAX_VALUE;

    public List<GraphLinkDw> adjList=new ArrayList<>();

     public GraphNodeDw(T data) {this.data=data;}


     public void connectToNodeDirected(GraphNodeDw<T> destNode,int cost) {
         adjList.add(new GraphLinkDw(destNode,cost));
     }

     public void connectToNodeUndirected(GraphNodeDw<T> destNode,int cost) {
         adjList.add(new GraphLinkDw(destNode,cost));
         destNode.adjList.add(new GraphLinkDw(this,cost));
     }
}
