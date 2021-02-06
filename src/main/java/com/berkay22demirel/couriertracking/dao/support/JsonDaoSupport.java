package com.berkay22demirel.couriertracking.dao.support;

import com.berkay22demirel.couriertracking.model.Store;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.PathResource;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public abstract class JsonDaoSupport<T, ID extends Serializable> implements IDaoSupport<T, ID> {

    private final String path;
    private final Field idField;

    @SuppressWarnings("unchecked")
    public JsonDaoSupport(String documentName) {
        this.path = "src/main/resources/static/" + documentName + ".json";
        Class<T> classType = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        idField = Arrays.stream(classType.getDeclaredFields()).filter(field -> field.getAnnotation(com.berkay22demirel.couriertracking.aop.annotation.ID.class) != null).findFirst().orElse(null);
        Objects.requireNonNull(idField).setAccessible(true);
    }

    @SuppressWarnings("unchecked")
    @Override
    public ID persist(T entity) throws IOException, IllegalAccessException {
        generateId(entity);
        Collection<T> insertData = read();
        insertData.add(entity);
        write(insertData);
        return (ID) idField.get(entity);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void update(T entity) throws IOException {
        Collection<T> updateData = read().stream().map(data -> {
            try {
                ID dataId = (ID) (ID) idField.get(data);
                ID updateId = (ID) idField.get(entity);
                if (dataId.equals(updateId)) return entity;
                return data;
            } catch (IllegalAccessException e) {
                return data;
            }
        }).collect(Collectors.toList());
        write(updateData);
    }

    @SuppressWarnings("unchecked")
    @Override
    public int delete(ID id) throws IOException {
        Collection<T> currentData = read();
        Collection<T> deleteData = currentData.stream().filter(data -> {
            try {
                ID dataId = (ID) idField.get(data);
                return !dataId.equals(id);
            } catch (IllegalAccessException e) {
                return true;
            }
        }).collect(Collectors.toList());
        write(deleteData);
        return currentData.size() - deleteData.size();
    }

    @SuppressWarnings("unchecked")
    @Override
    public T get(ID id) throws IOException {
        return read().stream().filter(data -> {
            try {
                ID dataId = (ID) idField.get(data);
                return dataId.equals(id);
            } catch (IllegalAccessException e) {
                return false;
            }
        }).findFirst().orElse(null);
    }

    @Override
    public Collection<T> findAll() throws IOException {
        return read();
    }

    private Collection<T> read() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        InputStream inputStream = new PathResource(path).getInputStream();
        JavaType type = mapper.getTypeFactory().
                constructCollectionType(Collection.class, Store.class);
        return mapper.readValue(inputStream, type);
    }

    private void write(Collection<T> data) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        FileWriter fileWriter = new FileWriter(path);
        mapper.writeValue(fileWriter, data);
    }

    @SuppressWarnings("unchecked")
    private void generateId(T entity) throws IllegalAccessException {
        ID entityId = (ID) idField.get(entity);
        if (entityId instanceof Long) {
            idField.set(entity, ThreadLocalRandom.current().nextLong(1000000));
        } else if (entityId instanceof String) {
            idField.set(entity, UUID.randomUUID().toString());
        }
    }

}
