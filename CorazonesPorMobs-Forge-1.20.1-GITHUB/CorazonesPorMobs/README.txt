CORAZONES POR MOBS — Minecraft Forge 1.20.1

Qué hace:
- Al derrotar a un mob, suma a tu vida máxima la vida máxima de ese mob.
- Minecraft mide la vida en puntos: 2 puntos equivalen a 1 corazón.
  Ejemplo: una gallina con 4 puntos de vida (2 corazones) te añade 4 puntos de vida máxima (2 corazones).
- Funciona con entidades LivingEntity que tengan el atributo de vida máxima, incluidos mobs de otros mods.
- Guarda la bonificación en los datos persistentes del jugador y la reaplica al entrar al mundo y reaparecer.
- También recupera salud actual por la cantidad ganada, hasta el nuevo máximo.

Versión objetivo: Minecraft 1.20.1 + Forge 47.x; configurado para Forge 47.4.10.

IMPORTANTE:
Este ZIP contiene el CÓDIGO FUENTE del mod, no un .jar compilado. No se pudo compilar ni probar en este entorno porque no está instalado Gradle ni hay acceso a Internet para descargar ForgeGradle y sus dependencias. Para generar el .jar se necesita JDK 17 y Gradle/ForgeGradle; después se ejecuta `gradle build` desde la carpeta del proyecto y el .jar aparecerá en build/libs.

Nota técnica: esta primera versión fija la base de vida máxima en 20 + bonificación; mods que también cambien la vida máxima base del jugador podrían entrar en conflicto. Haz copia de seguridad de tu mundo antes de probarlo.
