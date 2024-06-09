package com.asn2.asn2.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public String addRectangle(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String width,
            @RequestParam(required = false) String height,
            @RequestParam(required = false) String color,
            HttpServletResponse response) {

        // Sets all attributes. Uses default values if parameters are missing.
        String newName = (name != null && !name.isEmpty()) ? name : "defaultName";
        int newWidth = (width != null && !width.isEmpty()) ? Integer.parseInt(width) : 50;
        int newHeight = (height != null && !height.isEmpty()) ? Integer.parseInt(height) : 50;
        String newColor = (color != null && !color.isEmpty()) ? color : "#000000";

        Rectangle newRectangle = new Rectangle(newName, newWidth, newHeight, newColor);
        rectRepo.save(newRectangle);

        response.setStatus(201);
        return "redirect:/";
    }

    @GetMapping("/rectangles/{id}")
    public String getRectangleById(@PathVariable("id") int id, Model model) {

        // Finds rectangle by id in order to display it.
        Optional<Rectangle> rectangle = rectRepo.findById(id);
        model.addAttribute("rectangle", rectangle.get());
        return "rectangles/viewRectangle";
    }

    @GetMapping("/rectangles/delete/{id}")
    public String deleteRectangle(@PathVariable("id") int id, Model model) {

        // Removes rectangle from database.
        Optional<Rectangle> rectangle = rectRepo.findById(id);
        rectRepo.delete(rectangle.get());
        model.addAttribute("message", "Rectangle deleted successfully");
        return "redirect:/";
    }

    @PostMapping("/rectangles/edit/{id}")
    public String editRectangle(
            @PathVariable("id") int id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String width,
            @RequestParam(required = false) String height,
            @RequestParam(required = false) String color,
            HttpServletResponse response) {

        
        Optional<Rectangle> optionalRectangle = rectRepo.findById(id);
        Rectangle rectangle = optionalRectangle.get();

        // Sets all attributes. Uses ORIGINAL values if parameters are missing.
        rectangle.setName((name != null && !name.isEmpty()) ? name : rectangle.getName());
        rectangle.setWidth((width != null && !width.isEmpty()) ? Integer.parseInt(width) : rectangle.getWidth());
        rectangle.setHeight((height != null && !height.isEmpty()) ? Integer.parseInt(height) : rectangle.getHeight());
        rectangle.setColor((color != null && !color.isEmpty()) ? color : rectangle.getColor());

        rectRepo.save(rectangle);
        return "redirect:/";
    }
    
}
