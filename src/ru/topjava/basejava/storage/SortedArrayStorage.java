package ru.topjava.basejava.storage;

import ru.topjava.basejava.model.Resume;

import java.util.Arrays;
import java.util.Objects;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insertResume(Resume r) {
        int insertionPoint = -Arrays.binarySearch(storage, 0, size, r) - 1;
        if (insertionPoint < size) {
            System.arraycopy(storage, insertionPoint, storage, insertionPoint + 1,
                    size - insertionPoint);
        }
        storage[insertionPoint] = r;
    }

    @Override
    protected int indexOf(String uuid) {
        Objects.requireNonNull(uuid);
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
