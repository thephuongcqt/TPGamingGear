/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package constant;

import jaxb.Name;

/**
 *
 * @author PhuongNT
 */
public class CategoryEnum {
    public static String getRealCategoryName(String value){
        value = value.trim();
        if (value.equalsIgnoreCase("Gaming Keyboards")){
            return Name.BÀN_PHÍM_CHƠI_GAME.value();
        } else if (value.equalsIgnoreCase("Gaming Mice") ){
            return Name.CHUỘT_CHƠI_GAME.value();
        } else if (value.equalsIgnoreCase("Gaming Headsets") ){
            return Name.TAI_NGHE_CHƠI_GAME.value();
        } else if (value.equalsIgnoreCase("Gaming Mouse Pads")){
            return Name.BÀN_DI_CHUỘT_CHƠI_GAME.value();
        } else if (value.equalsIgnoreCase("Gaming Chair/Desks") ){
            return Name.GHẾ_CHƠI_GAME.value();
        } else if (value.equalsIgnoreCase("Gaming controllers")){
            return Name.TAY_CẦM_CHƠI_GAME.value();
        } else if (value.equalsIgnoreCase("Phụ kiện Gaming Gear")){
            return Name.PHỤ_KIỆN_GEAR_GAMING.value();
        } else if(value.equalsIgnoreCase("Máy Game Console")){
            return Name.MÁY_GAME_CONSOLE.value();
        }
        return value;
    }
}
