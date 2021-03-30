package models;

public class Landmark {
    private String name;
    private Boolean isCultural;
    private int x;
    private int y;

    public Landmark(String name, int x, int y,Boolean isCultural) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.isCultural=isCultural;
    }


    @Override
    public String toString() {
        return "Landmark{" +
                "name='" + name + '\'' +
                ", isCultural=" + isCultural +
                ", x=" + x +
                ", y=" + y +
                '}';
    }

    public Boolean getCultural() {
        return isCultural;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
