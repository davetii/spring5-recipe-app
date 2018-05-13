package guru.springframework.services;

import guru.springframework.domain.Recipe;
import guru.springframework.repo.RecipeRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;


@Slf4j
@Component
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepo recipeRepo;

    public RecipeServiceImpl(RecipeRepo recipeRepo) {
        this.recipeRepo = recipeRepo;
    }

    @Override
    public Set<Recipe> getAllRecipes() {
        log.debug("call getAllRecipes");
        Set<Recipe> recipes = new HashSet<>();
        recipeRepo.findAll().iterator().forEachRemaining(recipes::add);
        return recipes;
    }
}
