package com.portfolio.service;

import com.portfolio.model.Stock;
import com.portfolio.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;



import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    @Value("${api.key}")
    private String apiKey;

    private static final String API_URL = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=IBM&interval=5min&apikey=demo";

    // Add stock
    public Stock addStock(Stock stock) {
        if (stock.getQuantity() <= 0 || stock.getBuyPrice() < 0) {
            throw new IllegalArgumentException("Invalid stock data.");
        }
        return stockRepository.save(stock);
    }

    // Get all stocks
    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    // Get stock by ticker
    public Stock getStockByTicker(String ticker) {
        return stockRepository.findById(ticker).orElse(null);
    }

    // Update stock
    public Stock updateStock(String ticker, Stock updatedStock) {
        Stock stock = stockRepository.findById(ticker).orElse(null);
        if (stock != null) {
            stock.setQuantity(updatedStock.getQuantity());
            stock.setBuyPrice(updatedStock.getBuyPrice());
            return stockRepository.save(stock);
        }
        return null;
    }

    // Delete stock
    public void deleteStock(String ticker) {
        stockRepository.deleteById(ticker);
    }

    // Get real-time stock price
    public double getRealTimeStockPrice(String ticker) {
        String url = String.format(API_URL, ticker, apiKey);
        RestTemplate restTemplate = new RestTemplate();
        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            Map<String, String> quote = (Map<String, String>) response.get("Global Quote");
            return Double.parseDouble(quote.get("05. price"));
        } catch (Exception e) {
            return 0.0;
        }
    }

    // Portfolio Value
    public double getTotalPortfolioValue() {
        return stockRepository.findAll().stream()
                .mapToDouble(stock -> getRealTimeStockPrice(stock.getTicker()) * stock.getQuantity())
                .sum();
    }

    // Top-performing stock
    public Stock getTopPerformingStock() {
        return stockRepository.findAll().stream()
                .max(Comparator.comparingDouble(stock -> getRealTimeStockPrice(stock.getTicker()) - stock.getBuyPrice()))
                .orElse(null);
    }

    // Portfolio Distribution
    public Map<String, Double> getPortfolioDistribution() {
        double totalValue = getTotalPortfolioValue();
        return stockRepository.findAll().stream()
                .collect(Collectors.toMap(
                        Stock::getTicker,
                        stock -> (getRealTimeStockPrice(stock.getTicker()) * stock.getQuantity()) / totalValue * 100
                ));
    }

    // Initialize portfolio with 5 random stocks
    public List<Stock> initializePortfolio() {
        List<String> randomTickers = List.of("AAPL", "MSFT", "GOOGL", "AMZN", "META");
        return randomTickers.stream()
                .map(ticker -> {
                    Stock stock = new Stock();
                    stock.setTicker(ticker);
                    stock.setName("Company " + ticker);
                    stock.setQuantity(1);
                    stock.setBuyPrice(getRealTimeStockPrice(ticker));
                    return stockRepository.save(stock);
                })
                .collect(Collectors.toList());
    }
}
