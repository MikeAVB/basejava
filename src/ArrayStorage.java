import java.util.Arrays;
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
        size = 0;
    }

    public void save(Resume r) {
        int existingIndex = indexOf(r);
        if (existingIndex != -1) {
            storage[existingIndex] = r;
        } else {
            if (size == capacity) {
                ensureCapacity();
            }
            storage[size] = r;
            size++;
        }
    }

    private int indexOf(Resume r) {
        Objects.requireNonNull(r);
        for (int i = 0; i < size; i++) {
            if (r == storage[i] || storage[i].uuid.equals(r.uuid)) {
                return i;
            }
        }
        return -1;
    }

    private void ensureCapacity() {
        int newCapacity = capacity + (capacity >> 1);
        storage = Arrays.copyOf(storage, newCapacity);
        capacity = newCapacity;
    }

    public Resume get(String uuid) {
        int requiredIndex = indexOf(uuid);
        if (requiredIndex == -1) {
            return null;
        } else {
            return storage[requiredIndex];
        }
    }

    private int indexOf(String uuid) {
        Objects.requireNonNull(uuid);
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].uuid)) {
                return i;
            }
        }
        return -1;
    }

    public void delete(String uuid) {
        int requiredIndex = indexOf(uuid);
        if (requiredIndex != -1) {
            if (requiredIndex == size - 1) {
                storage[requiredIndex] = null;
            } else {
                System.arraycopy(storage, requiredIndex + 1, storage, requiredIndex,
                        size - requiredIndex - 1);
                storage[size - 1] = null;
            }
            size--;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    int size() {
        return size;
    }

}
