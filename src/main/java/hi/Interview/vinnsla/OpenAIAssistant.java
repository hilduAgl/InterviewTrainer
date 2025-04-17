package hi.Interview.vinnsla;
// OpenAIAssistant.java
import okhttp3.*;
import org.json.JSONObject;
import java.io.IOException;
import java.util.List;

public class OpenAIAssistant {
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private static final String API_KEY = "SETTU_LYKILINN_HÉR";


    public static String getAIResponse(String spurning, String userAnswer, String job, String company) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String safeUserAnswer = userAnswer.replace("\"", "\\\"");

        String json = "{ \"model\": \"gpt-3.5-turbo\", " +
                "\"messages\": [ " +
                "{\"role\": \"system\", \"content\": \"You are an AI that gives constructive and friendly feedback on job interview answers. Your answers are short and to the point. You answers in complete sentences and your answers end on a period.\"}," +
                "{\"role\": \"user\", \"content\": \"Give feedback on this answer to" + spurning + "in a job interview for a " + job + " position at " + company + ": " + safeUserAnswer + "\"}" +
                " ], " +
                "\"max_tokens\": 150, " +
                "\"temperature\": 0.7 }";

        RequestBody body = RequestBody.create(json, MediaType.get("application/json"));
        Request request = new Request.Builder()
                .url(API_URL)
                .post(body)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Villa í OpenAI svari: " + response.code() + " " + response.message());
            }

            String responseBody = response.body().string();
            JSONObject jsonSvar = new JSONObject(responseBody);

            return jsonSvar
                    .getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content")
                    .trim();
        }
    }

    public static String getChatCompletion(String prompt) throws IOException {
        OkHttpClient client = new OkHttpClient();

        String json = "{ \"model\": \"gpt-3.5-turbo\", " +
                "\"messages\": [ " +
                "{\"role\": \"system\", \"content\": \"You are a helpful assistant that generates job interview questions.\"}," +
                "{\"role\": \"user\", \"content\": \"" + prompt + "\"}" +
                " ], " +
                "\"max_tokens\": 300, " +
                "\"temperature\": 0.7 }";

        RequestBody body = RequestBody.create(json, MediaType.get("application/json"));
        Request request = new Request.Builder()
                .url(API_URL)
                .post(body)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Villa í OpenAI svari: " + response.code() + " " + response.message());
            }

            String responseBody = response.body().string();
            JSONObject jsonSvar = new JSONObject(responseBody);

            return jsonSvar
                    .getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content")
                    .trim();
        }
    }
    private static List<String> parseQuestionsFromResponse(String response) {
        List<String> questions = new java.util.ArrayList<>();
        String[] lines = response.split("\n");

        for (String line : lines) {
            line = line.trim();
            if (!line.isEmpty()) {
                // Remove numbering like "1. ", "2) ", "- ", etc.
                line = line.replaceFirst("^(\\d+\\.|\\d+\\)|-)", "").trim();
                questions.add(line);
            }
        }

        return questions;
    }

    public static List<String> getAIGeneratedQuestions(String category, String jobTitle) throws IOException {
        String prompt = "Generate 5 concise job interview questions for a " + jobTitle +
                " position in the " + category + " category. List them clearly.";

        String response = getChatCompletion(prompt);
        return parseQuestionsFromResponse(response);
    }


}

