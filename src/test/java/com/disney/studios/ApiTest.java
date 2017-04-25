package com.disney.studios;

import com.jayway.jsonpath.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Arrays;
import java.util.List;

import com.disney.studios.controller.PetService;
import com.disney.studios.dao.PictureDao;
import com.disney.studios.model.*;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ApiTest {

    private MockMvc mockMvc;
    
    @Mock
    private PetService petService;
	
    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(petService)
                .build();
    }
    // =========================================== GET Pictures ==========================================

    @Test
    public void findAll_test() throws Exception {
        
		List<Picture> users = Arrays.asList(
                new  Picture(1, 1, "myurl", "mybreed1", "myname"),
				new	 Picture(2, 2, "myurl", "mybreed", "myname"),
				new	 Picture(3, 3, "myurl", "mybreed", "myname"));

        when(petService.readPictures()).thenReturn(users);

        mockMvc.perform(get("/dogpictures"))
                .andExpect(status().isOk()) 
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].breed", is("mybreed1")))
                .andExpect(jsonPath("$[1].url", is("myurl")))
                .andExpect(jsonPath("$[2].name", is("myname")));

        verify(petService, times(1)).readPictures();
        verifyNoMoreInteractions(petService);
    }
	
    /* Utility Function
     * converts a Java object into JSON representation
     */
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}