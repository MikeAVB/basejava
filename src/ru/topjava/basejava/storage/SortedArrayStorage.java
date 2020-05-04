package ru.topjava.basejava.storage;

import ru.topjava.basejava.model.Resume;

import java.util.Arrays;
import java.util.Objects;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void saveToArray(Resume resume) {
        int insertionPoint = -Arrays.binarySearch(storage, 0, size, resume) - 1;
        if (insertionPoint < size) {
            System.arraycopy(storage, insertionPoint, storage, insertionPoint + 1, size - insertionPoint);
        }
        storage[insertionPoint] = resume;
    }

    @Override
    protected int indexOf(String uuid) {
        Objects.requireNonNull(uuid);
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void deleteFromArray(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index - 1);
        storage[size - 1] = null;
    }

}
