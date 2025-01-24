package com.portfolio.controller;

import com.portfolio.model.Stock;
import com.portfolio.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/stocks")
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
    public ResponseEntity<Stock> addStock(@RequestBody Stock stock) {
        try {
            Stock addedStock = stockService.addStock(stock);
            return ResponseEntity.status(HttpStatus.CREATED).body(addedStock);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Get all stocks
    @GetMapping
    public ResponseEntity<List<Stock>> getAllStocks() {
        return ResponseEntity.ok(stockService.getAllStocks());
    }

    // Get stock by ticker
    @GetMapping("/{ticker}")
    public ResponseEntity<Stock> getStock(@PathVariable String ticker) {
        Stock stock = stockService.getStockByTicker(ticker);
        return stock != null ? ResponseEntity.ok(stock) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Update stock information
    @PutMapping("/{ticker}")
    public ResponseEntity<Stock> updateStock(@PathVariable String ticker, @RequestBody Stock stock) {
        Stock updatedStock = stockService.updateStock(ticker, stock);
        return updatedStock != null ? ResponseEntity.ok(updatedStock) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Delete stock by ticker
    @DeleteMapping("/{ticker}")
    public ResponseEntity<Void> deleteStock(@PathVariable String ticker) {
        stockService.deleteStock(ticker);
        return ResponseEntity.noContent().build();
    }

    // Get total portfolio value
    @GetMapping("/portfolio-value")
    public ResponseEntity<Double> getTotalPortfolioValue() {
        return ResponseEntity.ok(stockService.getTotalPortfolioValue());
    }

    // Get top-performing stock
    @GetMapping("/top-performing")
    public ResponseEntity<Stock> getTopPerformingStock() {
        Stock topStock = stockService.getTopPerformingStock();
        return topStock != null ? ResponseEntity.ok(topStock) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
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
}
