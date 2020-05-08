package ru.topjava.basejava.storage;

import org.junit.jupiter.api.*;
import ru.topjava.basejava.exception.ExistStorageException;
import ru.topjava.basejava.exception.NotExistStorageException;
import ru.topjava.basejava.exception.StorageException;
import ru.topjava.basejava.model.Resume;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@Disabled("This is an abstract class")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AbstractArrayStorageTest {
    private static final Resume RESUME_TEST_1 = new Resume("RESUME_TEST_1");
    private static final Resume RESUME_TEST_2 = new Resume("RESUME_TEST_2");
    private static final Resume RESUME_TEST_3 = new Resume("RESUME_TEST_3");

    private static final String NONEXISTENT_UUID = "[nonexistent]";
    private static final Resume NONEXISTENT_RESUME = new Resume(NONEXISTENT_UUID);

    private static final Resume[] EMPTY_RESUME_ARRAY = new Resume[0];
    private static final Resume[] TEST_RESUME_ARRAY = new Resume[]{RESUME_TEST_1, RESUME_TEST_2, RESUME_TEST_3};

    private static final int INITIAL_TEST_SIZE = 3;

    private final AbstractArrayStorage storage;

    public AbstractArrayStorageTest(AbstractArrayStorage storage) {
        this.storage = storage;
    }

    @BeforeEach
    void init() {
        storage.clear();
        storage.save(RESUME_TEST_1);
        storage.save(RESUME_TEST_2);
        storage.save(RESUME_TEST_3);
    }

    @Nested
    @DisplayName("updating")
    class Updating {

        @Test
        @DisplayName("when the resume is null")
        void updateNullResume() {
            assertThrows(NullPointerException.class, () -> storage.update(null),
                    "should throw NullPointerException");
        }

        @Test
        @DisplayName("when the resume doesn't exist in storage")
        void deleteNotExistingResume() {
            assertThrows(NotExistStorageException.class, () -> storage.update(NONEXISTENT_RESUME),
                    "should throw NotExistStorageException");
        }

        @Test
        @DisplayName("when the resume exists in storage")
        void updateAnExistingResume() {
            Resume newResume = new Resume(RESUME_TEST_1.getUuid());
            storage.update(newResume);
            assertSame(newResume, storage.get(RESUME_TEST_1.getUuid()),
                    "should replace existing resume with a new one ");
        }

    }

    @Nested
    @DisplayName("getting")
    class Getting {

        @Test
        @DisplayName("when the resume exists in storage")
        void getAnExistingResume() {
            assertSame(RESUME_TEST_1, storage.get(RESUME_TEST_1.getUuid()),
                    "should return the existing resume from array");
        }

        @Test
        @DisplayName("when the UUID is null")
        void getUuidIsNull() {
            assertThrows(NullPointerException.class, () -> storage.get(null),
                    "should throw NullPointerException");
        }

        @Test
        @DisplayName("when the resume doesn't exist in storage")
        void deleteNotExistingResume() {
            assertThrows(NotExistStorageException.class, () -> storage.get(NONEXISTENT_UUID),
                    "should throw NotExistStorageException");
        }

    }

    @Nested
    @DisplayName("getting all")
    class GettingAll {

        @Test
        @DisplayName("when storage isn't empty")
        void getAllNotEmpty() {
            assertArrayEquals(TEST_RESUME_ARRAY, storage.getAll(),
                    () -> "should return array: " + Arrays.toString(TEST_RESUME_ARRAY));
        }

        @Test
        @DisplayName("when storage is empty")
        void getAllEmpty() {
            storage.clear();
            assertArrayEquals(EMPTY_RESUME_ARRAY, storage.getAll(),
                    "should return an empty Resume array");
        }

    }


    @Nested
    @DisplayName("saving")
    class Saving {

        @Test
        @DisplayName("when the resume is null")
        void saveNullResume() {
            assertThrows(NullPointerException.class, () -> storage.save(null),
                    "should throw NullPointerException");
        }

        @Test
        @DisplayName("when the resume exists in storage")
        void saveExistingResume() {
            assertThrows(ExistStorageException.class, () -> storage.save(RESUME_TEST_1),
                    "should throw ExistStorageException");
        }

        @Test
        @DisplayName("when the storage isn't full")
        void saveNotExistingResume() {
            storage.save(NONEXISTENT_RESUME);
            assertSame(NONEXISTENT_RESUME, storage.get(NONEXISTENT_RESUME.getUuid()),
                    "should return the same resume by UUID");
            assertSame(INITIAL_TEST_SIZE + 1, storage.size(),
                    "should increase the size by 1");
        }

        @Test
        @DisplayName("when the storage is full")
        void saveInFullStorage() {
            fillStorage();
            assertThrows(StorageException.class, () -> storage.save(new Resume()),
                    "should throw StorageException");
        }

        @Test
        @DisplayName("during the filling the storage")
        void fillingStorage() {
            assertDoesNotThrow(this::fillStorage, "shouldn't throw an exception");
        }

        private void fillStorage() {
            for (int i = storage.size(); i < AbstractArrayStorage.DEFAULT_CAPACITY; i++) {
                storage.save(new Resume());
            }
        }

    }

    @Nested
    @DisplayName("deleting")
    class Deleting {

        @Test
        @DisplayName("when the resume exists in storage")
        void deleteAnExistingResume() {
            storage.delete(RESUME_TEST_1.getUuid());
            assertSame(INITIAL_TEST_SIZE - 1, storage.size(), "should decrease the size by 1");
            assertThrows(NotExistStorageException.class, () -> storage.get(RESUME_TEST_1.getUuid()),
                    "should remove the resume from array");
        }

        @Test
        @DisplayName("when the UUID is null")
        void deleteUuidIsNull() {
            assertThrows(NullPointerException.class, () -> storage.delete(null),
                    "should throw NullPointerException");
        }

        @Test
        @DisplayName("when the resume doesn't exist in storage")
        void deleteNotExistingResume() {
            assertThrows(NotExistStorageException.class, () -> storage.delete(NONEXISTENT_UUID),
                    "should throw NotExistStorageException");
        }

    }

    @Nested
    @DisplayName("clearing")
    class Clearing {

        @Test
        @DisplayName("when the storage isn't empty")
        void clearNotEmpty() {
            int size = storage.size();
            storage.clear();
            assertSame(0, storage.size(), "should reduce the size to 0");
            for (int i = 0; i < size; i++) {
                if (storage.storage[i] != null) {
                    fail("should reset all deleted elements to null");
                }
            }
        }

    }

    @Nested
    @DisplayName("getting the size")
    class GettingSize {

        @Test
        @DisplayName("when the storage isn't empty")
        void sizeNotEmpty() {
            assertSame(INITIAL_TEST_SIZE, storage.size(),
                    "should return actual size: " + INITIAL_TEST_SIZE);
        }

        @Test
        @DisplayName("when the storage is empty")
        void sizeOfEmpty() {
            storage.clear();
            assertSame(0, storage.size(), "should return 0");
        }
    }

}
