param([switch]$Test)
$ErrorActionPreference = 'Stop'
Set-Location -LiteralPath $PSScriptRoot
$jdk = Join-Path $PSScriptRoot 'tools/jdk'
$compiler = Join-Path $jdk 'bin/javac.exe'
$java = Join-Path $jdk 'bin/java.exe'
if (!(Test-Path -LiteralPath $compiler)) { throw 'Lipseste JDK portabil din tools/jdk.' }
New-Item -ItemType Directory -Force -Path 'build/classes' | Out-Null
$sources = @(Get-ChildItem -LiteralPath 'src/main/java' -Filter '*.java' -Recurse | ForEach-Object FullName)
if ($Test) { $sources += @(Get-ChildItem -LiteralPath 'src/test/java' -Filter '*.java' -Recurse | ForEach-Object FullName) }
& $compiler -encoding UTF-8 -d 'build/classes' -cp 'lib/*' @sources
if ($LASTEXITCODE -ne 0) { throw 'Compilarea a esuat.' }
if ($Test) {
    & $java -cp 'build/classes;lib/*' ro.umfst.biblioteca.IntegrationTest
    if ($LASTEXITCODE -ne 0) { throw 'Testele au esuat.' }
} else {
    & $java -cp 'build/classes;lib/*' ro.umfst.biblioteca.App
    if ($LASTEXITCODE -ne 0) { throw 'Aplicatia s-a inchis cu eroare.' }
}