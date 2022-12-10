package com.tass.productservice.database.repository;

import com.tass.productservice.database.entities.Category;

import com.tass.productservice.model.response.SearchCategoryResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryExtentRepository {
    @Query(value = "select c.id ,c.name, c.icon, c.description, c.is_root\n" + "\n" + "from category c where c.name like '%child of C2%';", nativeQuery = true)
    void searchCategory(Integer isRoot, String name, Integer page, Integer pageSize, SearchCategoryResponse.Data data);

    List<Category> findByName(String name);
    @Query(value = "select * from category c, category_relationship cr where c.id = cr.parent_id and ",nativeQuery = true)
    List<Category> findAllChildren(Long id);

    @Query(value = "select * from category c, category_relationship cr where c.id = cr.category_id",nativeQuery = true)
    List<Category> findAllParent(Long id);


}
