# ChunkScout – Fabric 1.21.11

Ez a projekt GitHub Actions-szel buildelhető: a GitHub gépe automatikusan felrakja a Java 21-et és a Gradle 9.2.1-et, majd elkészíti a `build/libs/*.jar` fájlt.

## Használat
1. Hozz létre egy GitHub repositoryt.
2. Töltsd fel ennek a mappának a teljes tartalmát a repository gyökerébe.
3. GitHubon nyisd meg: **Actions → Build ChunkScout → Run workflow**.
4. Ha a build sikeres, az Actions futás oldalán az **Artifacts** alatt letölthető a JAR.

## Fontos
Ez a csomag jelenleg egy **buildelhető kliensoldali alapverzió**: Right Ctrl GUI, kapcsolók és 0–1000 közötti Anchor radius beállítás már benne van. A teljes ESP/freecam/suspicious-chunk megvalósítás még további fejlesztést igényel; ezt nem állítom késznek csak azért, hogy a build zöld legyen.
