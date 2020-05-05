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

    protected abstract void saveToArray(int index, Resume resume);

    protected abstract int indexOf(String uuid);

    protected abstract void deleteFromArray(int index);

    @Override
    public void save(Resume resume) {
        Objects.requireNonNull(resume);
        int index = indexOf(resume.getUuid());
        if (index >= 0) {
            System.out.printf("Resume with id: \"%s\" already exists in the storage!\n", resume.getUuid());
        } else if (size == storage.length) {
            System.out.println("The storage is full");
        } else {
            saveToArray(index, resume);
            size++;
        }
    }

    @Override
    public void update(Resume resume) {
        Objects.requireNonNull(resume);
        int index = indexOf(resume.getUuid());
        if (index < 0) {
            System.out.printf("Resume with id: \"%s\" does not exists in the storage!\n", resume.getUuid());
        } else {
            storage[index] = resume;
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
        if (index < 0) {
            System.out.printf("Resume with id: \"%s\" does not exists in the storage!\n", uuid);
        } else {
            if (index < size - 1) {
                deleteFromArray(index);
            }
            storage[--size] = null;
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
