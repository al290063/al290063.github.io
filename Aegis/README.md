# Aegis

Plugin de ejemplo para Minecraft 1.20.1 que integra un sistema completo de economía, trabajos, misiones y una tienda interactiva. Incluye comandos `/eco`, `/jobs`, `/missions` y `/shop`, todos con menús gráficos para mayor facilidad de uso.

La economía otorga un balance inicial configurable para cada jugador. El menú de la tienda se genera a partir de `config.yml` y permite comprar objetos haciendo clic sobre ellos.

El sistema de misiones cuenta con 1000 misiones predeterminadas que los jugadores pueden reclamar con `/missions claim <id>`.

`/jobs` permite listar trabajos disponibles y unirse a uno.

## Compilación

Ejecute `mvn package` dentro de la carpeta para generar el jar en `target/`.
