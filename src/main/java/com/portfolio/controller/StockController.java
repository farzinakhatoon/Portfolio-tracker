package com.portfolio.controller;

import com.portfolio.model.Stock;
import com.portfolio.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/stocks")
@CrossOrigin(origins = "http://localhost:3000") // Allow requests from your frontend
public class StockController {

    @Autowired
    private StockService stockService;

    // Welcome message
    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to the Portfolio Tracker API!";
    }

    // Add a new stock
    @PostMapping
    public ResponseEntity<?> addStock(@Valid @RequestBody Stock stock) {
        try {
            Stock addedStock = stockService.addStock(stock);
            return ResponseEntity.status(HttpStatus.CREATED).body(addedStock);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Get all stocks
    @GetMapping
    public ResponseEntity<List<Stock>> getAllStocks() {
        return ResponseEntity.ok(stockService.getAllStocks());
    }

    // Get stock by ticker
    @GetMapping("/{ticker}")
    public ResponseEntity<?> getStock(@PathVariable String ticker) {
        Stock stock = stockService.getStockByTicker(ticker);
        return stock != null 
            ? ResponseEntity.ok(stock) 
            : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Stock not found for ticker: " + ticker);
    }

    // Update stock information
    @PutMapping("/{ticker}")
    public ResponseEntity<?> updateStock(@PathVariable String ticker, @Valid @RequestBody Stock stock) {
        try {
            Stock updatedStock = stockService.updateStock(ticker, stock);
            return ResponseEntity.ok(updatedStock);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Delete stock by ticker
    @DeleteMapping("/{ticker}")
    public ResponseEntity<?> deleteStock(@PathVariable String ticker) {
        try {
            stockService.deleteStock(ticker);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Get total portfolio value
    @GetMapping("/portfolio-value")
    public ResponseEntity<Double> getTotalPortfolioValue() {
        return ResponseEntity.ok(stockService.getTotalPortfolioValue());
    }

    // Get top-performing stock
    @GetMapping("/top-performing")
    public ResponseEntity<?> getTopPerformingStock() {
        Stock topStock = stockService.getTopPerformingStock();
        return topStock != null 
            ? ResponseEntity.ok(topStock) 
            : ResponseEntity.status(HttpStatus.NOT_FOUND).body("No stocks found.");
    }

    // Get portfolio distribution
    @GetMapping("/portfolio-distribution")
    public ResponseEntity<Map<String, Double>> getPortfolioDistribution() {
        return ResponseEntity.ok(stockService.getPortfolioDistribution());
    }

    // Initialize portfolio with 5 random stocks
    @PostMapping("/initialize")
    public ResponseEntity<List<Stock>> initializePortfolio() {
        return ResponseEntity.ok(stockService.initializePortfolio());
    }

    // Handle validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldError().getDefaultMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
}

