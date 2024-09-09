package enigma.estore.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JsonPlaceHolderService {
    private final RestTemplate restTemplate;
    private final String baseUrl = "https://jsonplaceholder.typicode.com";

    public String getPost(int id) {
        String url = baseUrl + "/posts/" + id;
        return restTemplate.getForObject(url, String.class);
    }

    public String getAllPosts() {
        String url = baseUrl + "/posts";
        return restTemplate.getForObject(url, String.class);
    }

    public String createPost(String title, String body, int userId) {
        String url = baseUrl + "/posts";
        String requestBody = String.format("{\"title\": \"%s\", \"body\": \"%s\", \"userId\": %d}", title, body, userId);
        return restTemplate.postForObject(url, requestBody, String.class);
    }
}
