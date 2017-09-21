package uia.utils.cube;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamCube<T> implements Cube<T> {

    final Stream<Data<T>> data;

    StreamCube(Stream<Data<T>> data) {
        this.data = data;
    }

    @Override
    public T firstValue() {
        return this.data.findFirst().get().value;
    }

    @Override
    public Stream<T> values() {
        return this.data.map(t -> t.value);
    }

    @Override
    public Map<String, List<T>> mapping(final String tagName) {
        return this.data.collect(
                Collectors.groupingBy(
                        d -> d.tags.get(tagName),
                        Collectors.mapping(v -> v.value, Collectors.toList())));
    }

    @Override
    public Map<String, T> mappingFirst(final String tagName) {
        TreeMap<String, T> result = new TreeMap<String, T>();
        mapping(tagName).forEach((k, v) -> result.put(k, v.get(0)));
        return result;
    }

    @Override
    public Cube<T> select(String tagName, String tagValue) {
        return new StreamCube<T>(this.data.filter(d -> tagValue.equals(d.tags.get(tagName))));
    }
}
