package org.gk.controllers;

import org.gk.business.data.model.Entry;
import org.gk.business.data.repositories.interfaces.EntryRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;


public class HomeControllerTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void homeModel() throws Exception {
        List<Entry> news = Stream.iterate(0L, i-> i)
                .limit(7)
                .map(i -> new Entry(i, "Title","News" + i, new Date()))
                .collect(Collectors.toList());
        EntryRepository newsRepository = mock(EntryRepository.class);
        Specification<Entry> spec = any();
        when(newsRepository.findAll(spec)).thenReturn(news);

        HomeController homeController = new HomeController(newsRepository);
        MockMvc mockMvc = standaloneSetup(homeController)
                .build();

        /*mockMvc.perform(get("/"))
                .andExpect(view().name("home/home"))
                .andExpect(model().attributeExists("entryList"))
                .andExpect(model().attribute("entryList", hasItems(news.toArray())));*/
    }
}