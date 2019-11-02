package scanner.model;

public class Path {
    private String pathString;

    public Path(String string) {
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
}