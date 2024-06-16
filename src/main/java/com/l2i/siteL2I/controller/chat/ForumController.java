package com.l2i.siteL2I.controller.chat;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.l2i.siteL2I.dto.chat.ForumRequest;
import com.l2i.siteL2I.dto.chat.ForumResponse;
import com.l2i.siteL2I.service.chat.ForumService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/forum")
public class ForumController {

    private final ForumService forumService;

    @GetMapping
    public ResponseEntity<List<ForumResponse>> getAll() {
        return forumService.getAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<ForumResponse> getById(@PathVariable("id") Integer id) {
        return forumService.getById(id);
    }

    @PostMapping
    public ResponseEntity<ForumResponse> create(@RequestBody ForumRequest requestItem) {
        return forumService.create(requestItem);
    }

    @PutMapping("{id}")
    // @PutMapping(path = "/testpost")
    public ResponseEntity<ForumResponse> update(@PathVariable("id") Integer id, @RequestBody ForumRequest requestItem) {
        return forumService.update(id, requestItem);
    }
    

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Integer id) {
        return forumService.delete(id);
    }
}
