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
git clone <your-repository-url>
cd jemter-test

# 編譯專案
mvn clean compile

# 運行應用程式
mvn spring-boot:run
```

應用程式將在 `http://localhost:8080` 啟動

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

## JMeter 學習實戰指南

### 建立第一個測試計畫

1. **啟動 JMeter**
   ```bash
   # Windows
   jmeter.bat
   
   # Mac/Linux
   ./jmeter.sh
   ```

2. **建立基礎測試**
   - 新增執行緒群組 (Thread Group)
   - 設定使用者數量：10
   - 設定 Ramp-up 時間：10 秒
   - 設定迴圈次數：10

3. **新增 HTTP 請求**
   - 伺服器名稱：localhost
   - 連接埠：8080
   - 路徑：/api/test/fast

4. **新增監聽器**
   - View Results Tree（查看結果樹）
   - Summary Report（摘要報表）
   - Graph Results（圖形結果）

### 進階測試場景設計

#### 場景 1：基準性能測試
```
目標：建立系統基準性能指標
端點：/api/test/fast
設定：
- 使用者數：50
- Ramp-up：30 秒
- 執行時間：5 分鐘
預期學習：理解基本的響應時間和吞吐量概念
```

#### 場景 2：負載測試
```
目標：測試系統在預期負載下的表現
端點：/api/test/cpu-intensive?limit=5000
設定：
- 使用者數：100
- Ramp-up：60 秒
- 執行時間：10 分鐘
預期學習：觀察 CPU 負載對響應時間的影響
```

#### 場景 3：壓力測試
```
目標：找出系統的極限負載點
端點：混合多個端點
設定：
- 使用者數：200 → 500 → 1000（階段性增加）
- Ramp-up：每階段 120 秒
- 執行時間：每階段 15 分鐘
預期學習：識別系統瓶頸和崩潰點
```

#### 場景 4：錯誤處理測試
```
目標：測試系統的錯誤處理能力
端點：/api/test/error-prone
設定：
- 使用者數：100
- Ramp-up：30 秒
- 執行時間：10 分鐘
預期學習：理解錯誤率對系統的影響
```

## 報表產出實戰

### 命令列模式執行（推薦）

```bash
# 執行測試並產出 .jtl 檔案
jmeter -n -t your-test-plan.jmx -l results.jtl

# 生成 HTML 報表
jmeter -g results.jtl -o html-report/

# 同時執行測試和生成報表
jmeter -n -t your-test-plan.jmx -l results.jtl -e -o html-report/
```

### GUI 模式報表匯出

1. **即時監控**
   - 在 GUI 中執行測試
   - 觀察 Summary Report 的即時數據
   - 使用 View Results Tree 檢查個別請求

2. **匯出測試結果**
   - 執行完成後，在監聽器中點擊「Save Table Data」
   - 選擇 CSV 或其他格式匯出

### 專案內建測試腳本

```
backups/
├── HTTP 要求-000001.jmx    # 基礎測試腳本
├── HTTP 要求-000002.jmx    # 進階測試腳本
├── jmeter-test-000011.jmx  # 綜合測試腳本
└── ...
```

使用方式：
```bash
# 直接執行內建腳本
jmeter -n -t backups/jmeter-test-000011.jmx -l my-results.jtl -e -o my-report/
```

## 報表解讀技巧

### HTML 報表關鍵指標解讀

#### 1. APDEX (Application Performance Index)
```
- 優秀 (Excellent): > 0.94
- 良好 (Good): 0.85 - 0.94  
- 一般 (Fair): 0.70 - 0.84
- 差 (Poor): < 0.70
```

#### 2. 統計數據 (Statistics)
- **Sample**: 總請求數
- **Average**: 平均響應時間
- **Min/Max**: 最小/最大響應時間
- **Std. Dev**: 標準差（數值越小表示響應時間越穩定）
- **Error %**: 錯誤百分比
- **Throughput**: 吞吐量（每秒處理的請求數）

