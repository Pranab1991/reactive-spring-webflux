package com.learnreactiveprogramming.service;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.util.Arrays;

class FluxAndMonoGeneratorServiceTest {

    FluxAndMonoGeneratorService fluxAndMonoGeneratorService=new FluxAndMonoGeneratorService();

    @Test
    void fluxGenerator() {
        StepVerifier.create(fluxAndMonoGeneratorService.fluxGenerator())
                .expectNext("test")
                .expectNext("test2")
                .expectNext("test3")
                .verifyComplete();
    }

    @Test
    void fluxGeneratorCount() {
        StepVerifier.create(fluxAndMonoGeneratorService.fluxGenerator())
                .expectNext("test")
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void monoGenerator() {
        StepVerifier.create(fluxAndMonoGeneratorService.monoGenerator())
                .expectNext("Hello")
                .verifyComplete();
    }

    @Test
    void fluxGeneratorMap() {
        var nameMapFlux=fluxAndMonoGeneratorService.fluxGeneratorMap();
        StepVerifier.create(nameMapFlux)
                .expectNext("TEST","TEST2","TEST3").verifyComplete();
    }

    @Test
    void fluxGeneratorMapImmutability() {
        var nameMapFlux=fluxAndMonoGeneratorService.fluxGeneratorMapImmutability();
        StepVerifier.create(nameMapFlux)
                .expectNext("test","test2","test3")
                .verifyComplete();
    }

    @Test
    void fluxGeneratorMapFilter() {
        var nameFlux=fluxAndMonoGeneratorService.fluxGeneratorMapFilter(4);
        StepVerifier.create(nameFlux).expectNext("TEST2","TEST3").verifyComplete();
    }

    @Test
    void fluxGeneratorFlatMap() {
        StepVerifier.create(fluxAndMonoGeneratorService.fluxGeneratorFlatMap(4))
                .expectNext("T","E","S","T","2","T","E","S","T","3").verifyComplete();
    }

    @Test
    void fluxGeneratorFlatMapAsync() {
        StepVerifier.create(fluxAndMonoGeneratorService.fluxGeneratorFlatMapAsync(4))
                .expectNextCount(10).verifyComplete();
    }

    @Test
    void fluxGeneratorConcatMapAsync() {
        StepVerifier.create(fluxAndMonoGeneratorService.fluxGeneratorConcatMapAsync(4))
                .expectNext("T","E","S","T","2","T","E","S","T","3").verifyComplete();
    }

    @Test
    void monoFlatMap() {
        StepVerifier.create(fluxAndMonoGeneratorService.monoFlatMap())
                .expectNext(Arrays.asList("H","e","l","l","o")).verifyComplete();
    }

    @Test
    void monoToFlux() {
        StepVerifier.create(fluxAndMonoGeneratorService.monoToFlux())
                .expectNext("H","e","l","l","o").verifyComplete();
    }


    @Test
    void testTransform() {
        StepVerifier.create(fluxAndMonoGeneratorService.testTransform(3))
                .expectNext("ALEX")
                .expectNext("HARRY")
                .verifyComplete();
    }
}