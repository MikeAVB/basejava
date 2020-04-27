import java.util.Arrays;
import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private static final int DEFAULT_CAPACITY = 10_000;

    private final Resume[] storage;
    private int size;

    public ArrayStorage() {
        this.storage = new Resume[DEFAULT_CAPACITY];
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
        int index = indexOf(r.uuid);
        if (index != -1) {
            storage[index] = r;
        } else {
            storage[size] = r;
            size++;
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
        int index = indexOf(uuid);
        if (index != -1) {
            if (index < size - 1) {
                System.arraycopy(storage, index + 1, storage, index,
                        size - index - 1);
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
