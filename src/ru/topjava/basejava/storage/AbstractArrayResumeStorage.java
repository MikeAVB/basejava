package ru.topjava.basejava.storage;

import ru.topjava.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayResumeStorage implements ResumeStorage {
    protected static final int DEFAULT_CAPACITY = 10_000;
    protected final Resume[] storage;
    protected int size;

    protected AbstractArrayResumeStorage() {
        storage = new Resume[DEFAULT_CAPACITY];
    }

    @Override
    public abstract void save(Resume r);

    @Override
    public abstract void update(Resume r);

    @Override
    public abstract Resume get(String uuid);

    @Override
    public abstract void delete(String uuid);

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
