package ru.topjava.basejava.exception;

public class ExistStorageException extends StorageException {

    public ExistStorageException(String uuid) {
        super("Resume already exists", uuid);
    }
}
