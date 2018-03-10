/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

/**
 *
 * @author PhuongNT
 */
public class XMLUtils {
    public static String unicodeEscaped(char ch){
        if(ch < 0x10){
            return "\\u000" + Integer.toHexString(ch);
        } else if (ch < 0x100){
            return "\\u00" + Integer.toHexString(ch);
        } else if (ch < 0x1000){
            return "\\u0" + Integer.toHexString(ch);
        }
        return "\\u" + Integer.toHexString(ch);
    }
    public static String unicodeEscaped(String str){
        String returnValue = "";
        for (int i = 0; i < str.length(); i++) {
            returnValue += unicodeEscaped(str.charAt(i));
        }
        return returnValue;
    }
}
