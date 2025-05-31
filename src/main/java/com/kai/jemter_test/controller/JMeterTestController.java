package com.kai.jemter_test.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/api/test")
@Tag(name = "JMeter Test Controller", description = "API endpoints for JMeter performance testing scenarios")
public class JMeterTestController {
    
    // 這個註釋是由AI助手自動添加的，證明我可以控制IntelliJ IDEA！ 🤖    // 快速響應API - 立即返回
    @Operation(
            summary = "Fast Response Test",
            description = "Returns an immediate response with minimal processing time (~0ms). Perfect for testing baseline performance."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful fast response",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{\"message\": \"Fast response\", \"timestamp\": 1234567890, \"responseTime\": \"~0ms\"}"
                            )
                    )
            )
    })
    @GetMapping("/fast")
    public ResponseEntity<Map<String, Object>> fastResponse() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Fast response");
        response.put("timestamp", System.currentTimeMillis());
        response.put("responseTime", "~0ms");
        return ResponseEntity.ok(response);
    }    // 慢速響應API - 固定延遲2秒

    @Operation(
            summary = "Slow Response Test",
            description = "Returns a response after a fixed 2-second delay. Useful for testing timeout scenarios and slow server responses."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful slow response after 2 seconds",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{\"message\": \"Slow response after 2 seconds\", \"timestamp\": 1234567890, \"responseTime\": \"~2000ms\"}"
                            )
                    )
            )
    })
    @GetMapping("/slow")
    public ResponseEntity<Map<String, Object>> slowResponse() {
        try {
            Thread.sleep(2000); // 延遲2秒
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Slow response after 2 seconds");
        response.put("timestamp", System.currentTimeMillis());
        response.put("responseTime", "~2000ms");
        return ResponseEntity.ok(response);
    }    // 隨機響應時間API - 0-3秒隨機延遲

    @Operation(
            summary = "Random Response Time Test",
            description = "Returns a response after a random delay between 0-3 seconds. Useful for testing variable response time scenarios."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful response with random delay",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{\"message\": \"Random response\", \"timestamp\": 1234567890, \"delay\": \"1500ms\"}"
                            )
                    )
            )
    })
    @GetMapping("/random")
    public ResponseEntity<Map<String, Object>> randomResponse() {
        int delay = ThreadLocalRandom.current().nextInt(0, 3001); // 0-3秒隨機延遲

        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Random response");
        response.put("timestamp", System.currentTimeMillis());
        response.put("delay", delay + "ms");
        return ResponseEntity.ok(response);
    }    // CPU密集型API - 計算質數

    @Operation(
            summary = "CPU Intensive Test",
            description = "Performs CPU-intensive prime number calculations. Use this to test server performance under high CPU load."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "CPU intensive task completed successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{\"message\": \"CPU intensive task completed\", \"primesFound\": 1229, \"limit\": 10000, \"processingTime\": \"45ms\", \"timestamp\": 1234567890}"
                            )
                    )
            )
    })
    @GetMapping("/cpu-intensive")
    public ResponseEntity<Map<String, Object>> cpuIntensive(
            @Parameter(description = "Upper limit for prime number calculation", example = "10000")
            @RequestParam(defaultValue = "10000") int limit) {
        long startTime = System.currentTimeMillis();

        List<Integer> primes = calculatePrimes(limit);

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        Map<String, Object> response = new HashMap<>();
        response.put("message", "CPU intensive task completed");
        response.put("primesFound", primes.size());
        response.put("limit", limit);
        response.put("processingTime", duration + "ms");
        response.put("timestamp", System.currentTimeMillis());

        return ResponseEntity.ok(response);
    }    // 內存密集型API - 創建大量對象

    @Operation(
            summary = "Memory Intensive Test",
            description = "Creates a large number of objects in memory. Use this to test server performance under high memory usage."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Memory intensive task completed successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{\"message\": \"Memory intensive task completed\", \"itemsCreated\": 100000, \"memoryUsed\": \"150MB\", \"processingTime\": \"1200ms\", \"timestamp\": 1234567890}"
                            )
                    )
            )
    })
    @GetMapping("/memory-intensive")
    public ResponseEntity<Map<String, Object>> memoryIntensive(
            @Parameter(description = "Number of objects to create in memory", example = "100000")
            @RequestParam(defaultValue = "100000") int size) {
        long startTime = System.currentTimeMillis();

        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            dataList.add("Data item " + i + " - " + UUID.randomUUID().toString());
        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Memory intensive task completed");
        response.put("itemsCreated", dataList.size());
        response.put("memoryUsed", (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024 + "MB");
        response.put("processingTime", duration + "ms");
        response.put("timestamp", System.currentTimeMillis());

        return ResponseEntity.ok(response);
    }    // 錯誤測試API - 隨機返回錯誤

    @Operation(
            summary = "Error Prone Test",
            description = "Randomly returns different HTTP status codes: 20% chance of 500 error, 15% chance of 404 error, 65% chance of success. Perfect for testing error handling scenarios."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful response (65% probability)",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{\"message\": \"Success response\", \"timestamp\": 1234567890, \"lucky\": true}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found error (15% probability)",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{\"error\": \"Not Found\", \"message\": \"Resource not found\", \"timestamp\": 1234567890}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error (20% probability)",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{\"error\": \"Internal Server Error\", \"message\": \"Random server error occurred\", \"timestamp\": 1234567890}"
                            )
                    )
            )
    })
    @GetMapping("/error-prone")
    public ResponseEntity<Map<String, Object>> errorProne() {
        int random = ThreadLocalRandom.current().nextInt(1, 101);

        if (random <= 20) { // 20% 機率返回500錯誤
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Internal Server Error");
            errorResponse.put("message", "Random server error occurred");
            errorResponse.put("timestamp", System.currentTimeMillis());
            return ResponseEntity.status(500).body(errorResponse);
        } else if (random <= 35) { // 15% 機率返回404錯誤
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Not Found");
            errorResponse.put("message", "Resource not found");
            errorResponse.put("timestamp", System.currentTimeMillis());
            return ResponseEntity.status(404).body(errorResponse);
        } else { // 65% 機率正常返回
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Success response");
            response.put("timestamp", System.currentTimeMillis());
            response.put("lucky", true);
            return ResponseEntity.ok(response);
        }
    }    // POST API - 接收和處理數據

    @Operation(
            summary = "Data Processing Test",
            description = "Accepts JSON data via POST request and processes it with a 0.5 second delay. Use this to test POST request performance and data handling."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Data processed successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{\"message\": \"Data processed successfully\", \"receivedData\": {\"key\": \"value\"}, \"processedAt\": 1234567890, \"dataSize\": 1}"
                            )
                    )
            )
    })
    @PostMapping(value = "/data", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Map<String, Object>> processData(
            @Parameter(description = "JSON data to be processed", required = true)
            @RequestBody Map<String, Object> requestData) {
        // 模擬數據處理時間
        try {
            Thread.sleep(500); // 0.5秒處理時間
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Data processed successfully");
        response.put("receivedData", requestData);
        response.put("processedAt", System.currentTimeMillis());
        response.put("dataSize", requestData.size());

        return ResponseEntity.ok(response);
    }    // 健康檢查API

    @Operation(
            summary = "Health Check",
            description = "Returns the current health status of the application. Use this for monitoring and load balancer health checks."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Application is healthy",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{\"status\": \"UP\", \"timestamp\": 1234567890, \"uptime\": 1234567890, \"version\": \"1.0.0\"}"
                            )
                    )
            )
    })
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("timestamp", System.currentTimeMillis());
        response.put("uptime", System.currentTimeMillis());
        response.put("version", "1.0.0");

        return ResponseEntity.ok(response);
    }

    // 計算質數的輔助方法
    private List<Integer> calculatePrimes(int limit) {
        List<Integer> primes = new ArrayList<>();
        boolean[] isPrime = new boolean[limit + 1];
        Arrays.fill(isPrime, true);
        isPrime[0] = isPrime[1] = false;

        for (int i = 2; i * i <= limit; i++) {
            if (isPrime[i]) {
                for (int j = i * i; j <= limit; j += i) {
                    isPrime[j] = false;
                }
            }
        }

        for (int i = 2; i <= limit; i++) {
            if (isPrime[i]) {
                primes.add(i);
            }
        }

        return primes;
    }
}
