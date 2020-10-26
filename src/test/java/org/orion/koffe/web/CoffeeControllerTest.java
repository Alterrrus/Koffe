package org.orion.koffe.web;

import static org.junit.jupiter.api.Assertions.*;
import static org.orion.koffe.RestTestData.TEST_MATCHER;
import static org.orion.koffe.RestTestData.TEST_MATCHER1;
import static org.orion.koffe.RestTestData.coffe4;
import static org.orion.koffe.RestTestData.coffeeList;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Locale;
import javax.annotation.PostConstruct;
import org.junit.jupiter.api.Test;
import org.orion.koffe.TestUtil;
import org.orion.koffe.model.Coffee;
import org.orion.koffe.model.CoffeeType;
import org.orion.koffe.service.CoffeeService;
import org.orion.koffe.util.exception.ErrorType;
import org.orion.koffe.util.exception.NotFoundException;
import org.orion.koffe.web.json.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@SpringJUnitWebConfig(locations = {
    "classpath:spring/spring-app.xml",
    "classpath:spring/spring-mvc.xml",
    "classpath:spring/spring-db.xml"
})
@Transactional
class CoffeeControllerTest {

  private static final Locale RU_LOCALE = new Locale("ru");
  private static final CharacterEncodingFilter CHARACTER_ENCODING_FILTER = new CharacterEncodingFilter();


  @Autowired
  protected MessageSourceAccessor messageSourceAccessor;

  static {
    CHARACTER_ENCODING_FILTER.setEncoding("UTF-8");
    CHARACTER_ENCODING_FILTER.setForceEncoding(true);
  }

  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @PostConstruct
  private void postConstruct() {
    mockMvc = MockMvcBuilders
        .webAppContextSetup(webApplicationContext)
        .addFilter(CHARACTER_ENCODING_FILTER)
        .build();
  }

  public ResultActions perform(MockHttpServletRequestBuilder builder) throws Exception {
    return mockMvc.perform(builder);
  }

  private String getMessage(String code) {
    return messageSourceAccessor.getMessage(code, RU_LOCALE);
  }

  public ResultMatcher errorType(ErrorType type) {
    return jsonPath("$.type").value(type.name());
  }

  public ResultMatcher detailMessage(String code) {
    return jsonPath("$.details").value(getMessage(code));
  }

  @Autowired
  private CoffeeService service;

  @Test
  void getListCoffee() throws Exception {
    perform(MockMvcRequestBuilders.get("/"))
        .andExpect(status().isOk())
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(TEST_MATCHER.contentJson(coffeeList));
  }

  @Test
  void makeCoffee() throws Exception {
    Coffee newCoffee = new Coffee(null, CoffeeType.AMERICANO, LocalDateTime
        .of(2020, 03, 30, 10, 00, 00), 1);
    ResultActions action = perform(MockMvcRequestBuilders.post("/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(JsonUtil.writeValue(newCoffee)));
    Coffee created = TestUtil.readFromJson(action, Coffee.class);
    int newId = created.id();
    newCoffee.setId(newId);
    TEST_MATCHER1.assertMatch(created, newCoffee);
    TEST_MATCHER1.assertMatch(service.get(newId), newCoffee);
  }

  @Test
  void getCoffee() throws Exception {
    perform(MockMvcRequestBuilders.get("/coffee/10003"))
        .andExpect(status().isOk())
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(TEST_MATCHER1.contentJson(coffe4));
  }
  /**only for restfull crud**

   */

/*  @Test
  void drinkCoffee() throws Exception{
    perform(MockMvcRequestBuilders.delete("/coffee/10002"))
        .andExpect(status().isNoContent());
    assertThrows(NotFoundException.class, () -> service.get(10002));
  }*/


 /* @Test
  void deleteNotFound() throws Exception {
    perform(MockMvcRequestBuilders.delete("/coffee/10005"))
        .andExpect(status().isUnprocessableEntity());
  }*/
}