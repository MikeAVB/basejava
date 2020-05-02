package ru.topjava.basejava.storage;

import ru.topjava.basejava.model.Resume;

import java.util.Arrays;
import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayResumeStorage implements ResumeStorage {
    private static final int DEFAULT_CAPACITY = 10_000;

    private final Resume[] storage;
    private int size;

    public ArrayResumeStorage() {
        storage = new Resume[DEFAULT_CAPACITY];
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public void save(Resume r) {
        Objects.requireNonNull(r);
        int index = indexOf(r.getUuid());
        if (index == -1) {
            if (size < storage.length) {
                storage[size] = r;
                size++;
            } else {
                System.out.println("The storage is full");
            }
        } else {
            System.out.printf("Resume with id: \"%s\" already exists in the storage!\n",
                    r.getUuid());
        }
    }

    @Override
    public void update(Resume r) {
        Objects.requireNonNull(r);
        int index = indexOf(r.getUuid());
        if (index != -1) {
            storage[index] = r;
        } else {
            System.out.printf("Resume with id: \"%s\" does not exists in the storage!\n",
                    r.getUuid());
        }
    }

    @Override
    public Resume get(String uuid) {
        int index = indexOf(uuid);
        if (index != -1) {
            return storage[index];
        }
        System.out.printf("Resume with id: \"%s\" was not found in the storage!\n", uuid);
        return null;
    }

    private int indexOf(String uuid) {
        Objects.requireNonNull(uuid);
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void delete(String uuid) {
        int index = indexOf(uuid);
        if (index != -1) {
            if (index < size - 1) {
                System.arraycopy(storage, index + 1, storage, index,
                        size - index - 1);
            }
            storage[size - 1] = null;
            size--;
        } else {
            System.out.printf("Resume with id: \"%s\" does not exists in the storage!\n",
                    uuid);
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    public int size() {
        return size;
    }

}
