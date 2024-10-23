package server.server;

public interface Repository<T> {
    void save(String message);
    T load();
}
