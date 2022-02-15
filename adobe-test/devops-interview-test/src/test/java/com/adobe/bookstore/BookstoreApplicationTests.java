package com.adobe.bookstore;
 
import java.net.URI;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonInclude;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookstoreApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testAddOrderSuccess() throws URISyntaxException 
    {
        final String baseUrl = "http://localhost:"+port+"/placeorder/";
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");       
        headers.set("X-COM-PERSIST", "true");  

        BookOrder bookorder = new BookOrder();
        bookorder.setBook("80d9f5e3-e959-4365-b75f-7ca90e90057f");
        bookorder.setQuantity(1);   

        HttpEntity<String> request = new HttpEntity<>(bookorder.toString(), headers);
         
        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);
         
        //Verify request succeed
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void testAddOrderFailure() throws URISyntaxException 
    {
        final String baseUrl = "http://localhost:"+port+"/placeorder/";
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");       
        headers.set("X-COM-PERSIST", "true");  

        BookOrder bookorder = new BookOrder();
        bookorder.setBook("80d9f5e3-e959-4365-b75f-7ca90e90057f");
        bookorder.setQuantity(100);   

        HttpEntity<String> request = new HttpEntity<>(bookorder.toString(), headers);
         
        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);
         
        //Verify request succeed
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
    }

}
