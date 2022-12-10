package com.tass.productservice.service;

import com.tass.productservice.database.entities.Category;
import com.tass.productservice.database.entities.CategoryRelationship;
import com.tass.productservice.database.repository.CategoryExtentRepository;
import com.tass.productservice.database.repository.CategoryRelationshipRepository;
import com.tass.productservice.database.repository.CategoryRepository;
import com.tass.productservice.model.ApiException;
import com.tass.productservice.model.BaseResponse;
import com.tass.productservice.model.ERROR;
import com.tass.productservice.model.request.CategoryRequest;
import com.tass.productservice.model.response.SearchCategoryResponse;
import com.tass.productservice.utils.Constant;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import com.tass.productservice.database.repository.CategoryExtentRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2

public class CategoryService {

    //    private Logger
    @Autowired
    CategoryRepository categoryRepository;


    @Autowired
    CategoryRelationshipRepository categoryRelationshipRepository;

    @Transactional
    public SearchCategoryResponse search(Integer isRoot , String name, Integer page, Integer pageSize){
        if (page == null || page < 1){
            page = 1;
        }
        if (pageSize == null || pageSize < 1){
            pageSize = 10;
        }

        SearchCategoryResponse.Data data = new SearchCategoryResponse.Data();
        data.setCurrentPage(page);
        data.setPageSize(pageSize);

        categoryRepository.searchCategory(isRoot , name, page, pageSize, data);

        SearchCategoryResponse response = new SearchCategoryResponse();
        response.setData(data);
        return response;
    }

