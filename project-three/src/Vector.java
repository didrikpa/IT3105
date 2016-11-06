public class Vector {

    private double xValue;
    private double yValue;

    public Vector(double xValue, double yValue){
        this.xValue = xValue;
        this.yValue = yValue;
    }

    public double getxValue() {
        return xValue;
    }

    public void setxValue(double xValue) {
        this.xValue = xValue;
    }

    public double getyValue() {
        return yValue;
    }

    public void setyValue(double yValue) {
        this.yValue = yValue;
    }

    public String toString(){
        return "[" + xValue + ", " + yValue + "]";
    }
}
