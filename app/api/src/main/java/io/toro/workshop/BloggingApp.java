package io.toro.workshop;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.toro.workshop.blog.BlogService;
import io.toro.workshop.blog.GoogleSheetsBlogService;
import io.toro.workshop.blog.event.BlogEventListener;
import io.toro.workshop.connectors.GoogleSheetsConnector;
import io.toro.workshop.connectors.TwitterConnector;

@SpringBootApplication
public class BloggingApp {

    public static void main( String[] args ) {
        SpringApplication.run( BloggingApp.class, args );
    }

    @Bean
    BlogService blogService() {
        return new GoogleSheetsBlogService( googleSheetsConnector() );
    }

    @Bean
    BlogEventListener blogEventListener() {
        return new BlogEventListener( twitterConnector(), executorService() );
    }

    @Bean
    TwitterConnector twitterConnector() {
        return new TwitterConnector();
    }

    @Bean
    GoogleSheetsConnector googleSheetsConnector() {
        return new GoogleSheetsConnector();
    }

    @Bean
    ExecutorService executorService() {
        return Executors.newCachedThreadPool();
    }
}
