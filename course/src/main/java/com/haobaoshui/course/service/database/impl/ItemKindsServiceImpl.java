package com.haobaoshui.course.service.database.impl;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.database.ItemKinds;
import com.haobaoshui.course.repository.database.ItemKindsRepository;
import com.haobaoshui.course.service.database.ItemKindsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ItemKindsServiceImpl implements ItemKindsService {

    private final ItemKindsRepository itemKindsRepository;


    @Autowired
    public ItemKindsServiceImpl(ItemKindsRepository itemKindsRepository) {
        this.itemKindsRepository = itemKindsRepository;
    }

    @Override
    public String add(ItemKinds itemKinds) {
        return itemKindsRepository.add(itemKinds);
    }

    @Override
    public String add(String name, String note) {
        return itemKindsRepository.add(name, note);
    }

    @Override
    public int deleteById(String id) {
        return itemKindsRepository.deleteById(id);
    }

    @Override
    public int delete(ItemKinds itemKinds) {
        if (itemKinds == null) return 0;
        return deleteById(itemKinds.getId());
    }

    @Override
    public int deleteAll() {
        return itemKindsRepository.deleteAll();
    }

    @Override
    public int update(String id, String name, String note) {
        return itemKindsRepository.update(id, name, note);
    }

    @Override
    public int update(ItemKinds itemKinds) {
        return itemKindsRepository.update(itemKinds);
    }

    @Override
    public int getCount() {
        return itemKindsRepository.getCount();
    }

    @Override
    public List<ItemKinds> getAllByLikeName(String name) {
        return itemKindsRepository.getAllByLikeName(name);
    }

    @Override
    public ItemKinds getByName(String name) {
        return itemKindsRepository.getByName(name);
    }

    @Override
    public ItemKinds getByID(String id) {
        return itemKindsRepository.getByID(id);
    }

    @Override
    public List<ItemKinds> getAll() {
        return itemKindsRepository.getAll();
    }

    @Override
    public Page<ItemKinds> getPage(int pageNo, int pageSize) {
        return itemKindsRepository.getPage(pageNo, pageSize);
    }

    @Override
    public Page<ItemKinds> getAllPage() {
        return itemKindsRepository.getAllPage();
    }

    @Override
    public Page<ItemKinds> getPageByLikeName(String name, int pageNo, int pageSize) {
        return itemKindsRepository.getPageByLikeName(name, pageNo, pageSize);
    }


}
