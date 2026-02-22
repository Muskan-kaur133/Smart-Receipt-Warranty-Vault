 //Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
}
implementation("androidx.work:work-runtime:2.9.0")

fun implementation(string: String) {}
