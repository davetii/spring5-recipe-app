package guru.springframework.controllers;

import guru.springframework.domain.Difficulty;
import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;
import net.bytebuddy.asm.Advice;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class IndexControllersTest {

    private IndexControllers controller;

    @Mock
    private RecipeService service;

    @Mock
    private Model model;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        controller = new IndexControllers(service);
    }

    @Test
    public void getIndexPage() {
        Recipe rec1 = new Recipe();
        rec1.setDifficulty(Difficulty.EASY);
        Recipe rec2 = new Recipe();
        rec2.setDifficulty(Difficulty.MODERATE);
        rec2.setId(new Long("123"));
        Set<Recipe> recipes = new HashSet<>();
        recipes.add(rec1);
        recipes.add(rec2);
        Mockito.when(service.getAllRecipes()).thenReturn(recipes);
        ArgumentCaptor<Set<Recipe>> captor = ArgumentCaptor.forClass(Set.class);


        assertEquals("index", controller.getIndexPage(model));
        Mockito.verify(service, Mockito.times(1)).getAllRecipes();
        Mockito.verify(model, Mockito.times(1)).addAttribute(Mockito.eq("recipes"),
                captor.capture());
        Set<Recipe> captorResult = captor.getValue();
        assertEquals(2, captorResult.size());
    }

    @Test
    public void testMockMVC() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"));
    }
}