package ru.topjava.basejava.storage;

import org.junit.jupiter.api.DisplayName;

@DisplayName("Unsorted array storage test")
class ArrayStorageTest extends AbstractArrayStorageTest {

    public ArrayStorageTest() {
        super(new ArrayStorage());
    }

}
