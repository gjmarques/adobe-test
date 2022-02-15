package com.adobe.bookstore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.*;

@RestController    
public class BookOrderResource {  

    @Autowired
    private BookStockRepository bookStockRepository;

    @Autowired
    private BookOrderRepository bookOrderRepository;

    @RequestMapping(
        value = "/placeorder/", 
        method = RequestMethod.POST)
    public String placeOrder(@RequestBody String payload) 
        throws Exception {
            
            String uuid = UUID.randomUUID().toString();
            ObjectMapper objectMapper = new ObjectMapper();
            List<BookOrder> bookOrderList = objectMapper.readValue(payload, new TypeReference<List<BookOrder>>(){});

            for (int i = 0; i < bookOrderList.size(); i++) {

                BookStock book = bookStockRepository.findById(bookOrderList.get(i).getBook()).map(bookStock -> bookStock).orElse(null);

                if(book == null){
                    return "Invalid book name: " + bookOrderList.get(i).getBook();
                }

                if(bookOrderList.get(i).getQuantity() > book.getQuantity())
                {
                    return "Insufficient books to fulfill order: " + bookOrderList.get(i).getBook();
                }

                book.setQuantity(book.getQuantity() - bookOrderList.get(i).getQuantity());
                bookStockRepository.save(book);
                bookOrderRepository.save(bookOrderList.get(i));
            }
            
        return uuid;
    }

    @RequestMapping("/orders/")
    public List<BookOrder> orders() {
        return bookOrderRepository.findAll();
    }
}


