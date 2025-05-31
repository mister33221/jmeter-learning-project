# PowerShell script to test the /data endpoint with proper Content-Type header
# This fixes the Content-Type issue

$baseUrl = "http://localhost:8080"
$endpoint = "/api/test/data"
$url = $baseUrl + $endpoint

# Test data - the same JSON that was causing issues
$testData = @{
    "additionalProp1" = @{}
    "additionalProp2" = @{}
    "additionalProp3" = @{}
}

Write-Host "Testing POST /api/test/data endpoint..." -ForegroundColor Green
Write-Host "URL: $url" -ForegroundColor Cyan
Write-Host "Test data: $($testData | ConvertTo-Json)" -ForegroundColor Yellow

try {
    # Convert to JSON and send with proper Content-Type
    $jsonBody = $testData | ConvertTo-Json -Depth 10
    
    Write-Host "`nSending request with proper Content-Type header..." -ForegroundColor Magenta
    
    $response = Invoke-RestMethod -Uri $url -Method POST -Body $jsonBody -ContentType "application/json" -ErrorAction Stop
    
    Write-Host "`n✅ SUCCESS!" -ForegroundColor Green
    Write-Host "Response:" -ForegroundColor Cyan
    $response | ConvertTo-Json -Depth 10 | Write-Host
    
} catch {
    Write-Host "`n❌ ERROR!" -ForegroundColor Red
    Write-Host "Status Code: $($_.Exception.Response.StatusCode.value__)" -ForegroundColor Red
    Write-Host "Status Description: $($_.Exception.Response.StatusDescription)" -ForegroundColor Red
    Write-Host "Error Message: $($_.Exception.Message)" -ForegroundColor Red
    
    if ($_.Exception.Response) {
        $responseStream = $_.Exception.Response.GetResponseStream()
        $reader = New-Object System.IO.StreamReader($responseStream)
        $responseBody = $reader.ReadToEnd()
        Write-Host "Response Body: $responseBody" -ForegroundColor Yellow
    }
}

Write-Host "`n=== Test completed ===" -ForegroundColor Green