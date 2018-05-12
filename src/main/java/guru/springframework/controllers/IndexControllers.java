package guru.springframework.controllers;

import guru.springframework.domain.Category;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repo.CategoryRepo;
import guru.springframework.repo.UnitOfMeasureRepo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class IndexControllers {
    private CategoryRepo categoryRepo;
    private UnitOfMeasureRepo unitOfMeasureRepo;

    public IndexControllers(CategoryRepo categoryRepo, UnitOfMeasureRepo unitOfMeasureRepo) {
        this.categoryRepo = categoryRepo;
        this.unitOfMeasureRepo = unitOfMeasureRepo;
    }

    @RequestMapping({"", "/", "/index"})
    public String getIndexPage() {
        Optional<Category> cat = categoryRepo.findByDescription("American");
        Optional<UnitOfMeasure> uom = unitOfMeasureRepo.findByDescription("Teaspoon");
        System.out.println("Cat id: " + cat.get().getId());
        System.out.println("uom id: " + uom.get().getId());
        return "index";
    }
}
