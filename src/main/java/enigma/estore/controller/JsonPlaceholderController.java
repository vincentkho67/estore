package enigma.estore.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import enigma.estore.service.JsonPlaceHolderService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/jsonplaceholder")
@RequiredArgsConstructor
public class JsonPlaceholderController {
    private final JsonPlaceHolderService service;

    @GetMapping("/posts/{id}")
    public ResponseEntity<String> getPost(@PathVariable int id) {
        String post = service.getPost(id);
        return ResponseEntity.ok(post);
    }

    @GetMapping("/posts")
    public ResponseEntity<String> getAllPosts() {
        String posts = service.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @PostMapping("/posts")
    public ResponseEntity<String> createPost(@RequestBody Map<String, Object> postData) {
        String title = (String) postData.get("title");
        String body = (String) postData.get("body");
        int userId = (int) postData.get("userId");
        String createdPost = service.createPost(title, body, userId);
        return ResponseEntity.ok(createdPost);
    }

}
