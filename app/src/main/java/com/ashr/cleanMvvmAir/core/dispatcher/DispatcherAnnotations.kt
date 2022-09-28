package com.ashr.cleanMvvmAir.core.dispatcher

import javax.inject.Qualifier

class DispatcherAnnotations {

    @Retention(AnnotationRetention.BINARY)
    @Qualifier
    annotation class Io

    @Retention(AnnotationRetention.BINARY)
    annotation class Main

    @Retention(AnnotationRetention.BINARY)
    annotation class Default
}