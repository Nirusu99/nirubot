package nirusu.nirubot.core;

public class Tupel<T, E> {
    private T first;
    private E second;

    public static<T, E> Tupel<T, E> of(T first, E second) {
        return new Tupel<>(first, second);
    }

    private Tupel(T first, E second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return this.first;
    }

    public E getSecond() {
        return this.second;
    }
    
}
