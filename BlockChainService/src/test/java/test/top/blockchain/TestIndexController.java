package test.top.blockchain;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import top.blockchain.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class TestIndexController {
  @Autowired
  private MockMvc mvc;
  // private ObjectMapper mapper = new ObjectMapper();

  @Test
  public void testIndex() throws Exception {
    // .andExpect(content().string(equalTo(mapper.writeValueAsString(JsonMessage.getSuccessMessage("欢迎使用区块链服务!")))))
    mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
  }
}
