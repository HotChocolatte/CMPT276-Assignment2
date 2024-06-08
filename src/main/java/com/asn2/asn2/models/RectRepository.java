package com.asn2.asn2.models;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RectRepository extends JpaRepository<Rectangle,Integer>{
    List<Rectangle> findByHeightAndWidth(int height, int width);
    List<Rectangle> findByName(String name);
}
