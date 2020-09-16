package com.haobaoshui.course.service.database.impl;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.database.ItemDifficulty;
import com.haobaoshui.course.repository.database.ItemDifficultyRepository;
import com.haobaoshui.course.service.database.ItemDifficultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ItemDifficultyServiceImpl implements ItemDifficultyService {


    private final ItemDifficultyRepository itemDifficultyRepository;

    @Autowired
    public ItemDifficultyServiceImpl(ItemDifficultyRepository itemDifficultyRepository) {
        this.itemDifficultyRepository = itemDifficultyRepository;
    }

    @Override
    public String add(ItemDifficulty itemDifficulty) {
        return itemDifficultyRepository.add(itemDifficulty);
    }

    @Override
    public String add(String name, String note) {
        return itemDifficultyRepository.add(name, note);
    }

    @Override
    public int deleteById(String id) {
        return itemDifficultyRepository.deleteById(id);
    }

    @Override
    public int delete(ItemDifficulty itemDifficulty) {
        if (itemDifficulty == null) return 0;
        return deleteById(itemDifficulty.getId());
    }

    @Override
    public int deleteAll() {
        return itemDifficultyRepository.deleteAll();
    }

    @Override
    public int update(String id, String name, String note) {
        return itemDifficultyRepository.update(id, name, note);
    }

    @Override
    public int update(ItemDifficulty itemDifficulty) {
        return itemDifficultyRepository.update(itemDifficulty);
    }

    @Override
    public int getCount() {
        return itemDifficultyRepository.getCount();
    }

    @Override
    public List<ItemDifficulty> getAllByLikeName(String name) {
        return itemDifficultyRepository.getAllByLikeName(name);
    }

    @Override
    public ItemDifficulty getByName(String name) {
        return itemDifficultyRepository.getByName(name);
    }

    @Override
    public ItemDifficulty getByID(String id) {
        return itemDifficultyRepository.getByID(id);
    }

    @Override
    public List<ItemDifficulty> getAll() {
        return itemDifficultyRepository.getAll();
    }

    @Override
    public Page<ItemDifficulty> getPage(int pageNo, int pageSize) {
        return itemDifficultyRepository.getPage(pageNo, pageSize);
    }

    @Override
    public Page<ItemDifficulty> getAllPage() {
        return itemDifficultyRepository.getAllPage();
    }

    @Override
    public Page<ItemDifficulty> getPageByLikeName(String name, int pageNo, int pageSize) {
        return itemDifficultyRepository.getPageByLikeName(name, pageNo, pageSize);
    }


}