    public ResponseEntity<BaseResponse> findById(Long id) throws ApiException {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new BaseResponse(200, "OK", optionalCategory)
            );
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse(500, "Not found", null));
        }
    }
    public BaseResponse findAllParentAndChildById(Long id) throws ApiException {
        Optional<Category> list = categoryRepository.findById(id);
        if (list.isEmpty()) {
            throw new ApiException(ERROR.INVALID_PARAM, "category does not exist!");
        }
        String query ="select c.*,(select JSON_ARRAYAGG(JSON_OBJECT('id',d.id,'name',d.name,'icon',d.icon,'description',d.description,'is_root',d.is_root))\n" +
                "            from category d where d.id in(select cr.link_id from category_relationship cr where  cr.id=c.id)) as child,\n" +
                "       (select json_arrayagg(json_object('id',p.id,'name',p.name,'icon',p.icon,'description',p.description,'is_root',p.is_root))\n" +
                "        from category p where p.id in(select cr2.id from  category_relationship cr2 where cr2.link_id=c.id)) as parent\n" +
                "from category c where c.id = "+id+"";
        log.info(query);
        return new BaseResponse(200 , "success", categoryRepository.findAllParentAndChildByQuery(query));
    }
    public BaseResponse findAllParentAndChildByName(String name) throws ApiException {
        List<Category> list = categoryRepository.findByName(name);
        if (list.isEmpty()) {
            throw new ApiException(ERROR.INVALID_PARAM, "category does not exist!");
        }
        String query ="select c.*,(select JSON_ARRAYAGG(JSON_OBJECT('id',d.id,'name',d.name,'icon',d.icon,'description',d.description,'is_root',d.is_root))\n" +
                "            from category d where d.id in(select cr.link_id from category_relationship cr where  cr.id=c.id)) as child,\n" +
                "       (select json_arrayagg(json_object('id',p.id,'name',p.name,'icon',p.icon,'description',p.description,'is_root',p.is_root))\n" +
                "        from category p where p.id in(select cr2.id from  category_relationship cr2 where cr2.link_id=c.id)) as parent\n" +
                "from category c where c.name like '"+name+"'";
        log.info(query);
        return new BaseResponse(200 , "success", categoryRepository.findAllParentAndChildByQuery(query));
    }
    public BaseResponse findAllParentAndChildWithView(Long id) throws ApiException{
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (!optionalCategory.isPresent()) {
            throw new ApiException(ERROR.INVALID_PARAM, "category does not exist!");
        }
        String query = "select * from cp where id = " + id;
        return new BaseResponse(200, "success", categoryRepository.findCateByName(query));
    }

    public BaseResponse findAllChildrenByQuery(String name) throws ApiException {
        List<Category> list = categoryRepository.findByName(name);
        if (list.isEmpty()) {
            throw new ApiException(ERROR.INVALID_PARAM, "category does not exist!");
        }
        String query ="SELECT c.id, c.name,c.icon,c.description,c.is_root\n" +
                "FROM category_relationship cr join category c\n" +
                "                                   on cr.link_id = c.id\n" +
                "WHERE  EXISTS ( SELECT NULL\n" +
                "                from category c where cr.id = c.id and c.name like '"+name+"' )";
        log.info(query);
        return new BaseResponse(200 , "success", categoryRepository.findCateByName(query));
    }
    public BaseResponse findAllParentByQuery(String name) throws ApiException {
        List<Category> list = categoryRepository.findByName(name);
        if (list.isEmpty()) {
            throw new ApiException(ERROR.INVALID_PARAM, "category does not exist!");
        }
        String query ="SELECT c.id, c.name,c.icon,c.description,c.is_root\n" +
                "FROM category_relationship cr join category c\n" +
                "                                   on cr.id = c.id\n" +
                "WHERE  EXISTS ( SELECT NULL\n" +
                "                from category c where cr.link_id = c.id and c.name like '"+name+"' )";
        log.info(query);
        return new BaseResponse(200 , "success", categoryRepository.findCateByName(query));
    }


    @Transactional
    public BaseResponse editCategory(Long id, CategoryRequest request) throws ApiException {
        //step1: validate
        log.info("edit category with id : {}, json body {}", id, request);
        validateRequestCreateException(request);

        //step2: find category with id on database
        Optional<Category> categoryOpt = categoryRepository.findById(id);

        if (categoryOpt.isEmpty()) {
            log.debug("not found category with id {} on database", id);
            throw new ApiException(ERROR.INVALID_PARAM, "category not found");

        }
        Category category = categoryOpt.get();
        //step3: set value
        //step3.1: value type
        if (request.getIsRoot() != null && category.getIsRoot() != request.getIsRoot()) {
            log.debug("request change category type from {} to type {}", category.getIsRoot(),
                    request.getIsRoot());
            throw new ApiException(ERROR.INVALID_PARAM);
        }

        category.setDescription(request.getDescription());
        category.setIcon(request.getIcon());
        category.setName(request.getName());

        if (!category.checkIsRoot() && request.getParentId() != null) {

            //3.2 validate ralationship
            CategoryRelationship.PK pk = new CategoryRelationship.PK(request.getParentId(), category.getId());
            Optional<CategoryRelationship> categoryRelationshipOptional = categoryRelationshipRepository.findById(pk);
            if (categoryRelationshipOptional.isEmpty()) {
                CategoryRelationship categoryRelationship = new CategoryRelationship();
                categoryRelationship.setPk(pk);

                categoryRelationshipRepository.save(categoryRelationship);
            }
        }
        log.info("edit category with id {} success", id);
        return new BaseResponse(category);
    }

    @Transactional
    public BaseResponse deleteCategory(Long id) throws ApiException {
        //step1: del category
        categoryRepository.deleteById(id);
        //step2: del children
        this.deleteCategoryImpl(id);
        return new BaseResponse();
    }

    private void deleteCategoryImpl(long id) throws ApiException {
        List<CategoryRelationship> listChildren = categoryRelationshipRepository.findAllChildrenByParentId(id);

        if (CollectionUtils.isEmpty(listChildren)) {
            return;
        }

        List<CategoryRelationship> deleteRelationship = new ArrayList<>();
        for (CategoryRelationship cr : listChildren) {

            long countParent = categoryRelationshipRepository.countParent(cr.getPk().getChildrenId());

            if (countParent == 1) {
                deleteRelationship.add(cr);
                this.deleteCategoryImpl(cr.getPk().getChildrenId());
            }
        }

        if (!CollectionUtils.isEmpty(deleteRelationship)) {
            categoryRelationshipRepository.deleteAll(deleteRelationship);
        }
    }

    @Transactional
    public BaseResponse createCategory(CategoryRequest request) throws ApiException {

        //step1: validate request
        validateRequestCreateException(request);

        if (!request.checkIsRoot()) {
            // validate parent co ton tai khong

            Optional<Category> checkParentOpt = categoryRepository.findById(request.getParentId());
            if (checkParentOpt.isEmpty()) {
                throw new ApiException(ERROR.INVALID_PARAM, "parent is invalid");
            }
        }

        Category category = new Category();
        category.setDescription(request.getDescription());
        category.setIcon(request.getIcon());
        category.setName(request.getName());
        category.setIsRoot(request.checkIsRoot() ? Constant.ONOFF.ON : Constant.ONOFF.OFF);

        categoryRepository.save(category);

        // Su dung many to many key word xu li case co parent
        if (!request.checkIsRoot()) {
            //tao quan he
            CategoryRelationship categoryRelationship = new CategoryRelationship();
            CategoryRelationship.PK pk = new CategoryRelationship.PK(request.getParentId(), category.getId());
            categoryRelationship.setPk(pk);
            categoryRelationshipRepository.save(categoryRelationship);
        }
        return new BaseResponse(category);
    }

    private void validateRequestCreateException(CategoryRequest request) throws ApiException {
        if (StringUtils.isBlank(request.getIcon())) {
            log.debug("icon is blank");
            throw new ApiException(ERROR.INVALID_PARAM, "icon is blank");
        }
        if (StringUtils.isBlank(request.getName())) {
            throw new ApiException(ERROR.INVALID_PARAM, "name is blank");
        }
        if (StringUtils.isBlank(request.getDescription())) {
            throw new ApiException(ERROR.INVALID_PARAM, "description is blank");
        }
        if (request.checkIsRoot() && request.getParentId() != null) {
            throw new ApiException(ERROR.INVALID_PARAM, "level is invalid");
        }

        if (!request.checkIsRoot() && request.getParentId() == null) {
            throw new ApiException(ERROR.INVALID_PARAM, "parent is blank");
        }

    }
}
