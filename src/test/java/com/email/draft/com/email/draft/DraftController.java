package com.email.draft.com.email.draft;

import static org.assertj.core.api.Assertions.assertThat;
import com.email.draft.service.DraftService;
import com.email.draft.vo.DraftVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DraftController {

	private MockMvc restMvc;
	@Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;
    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;
    @Autowired
    private DraftService service;

    ObjectMapper objMapper = new ObjectMapper();
    
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        DraftController resource = new DraftController();
        
        this.restMvc = MockMvcBuilders
            .standaloneSetup(resource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }
    
    /*@Test
    @Transactional
    public void testEmail() throws Exception {
        MockHttpServletResponse response = restMvc.perform(
            get("/email/draft/1")
	            .contentType("application/json"))
            .andExpect(status().isOk())
            .andReturn().getResponse();
        DraftVO draft = objMapper.readValue(response.getContentAsString(), DraftVO.class);
        assertThat(draft).isNotNull();
    }*/
}