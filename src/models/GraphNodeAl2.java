package models;

import java.util.ArrayList;
import java.util.List;

public class GraphNodeAl2<T> {
    public T data;
    public List<GraphLinkAl> adjList=new ArrayList<>();

    public GraphNodeAl2(T data){
        this.data=data;
    }

    public void connectToNodeDirected(GraphNodeAl2<T> destNode,int cost){
        adjList.add(new GraphLinkAl(destNode,cost));
    }

    public void connectToNodeUndirected(GraphNodeAl2<T> destNode,int cost){
        adjList.add(new GraphLinkAl(destNode,cost));
        destNode.adjList.add(new GraphLinkAl(this,cost));
    }
}
