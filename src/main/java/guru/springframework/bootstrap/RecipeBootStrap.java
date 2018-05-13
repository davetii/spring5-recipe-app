package guru.springframework.bootstrap;

import guru.springframework.domain.*;
import guru.springframework.repo.CategoryRepo;
import guru.springframework.repo.RecipeRepo;
import guru.springframework.repo.UnitOfMeasureRepo;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class RecipeBootStrap implements ApplicationListener<ContextRefreshedEvent> {

    private CategoryRepo categoryRepo;
    private RecipeRepo recipeRepo;
    private UnitOfMeasureRepo unitOfMeasureRepo;

    public RecipeBootStrap(CategoryRepo categoryRepo, RecipeRepo recipeRepo, UnitOfMeasureRepo unitOfMeasureRepo) {
        this.categoryRepo = categoryRepo;
        this.recipeRepo = recipeRepo;
        this.unitOfMeasureRepo = unitOfMeasureRepo;
    }

    private List<Recipe> getRecipes() {
        List<Recipe> recipes = new ArrayList<>();

        Optional<UnitOfMeasure> eachUomOpt = unitOfMeasureRepo.findByDescription("each");
        if(!eachUomOpt.isPresent()) { throw new RuntimeException("Expected UoM Not found"); }
        Optional<UnitOfMeasure> tspUomOpt = unitOfMeasureRepo.findByDescription("teaspoon");
        if(!tspUomOpt.isPresent()) { throw new RuntimeException("Expected UoM Not found"); }
        Optional<UnitOfMeasure> dashUomOpt = unitOfMeasureRepo.findByDescription("dash");
        if(!dashUomOpt.isPresent()) { throw new RuntimeException("Expected UoM Not found"); }
        Optional<UnitOfMeasure> pinchUomOpt = unitOfMeasureRepo.findByDescription("pinch");
        if(!pinchUomOpt.isPresent()) { throw new RuntimeException("Expected UoM Not found"); }
        Optional<UnitOfMeasure> tblUomOpt = unitOfMeasureRepo.findByDescription("tablespoon");
        if(!tblUomOpt.isPresent()) { throw new RuntimeException("Expected UoM Not found"); }
        Optional<UnitOfMeasure> cupOumOpt = unitOfMeasureRepo.findByDescription("cup");
        if(!cupOumOpt.isPresent()) { throw new RuntimeException("Expected UoM Not found"); }

        UnitOfMeasure eachUom = eachUomOpt.get();
        UnitOfMeasure tspUom = tspUomOpt.get();
        UnitOfMeasure dashUom = dashUomOpt.get();
        UnitOfMeasure pinchUom = pinchUomOpt.get();
        UnitOfMeasure tblUom = tblUomOpt.get();
        UnitOfMeasure cupOum = cupOumOpt.get();

        Optional<Category> amerCatOpt = categoryRepo.findByDescription("American");
        Optional<Category> mexCatOpt = categoryRepo.findByDescription("Mexican");

        if(!amerCatOpt.isPresent()) { throw new RuntimeException("Expected Category Not found"); }
        if(!mexCatOpt.isPresent()) { throw new RuntimeException("Expected Category Not found"); }

        Category amerCat = amerCatOpt.get();
        Category mexCat = mexCatOpt.get();

        Recipe guacRecipe = new Recipe();
        guacRecipe.setDescription("Perfect Guacomole");
        guacRecipe.setPrepTime(10);
        guacRecipe.setCookTime(0);
        guacRecipe.setDifficulty(Difficulty.EASY);
        guacRecipe.setDirections("This is a String for directions to make perfect guac");

        Notes guacNotes = new Notes();
        guacNotes.setRecipeNotes("I am the notes for the guac recipe");
        guacRecipe.setNotes(guacNotes);

        guacRecipe.getIngredients().add(new Ingredient("ripe avacado", new BigDecimal(2), eachUom));
        guacRecipe.getIngredients().add(new Ingredient("kosher salr", new BigDecimal(".5"), tspUom));
        guacRecipe.getIngredients().add(new Ingredient("lme juice", new BigDecimal(2), tblUom));
        guacRecipe.getIngredients().add(new Ingredient("chilies", new BigDecimal(2), eachUom));
        guacRecipe.getIngredients().add(new Ingredient("Cilantro", new BigDecimal(2), dashUom));

        guacRecipe.getCategories().add(amerCat);
        guacRecipe.getCategories().add(mexCat);

        recipes.add(guacRecipe);
        return recipes;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        recipeRepo.saveAll(getRecipes());
    }
}

