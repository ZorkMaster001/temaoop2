param([switch]$Test)
$ErrorActionPreference = 'Stop'
Set-Location -LiteralPath $PSScriptRoot
$jdk = Join-Path $PSScriptRoot 'tools/jdk'
$compiler = Join-Path $jdk 'bin/javac.exe'
$java = Join-Path $jdk 'bin/java.exe'
if (!(Test-Path -LiteralPath $compiler)) {
    if ($env:JAVA_HOME -and (Test-Path -LiteralPath (Join-Path $env:JAVA_HOME 'bin/javac.exe'))) {
        $compiler = Join-Path $env:JAVA_HOME 'bin/javac.exe'
        $java = Join-Path $env:JAVA_HOME 'bin/java.exe'
    } elseif (Get-Command javac -ErrorAction SilentlyContinue) {
        $compiler = (Get-Command javac).Source
        $java = Join-Path (Split-Path -Parent $compiler) 'java.exe'
    } else {
        throw 'Configureaza un JDK 17+ in JAVA_HOME sau tools/jdk. Pentru IntelliJ vezi INTELLIJ.md.'
    }
}
New-Item -ItemType Directory -Force -Path 'build/classes' | Out-Null
$sources = @(Get-ChildItem -LiteralPath 'src' -Filter '*.java' -Recurse | ForEach-Object FullName)
if ($Test) { $sources += @(Get-ChildItem -LiteralPath 'tests' -Filter '*.java' -Recurse | ForEach-Object FullName) }
& $compiler -encoding UTF-8 -d 'build/classes' -cp 'lib/*' @sources
if ($LASTEXITCODE -ne 0) { throw 'Compilarea a esuat.' }
if ($Test) {
    & $java -cp 'build/classes;lib/*' ro.umfst.biblioteca.IntegrationTest
    if ($LASTEXITCODE -ne 0) { throw 'Testele au esuat.' }
    & $java -cp 'build/classes;lib/*' MainRunTest
    if ($LASTEXITCODE -ne 0) { throw 'Testul pornirii Main a esuat.' }
} else {
    & $java -cp 'build/classes;lib/*' Main
    if ($LASTEXITCODE -ne 0) { throw 'Aplicatia s-a inchis cu eroare.' }
}