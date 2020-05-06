package ru.topjava.basejava.exception;

public class NotExistStorageException extends StorageException {

    public NotExistStorageException(String uuid) {
        super("Resume not exists in the storage", uuid);
    }
}
