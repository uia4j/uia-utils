package uia.utils.cube;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

public interface Cube<T> {

    public Cube<T> select(String tagName, String tagValue);

    public Map<String, List<T>> mapping(final String tagName);

    public Map<String, T> mappingFirst(final String tagName);

    public T firstValue();

    public Stream<T> values();

    public static class Data<T> {

        private final TreeMap<String, String> tags;

        public final T value;

        Data(T value) {
            this.tags = new TreeMap<String, String>();
            this.value = value;
        }

        public Data<T> addTag(String tagName, String tagValue) {
            this.tags.put(tagName, tagValue);
            return this;
        }

        public String getTag(String tagName) {
            return this.tags.get(tagName);
        }
    }
}
