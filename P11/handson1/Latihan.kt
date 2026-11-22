// Hands-on 1: Inline Function & Reified Generics
//
// Konteks: JVM menerapkan TYPE ERASURE pada generics — informasi tipe generik
// (mis. List<String> vs List<Int>) DIHAPUS saat dikompilasi menjadi bytecode
// .class, sehingga pada saat runtime JVM hanya melihat "List" biasa. Karena
// itu, fungsi generik biasa TIDAK BISA melakukan pengecekan `value is T`.
//
// Kotlin punya solusi: kombinasi `inline` + `reified`. Fungsi inline "disalin"
// (di-inline) langsung ke tempat pemanggilannya oleh compiler backend Kotlin
// SEBELUM menjadi bytecode, sehingga tipe T yang konkret (mis. String) ikut
// tersalin dan tidak pernah "dihapus" oleh JVM.
//
// Tugas: Lengkapi fungsi isType() di bawah agar bisa mengecek tipe value pada
// runtime.

// TODO 1: Tambahkan modifier `inline` pada fungsi ini
// TODO 2: Ubah <T> menjadi <reified T> agar T bisa dipakai di `is` check
inline fun <reified T> isType(value: Any): Boolean {
    // TODO 3: Ganti baris di bawah dengan: return value is T
    return value is T
}

fun main() {
    println(isType<String>("Hello"))   // harus mencetak: true
    println(isType<Int>("Hello"))      // harus mencetak: false
    println(isType<String>(123))       // harus mencetak: false

    // Pertanyaan diskusi (jawab di komentar):
    // Kenapa `fun <T> isType(value: Any): Boolean = value is T` TIDAK BISA
    // dikompilasi tanpa `inline` + `reified`? Jelaskan hubungannya dengan
    // type erasure pada JVM bytecode.

    // Jawaban:
    // Karena type erasure, tipe T dihapus saat dikompilasi ke bytecode, jadi
    // pada runtime JVM tidak tahu T itu apa. Akibatnya `value is T` tidak bisa
    // dicek dan compiler menolaknya. Dengan `inline`, isi fungsi disalin ke
    // tempat pemanggilan sehingga tipe konkretnya (misal String) ikut tersalin
    // ke bytecode. `reified` memberi izin memakai T sebagai tipe nyata di sana,
    // jadi `value is T` berubah jadi `value is String` dan bisa dicek.
}