package com.asn2.asn2.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
        try {
            // Log raw parameter values for debugging
            System.out.println("Raw input values - Name: " + name + ", Width: " + width + ", Height: " + height + ", Color: " + color);

            // Use default values if parameters are missing
            String newName = (name != null && !name.isEmpty()) ? name : "defaultName";
            int newWidth = (width != null && !width.isEmpty()) ? Integer.parseInt(width) : 50;
            int newHeight = (height != null && !height.isEmpty()) ? Integer.parseInt(height) : 50;
            String newColor = (color != null && !color.isEmpty()) ? color : "#000000";

            // Log parsed values for debugging
            System.out.println("Parsed values - Name: " + newName + ", Width: " + newWidth + ", Height: " + newHeight + ", Color: " + newColor);

            Rectangle newRectangle = new Rectangle(newName, newWidth, newHeight, newColor);
            rectRepo.save(newRectangle);

            response.setStatus(201);
            return "redirect:/";
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(400);
            return "rectangles/error";
        }
    }

    @GetMapping("/rectangles/{id}")
    public String getRectangleById(@PathVariable("id") int id, Model model) {
        Optional<Rectangle> rectangle = rectRepo.findById(id);
        if (rectangle.isPresent()) {
            model.addAttribute("rectangle", rectangle.get());
            return "rectangles/viewRectangle";
        } else {
            return "rectangles/error";
        }
    }

    @GetMapping("/rectangles/delete/{id}")
    public String deleteRectangle(@PathVariable("id") int id, Model model) {
        Optional<Rectangle> rectangle = rectRepo.findById(id);
        if (rectangle.isPresent()) {
            rectRepo.delete(rectangle.get());
            model.addAttribute("message", "Rectangle deleted successfully");
            return "redirect:/";
        } else {
            model.addAttribute("message", "Rectangle not found");
            return "rectangles/error";
        }
    }

    @PostMapping("/rectangles/edit/{id}")
    public String editRectangle(
            @PathVariable("id") int id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String width,
            @RequestParam(required = false) String height,
            @RequestParam(required = false) String color,
            HttpServletResponse response) {
        try {
            Optional<Rectangle> optionalRectangle = rectRepo.findById(id);
            if (optionalRectangle.isPresent()) {
                Rectangle rectangle = optionalRectangle.get();
                rectangle.setName((name != null && !name.isEmpty()) ? name : rectangle.getName());
                rectangle.setWidth((width != null && !width.isEmpty()) ? Integer.parseInt(width) : rectangle.getWidth());
                rectangle.setHeight((height != null && !height.isEmpty()) ? Integer.parseInt(height) : rectangle.getHeight());
                rectangle.setColor((color != null && !color.isEmpty()) ? color : rectangle.getColor());

                rectRepo.save(rectangle);
                return "redirect:/";
            } else {
                return "rectangles/error";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "rectangles/error";
        }
    }
    
}
