package com.example.AutoVault.controllers;

import com.example.AutoVault.dtos.SubscriptionDto;
import com.example.AutoVault.dtos.SubscriptionInputDto;
import com.example.AutoVault.service.SubscriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class SubscriptionController {

    SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService SubscriptionService) {this.subscriptionService = SubscriptionService;}

    @GetMapping("/subscription")
    public ResponseEntity<List<SubscriptionDto>> getAllSubscription() {
        return ResponseEntity.ok(subscriptionService.getAllSubscriptions());
    }

    @GetMapping("/subscription/{id}")
    public ResponseEntity<SubscriptionDto> getOneSubscription(@PathVariable Long id) {
        SubscriptionDto SubscriptionDto = subscriptionService.getOneSubscription(id);
        return ResponseEntity.ok(SubscriptionDto);
    }

    @PostMapping("/subscription")
    public ResponseEntity<Object> createSubscription(@RequestBody SubscriptionInputDto subscriptionInputDto) {
        SubscriptionDto subscriptionSavedLocal = subscriptionService.createSubscription(subscriptionInputDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(subscriptionSavedLocal.getId()).toUri();
        return ResponseEntity.created(location).body("Created subscription with id: " + subscriptionSavedLocal.getId());
    }

    @PutMapping("/subscription/{id}")
    public ResponseEntity<Object> updateSubscription(@PathVariable Long id, @RequestBody SubscriptionInputDto s) {
        return ResponseEntity.ok(subscriptionService.updateSubscription(id, s));
    }

    @DeleteMapping("/subscription/{id}")
    public ResponseEntity<Object> deleteSubscription(@PathVariable Long id) {
        subscriptionService.deleteSubscription(id);
        return ResponseEntity.ok("Subscription with id " + id + " is deleted.");
    }
}
