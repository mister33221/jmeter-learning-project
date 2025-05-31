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

--- 

# 如何閱讀與分析 JMeter 測試報表

本篇將介紹如何有效閱讀與分析 Apache JMeter 所產生的測試報表，協助你判斷系統效能瓶頸、穩定性問題與使用者體驗指標。

---

## 產出 Jmeter 報表的指令

1. 我已經先使用 Jmeter 的 GUI 把測試腳本設定好了
2. 在正式進行測試時，不要使用 GUI 來進行測試，因為使用 GUI 會消耗太多其他不必要的資源，進而影響測試結果
3. 我編寫了以下的 bat 腳本來執行測試並產出報表
    ```bat
    @echo off
    chcp 65001 > nul

    REM 設定環境變數
    set JMETER_BIN=<path/to/jmeter/bin>
    set SCRIPT_DIR=<path/to/your/script/directory>
    set TEST_PLAN=%SCRIPT_DIR%\<your_test_plan>.jmx
    set RESULT_FILE=%SCRIPT_DIR%\<your_result_file>.jtl
    set REPORT_DIR=%SCRIPT_DIR%\<your_report_directory>

    echo 執行 JMeter 測試: %TEST_PLAN%

    REM 清理舊檔案
    if exist "%RESULT_FILE%" (
        echo 刪除舊的 JTL 檔案...
        del "%RESULT_FILE%"
    )
    if exist "%REPORT_DIR%" (
        echo 刪除舊的報表資料夾...
        rmdir /s /q "%REPORT_DIR%" 2>nul
    )

    REM 執行測試
    call "%JMETER_BIN%\jmeter.bat" -n -t "%TEST_PLAN%" -l "%RESULT_FILE%"
    if %ERRORLEVEL% neq 0 (
        echo ❌ 測試執行失敗
        pause
        exit /b
    )

    REM 檢查結果檔案
    if not exist "%RESULT_FILE%" (
        echo ❌ JTL 檔案未產生
        pause
        exit /b
    )

    REM 產生報表
    mkdir "%REPORT_DIR%" 2>nul
    call "%JMETER_BIN%\jmeter.bat" -g "%RESULT_FILE%" -o "%REPORT_DIR%"
    if %ERRORLEVEL% neq 0 (
        echo ❌ 報表產生失敗
        pause
        exit /b
    )

    REM 開啟報表
    if exist "%REPORT_DIR%\index.html" (
        echo ✅ 完成！開啟報表...
        start "" "%REPORT_DIR%\index.html"
    ) else (
        echo ❌ 報表檔案未找到
    )

    pause
    ```
---


## 報表範例

我用 Java 先寫了一個測試專案，

