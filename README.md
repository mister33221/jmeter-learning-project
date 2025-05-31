# JMeter 學習實戰專案

一個專門用於學習 JMeter 性能測試、報表產出和報表分析的實戰專案。通過提供多種測試場景的 REST API 端點，幫助您深入理解 JMeter 的使用方法和報表解讀技巧。

## 專案目標

本專案的主要目標是：
- **實戰性能測試** - 透過真實的 API 端點進行各種測試場景
- **報表產出練習** - 學習如何生成和匯出 JMeter 測試報表
- **報表解讀能力** - 培養分析和理解性能測試報表的能力

## 技術棧

- **Java 17+**
- **Spring Boot 3.x**
- **Spring Web**
- **Swagger/OpenAPI 3** (API 文檔)
- **Maven** (依賴管理)

## 快速開始

### 前置需求

- Java 17 或更高版本
- Maven 3.6+
- JMeter 5.x (用於性能測試)

### 安裝與運行

```bash
# 克隆專案
git clone https://github.com/mister33221/jmeter-learning-project.git
cd jemter-test

# 編譯專案
mvn clean compile

# 運行應用程式
mvn spring-boot:run
```

應用程式將在 `http://localhost:8080` 啟動

### 專案結構

```
src/
├── main/
│   ├── java/
│   │   └── com/kai/jemter_test/
│   │       ├── JemterTestApplication.java      # 主應用程式入口
│   │       ├── config/                          # 配置類
│   │       │   └── SwaggerConfig.java          # Swagger API 文檔配置
│   │       └── controller/                      # 控制器層
│   │           └── JMeterTestController.java   # JMeter 測試端點控制器
│   └── resources/
│       ├── application.properties              # 應用程式配置
│       ├── static/                             # 靜態資源
│       └── templates/                          # 模板檔案
├── test/
│   └── java/
│       └── com/kai/jemter_test/
│           └── JemterTestApplicationTests.java # 單元測試
jmeter-related/                                 # JMeter 相關檔案
├── jmeter-test.jmx                            # JMeter 測試計畫
└── run-jemeter.bat                            # JMeter 執行批次檔
pom.xml                                        # Maven 依賴管理
README.md                                      # 專案說明文檔
└── ...
```

### JMeter

- 相關的資料我放在 `jmeter-related` 資料夾中
- 包含 jmeter 腳本腳本，以及啟動測試及產出報表的 bat 檔。可以放在自己 jmeter 的 bin 資料夾中，修改一下 bat 中相關的路徑，然後直接執行即可。

### API 文檔

啟動應用程式後，可以透過以下網址查看 Swagger API 文檔：
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs

## API 端點詳細說明

### 基礎測試端點

| 端點 | 方法 | 說明 | 響應時間 |
|------|------|------|----------|
| `/api/test/fast` | GET | 快速響應測試 | ~0ms |
| `/api/test/slow` | GET | 慢速響應測試 | ~2000ms |
| `/api/test/random` | GET | 隨機響應時間 | 0-3000ms |
| `/api/test/health` | GET | 健康檢查 | ~0ms |

### 負載測試端點

| 端點 | 方法 | 參數 | 說明 |
|------|------|------|------|
| `/api/test/cpu-intensive` | GET | `limit` (預設: 10000) | CPU 密集型任務 |
| `/api/test/memory-intensive` | GET | `size` (預設: 100000) | 記憶體密集型任務 |
| `/api/test/error-prone` | GET | 無 | 隨機錯誤測試 |
| `/api/test/data` | POST | JSON 資料 | 資料處理測試 |

### 錯誤率說明

`/api/test/error-prone` 端點的錯誤分布：
- **65%** - 成功響應 (200)
- **20%** - 伺服器錯誤 (500)
- **15%** - 未找到錯誤 (404)


