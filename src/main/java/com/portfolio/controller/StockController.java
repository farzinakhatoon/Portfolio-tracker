package com.portfolio.controller;

import com.portfolio.model.Stock;
import com.portfolio.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to the Portfolio Tracker API!";
    }

    @Autowired
    private StockService stockService;

    // Add a new stock
    @PostMapping
    public Stock addStock(@RequestBody Stock stock) {
        return stockService.addStock(stock);
    }

    // Get all stocks
    @GetMapping
    public List<Stock> getAllStocks() {
        return stockService.getAllStocks();
    }

    // Get stock by ticker
    @GetMapping("/{ticker}")
    public Stock getStock(@PathVariable String ticker) {
        return stockService.getStockByTicker(ticker);
    }

    // Inside StockController.java
        @GetMapping("/real-time/{ticker}")
        public double getRealTimeStockPrice(@PathVariable String ticker) {
            return stockService.getRealTimeStockPrice(ticker);
        }

    // Update stock information
    @PutMapping("/{ticker}")
    public Stock updateStock(@PathVariable String ticker, @RequestBody Stock stock) {
        return stockService.updateStock(ticker, stock);
    }

    // Delete stock by ticker
    @DeleteMapping("/{ticker}")
    public void deleteStock(@PathVariable String ticker) {
        stockService.deleteStock(ticker);
    }

    // Get total portfolio value
    @GetMapping("/portfolio-value")
    public double getTotalPortfolioValue() {
        return stockService.getTotalPortfolioValue();
    }

     // Get top-performing stock
    @GetMapping("/top-performing")
    public Stock getTopPerformingStock() {
        return stockService.getTopPerformingStock();
    }

    // Get portfolio distribution
    @GetMapping("/portfolio-distribution")
    public Map<String, Double> getPortfolioDistribution() {
        return stockService.getPortfolioDistribution();
    }
}
