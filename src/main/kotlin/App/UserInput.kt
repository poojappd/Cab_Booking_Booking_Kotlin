package App

import java.util.*

object UserInput {

    fun getIntInput(): Int {

        var testValue = -1
        var noError = false
        do{
            val input = Scanner(System.`in`)
        try {
                testValue = input.nextInt()
                noError = true
        } catch (ie: InputMismatchException) {
                println("Please enter a valid value!")
            }
        }while (!noError)
        return testValue
    }

    fun getMenuChoiceInput(choiceLimit: Int, stopValue:Int = 99999): Int {
        var chosenMenuOption: Int
        do {
            chosenMenuOption = getIntInput()

            if(chosenMenuOption==stopValue) return stopValue

            val choiceWithinLimit = chosenMenuOption < 1 || chosenMenuOption > choiceLimit
            if (choiceWithinLimit) {
                println("Please enter the right option")
            }
        } while (choiceWithinLimit)
        return chosenMenuOption
    }

    fun getStringInput(): String {
        val input = Scanner(System.`in`)
        val value=input.nextLine()
        return value
    }


    fun getInputFromRange(lowerLimit: Int, upperLimit: Int): Int {
        var value :Int

        do {
            value = getIntInput()
            val isWithinRange = value in lowerLimit..upperLimit
            if (!isWithinRange) {
                println("Please enter a value within the range ($lowerLimit - $upperLimit)")
            }
        } while (!isWithinRange)
        return value
    }
}