package com.mobiquity.implementation;
import com.mobiquity.implementation.exception.ApiException;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class HttpRequestTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/pack?filePath=src/test/resources/packer/example_input")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("4\n" +
                        "-\n" +
                        "2,7\n" +
                        "8,9")));
    }

    @Test
    public void greetingShouldThrowException() throws Exception {
        assertThrows(NestedServletException.class,() ->
                this.mockMvc.perform(get("/pack?filePath=/src/test/resources/packer/example_input")) );
    }
}
