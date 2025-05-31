# Test script for /data endpoint

Write-Host "Testing the /data endpoint..."

$url = "http://localhost:8080/api/test/data"
$headers = @{"Content-Type" = "application/json"}
$body = '{"additionalProp1": {}, "additionalProp2": {}, "additionalProp3": {}}'

try {
    Write-Host "Sending POST request to $url"
    Write-Host "Request body: $body"
    
    $response = Invoke-RestMethod -Uri $url -Method POST -Body $body -ContentType "application/json"
    
    Write-Host "SUCCESS! Response received:"
    Write-Host ($response | ConvertTo-Json -Depth 5)
} catch {
    Write-Host "ERROR occurred:"
    Write-Host "Status Code: $($_.Exception.Response.StatusCode.value__)"
    Write-Host "Status Description: $($_.Exception.Response.StatusDescription)"
    Write-Host "Error Message: $($_.Exception.Message)"
    
    if ($_.Exception.Response) {
        $stream = $_.Exception.Response.GetResponseStream()
        $reader = New-Object System.IO.StreamReader($stream)
        $responseBody = $reader.ReadToEnd()
        Write-Host "Response Body: $responseBody"
    }
}