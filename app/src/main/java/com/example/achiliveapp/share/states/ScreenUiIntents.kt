package com.example.achiliveapp.share.states

open class ScreenUiIntents() {


    class BackIntent<T>(val data: T): ScreenUiIntents()

    class Calm(): ScreenUiIntents()

}