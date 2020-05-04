package ru.topjava.basejava.storage;

import ru.topjava.basejava.model.Resume;

import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void saveToArray(Resume resume) {
        storage[size] = resume;
    }

    @Override
    protected int indexOf(String uuid) {
        Objects.requireNonNull(uuid);
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void deleteFromArray(int index) {
        storage[index] = storage[size - 1];
        storage[size - 1] = null;
    }

}