#### 3. 響應時間分布 (Response Times Over Time)
```
觀察重點：
- 是否有明顯的響應時間增長趨勢
- 是否有突然的響應時間峰值
- 響應時間是否穩定在可接受範圍內
```

#### 4. 吞吐量分析 (Throughput Over Time)
```
觀察重點：
- 吞吐量是否穩定
- 是否有明顯的下降趨勢
- 峰值吞吐量是否達到預期
```

### 常見問題診斷

#### 響應時間過長
```
可能原因：
1. CPU 使用率過高 → 檢查 cpu-intensive 端點的測試結果
2. 記憶體不足 → 檢查 memory-intensive 端點的測試結果
3. 網路延遲 → 檢查網路環境
4. 資料庫瓶頸 → 檢查資料庫連線和查詢效率
```

#### 錯誤率過高
```
可能原因：
1. 系統過載 → 減少並發用戶數重新測試
2. 服務異常 → 檢查應用程式日誌
3. 網路問題 → 檢查網路連線穩定性
```

#### 吞吐量下降
```
可能原因：
1. 資源耗盡 → 監控 CPU、記憶體使用率
2. 執行緒阻塞 → 檢查應用程式執行緒狀態
3. I/O 瓶頸 → 檢查磁碟和網路 I/O
```

### 學習檢查清單

**基礎報表解讀能力**
- [ ] 能夠理解 Summary Report 中的所有指標
- [ ] 能夠識別正常和異常的響應時間分布
- [ ] 能夠解讀錯誤率的含義和影響

**進階分析能力**  
- [ ] 能夠從響應時間趨勢圖識別性能問題
- [ ] 能夠分析吞吐量變化的原因
- [ ] 能夠根據報表提出系統優化建議

**實戰應用能力**
- [ ] 能夠設計合適的測試場景
- [ ] 能夠產出專業的測試報表
- [ ] 能夠向團隊解釋測試結果和建議

## 開發指南

### 新增測試端點

1. 在 `JMeterTestController.java` 中新增方法
2. 添加適當的 Swagger 註解
3. 更新此 README 文檔
4. 建立對應的 JMeter 測試腳本

### 專案結構

```
src/
├── main/
│   ├── java/
│   │   └── com/kai/jemter_test/
│   │       ├── JemterTestApplication.java
│   │       └── controller/
│   │           └── JMeterTestController.java
│   └── resources/
│       └── application.properties
├── test/
└── ...
```

## 故障排除

### 常見問題

1. **應用程式無法啟動**
   - 檢查 Java 版本 (需要 17+)
   - 確認 8080 端口未被占用

2. **JMeter 測試失敗**
   - 確認應用程式正在運行
   - 檢查防火牆設定
   - 驗證測試腳本中的 URL

3. **記憶體不足錯誤**
   - 調整 JVM 記憶體參數: `-Xmx2g`
   - 降低 memory-intensive 測試的 size 參數

## 貢獻指南

1. Fork 本專案
2. 建立功能分支 (`git checkout -b feature/amazing-feature`)
3. 提交變更 (`git commit -m 'Add amazing feature'`)
4. 推送到分支 (`git push origin feature/amazing-feature`)
5. 開啟 Pull Request

## 授權

本專案採用 MIT 授權 - 詳見 [LICENSE](LICENSE) 檔案

## 聯絡資訊

- **專案維護者**: Kai
- **Email**: [您的信箱]
- **專案網址**: [您的專案網址]

---

## 附錄

### JMeter 測試報告範例

完成測試後，您將獲得包含以下指標的詳細報告：

- **吞吐量** (Transactions per Second)
- **響應時間** (Response Time)
- **錯誤率** (Error Rate)
- **資源使用率** (CPU/Memory Usage)

### 推薦工具

- **JMeter** - 性能測試
- **Grafana** - 監控視覺化
- **Prometheus** - 指標收集
- **Postman** - API 測試

---

**祝您測試愉快！**
