package uia.utils.cube;

import java.util.ArrayList;

import uia.utils.cube.Cube.Data;

public class CubeBuilder<T> {

    private ArrayList<Data<T>> data;

    public CubeBuilder() {
        this.data = new ArrayList<Data<T>>();
    }

    public Cube<T> build() {
        return new ListCube<T>(this.data);
    }

    public Data<T> put(T value) {
        Data<T> data = new Data<T>(value);
        this.data.add(data);
        return data;
    }
}
