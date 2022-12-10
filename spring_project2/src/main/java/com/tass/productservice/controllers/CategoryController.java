package com.tass.productservice.controllers;

import com.tass.productservice.model.ApiException;
import com.tass.productservice.model.BaseResponse;
import com.tass.productservice.model.request.CategoryRequest;

import com.tass.productservice.model.response.SearchCategoryResponse;
import com.tass.productservice.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController extends BaseController {

    @Autowired
    CategoryService categoryService;

    @GetMapping
    public SearchCategoryResponse search(@RequestParam(name = "is_root", required = false) Integer isRoot,
                                         @RequestParam(required = false) String name,
                                         @RequestParam(required = false) Integer page,
                                         @RequestParam(name = "page_size", required = false) Integer pageSize) {
        return categoryService.search(isRoot, name, page, pageSize);
    }
    @GetMapping("/{id}")
    ResponseEntity<BaseResponse> findById(@PathVariable Long id) {
        return categoryService.findById(id);
    }

    @GetMapping(path = "/children")
    public ResponseEntity<BaseResponse> searchAllChild(@RequestParam(name = "name", required = false) String name) {
        return createdResponse(categoryService.findAllChildrenByQuery(name), HttpStatus.OK);
    }

    @GetMapping(path = "/all/{id}")
    public ResponseEntity<BaseResponse> searchAllParentAndChild(@PathVariable Long id) {
        return createdResponse(categoryService.findAllParentAndChildById(id), HttpStatus.OK);
    }

    @GetMapping(path = "/name")
    public ResponseEntity<BaseResponse> searchAllParentAndChild(@RequestParam(name = "name", required = false) String name) {
        return createdResponse(categoryService.findAllParentAndChildByName(name), HttpStatus.OK);
    }

    @GetMapping(path = "/parent")
    public ResponseEntity<BaseResponse> searchAllParent(@RequestParam(name = "name", required = false) String name) {
        return createdResponse(categoryService.findAllParentByQuery(name), HttpStatus.OK);
    }

    @GetMapping(path = "/view/{id}")
    public ResponseEntity<BaseResponse> findALlWithView(@PathVariable Long id) {
        return createdResponse(categoryService.findAllParentAndChildWithView(id));
    }

    @PostMapping
    public ResponseEntity<BaseResponse> create(@RequestBody CategoryRequest request) throws ApiException {
        return createdResponse(categoryService.createCategory(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> delete(@PathVariable Long id) throws
            ApiException {
        return createdResponse(categoryService.deleteCategory(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> edit(@PathVariable Long id,
                                             @RequestBody CategoryRequest request) throws
            ApiException{
        return createdResponse(categoryService.editCategory(id, request));
    }
}
