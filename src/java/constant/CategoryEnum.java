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
        if (value.equalsIgnoreCase("Gaming Keyboards") || value.equalsIgnoreCase("Bàn phím chơi game")){
            return Name.BÀN_PHÍM_CHƠI_GAME.value();
        } else if (value.equalsIgnoreCase("Gaming Mice") || value.equalsIgnoreCase("Chuột chơi game")){
            return Name.CHUỘT_CHƠI_GAME.value();
        } else if (value.equalsIgnoreCase("Gaming Headsets") || value.equalsIgnoreCase("Tai nghe chơi game")){
            return Name.TAI_NGHE_CHƠI_GAME.value();
        } else if (value.equalsIgnoreCase("Gaming Mouse Pads") || value.equalsIgnoreCase("Bàn di chuột chơi game")){
            return Name.BÀN_DI_CHUỘT_CHƠI_GAME.value();
        } else if (value.equalsIgnoreCase("Gaming Chair/Desks") || value.equalsIgnoreCase("Bàn - Ghế chơi game")){
            return Name.GHẾ_CHƠI_GAME.value();
        } else if (value.equalsIgnoreCase("Gaming controllers") || value.equalsIgnoreCase("Tay cầm chơi game")){
            return Name.TAY_CẦM_CHƠI_GAME.value();
        } else if (value.equalsIgnoreCase("Phụ kiện Gaming Gear") || value.equalsIgnoreCase("Phụ kiện gear gaming")){
            return Name.PHỤ_KIỆN_GEAR_GAMING.value();
        } else if(value.equalsIgnoreCase("Máy Game Console")){
            return Name.MÁY_GAME_CONSOLE.value();
        } else if(value.equalsIgnoreCase("Loa Gaming")){
            return Name.LOA_GAMING.value();
        }
        return null;
    }
}
