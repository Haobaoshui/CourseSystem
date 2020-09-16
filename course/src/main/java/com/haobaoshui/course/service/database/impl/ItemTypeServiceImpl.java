package com.haobaoshui.course.service.database.impl;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.database.ItemType;
import com.haobaoshui.course.repository.database.ItemTypeRepository;
import com.haobaoshui.course.service.database.ItemTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ItemTypeServiceImpl implements ItemTypeService {


    private final ItemTypeRepository itemTypeRepository;

    @Autowired
    public ItemTypeServiceImpl(ItemTypeRepository itemTypeRepository) {
        this.itemTypeRepository = itemTypeRepository;
    }

    @Override
    public String add(ItemType itemType) {
        return itemTypeRepository.add(itemType);
    }

    @Override
    public String add(String name, String note) {
        return itemTypeRepository.add(name, note);
    }

    @Override
    public int deleteById(String id) {
        return itemTypeRepository.deleteById(id);
    }

    @Override
    public int delete(ItemType itemType) {
        if (itemType == null) return 0;
        return deleteById(itemType.getId());
    }

    @Override
    public int deleteAll() {
        return itemTypeRepository.deleteAll();
    }

    @Override
    public int update(String id, String name, String note) {
        return itemTypeRepository.update(id, name, note);
    }

    @Override
    public int update(ItemType itemType) {
        return itemTypeRepository.update(itemType);
    }

    @Override
    public int getCount() {
        return itemTypeRepository.getCount();
    }

    @Override
    public List<ItemType> getAllByLikeName(String name) {
        return itemTypeRepository.getAllByLikeName(name);
    }

    @Override
    public ItemType getByName(String name) {
        return itemTypeRepository.getByName(name);
    }

    @Override
    public ItemType getByID(String id) {
        return itemTypeRepository.getByID(id);
    }

    @Override
    public List<ItemType> getAll() {
        return itemTypeRepository.getAll();
    }

    @Override
    public Page<ItemType> getPage(int pageNo, int pageSize) {
        return itemTypeRepository.getPage(pageNo, pageSize);
    }

    @Override
    public Page<ItemType> getAllPage() {
        return itemTypeRepository.getAllPage();
    }

    @Override
    public Page<ItemType> getPageByLikeName(String name, int pageNo, int pageSize) {
        return itemTypeRepository.getPageByLikeName(name, pageNo, pageSize);
    }

}
