package com.example.view
import javafx.scene.control.TextField
import java.io.File
import com.example.core.AffineCipher
import javafx.beans.binding.Bindings
import javafx.stage.FileChooser
import tornadofx.*
import java.io.FileNotFoundException



class MainView : View() {
    override val root = borderpane()

    private val ef = arrayOf(FileChooser.ExtensionFilter("Любой файл", "*"))


    private lateinit var filePath: TextField
    private  lateinit var  decodeFilePath: TextField
    private  var encodeBtn = button("Пуск"){
        action {
                AffineCipher.encodeFile(filePath.text)
                decodeFilePath.text = filePath.text
                filePath.text=""
        }
    }
    private  var decodeBtn = button("Пуск"){ action {AffineCipher.decodeFile(decodeFilePath.text); }}
    init {
        with(root) {
            title = "Аффинный шифр"
           center= form {
                    fieldset("Выберите файл") {
                        hbox {
                            field("Путь до файла") {
                                hbox {
                                    filePath = textfield()
                                    encodeBtn.disableProperty().bind(
                                            Bindings.isEmpty(filePath.textProperty())
                                    )
                                    button("Добавить") {
                                        action {
                                            val fn: List<File> = chooseFile("Выберите файл",ef, mode= FileChooserMode.Single)
                                            if (fn.isNotEmpty()) {
                                                filePath.text = "${fn.first()}"
                                            }
                                        }
                                    }


                                }
                            }

                        }

                        field("Зашифровать") {
                                this+=encodeBtn
                        }
                        field("Путь до файла") {
                            hbox {
                                decodeFilePath = textfield()
                                decodeBtn. disableProperty().bind(
                                        Bindings.isEmpty(decodeFilePath.textProperty())
                                )
                                button("Добавить") {
                                    action {
                                        val fn: List<File> = chooseFile("Выберите файл",ef, mode= FileChooserMode.Single)
                                        if (fn.isNotEmpty()) {
                                            decodeFilePath.text = "${fn.first()}"
                                        }
                                    }
                                }
                            }
                        }
                        field("Разшифровать") {
                            this+=decodeBtn
                        }
                    }
                }



        }
    }
}
