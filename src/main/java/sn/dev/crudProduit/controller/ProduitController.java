package sn.dev.crudProduit.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sn.dev.crudProduit.Entities.Produit;
import sn.dev.crudProduit.service.ProduitService;

import java.util.List;
import java.util.Optional;

@Controller
public class ProduitController {

    private final ProduitService produitService;

    @Autowired
    public ProduitController(ProduitService produitService) {
        this.produitService = produitService;
    }

    @GetMapping("/produit")
    public String produit(Model model, @RequestParam(name = "query", defaultValue = "", required = false) String query) {
        List<Produit> produits = query.isEmpty() ? produitService.findAll() : produitService.findAllByDesignationContaining(query);
        model.addAttribute("listeproduits", produits);
        model.addAttribute("produit", new Produit());
        model.addAttribute("query", query);
        return "produit";
    }

    @GetMapping("/delete")
    public String deleteProduit(@RequestParam("id") Long id) {
        produitService.delete(id);
        return "redirect:/produit";
    }

    @PostMapping("/save")
    public String save(@Valid Produit produit, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/produit";
        }
        produitService.create(produit);
        return "redirect:/produit";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam("id") Long id, Model model) {
        Optional<Produit> produitOpt = produitService.findById(id);
        if (produitOpt.isPresent()) {
            model.addAttribute("produit", produitOpt.get());
            model.addAttribute("listeproduits", produitService.findAll());
            return "produit";
        } else {
            return "redirect:/produit"; // Redirige si le produit n'est pas trouvé
        }
    }
}
