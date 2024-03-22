package com.example.delivery.repository;

import com.example.delivery.domain.Menu;
import com.example.delivery.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Integer> {

    Optional<Store> findByUserId(Long id);
    List<Store> findByCategory(String category);

//    @Query("SELECT s FROM Store s WHERE EXISTS (SELECT 1 FROM Menu m WHERE m.name LIKE %:keyword%)")
    @Query("SELECT s FROM Store s JOIN s.menus m WHERE m.name LIKE %:keyword%")
    List<Store> findByMenuNameContainingKeyword(@Param("keyword") String keyword);
}
