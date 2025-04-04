public interface DataAccessObject<T> {
    void create(T entity);
    T read(int id);
    void update(T entity);
    void delete(int id);
}