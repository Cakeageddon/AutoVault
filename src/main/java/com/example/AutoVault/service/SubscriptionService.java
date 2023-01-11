package com.example.AutoVault.service;

import com.example.AutoVault.dtos.SubscriptionDto;
import com.example.AutoVault.dtos.SubscriptionInputDto;
import com.example.AutoVault.exceptions.RecordNotFoundException;

import com.example.AutoVault.models.Subscription;
import com.example.AutoVault.repositories.SubscriptionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionService {

    SubscriptionRepository subscriptionRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository) {this.subscriptionRepository = subscriptionRepository;}

    public List<SubscriptionDto> getAllSubscriptions() {
        List<Subscription> allSubscriptions = subscriptionRepository.findAll();
        List<SubscriptionDto> allSubscriptionDto = new ArrayList<>();
        for (Subscription c : allSubscriptions) {
            allSubscriptionDto.add(transferToSubscriptionDto(c));
        }
        return allSubscriptionDto;
    }

    public SubscriptionDto getOneSubscription(Long id) {
        Optional<Subscription> optionalSubscription = subscriptionRepository.findById(id);
        if (optionalSubscription.isEmpty()) {
            throw new RecordNotFoundException("No subscription found with id: " + id);
        } else {
            Subscription subscription = optionalSubscription.get();
            return transferToSubscriptionDto(subscription);
        }
    }

    public SubscriptionDto updateSubscription(Long id, SubscriptionInputDto s) {
        Optional<Subscription> optionalSubscription = subscriptionRepository.findById(id);
        if (optionalSubscription.isEmpty()) {
            throw new RecordNotFoundException("No subscription found with id: " + id);
        } else {
            Subscription updateSubscription = optionalSubscription.get();
            if (s.getId() != null) {updateSubscription.setId(s.getId());}
            if (s.getPrice() != null) {updateSubscription.setPrice(s.getPrice());}
            if (s.getType() != null) {updateSubscription.setType(s.getType());}
            subscriptionRepository.save(updateSubscription);
            return transferToSubscriptionDto(updateSubscription);
        }
    }

    public void deleteSubscription(Long id) {
        Optional<Subscription> optionalSubscription = subscriptionRepository.findById(id);
        if (optionalSubscription.isEmpty()) {
            throw new RecordNotFoundException("No subscription found with id: " + id);
        } else {
            subscriptionRepository.deleteById(id);
        }
    }

    public SubscriptionDto createSubscription(SubscriptionInputDto subscription) {
        Subscription subscriptionSavedLocal = subscriptionRepository.save(transferToSubscription(subscription));
        return transferToSubscriptionDto(subscriptionSavedLocal);
    }

    public static SubscriptionDto transferToSubscriptionDto(Subscription subscription) {
        SubscriptionDto dto = new SubscriptionDto();
        dto.setId(subscription.getId());
        dto.setPrice(subscription.getPrice());
        dto.setType(subscription.getType());
        return dto;
    }

    public static Subscription transferToSubscription(SubscriptionInputDto dto) {
        Subscription subscription = new Subscription();
        subscription.setPrice(dto.getPrice());
        subscription.setType(dto.getType());
        return subscription;
    }
}
