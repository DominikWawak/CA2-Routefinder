package models;

public class GraphLinkDw {

    public GraphNodeDw<?> destNode;

    public int cost;

    public GraphLinkDw(GraphNodeDw<?> destNode,int cost){
        this.destNode=destNode;
        this.cost=cost;
    }
}
