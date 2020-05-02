package ru.topjava.basejava.storage;

import ru.topjava.basejava.model.Resume;

import java.util.Arrays;
import java.util.Objects;

public abstract class AbstractArrayStorage implements Storage {

    protected static final int DEFAULT_CAPACITY = 10_000;
    protected final Resume[] storage;
    protected int size;

    protected AbstractArrayStorage() {
        storage = new Resume[DEFAULT_CAPACITY];
    }

    protected abstract void insertResume(Resume r);

    protected abstract int indexOf(String uuid);

    @Override
    public void save(Resume r) {
        Objects.requireNonNull(r);
        int index = indexOf(r.getUuid());
        if (index < 0) {
            if (size < storage.length) {
                insertResume(r);
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
        if (index >= 0) {
            storage[index] = r;
        } else {
            System.out.printf("Resume with id: \"%s\" does not exists in the storage!\n",
                    r.getUuid());
        }
    }

    @Override
    public Resume get(String uuid) {
        int index = indexOf(uuid);
        if (index >= 0) {
            return storage[index];
        }
        System.out.printf("Resume with id: \"%s\" was not found in the storage!\n", uuid);
        return null;
    }

    @Override
    public void delete(String uuid) {
        int index = indexOf(uuid);
        if (index >= 0) {
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

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
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
