package com.asn2.asn2.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.asn2.asn2.models.RectRepository;
import com.asn2.asn2.models.Rectangle;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class RectController {

    @Autowired
    private RectRepository rectRepo;

    @GetMapping("")
    public String getAllRect(Model model) {
        System.out.println("Getting all rectangles...");
        // Retrieve all rectangles from database.
        List<Rectangle> rectangles = rectRepo.findAll();
        // end of database call.
        model.addAttribute("rec", rectangles);
        return "rectangles/showAll";
    }

    @PostMapping("/rectangles/add")
    public String addRectangle(@RequestParam Map<String, String> newrect, HttpServletResponse response){
        System.out.println("ADD rectangle");
        String newName = newrect.get("name");
        int newWidth = Integer.parseInt(newrect.get("width"));
        int newHeight = Integer.parseInt(newrect.get("height"));
        String newColor = newrect.get("color");
        rectRepo.save(new Rectangle(newName,newWidth,newHeight,newColor));
        response.setStatus(201);
        return "rectangles/addedRectangle";
    }
    
}
