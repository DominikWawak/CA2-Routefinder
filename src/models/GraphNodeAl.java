package models;

import java.util.ArrayList;
import java.util.List;

public class GraphNodeAl<T> {
    public T data;
    public List<GraphNodeAl<T>> adjlist=new ArrayList<>();

    public GraphNodeAl(T data){
        this.data=data;
    }

    public void connectToNodeDirected(GraphNodeAl<T> destNode){
        adjlist.add(destNode);
    }

    public void connectToNodeUndirected(GraphNodeAl<T> destNode){
        adjlist.add(destNode);
        destNode.adjlist.add(this);
    }
}
