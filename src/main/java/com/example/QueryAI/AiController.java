package com.example.QueryAI;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AiController {

    @Value("${groq.api.key}")
    private String apiKey;

    private static final String GROQ_API_URL = "https://api.groq.com/openai/v1/chat/completions";

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/ask")
    public String askAi(@RequestParam String question, Model model) {
        try {
            String apiResponse = callGroqAPI(question);
            String markdownAnswer = parseGroqResponse(apiResponse);

            // NEW: Convert Markdown to HTML before sending to the view
            String htmlAnswer = convertMarkdownToHtml(markdownAnswer);

            model.addAttribute("question", question);
            model.addAttribute("answer", htmlAnswer);

        } catch (Exception e) {
            model.addAttribute("question", question);
            model.addAttribute("answer", "Error: " + e.getMessage());
        }

        return "index";
    }

    // --- NEW HELPER METHOD ---
    private String convertMarkdownToHtml(String markdown) {
        // 1. Create a parser
        Parser parser = Parser.builder().build();
        // 2. Parse the markdown text into a document node
        Node document = parser.parse(markdown);
        // 3. Create a renderer
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        // 4. Render the document to HTML
        return renderer.render(document);
    }

    // --- EXISTING HELPER METHODS ---

    private String callGroqAPI(String question) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        JSONObject message = new JSONObject();
        message.put("role", "user");
        message.put("content", question);
        JSONArray messagesArray = new JSONArray();
        messagesArray.put(message);
        JSONObject payload = new JSONObject();
        payload.put("model", "llama-3.1-8b-instant");
        payload.put("messages", messagesArray);
        String jsonPayload = payload.toString();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(GROQ_API_URL))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) return "Error from Groq: " + response.body();
        return response.body();
    }

    private String parseGroqResponse(String responseBody) {
        try {
            if (responseBody.startsWith("Error from Groq:")) return responseBody;
            JSONObject jsonResponse = new JSONObject(responseBody);
            return jsonResponse.getJSONArray("choices")
                               .getJSONObject(0)
                               .getJSONObject("message")
                               .getString("content");
        } catch (Exception e) {
            return "Error parsing response: " + e.getMessage();
        }
    }
}