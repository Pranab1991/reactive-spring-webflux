package com.learnreactiveprogramming.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class FluxAndMonoGeneratorService {

    public  Flux<String> fluxGenerator(){

        return Flux.fromIterable(Arrays.asList("test","test2","test3")).log();
    }

    public Mono<String> monoGenerator(){

        return Mono.just("Hello").log();
    }

    public Flux<String> fluxGeneratorMap(){

        return Flux.fromIterable(Arrays.asList("test","test2","test3"))
                .map(String::toUpperCase)
                .log();
    }

    public Flux<String> fluxGeneratorMapImmutability(){

        var nameflux= Flux.fromIterable(Arrays.asList("test","test2","test3")).log();
        nameflux.map(String::toUpperCase);
        return nameflux;
    }

    public Flux<String> fluxGeneratorMapFilter(int length){

        return Flux.fromIterable(Arrays.asList("test","test2","test3"))
                .map(String::toUpperCase)
                .filter(s->s.length()>length)
                .log();
    }

    public Flux<String> fluxGeneratorFlatMap(int length){

        return Flux.fromIterable(Arrays.asList("test","test2","test3"))
                .map(String::toUpperCase)
                .filter(s->s.length()>length)
                .flatMap(FluxAndMonoGeneratorService::splitString)
                .log();
    }

    public Flux<String> fluxGeneratorFlatMapAsync(int length){

        return Flux.fromIterable(Arrays.asList("test","test2","test3"))
                .map(String::toUpperCase)
                .filter(s->s.length()>length)
                .flatMap(FluxAndMonoGeneratorService::splitStringDelay)
                .log();
    }

    public Flux<String> fluxGeneratorConcatMapAsync(int length){

        return Flux.fromIterable(Arrays.asList("test","test2","test3"))
                .map(String::toUpperCase)
                .filter(s->s.length()>length)
                .concatMap(FluxAndMonoGeneratorService::splitStringDelay)
                .log();
    }

    public Mono<List<String>> monoFlatMap(){

        return Mono.just("Hello")
                .flatMap(FluxAndMonoGeneratorService::splitStringList).log();
    }

    public Flux<String> monoToFlux(){

        return Mono.just("Hello")
                .flatMapMany(FluxAndMonoGeneratorService::splitString).log();
    }

    public Flux<String> testTransform(int stringLen){

        Function<Flux<String>,Flux<String>> transform=name->name.filter(s->s.length()>stringLen).map(String::toUpperCase);
        return Flux.fromIterable(Arrays.asList("Alex","Tom","Harry"))
                .transform(transform);
    }

    public static Mono<List<String>> splitStringList(String value){
        String[] charArray=value.split("");
        return Mono.just(List.of(charArray));
    }

    public static Flux<String> splitString(String value){
        String[] charArray=value.split("");
        return Flux.fromArray(charArray);
    }

    public static Flux<String> splitStringDelay(String value){
        int duration= new Random().nextInt(1000);
        String[] charArray=value.split("");
        return Flux.fromArray(charArray).delayElements(Duration.ofMillis(duration));
    }

    public static void main(String[] args) {
        FluxAndMonoGeneratorService fluxAndMonoGeneratorService=new FluxAndMonoGeneratorService();

        fluxAndMonoGeneratorService.fluxGenerator().subscribe(System.out::println);

        fluxAndMonoGeneratorService.monoGenerator().subscribe(System.out::println);

        fluxAndMonoGeneratorService.fluxGeneratorMapImmutability().subscribe(System.out::println);
    }
}
