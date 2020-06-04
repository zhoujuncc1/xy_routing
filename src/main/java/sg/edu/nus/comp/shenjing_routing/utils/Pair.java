package sg.edu.nus.comp.shenjing_routing.utils;

public class Pair<T>{
    public T x;
    public T y;

    public Pair(T x, T y){
        this.x=x;
        this.y=y;
    }

    public boolean equals(Pair<T> o) {
        return x.equals(o.x) && y.equals(o.y);
    }
}
