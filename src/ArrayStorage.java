import java.util.Arrays;
import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private static final int DEFAULT_CAPACITY = 10_000;

    private Resume[] storage;
    private int size;

    public ArrayStorage() {
        this.storage = new Resume[DEFAULT_CAPACITY];
        this.size = 0;
    }

    public ArrayStorage(int capacity) {
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
        Objects.requireNonNull(r);
        int existingIndex = indexOf(r.uuid);
        if (existingIndex != -1) {
            storage[existingIndex] = r;
        } else {
            ensureCapacity(size + 1);
            storage[size] = r;
            size++;
        }
    }

    private void ensureCapacity(int requiredCapacity) {
        if (requiredCapacity > storage.length) {
            int oldCapacity = storage.length;
            int newCapacity = oldCapacity + (oldCapacity >> 1);
            storage = Arrays.copyOf(storage, newCapacity);
        }
    }

    public Resume get(String uuid) {
        int requiredIndex = indexOf(uuid);
        if (requiredIndex == -1) {
            return null;
        }
        return storage[requiredIndex];
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
            if (requiredIndex < size - 1) {
                System.arraycopy(storage, requiredIndex + 1, storage, requiredIndex,
                        size - requiredIndex - 1);
            }
            storage[size - 1] = null;
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
