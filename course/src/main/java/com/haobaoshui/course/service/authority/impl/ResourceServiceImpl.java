package com.haobaoshui.course.service.authority.impl;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.authority.Resource;
import com.haobaoshui.course.repository.authority.ResourceRepository;
import com.haobaoshui.course.service.authority.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ResourceServiceImpl implements ResourceService {


    private final ResourceRepository resourceRepository;

    @Autowired
    public ResourceServiceImpl(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }


    @Override
    public String add(Resource t) {

        return resourceRepository.add(t);
    }

    /*
     * 添加
     */
    @Override
    public String add(String uri, String uri_element) {
        return resourceRepository.add(uri, uri_element);
    }

    /*
     * 删除
     */
    @Override
    public int delete(String id) {
        return resourceRepository.delete(id);
    }

    @Override
    public int delete(Resource resource) {
        if (resource == null) return 0;
        return delete(resource.getId());
    }

    /*
     * 更新
     */
    @Override
    public int update(String id, String uri, String uri_element) {
        return resourceRepository.update(id, uri, uri_element);
    }

    @Override
    public int update(Resource resource) {
        if (resource == null || resource.getId() == null || resource.getUri() == null || resource.getUriElement() == null)
            return 0;
        return resourceRepository.update(resource.getId(), resource.getUri(), resource.getUriElement());
    }

    /*
     * 查找
     */
    @Override
    public List<Resource> select(String uri) {

        return resourceRepository.select(uri);
    }


    @Override
    public Resource getByURI(String uri) {

        return resourceRepository.getByURI(uri);
    }


    @Override
    public Resource getByID(String id) {

        return resourceRepository.getByID(id);
    }

    /*
     * 返回所有结果
     */
    @Override
    public List<Resource> getAll() {

        return resourceRepository.getAll();
    }


    @Override
    public Page<Resource> getPage(int pageNo, int pageSize) {
        return resourceRepository.getPage(pageNo, pageSize);

    }

    @Override
    public Page<Resource> getAllPage() {
        return resourceRepository.getAllPage();

    }

    @Override
    public Page<Resource> select(String name, int pageNo, int pageSize) {
        return resourceRepository.select(name, pageNo, pageSize);
    }

}
