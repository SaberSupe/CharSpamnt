package Saber.Utils;

public class Shortener {

    //Takes a message and a max character spam length then return message with all character spam shortened to max length
    public static String ShortenCharSpam (String message, int max){

        //Initalize variables
        char[] letters = message.toLowerCase().toCharArray();
        int count = 1;
        char last = letters[letters.length-1];

        //Loop through letters backwards to avoid mis aligning letters and message index with removals
        for (int i = letters.length-2; i>=0; i--){

            //Check if character is repeated and count it
            if (letters[i] == last){
                count++;
            }
            //Reset count on new character
            else{
                count = 1;
            }

            //If the count has exceeded the max, remove one of the spammed characters
            if (count > max) {
                message = message.substring(0, i) + message.substring(i + 1);
            }
            last = letters[i];
        }

        return message;
    }
}
