import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private static final int DEFAULT_CAPACITY = 10_000;
    private Resume[] storage;
    private int capacity;
    private int size;

    public ArrayStorage() {
        this.capacity = DEFAULT_CAPACITY;
        this.storage = new Resume[DEFAULT_CAPACITY];
        this.size = 0;
    }

    public ArrayStorage(int capacity) {
        this.capacity = capacity;
        this.storage = new Resume[capacity];
        this.size = 0;
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
    }

    void save(Resume r) {
    }

    Resume get(String uuid) {
        return null;
    }

    void delete(String uuid) {
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return new Resume[0];
    }

    int size() {
        return 0;
    }

    private int indexOf(Resume r) {
        Objects.requireNonNull(r);
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(r.uuid)) {
                return i;
            }
        }
        return -1;
    }

}
