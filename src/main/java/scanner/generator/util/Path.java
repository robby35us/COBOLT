package scanner.generator.util;

public class Path implements  Comparable<Path>{
    private String pathString;

    Path(String string) {
        pathString = string;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Path
                && this.pathString.equals(((Path) o ).pathString);
    }

    @Override
    public String toString() {
        return pathString;
    }

    @Override
    public int compareTo(Path o) {
        return this.pathString.compareTo(o.pathString);
    }
}