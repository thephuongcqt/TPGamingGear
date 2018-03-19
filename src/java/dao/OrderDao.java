/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entities.TblOrder;

/**
 *
 * @author PhuongNT
 */
public class OrderDao extends BaseDao<TblOrder, Long>{
    private OrderDao() {
    }

    private static OrderDao instance;
    private final static Object LOCK = new Object();

    public static OrderDao getInstance() {
        synchronized (LOCK) {
            if (instance == null) {
                instance = new OrderDao();
            }
        }
        return instance;
    }
}
