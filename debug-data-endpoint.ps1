# 簡單的調試腳本來測試 /data 端點
Write-Host "=== 測試 /data 端點 ==="

# 測試基本 JSON
Write-Host "`n1. 測試簡單 JSON..."
try {
    $response = Invoke-RestMethod -Uri "http://localhost:8080/api/test/data" -Method POST -Body '{"test": "value"}' -ContentType "application/json" -ErrorAction Stop
    Write-Host "成功: $($response | ConvertTo-Json)"
} catch {
    Write-Host "錯誤: $($_.Exception.Message)"
    if ($_.ErrorDetails.Message) {
        Write-Host "詳細錯誤: $($_.ErrorDetails.Message)"
    }
}

# 測試您的 JSON
Write-Host "`n2. 測試您的 JSON (空對象)..."
try {
    $response = Invoke-RestMethod -Uri "http://localhost:8080/api/test/data" -Method POST -Body '{"additionalProp1": {}, "additionalProp2": {}, "additionalProp3": {}}' -ContentType "application/json" -ErrorAction Stop
    Write-Host "成功: $($response | ConvertTo-Json)"
} catch {
    Write-Host "錯誤: $($_.Exception.Message)"
    if ($_.ErrorDetails.Message) {
        Write-Host "詳細錯誤: $($_.ErrorDetails.Message)"
    }
}

Write-Host "`n=== 測試完成 ==="