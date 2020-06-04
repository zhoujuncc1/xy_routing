package sg.edu.nus.comp.shenjing_routing.utils;

public class Triple<T>{
    public T x;
    public T y;
    public T z;
    public Triple(T x, T y, T z){
        this.x=x;
        this.y=y;
        this.z=z;
    }

    public boolean equals(Triple<T> o) {
        return x.equals(o.x) && y.equals(o.y) && z.equals(o.z);
    }
}
