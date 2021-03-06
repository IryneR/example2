package example2.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import example2.dto.IssueDto;
import example2.services.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class ThymeleafController {
    @Autowired
    IssueService issueService;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    //@Autowired
    private ObjectMapper mapper = new ObjectMapper();

    @GetMapping("/thymeleaf")
    public String getIframe() {
        return "thymeleaf";
    }

    @RequestMapping(value = "/webhook-received", method = RequestMethod.POST)
    @ResponseBody
    public String onWebhookReceived(@RequestBody Map<String, Object> request) throws JsonProcessingException {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        IssueDto issueDto = mapper.readValue(GSON.toJson(request.get("issue")), IssueDto.class);
        issueService.createIssue(issueDto);
        return "{\"result\": \"ok\"}";
    }
}
