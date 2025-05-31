@echo off
chcp 65001 > nul

REM 設定環境變數
set JMETER_BIN=C:\Users\miste\Desktop\work-software\else\apache-jmeter-5.6.3\apache-jmeter-5.6.3\bin\
set SCRIPT_DIR=%JMETER_BIN%\scripts\jmeter-test
set TEST_PLAN=%SCRIPT_DIR%\jmeter-test.jmx
set RESULT_FILE=%SCRIPT_DIR%\test-result.jtl
set REPORT_DIR=%SCRIPT_DIR%\report

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