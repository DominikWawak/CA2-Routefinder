package models;

public class GraphLinkAl {
    public GraphNodeAl2<?> destNode;
    public int cost;

    public GraphLinkAl(GraphNodeAl2<?> destNode,int cost){
        this.destNode=destNode;
        this.cost=cost;
    }
}
