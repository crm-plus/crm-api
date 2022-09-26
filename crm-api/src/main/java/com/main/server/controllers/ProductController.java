package com.main.server.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@CrossOrigin
@RequestMapping(path = {"/api/organizations"})
public class ProductController {

    @PostMapping("/{id}/product")
    public void saveProduct(@PathVariable @NotNull Long id) {

    }
}
