package guru.springframework.services;

import guru.springframework.domain.Recipe;
import guru.springframework.repo.RecipeRepo;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class RecipeServiceImplTest {

    RecipeService recipeService;

    @Mock
    RecipeRepo repo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(repo);
    }


    @Test
    public void getAllRecipes() {
        Recipe recipe = new Recipe();
        HashSet recipesData = new HashSet();
        recipesData.add(recipe);
        Mockito.when(recipeService.getAllRecipes()).thenReturn(recipesData);
        Set<Recipe> recipes = recipeService.getAllRecipes();
        assertEquals(recipes.size(), 1);
        Mockito.verify(repo, Mockito.times(1)).findAll();
    }
}