plugins {
    id("com.android.application") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
    alias(libs.plugins.kotlin.compose) apply false  // CAMBIA: da 2.0.21 a 1.9.22
    // RIMUOVI questa riga: id("org.jetbrains.kotlin.plugin.compose") version "2.0.21" apply false
}