package com.WebXChat.WebXChat.Controller;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.parser.JSONParser;



@Controller
public class MasterController {
    @GetMapping("/webx/")
    public String getMainPage()
    {
        return "ChatgptClon";
    }
    @GetMapping("/webx/chat/")
    public String getChatPage()
    {
        return "chat";
    }

    @ResponseBody
    @PostMapping("/webx/chat/search/")
    public String[] handlePostRequestOfSeatCount(Model model, @RequestBody String data) {
        String arr[] = data.split("=");
        String[] answer = new String[1];
        String  query = arr[0];
        System.out.println(query);
        try {
            String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8.toString());
            String apiKey = "AIzaSyCxVR1yU1lOpBMCLVEmgYGk-sK5Q04eYnE"; // Replace with your actual API key
            String cx = "a16405c38ad184444"; // Replace with your actual Custom Search Engine ID

            String url = "https://www.googleapis.com/customsearch/v1?q=" + encodedQuery + "&key=" + apiKey + "&cx=" + cx;
            System.out.println(url);
            HttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(url);

            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity);

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(responseString);

            JSONArray items = (JSONArray) jsonObject.get("items");
            if (items != null) {
                for (Object item : items) {
                    if (item instanceof JSONObject) {
                        JSONObject result = (JSONObject) item;
//                        System.out.println("result:"+result);
                        String snippet = (String) result.get("snippet");
//                        String title = (String) result.get("title");
//                        String link = (String) result.get("link");

                        System.out.println("Answer: " + snippet);

//                        System.out.println("Title: " + title);
//                        System.out.println("Link: " + link);
                        answer[0] = snippet;
                        System.out.println();
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            answer[0]="Sorry for this issue i request you to Please check your internet connection and try again if still you got same thing then refresh the page again... ";
        }

        return answer;
    }

}
