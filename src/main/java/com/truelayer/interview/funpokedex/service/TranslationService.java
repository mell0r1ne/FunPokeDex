package com.truelayer.interview.funpokedex.service;


import com.truelayer.interview.funpokedex.client.TranslationClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TranslationService {
    private final TranslationClient translationClient;

}
