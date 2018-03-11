/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author PhuongNT
 */
public interface IGenericDao<T, PK extends Serializable>{
    T create(T t);
    T findByID(PK id);
    T update(T t);
    boolean delete(T t);
    List<T> getAll(String namedQuery);
}