[專案在這裡](https://github.com/mister33221/jmeter-learning-project)

並用 Jmeter 產出了報表。

我們先來看一下 JMeter 測試報表的範例。


我們希先來看一下 JMeter 測試報表的範例。

其實產出的報表有很多詳細內容可以看，但主要會先閱讀這兩個部分
1. **APDEX 指標**：評估使用者對 API 回應時間的滿意度。

![image](https://hackmd.io/_uploads/Hk-oCbdGel.png)


2. **Statistics（統計數據）**：提供每個測試項目的整體效能指標。

![image](https://hackmd.io/_uploads/rJq5RZdMgg.png)

## 一、APDEX 指標（使用者體感）

**APDEX (Application Performance Index)** 是一個介於 0～1 的數值，代表使用者對 API 回應時間的滿意度。

### 判讀依據：

- **APDEX 值**：
    * 計算公式：`Apdex = (滿意次數 + 可忍受次數 ÷ 2) / 總請求次數`
    * 1.000：完美體驗，皆在滿意時間內完成
    * 0.8 \~ 0.9：大部分可接受，少部分延遲
    * < 0.7：使用者體驗差，應優化
    * 當然以上的數值區間界定，可以依照專案類型，自行調整。

### Apdex 深入解析與設定方式

Apdex 指標的核心是兩個時間閾值：T 與 F

| 名稱 | 全名                    | 意思             | 預設值    |
| -- | --------------------- | -------------- | ------ |
| T  | Toleration Threshold  | 使用者「滿意」的最大回應時間 | 500ms  |
| F  | Frustration Threshold | 超過此值即視為「失望」    | 1500ms |

- Toleration Threshold（T）：
    - 使用者對回應時間的滿意上限。
    - 若沒有特別設定，預設此值為 500ms，但我們可以依據專案性質，自行調整。
        - 若專案是一個社群網站，則 T 或許可以設定為 800ms。
        - 若專愛是一個金融交易系統，則 T 可能需要設定為 200ms。
        - 以上是比較假設的設定，我個人是還沒有該如何設定會更符合需舊的體感啦....
- Frustration Threshold (F)：
    - 超過此值即視為「失望」
    - 若沒有設別這定，預設此值為 1500ms
    - 通常設定為 T 的 2~4 倍，預設為 1500ms。
- T 值與 F 值得設定
    - 在啟動 JMeter 時，可以透過 `jmeter.properties` 檔案來設定 T 與 F 的值。
    - 在 JMeter 的安裝目錄下，通常在 `bin` 資料夾中，可以找到 `jmeter.properties` 檔案。其中包含以下片段，你可以自行修改，再重新測試後，就會看到你修改的數字成現在報表中
    ```properties
    # Change this parameter if you want to override the APDEX satisfaction threshold.
    jmeter.reportgenerator.apdex_satisfied_threshold=600

    # Change this parameter if you want to override the APDEX tolerance threshold.
    jmeter.reportgenerator.apdex_tolerated_threshold=1500
    ```

#### Apdex 計算公式

```text
Apdex = (滿意次數 + 可忍受次數 ÷ 2) / 總請求次數
```

| 狀態  | 判斷依據         |
| --- | ------------ |
| 滿意  | 回應時間 ≤ T     |
| 可忍受 | T < 回應時間 ≤ F |
| 失望  | 回應時間 > F     |

#### 範例計算

以 T = 500ms 為例，假設有 100 次請求：

| 狀態  | 次數 |
| --- | -- |
| 滿意  | 60 |
| 可忍受 | 30 |
| 失望  | 10 |

計算：

```text
Apdex = (60 + 30/2) / 100 = 0.75
```
---

#### 網路上看到的建議值參考（依不同 API 性質 > 僅供參考，我也沒啥概念~）

| 類型          | T 值（滿意）      | F 值（可忍受）     |
| ----------- | ------------ | ------------ |
| 快速查詢 API    | 300\~500ms   | 1200\~2000ms |
| 表單送出 / 一般互動 | 800\~1000ms  | 3000\~4000ms |
| 報表下載 / 大量查詢 | 2000\~3000ms | 6000\~8000ms |

---

## 二、Statistics（統計數據）區塊說明

JMeter 報表中的 Statistics 表格呈現每個測試項目的整體效能指標。

| 欄位名稱                       | 說明            | 判讀建議                      |
| -------------------------- | ------------- | ------------------------- |
| **Label**                  | 測試項目名稱        | 與測試腳本中的 HTTP Sampler 名稱一致 |
| **# Samples**              | 請求次數          | 次數越多，統計越具參考性              |
| **# Errors / Error %**     | 錯誤數量與比率       | 任何 >0 都應進一步追查 log         |
| **Average**                | 平均回應時間（ms）    | >800ms 建議關注，>2000ms 表現不佳  |
| **Min / Max**              | 最小 / 最大回應時間   | Max 過高可能是尖峰、GC 或 DB 問題    |
| **90% / 95% / 99% Line**   | 百分位回應時間       | 大於 1s 表示有顯著延遲，應進一步分析      |
| **Throughput**             | 每秒完成請求數       | 越高表示效能越好，可用來評估容量          |
| **Received / Sent KB/sec** | 網路頻寬（接收 / 傳送） | 可反映 API 輸出入資料大小與 I/O 壓力   |

- **Samples**
    - 要測多少次才有參考價值呢?要看我們想要測試的 API 性質
        - 快速 API 測試：500 ~ 1000 次，足以砍出平均與百分為趨勢
        - 中等複雜度任務：2000 ~ 5000 次。降低偶發尖峰影響
        - 重要核心流程 / SLA驗收：10000 次以上。減少極端值誤差，分佈穩定，結果比較可靠
    - 要有足夠多的 Samples，才能讓百分位的數值有足夠的可靠性，也才能測出是否有某些區塊反應變慢、可能有種資料可能會有影響、或是在持續工作的狀況下，會不會有記憶體洩漏、資源耗盡的情形。
- Min / Max
    - **Min**：
        - 最小回應時間
        - 若與 Average 差異不大，表示系統穩定。
        - 若與 Average 差異過大，可能有偶發尖峰、回應不穩定。也可能是 Samples 數量不足，導致統計不準確。
    - **Max**：
        - 最大回應時間
        - 若 Max 遠高於 Average，表示有極端延遲事件。
        - 可能原因：GC、DB 查詢慢、網路問題等。
        - 應進一步分析 log 與系統資源使用情況。
        - 若是 Max 明顯大於 95% Line -> 可能是極少數請求出現異常，應排查。
        - 若是 Max 與 95% Line 都很大 -> 可能是系統整體效能瓶頸，應優化。
- **Average**
    - 平均回應時間
    - 反映整體效能，若大於 800ms 建議關注。
    - 若大於 2000ms，表示系統效能不佳，應優化。
    - 注意：平均值可能受極端值影響，需結合 Min/Max 與百分位數分析。
- **Error %**
    - 錯誤數量與比率
    - 這沒什麼好多的，有錯誤就是最嚴重的問題，應該安排於第一個優先處理。
- **90% / 95% / 99% Line**
    - 百分位回應時間
    - 90% Line：90% 請求在此時間內完成，通常應小於 800ms(看 API 性質)。
    - 95% Line：95% 請求在此時間內完成，通常應小於 1000ms(看 API 性質)。
    - 99% Line：99% 請求在此時間內完成，通常應小於 1500ms（看 API 性質）。
    - 若任何百分位數大於 1 秒，表示有顯著延遲，應進一步分析。
    - 可以將 min 與 max 的值與百分位數做比較
        - 若 min 與 90% Line 差異不大，表示系統穩定。
        - 若 min 與 90% Line 差異過大，可能有偶發尖峰或回應不穩定。
        - 若 max 與 99% Line 差不多，表示系統整體效能瓶頸。
        - 若 max 遠高於 99% Line，表示有極端延遲事件。
- **Throughput**
    - 每秒完成請求數(requests/second)
    - 反映系統併發能力，越高表示效能越好。
    - 可用來評估系統容量極限。
    - 若是光看數值沒辦法進一步分析，可以使用報告中的其他圖形來看看出什麼問題
        - Throughput Over Time -> 是否穩定持平，是否出現吞吐驟降
        - Active Threads -> 系統是否能有效支撐更多併發
        - Response Time vs Threads -> 測試系統在不同併發數下的 Throughput 與延遲變化曲線
    - 若 Throughput 過低，
        - 有可能是腳本設計不良，Thread 產稱的速路太低、Ramp-up 太低。
        - 後端硬體資源可能不足。
        - 服務效能可能有瓶頸。
        - DB 或 其他依賴服務可能成為瓶頸。
        - 同步處理太多，如 鎖、同步 I/O 等。
        - 網路品質不佳或限流導致。
    - 舉幾個範例
        - Throughput 低 + Avg 高 + 無 Error
            - 系統忙不過來，但沒有錯誤。可能是 DB 查詢慢、同步邏輯造成處理速度慢
        - Throughput 高 + Error Rate 高
            - 系統表面處理很多請求，但其中大量回錯，常見於超過最大連線數、隊列滿等情況（可配合 Active Threads Over Time 分析）
        - 併發數提升，但 Throughput 不變
            - 系統已經達到極限，增加 Thread 數無法再提升處理速率，需進行瓶頸分析（如 thread dump、DB profiling）

## 分析建議流程

1. **先看 Error % 是否 > 0** → 確認系統穩定性
2. **檢查 Average / Max / 99% 回應時間** → 找出潛在瓶頸
3. **查看 APDEX 分數** → 評估使用者實際體感
4. **分析 Throughput** → 評估系統併發能力與容量極限
5. **針對異常項目比對 JMeter 測試場景與 log 訊息**

通常一個實際的專案，在規劃前期產出的 SOW(Statement of work) 工作說明書中，就會對一些數值訂好驗收標準，最常見的包撼

- 響應時間指標 (Response Time)
- 吞吐量指標 (Throughput)
- 容量規劃指標
- 可用性指標 (Availability)
- 錯誤率指標 (Error Rate)

---

## 常見問題與可能原因

| 現象              | 可能原因            | 解法建議                            |
| --------------- | --------------- | ------------------------------- |
| Error 500 / 404 | 系統錯誤或路徑錯誤       | 檢查 API、參數與驗證邏輯                  |
| 回應時間高 / 波動大     | DB 查詢慢、GC、鎖、轉換慢 | Profiling + DB trace + async 設計 |
| Max 時間遠高於 Avg   | 偶發尖峰、記憶體不足      | 排程分流、排除 GC、調整 JVM 設定            |
| Throughput 偏低   | 單線程效能低或限流       | 增加 worker / 多執行緒 / 承載優化         |
| APDEX < 0.7     | 體感延遲過高          | 優化關鍵路徑、資料快取、改背景處理               |

---

掌握以上欄位意義與分析流程，或許我們就可以從 JMeter 報表中找出效能瓶頸、驗證優化成效，提升系統可用性與使用者體驗。

老實說這個分析好像很多體感跟通靈的成分。

總歸的來需，還是需要大量的累積經驗才有辦法在不同需求的狀況下，好好閱讀這張報表，並得出合宜的判斷。

ˊ_>ˋ
---

###### Tags：｀JMeter｀, ｀Performance Testing｀, ｀Report Analysis｀, ｀uncategorized｀

